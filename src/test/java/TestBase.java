import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


@ExtendWith({TestResultListener.class, ExtentTestListener.class})
public class TestBase {

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();

        ExecutionContext.CURRENT_SPARK_REPORTER.set(new ExtentSparkReporter(System.getProperty("user.dir") +"\\test-output\\testReport.html"));
        ExecutionContext.CURRENT_EXTENT_REPORTS.set(new ExtentReports());

        ExecutionContext.CURRENT_EXTENT_REPORTS.get().attachReporter(ExecutionContext.CURRENT_SPARK_REPORTER.get());
        ExecutionContext.CURRENT_SPARK_REPORTER.get().config().setOfflineMode(true);
        ExecutionContext.CURRENT_SPARK_REPORTER.get().config().setDocumentTitle("Selenium Exercise Report");
        ExecutionContext.CURRENT_SPARK_REPORTER.get().config().setReportName("Test Report");
        ExecutionContext.CURRENT_SPARK_REPORTER.get().config().setTheme(Theme.STANDARD);
        ExecutionContext.CURRENT_SPARK_REPORTER.get().config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        ExecutionContext.CURRENT_SPARK_REPORTER.get().config().setEncoding("UTF-8");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ExecutionContext.CURRENT_DRIVER.set(new ChromeDriver(options));
        ExecutionContext.CURRENT_DRIVER.set( ExecutionContext.CURRENT_DRIVER.get());
        ExecutionContext.CURRENT_DRIVER.get().manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(ExecutionContext.CURRENT_DRIVER.get(), Duration.ofSeconds(10));
        ExecutionContext.CURRENT_WAIT.set(wait);

    }


    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (ExecutionContext.CURRENT_TEST_RESULT.get().equals(TestResult.SUCCESS)) {
            System.out.println("Test " + testInfo.getDisplayName() + " succeeded.");

            ExecutionContext.CURRENT_EXTENT_TEST.get().log(Status.PASS, ExecutionContext.CURRENT_TEST_EXCEPTION.get());
        } else {
            System.out.println("Test " + testInfo.getDisplayName() + " failed with error: " + ExecutionContext.CURRENT_TEST_EXCEPTION.get().getMessage());
            ExecutionContext.CURRENT_EXTENT_TEST.get().log(Status.FAIL, ExecutionContext.CURRENT_TEST_EXCEPTION.get());
        }
    }

    @AfterAll
    public static void afterClassCore(TestInfo testInfo) {
        if (ExecutionContext.CURRENT_DRIVER.get() != null) {
            ExecutionContext.CURRENT_DRIVER.get().quit();
        }

        ExecutionContext.CURRENT_EXTENT_REPORTS.get().flush();
    }
}