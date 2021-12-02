package core.utilities.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private int maxTry = 2;
    private boolean retry;

    public boolean retry(ITestResult iTestResult) {

        if(System.getProperty("rerunCount")!=null){
            maxTry = Integer.parseInt(System.getProperty("rerunCount"));
        }
        if (count < maxTry) {                            //Check if maxtry count is reached
            count++;                                     //Increase the maxTry count by 1
            retry = true;                                 //Tells TestNG to re-run the test
        }else{
            retry = false;
        }
        return retry;
    }
}
