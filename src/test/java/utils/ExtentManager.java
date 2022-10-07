package utils;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ExtentManager {
    protected ExtentReports extentReports;
    protected ExtentTest extentTest;

    public void createReport(){
        extentReports = new ExtentReports();

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/report.html");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("AutomationPractice");
        sparkReporter.config().setReportName("Report for US");

        extentReports.attachReporter(sparkReporter);
    }

    public void closeReport(){
        extentReports.flush();
    }

    public void createTestReport(Method method){
        extentTest = extentReports.createTest(getTestName(method));
    }



    public String getTestName(Method method){
        Test testClass = method.getAnnotation(Test.class);
        String testName;

        if(testClass.testName() != null && !testClass.testName().equals("")) {
            testName = testClass.testName();
        }else{
            testName = method.getName();
        }

        return testName;
    }

    public void logInfo(String msg){
        extentTest.info(msg);
    }

    public void logInfo(String msg, WebElement element){
        String str = element.toString();
        String locator = str.substring(str.indexOf("->"), str.length()-1);

        logInfo(msg + " " + locator);
    }

    public void logResult(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            extentTest.fail("TEST FAILED");
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass("TEST PASSED");
        }
    }

}
