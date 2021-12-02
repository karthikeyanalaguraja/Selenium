package pages.Other;

import core.basePages.AllCommunityPages;
import core.basePages.BaseCommunityPage;
import core.utilities.baseUtilities.Browser;
import core.utilities.baseUtilities.SqlManager;
import core.utilities.email.EmailManager;
import core.utilities.enums.UserProfileInfo;
import core.utilities.logger.Log;
import core.utilities.objects.User;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class LoginPage extends BaseCommunityPage {

    public static String baseUrl;
    public String endPoint;
    public String url;
    private String[] cooks;
    private static String sessionCookie;

    public static String getSessionCookie() {
        return sessionCookie;
    }

    @FindBy(xpath = "//html/body")
    public WebElement htmlBody;

    @FindBy(xpath = ".//frame[@name='start_bottom']")
    public WebElement mainFrame;

    @FindBy(name = "aid")
    public WebElement accessId;

    @FindBy(name = "passcode")
    public WebElement passcode;

    @FindBy(name = "submit")
    public WebElement submitBtn;

    @FindBy(xpath = ".//input[contains(@name,'continue')]")
    public WebElement continueBtn;

    @FindBy(xpath = "//input[@id='input1']")
    WebElement codeField1;

    @FindBy(xpath = "//input[@id='input2']")
    WebElement codeField2;

    @FindBy(name = "sa_submit")
    WebElement emailSubmitBtn;

    @FindBy(xpath = ".//a[@class='login-link']//b[text() = 'Enroll'] | .//a[text() = 'Enroll']")
    WebElement enroll;

    @FindBy(xpath=".//input[@name='ci_agree']")
    WebElement disclosureCheckBox;

    @FindBy(xpath=".//span[@class='ui-button-text' and contains(text(),'I Agree')]")
    WebElement disclosureIAgree;

    @FindBy(xpath = ".//a[contains(text(),'log in again')]")
    WebElement loginAgainBtn;

    @FindBy(xpath = ".//a[@class='login-link' and text() = 'Businesses Enroll']")
    WebElement businessesEnroll;

    @FindBy(className = "forgot-passcode")
    WebElement forgotPasscode;

    //@FindBy(xpath = ".//input[contains(@value,'Remind me later')]")
    @FindBy(xpath = ".//input[contains(@value,'Remind me later') or contains(@value,'Remind Me Later')]")
    WebElement remindMeLater;

    @FindBy(xpath=".//div[@id='splash-3344055-body']")
    WebElement trusteerFancyBox;

    @FindBy(xpath=".//div[@id='splash-3344055-footer-left']//a[3]")
    WebElement trusteerRemindMeLater;
    
    @FindBy(xpath=".//input[@name='accept']")
    WebElement targetNotficationAccept;

    By error400 = By.xpath("//h1[contains(text(),'ERROR')]");
    
    @FindBy(xpath=".//h3[contains(text(),'Additional Authentication Required')]")
    WebElement angularMFAHeader;

    @FindBy(xpath=".//p[contains(text(), 'Additional Authentication Required')]")
    WebElement secondAuthPageHeader;

    @FindBy(xpath=".//button[@id='select-mfa-verify']")
    WebElement mfaSqaBtn;

    @FindBy(xpath=".//input[@id='answer0']")
    WebElement sqaInput1;

    @FindBy(xpath=".//input[@id='answer1']")
    WebElement sqaInput2;

    @FindBy(xpath = "//div[@id='marketing-area']//h2")
    WebElement loginError;

    @FindBy(xpath = "(//h2[text()='Too many failed login attempts'])[2]")
    WebElement tooManyLoginAttempts;

    @FindBy(xpath = "//div[@id='marketing-area']//strong")
    WebElement resetPasswordText;

    @FindBy(xpath = "//div[@id='marketing-area']//input")
    WebElement resetPasswordButtn;

    public LoginPage(Browser browser, AllCommunityPages pages) {
        super(browser, pages);
        this.driver = browser.driver;
        baseUrl = browser.environment;
    }

    @Step("Login user: {user.accessId},  password: {user.password}, security answer: {user.firstSecurityAnswer}")
    public void login(User user) {
        browser.logTestAction("Logging in: "+user.accessId+" with password: "+user.password);

        accessId.sendKeys(user.accessId);
        passcode.sendKeys(user.password);
        clickElementOnceClickable(submitBtn);
        System.out.println("Attempting to login as " + user.accessId + " with passcode " + user.password);
        //unsure what checks are performed to determine if second_auth displays, so just always check\
        pageWait.until(
          ExpectedConditions.or(
                  ExpectedConditions.urlContains("/fx"),
                  ExpectedConditions.urlContains("/home"),
                  ExpectedConditions.urlContains("/second_auth"),
                  ExpectedConditions.urlContains("olb"),
                  ExpectedConditions.urlContains("/change_email_remind"),
                  ExpectedConditions.urlContains("/change_question_reminder"),
                  ExpectedConditions.urlContains("/enroll_two_way_sms_alerts"),
                  ExpectedConditions.urlContains("/voice_activate_remind"),
                  ExpectedConditions.urlContains("/change_pass"),
                  ExpectedConditions.urlContains("/notifications")
          )
        );

        //pages.mfaPage.checkSecuritcheckSecurityTokenEnableyTokenEnable();
        // ToDo: need to conditionalize this for the case that an FI does not allow Security Question/Answers as an MFA option
        // ToDo: the following submitSecurityAnswer function needs to be fixed to handle different answers per different questions; i.e. don't assume answers are the same for all questions
        // the submitSecurityAnswer() method now handles both Angular and Mic MFA and includes the check and turns into a noOp in the event that MFA isn't triggered.
        handleMFA(user);
        String newPassword = null;
        if(driver.getCurrentUrl().contains("/change_pass")) {
            System.out.println("User password expired message received after secondary auth. Changing password..");
            String env = getEnvironment();
            if(env == "PROD") {
                System.out.println(getPassword());
                newPassword = getPassword();
            } else {
                newPassword = UUID.randomUUID().toString().replaceAll("-", "").substring(0,12);
            }
            System.out.println(UUID.randomUUID().toString());
            System.out.println(newPassword);
           
        }
        if(driver.getCurrentUrl().contains("/olb")){
            System.out.println("Clicking the 'Log in again' button");

            clickElementOnceClickable(loginAgainBtn);
            System.out.println("Attempting to login as " + user.accessId + " with passcode " + user.password);
            accessId.sendKeys(user.accessId);
            passcode.sendKeys(user.password);
            clickElementOnceClickable(submitBtn);

        }
    
        if(driver.getCurrentUrl().contains("/notifications")) {
            System.out.println("User is presented with target notfication");
            clickElementOnceClickable(targetNotficationAccept);
        }

        if(driver.getCurrentUrl().contains("/change_pass")) {
            browser.logTestAction("User password expired message received after secondary auth. Changing password..");
            System.out.println("User password expired message received after secondary auth. Changing password..");
            String env = getEnvironment();
            if(env == "PROD") {
                System.out.println(getPassword());
                newPassword = getPassword();
            } else {
                newPassword = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
            }
            
        }

        if(driver.getCurrentUrl().contains("/change_email_remind")) {
                        System.out.println("Landed on the Change Email Reminder screen. Clicking the 'Remind Me Later' button");
                        clickElementOnceClickable(remindMeLater);
                    }
                if(driver.getCurrentUrl().contains("/voice_activate_remind")){
                        System.out.println("Landed on the Voice Activate Reminder screen. Clicking the 'Remind Me Later' button");
                        clickElementOnceClickable(remindMeLater);
                    }
                if(driver.getCurrentUrl().contains("/change_question_reminder")){
                        System.out.println("Landed on the Change Security Question Reminder screen. Clicking the 'Continue' button");
                        clickElementOnceClickable(continueBtn);
                    }
                if(driver.getCurrentUrl().contains("/enroll_two_way_sms_alerts")){
                        System.out.println("Landed on the Transaction Approval Text Alerts Enrollment screen. Clicking the 'Remind Me Later' button");
                        clickElementOnceClickable(remindMeLater);
        }

        //should end on home page
        Boolean isPresent = driver.findElements(By.xpath(".//button[@id='select-mfa-verify']")).size() > 0;
                if(isPresent) {
                    System.out.println("New Angular MFA triggred");
                    browser.logTestPass("New Angular MFA triggred");
                    pages.auth.submitSecurityAnswer(user.firstSecurityAnswer);
                }
        
        pages.home.waitForPageLoad();
        //pages.visualTestingPage.getScreenShot("FXWEB Home Page","Home Page","Home Window");
        cooks = driver.manage().getCookies().toString().split(" ");
        sessionCookie = "";
            for(String s : cooks){
               if(s.contains("sid.fxweb") || s.contains("XSRF")){
                   sessionCookie = sessionCookie.concat(s).replace("[","");
               }
            }
        //Check if login is successful
        if(browser.driver.getCurrentUrl().contains("home")) {
            browser.logTestPass("User successfully logged in and landed on home page: "+browser.driver.getCurrentUrl());
            Log.info("User successfully logged in and landed on home page: "+browser.driver.getCurrentUrl());
        }else{
            browser.logTestFailure("User didn't log in:"+browser.driver.getCurrentUrl());
            Log.info("User didn't log in:"+browser.driver.getCurrentUrl());
        }
    }

    @Step("Login user with incorrect password: {user.accessId}")
    public void loginFail(User user) {
        System.out.println("Attempting to login as " + user.accessId + " with passcode " + "letmein12FAIL");
        accessId.sendKeys(user.accessId);
        passcode.sendKeys("letmein12FAIL");
        submitBtn.click();
        assertLoginErrorDisplayed();
    }

    @Step("Login user who already had more than 3 login attempts with incorrect password: {user.accessId}")
    public void tooManyFailedLogin(User user, String password) {
        System.out.println("Attempting to login as " + user.accessId + " with passcode " + password);
        accessId.sendKeys(user.accessId);
        passcode.sendKeys(password);
        submitBtn.click();
        assertTooManyLoginAttempts();
    }

    @Step("Navigate to login page for institution: {fiid}")
    public void navigateToUpdate( String fiid){
        endPoint = "/start/"+fiid;
        url = baseUrl+endPoint;
        driver.get(url);
        pageWait.until(getPageLoadExpectedConditions());

        if(isElementPresent(error400, 1) | browser.driver.getTitle().contains("ERROR")){
            browser.logTestFailure(htmlBody.getText());
            Assert.fail("FXWEB Login page is not reachable");
        }else{
            browser.logTestAction("Loaded browser to " + url);
            System.out.println("Loaded browser to " + url);
        }

    }

    //TODO angular mfa may need extra work
    @Step("{method}")
    public void handleMFA(User user){
        if(driver.getCurrentUrl().contains("/second_auth") ||
                driver.getCurrentUrl().contains("_token=")){
            pages.auth.submitSecurityAnswer(user.firstSecurityAnswer);
        } else if (driver.getCurrentUrl().contains("/services/sec_info/")) {
            //clickElementOnceClickable(continueBtn);
            EmailManager email = new EmailManager();
            String securityCode  = email.getSecurityCode(user.email, () -> pages.login.navigateToEmailValidation());
            browser.logTestAction("Security CODE: "+securityCode);
            pages.login.enterSecurityCode(securityCode);
        }
    }

    @Step("{method}")
    public void handleAngularMFA(User user){
        clickElementOnceClickable(mfaSqaBtn, 2000);
        waitForPresencesOfElement(angularMFAHeader, 2000);
        sendKeysThenTab(sqaInput1, user.firstSecurityAnswer);
        sendKeysThenTab(sqaInput2, user.secondSecurityAnswer);
        clickElementOnceClickable(pages.auth.mfaContninueBtn, 1000);
    }
    
    //TODO: this is for trouble shooting to find the IP address
    public void getIpAddress(){
        driver.get("https://ifconfig.me/");
        String myIp = driver.findElement(By.xpath(".//strong[@id='ip_address']")).getText();
        System.out.println("The Ip address of the browser is : " + myIp);
    }

    public String getEnvironment(){

        String environment = null;
        String path = browser.environment;
        String path2 = path.substring(path.indexOf("/")+2, path.indexOf("/")+4);

        switch (path2){
            case "ua":
                environment="UAT";
                break;
            case "qa":
                environment="QA";
                break;
            case "fe":
                environment="QA";
                break;
            case "sa":
                environment="QA";
                break;
            case "am":
                environment="QA";
                break;
            case "ca":
                environment ="UAT";
                break;
            case "se":
                environment ="PROD";
                break;
            case "pr":
                environment ="PROD";
                break;
        }
        System.out.println("getting the environment " + environment);
        return environment;
    }
    
    @Step("Getting a new production Password")
    public String getPassword() {
        Random r = new Random();
        int n = r.nextInt(50) + 1;
        String batchName = "AwsisGreat" + "@" + n;
        return batchName;
    }

    @Step("Navigate to fxweb")
    public void navigateTo(){
        driver.get(url);

        pageWait.until(getPageLoadExpectedConditions());
        
        if(isElementPresent(error400, 1) | browser.driver.getTitle().contains("ERROR")){
            browser.logTestFailure(htmlBody.getText());
            Assert.fail("FXWEB Login page is not reachable");
        }
        System.out.println("Loaded browser to " + url);
    }

    @Step("{method}")
    public void navigateToEmailValidation(User user) {
        System.out.println("Attempting to enter " + user.accessId + " and " + user.password);
        accessId.sendKeys(user.accessId);
        passcode.sendKeys(user.password);
        clickElementOnceClickable(submitBtn);
        System.out.println("Clicking the Continue button");
        clickElementOnceClickable(continueBtn);
    }

    @Step("{method}")
    public void navigateToEmailValidation() {
        System.out.println("Clicking the Continue button");
        clickElementOnceClickable(continueBtn);
    }

    @Step("{method}")
    public void enterSecurityCode(String securityCode) {
        System.out.println("Entering security code " + securityCode + " and clicking the Submit button.");
        String[] tempCode = securityCode.split("-");
        codeField1.sendKeys(tempCode[0]);
        codeField2.sendKeys(tempCode[1]);
        emailSubmitBtn.click();
    }


    @Step ("Click on Enroll (Commercial Customers)")
     public void businessEnroll () {
        System.out.println("Clicking the Business Enroll link");
        businessesEnroll.click();
    }

    @Override
    public ExpectedCondition getPageLoadExpectedConditions() {
        return ExpectedConditions.and(
                ExpectedConditions.elementToBeClickable(accessId),
                ExpectedConditions.elementToBeClickable(submitBtn)
        );
    }

    /**
     * This method will verify, user sees error message
     * "Too many failed login attempts" when tries to login more than 3 times with the wrong password
     */
    public void assertTooManyLoginAttempts() {
        Assert.assertEquals(tooManyLoginAttempts.getText(),"Too many failed login attempts","Login error message is not displayed when it should be.");
        Assert.assertEquals(resetPasswordText.getText(), "Recover your account using our passcode reset feature below:");
        Assert.assertEquals(resetPasswordButtn.getAttribute("value"), "Reset your passcode");
    }

    /**
     * This method will verify, user sees error message
     * "LOG IN ERROR" when tries to login with the wrong password
     */
    public void assertLoginErrorDisplayed() {
        Assert.assertEquals(loginError.getText(),"LOG IN ERROR","Login error message is not displayed when it should be.");

    }

}
