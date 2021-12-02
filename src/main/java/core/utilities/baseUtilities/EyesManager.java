package core.utilities.baseUtilities;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EyesManager {

    private Eyes eyes;
    private String appName;
    public WebDriver driver;
    String APPLITOOLS_API_KEY = "Xw76rp2cir7tXvGCHVRgNjofgU8xc111FUryctEa74iVw110";

    public void EyesManager  (WebDriver driver, String appName){
        this.driver = driver;
        this.appName = appName;
        eyes = new Eyes();
        eyes.setApiKey(APPLITOOLS_API_KEY);
    }

    public void setBatchName(String batchName){
        eyes.setBatch(new BatchInfo(batchName));
    }

    public void validateWindow(){
        eyes.open(driver, appName, Thread.currentThread().getStackTrace()[2].getMethodName());
        eyes.setForceFullPageScreenshot(true);
        eyes.checkWindow();
        eyes.close();
    }

    public void validateElement( By locator){
        eyes.open(driver, appName, Thread.currentThread().getStackTrace()[2].getMethodName());
        eyes.setForceFullPageScreenshot(true);
        eyes.checkElement(locator);
        eyes.close();
    }

    public void validateFrame( String locator){
        eyes.open(driver, appName, Thread.currentThread().getStackTrace()[2].getMethodName());
        eyes.setForceFullPageScreenshot(true);
        eyes.checkFrame(locator);
        eyes.close();
    }

    public void abort(){
        eyes.abortIfNotClosed();
    }

    public void closeEyes(){
        eyes.closeAsync();
        driver.quit();
        eyes.abortIfNotClosed();
    }

    public Eyes getEyes(){
        return eyes;
    }

}
