import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

public class ExtentTestListener implements TestWatcher {
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        ExecutionContext.CURRENT_EXTENT_TEST.get().log(Status.SKIP, MarkupHelper.createLabel(context.getDisplayName() + " SKIPPED", ExtentColor.PURPLE));
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        ExecutionContext.CURRENT_EXTENT_TEST.get().log(Status.PASS, MarkupHelper.createLabel(context.getDisplayName().toUpperCase() + " PASS", ExtentColor.GREEN));
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        ExecutionContext.CURRENT_EXTENT_TEST.get().log(Status.SKIP, MarkupHelper.createLabel(context.getDisplayName() + " ABORTED", ExtentColor.YELLOW));
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        try {
            ExecutionContext.CURRENT_EXTENT_TEST.get().log(Status.FAIL, cause);
            ExecutionContext.CURRENT_EXTENT_TEST.get().log(Status.FAIL, MarkupHelper.createLabel(context.getDisplayName().toUpperCase() + " FAIL", ExtentColor.RED));

            captureScreenshotOnFailure();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void captureScreenshotOnFailure() {
        try {
            var driver = ExecutionContext.CURRENT_DRIVER.get();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            String fileName = "screenshot-" + System.currentTimeMillis() + ".png";
            File screenshotFile = new File(System.getProperty("user.dir") + "/test-output/" + fileName);
            ImageIO.write(screenshot.getImage(), "PNG", screenshotFile);
            InputStream in = new FileInputStream(screenshotFile.getAbsolutePath());
            byte[] imageBytes = IOUtils.toByteArray(in);
            String base64 = Base64.getEncoder().encodeToString(imageBytes);

            ExecutionContext.CURRENT_EXTENT_TEST.get().addScreenCaptureFromBase64String(base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
