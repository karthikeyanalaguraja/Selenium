package core.utilities.listeners;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import core.utilities.baseTest.BaseCommunityTest;
import core.utilities.logger.Log;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.sql.Timestamp;
import java.util.Set;

public class MyTestListener extends BaseCommunityTest implements ITestListener, ISuiteListener {

    Timestamp startTime;

    @Override
    public void onStart(ISuite suite){
        startTime = new Timestamp(System.currentTimeMillis());
        System.out.println(suite.getName()+" - Started: "+startTime);
    }

    @Override
    public void onFinish(ITestContext context) {
        Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
        for (ITestResult temp : failedTests) {
            ITestNGMethod method = temp.getMethod();
            if (context.getFailedTests().getResults(method).size() > 1) {
                failedTests.remove(temp);
            } else {
                if (context.getPassedTests().getResults(method).size() > 0) {
                    failedTests.remove(temp);
                }
            }
        }
    }



    @Override
    public synchronized void onTestStart(ITestResult result) {
        System.out.println(">>> TEST STARTED: "+(result.getMethod().getMethodName())+" <<<");
        extentTest = extent.createTest(getTestMethodName(result), "Description: "+result.getMethod().getDescription());
        String testCategory = result.getMethod().getRealClass().getName();
        extentTest.assignCategory(testCategory.substring(testCategory.lastIndexOf(".")+1));
        test.set(extentTest);
        result.setStatus(ITestResult.STARTED);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println(">>> TEST PASSED: "+ getTestMethodName(result)+" <<<");
        test.get().pass(MarkupHelper.createLabel("Test passed", ExtentColor.GREEN));
        Log.info(getTestMethodName(result)+"Test Passed.");
        result.setStatus(ITestResult.SUCCESS);
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        System.out.println(">>> TEST FAILED: "+ getTestMethodName(result)+" <<<");
        test.get().fail(result.getThrowable());
        test.get().fail(MarkupHelper.createLabel("Test failed", ExtentColor.RED));
        Log.info(getTestMethodName(result)+"Test Failed.");

        result.setStatus(ITestResult.FAILURE);
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println(">>> TEST SKIPPED: "+ getTestMethodName(result)+" <<<");
        test.get().skip(result.getThrowable());
        Log.info(getTestMethodName(result)+"Test Skipped.");
        result.setStatus(ITestResult.SKIP);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("onTestFailedButWithinSuccessPercentage for " + getTestMethodName(result));
    }

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }


}
