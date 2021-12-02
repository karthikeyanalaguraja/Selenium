package pages.Other;

import core.basePages.AllCommunityPages;
import core.basePages.BaseCommunityPage;
import core.utilities.baseUtilities.Browser;
import core.utilities.email.EmailManager;
import core.utilities.objects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class MFAPage extends BaseCommunityPage{

    @FindBy(xpath = ".//")
    WebElement pageTitle;

    @FindBy(xpath = ".//h3[contains(text(),'Security Token')]")
    WebElement securityTokenTitle;

    @FindBy(xpath = ".//h3[contains(text(),'Security Code via Email')]")
    WebElement securityCodeEmailTitle;

    @FindBy(xpath = ".//h3[contains(text(),'Security Verification Question')]")
    WebElement securityVerificationQuestion;

    @FindBy(name = "defer_update")
    WebElement remindLaterButton;

    @FindBy(xpath = ".//p[contains(text(),'Errors Were Encountered')]")
    public WebElement errorMessage;

    @FindBy(xpath = "//input[@id='submit']")
    WebElement timeoutOkButton;

    @FindBy(xpath = ".//b[contains(text(),'The security code was generated more than 10 minutes ago')]")
    public WebElement overTenMinutesMessage;

    String securityMFA;

    public MFAPage(Browser browser, AllCommunityPages fundsXpressPages){
        super(browser, fundsXpressPages);
    }


    @Override
    public ExpectedCondition getPageLoadExpectedConditions() {
        return ExpectedConditions.and(
                ExpectedConditions.visibilityOf(pageTitle));
    }

    public void clickRemindLater() {
        try {
            remindLaterButton.click();
        }
        catch (Exception e){ }
    }

    public void assertAllMFAOptionsAppear(){
        Assert.assertTrue(securityTokenTitle.isDisplayed(), "Security Token Title was not displayed.");
        Assert.assertTrue(securityCodeEmailTitle.isDisplayed(), "Security Code Email Title was not displayed.");
        Assert.assertTrue(securityVerificationQuestion.isDisplayed(), "Security Verification Question is not displayed.");
    }

    public void checkSecuritcheckSecurityTokenEnableyTokenEnable(){
        boolean visible = driver.findElements(By.xpath(".//h3[contains(text(),'Security Token')]")).size() > 0;
        if (!visible) {
            securityMFA = "Unable to find Transfer tab in Home Page";
        } else if (visible) {
            assertAllMFAOptionsAppear();
        }
    }

    public void tenMinuteSleep() {
        try{
            Long initialTime = System.currentTimeMillis();
            System.out.println("Beginning 10 minute wait");
            while((System.currentTimeMillis()-initialTime)<601000){
                if(driver.getWindowHandles().size()>1) {
                    handlePopupWindow();
                }
                Thread.sleep(55000);
            }
           }catch (Exception e){
            System.out.println(e);
        }
    }

    public void secondLogin(User u, String initialCode) {
        pages.login.navigateToUpdate(u.institutionID);
        EmailManager secondEmail = new EmailManager();
        String securityCode  = secondEmail.getSecurityCode(u.email, () -> pages.login.navigateToEmailValidation(u));
        System.out.println(securityCode);
        pages.login.enterSecurityCode(initialCode);
    }

    public void handlePopupWindow(){
        String winHandleBefore = driver.getWindowHandle();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
                if(driver.getCurrentUrl().contains("timeout_popup")){
                    timeoutOkButton.click();
                }
        }
        driver.switchTo().window(winHandleBefore);
    }
}
