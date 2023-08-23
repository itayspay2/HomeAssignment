import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ExecutionContext {
    public static final ThreadLocal<TestResult> CURRENT_TEST_RESULT = new ThreadLocal<>();
    public static final ThreadLocal<Throwable> CURRENT_TEST_EXCEPTION = new ThreadLocal<>();
    public static final ThreadLocal<ExtentTest> CURRENT_EXTENT_TEST = new ThreadLocal<>();
    public static final ThreadLocal<WebDriver> CURRENT_DRIVER = new ThreadLocal<>();
    public static final ThreadLocal<ExtentSparkReporter> CURRENT_SPARK_REPORTER = new ThreadLocal<>();
    public static final ThreadLocal<ExtentReports> CURRENT_EXTENT_REPORTS = new ThreadLocal<>();
    public static final ThreadLocal<WebDriverWait> CURRENT_WAIT = new ThreadLocal<>();


}
