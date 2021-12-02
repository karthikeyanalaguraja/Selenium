package pages.Other;

import core.basePages.AllCommunityPages;
import core.basePages.BaseCommunityPage;
import core.utilities.baseUtilities.Browser;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class AuthPage extends BaseCommunityPage {

    @FindBy( xpath= ".//h3[contains(text(),'Additional Authentication Required')]")
    WebElement MFAModalHeader;

    @FindBy( xpath= ".//button[@id='select-mfa-token']")
    WebElement mfaSecurityTokenBtn;

    @FindBy( xpath= ".//div[@id='securityTokenOtp']")
    WebElement securityTokenOTP;

    @FindBy( xpath= ".//input[@id='otp']")
    WebElement securityTokenOTPInput;

    @FindBy(xpath = ".//button[@id='continue']")
    WebElement mfaContninueBtn;

    @FindBy(xpath = ".//strong[contains(text(),('ERROR!'))]")
    List<WebElement> securityTokenErrorMessages;

    @FindBy(xpath = ".//div[@class='modal-body']//ngb-alert[1]")
    WebElement securityTokenErrorMessageBody;

    @FindBy(xpath = ".//div[@class='modal-body']//ngb-alert[2]")
    WebElement securityTokenErrorMessageBody2;

    @FindBy(xpath = ".//button[@id='back']")
    WebElement mfaBackBtn;

    @FindBy(xpath=".//input[contains(@name,'verify_challenge_response') and contains(@name,'::response')]")
    List <WebElement> securityAnswerInput;
    
    @FindBy(xpath=".//button[contains(text(),'Security Verification Question')]")
    WebElement chooseSQAAngularPage;
    By choseSQAAngularPageLocator = By.xpath(".//button[contains(text(),'Security Verification Question')]");
    
    @FindBy(xpath=".//div[@class='mfa-modal']")
    WebElement angularMFAModel;
    
    @FindBy(xpath=".//div[@class='modal-body']")
    WebElement angularMFAModelBody;
    
    @FindBy(xpath=".//input[@type='password']")
    List <WebElement> securityAnswerInputAngular;
    
    String angularSQA = ".//div[contains(@class,'verifyChallenge')]//label[@for='answer%s']//following::input[1][@type='password']";

    @FindBy(id="continue")
    WebElement angularContinueButton;

    By choseSQAMicLocator = By.id("select-mfa-verify");
    @FindBy(xpath = ".//input[@type='submit' and @name='sa_submit']")
    WebElement submitSecurityAnswerBtn;
    By submitSecurityAnswerBtnLocator = By.xpath(".//input[@type='submit' and @name='sa_submit']");

    public AuthPage(Browser browser, AllCommunityPages pages){

        super(browser,pages);
    }

    @Override
    public ExpectedCondition getPageLoadExpectedConditions() {
        return ExpectedConditions.or(
                ExpectedConditions.visibilityOf(submitSecurityAnswerBtn),
                ExpectedConditions.visibilityOf(chooseSQAAngularPage)
        );
    }

    // This method handles both Angular and MIC MFA
    // ToDo: need to change this to allow different answers for different questions
    public boolean submitSecurityAnswer(String answer){

        // Mic MFA
        if( isElementPresent(submitSecurityAnswerBtnLocator,1)) {

            pageWait.until(ExpectedConditions.visibilityOf(securityAnswerInput.get(0)));
            pageWait.until(ExpectedConditions.visibilityOf(submitSecurityAnswerBtn));

            int var = securityAnswerInput.size();
            for (int i = 0; i < var; i++) {
                pageWait.until(ExpectedConditions.visibilityOf(securityAnswerInput.get(i)));
                WebElement secAnswer = securityAnswerInput.get(i);
                System.out.println("Entering security answer '" + answer + "' for question " + i);
                secAnswer.sendKeys(answer);

            }

            submitSecurityAnswerBtn.click();
            return true;
        }// Angular MFA
        else if( isElementPresent(choseSQAAngularPageLocator,1)){
            pageWait.until(ExpectedConditions.visibilityOf(chooseSQAAngularPage));
            chooseSQAAngularPage.click();

            pageWait.until(ExpectedConditions.visibilityOf(angularMFAModel));
            pageWait.until(ExpectedConditions.visibilityOf(angularMFAModelBody));

            int var = securityAnswerInputAngular.size();
            for (int i=0; i<var; i++) {
                driver.findElement(By.xpath(String.format(angularSQA,i))).sendKeys(answer);
                System.out.println("Entering security answer '" + answer + "' for question " + i);
            }

            angularContinueButton.click();
            return true;
        }
        return false;
    }

    @Step("Is MFA Displayed")
    public boolean isMFADisplayed(){
        waitForPresencesOfElement(MFAModalHeader,2000);
        if (MFAModalHeader.isDisplayed()){
            return true;
        }
        return false;
    }

    @Step("Validating error messages on Securit Token MFA")
    public void validateSecurityTokenMFAErrorMessages(){
        clickElementOnceClickable(mfaSecurityTokenBtn);
        pageWait.until(ExpectedConditions.visibilityOf(securityTokenOTP));
        pageWait.until(ExpectedConditions.visibilityOf(securityTokenOTPInput));
        //entering an invalid token
        securityTokenOTPInput.sendKeys("123123");
        clickElementOnceClickable(mfaContninueBtn,2000);
        waitForPresencesOfElement(securityTokenErrorMessageBody, 1000);
        Assert.assertEquals(securityTokenErrorMessageBody.getText(), "ERROR! Your security token response was not valid.\n×");
        //entering an invalid token for the 2nd time
        securityTokenOTPInput.sendKeys("321321");
        clickElementOnceClickable(mfaContninueBtn,2000);
        waitForPresencesOfElement(securityTokenErrorMessageBody2, 1000);
        Assert.assertEquals(securityTokenErrorMessageBody2.getText(), "ERROR! Your security token response was not valid. Please enter 2 different consecutive security codes.\n×");
        clickElementOnceClickable(mfaBackBtn, 2000);

    }
}
