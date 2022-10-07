package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ExtentManager;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentManager extentManager;

    @BeforeSuite
    public void startReport(){
        extentManager = new ExtentManager();
        extentManager.createReport();
    }

    @AfterSuite
    public void closeReport(){
        extentManager.closeReport();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpDriver(Method method){
        initializeDriver("chrome");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(ConfigReader.readProperty("url"));

        extentManager.createTestReport(method);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }

    public void initializeDriver(String browser){
        switch (browser){
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "/Users/kuba/Projects/TLA/General/libs/chromedriver");
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "/Users/kuba/Projects/TLA/General/libs/geckodriver");
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Invalid browser name");
        }
    }


}
