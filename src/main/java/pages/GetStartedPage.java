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


public class LoginPage extends BaseCommunityPage {

    public static String baseUrl;
    public String url;

    public LoginPage(Browser browser, AllCommunityPages pages) {
        super(browser, pages);
        this.driver = browser.driver;
        baseUrl = browser.environment;
    }

    public String getEnvironment(){

        String environment = null;
        String path = browser.environment;
        String path2 = path.substring(path.indexOf("-")+1, path.indexOf("-")+3);
        
        switch (path2){
            case "st":
                environment="STAGING";
                break;
            case "qa":
                environment="QA";
                break;
        }
        System.out.println("Testing with the " + environment + " environment");
        return environment;
    }
    
    // Collecting the xpath for the login page
    
    By error400 = By.xpath("//h1[contains(text(),'ERROR')]");
    
    @FindBy(xpath = "//html/body")
    public WebElement htmlBody;
    
    @FindBy(xpath=".//h2[contains(text(),'Sign in')]")
    public WebElement signInHeader;
    
    @FindBy(xpath=".//a[contains(text(),'Sign up')]")
    public WebElement signUpLink;
    
    @FindBy(xpath=".//input[@id='email']")
    public WebElement emailInput;
    
    @FindBy(xpath=".//input[@id='password']")
    public WebElement passwordInput;
    
    @FindBy(xpath="//button/span[contains(text(),'Sign in')]")
    public WebElement signInButton;
    
    @FindBy(xpath=".//button/span[contains(text(),'Forgot password?')]")
    public WebElement forgotPasswordButton;
    
    @FindBy(xpath=".//p[@id='password-helper-text']")
    public WebElement errorText;
    
    // Reset Password Modal related XPATHs
    
    @FindBy(xpath=".//div[@class='MuiDialog-container MuiDialog-scrollPaper']//following::p")
    public WebElement resetPasswordMessage;
    
    @FindBy(xpath=".//div[@class='MuiDialog-container MuiDialog-scrollPaper']//following::input[@id='email']")
    public WebElement resetPasswordEmailInput;
    
    @FindBy(xpath=".//div[@class='MuiDialog-container MuiDialog-scrollPaper']//following::span[contains(text(),'Send')]")
    public WebElement resetPasswordEmailSend;
    
    @Override
    protected ExpectedCondition getPageLoadExpectedConditions() {
        return ExpectedConditions.and(
                ExpectedConditions.elementToBeClickable(signInHeader),
                ExpectedConditions.elementToBeClickable(signUpLink)
        );
    }
    
    @Step("Navigate to login page")
    public void navigateToUrl() {
        driver.get(baseUrl);
        pageWait.until(getPageLoadExpectedConditions());
    
        if(isElementPresent(error400, 1) | browser.driver.getTitle().contains("ERROR")){
            browser.logTestFailure(htmlBody.getText());
            Assert.fail("Login page is not reachable");
        }else{
            browser.logTestAction("Loaded browser to " + baseUrl);
            System.out.println("Loaded browser to " + baseUrl);
        }
    
    }
    
    @Step("Login user with correct credentials")
    public void login(String Username, String Password) {
        navigateToUrl();
        Assert.assertEquals(signInHeader.getText(),"Sign in");
        emailInput.sendKeys(Username);
        passwordInput.sendKeys(Password);
        signInButton.click();
    
    }
    
    
}
