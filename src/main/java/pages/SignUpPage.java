package pages;

import core.basePages.AllCommunityPages;
import core.basePages.BaseCommunityPage;
import core.utilities.baseUtilities.Browser;
import core.utilities.objects.User;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import core.utilities.objects.User;
import core.utilities.objects.UserTypes;



public class SignUpPage extends BaseCommunityPage {
    
    @FindBy(xpath=".//h2[contains(text(),'Setup free trial')]")
    public WebElement signUpHeader;
    
    @FindBy(xpath=".//span[contains(text(),'Create your profile')]")
    public WebElement createProfileHeader;
    
    @FindBy(xpath=".//input[@data-testid='progressive-form-firstname']")
    public WebElement firstName;
    
    @FindBy(xpath=".//input[@data-testid='progressive-form-lastname']")
    public WebElement lastName;
    
    @FindBy(xpath=".//input[@data-testid='progressive-form-phone']")
    public WebElement phoneNumber;
    
    @FindBy(xpath=".//input[@data-testid='progressive-form-email']")
    public WebElement email;
    
    @FindBy(xpath=".//span[contains(text(),'continue')]")
    public WebElement step1Continue;
    
    @FindBy(xpath=".//input[@data-testid='progressive-form-company']")
    public WebElement companyName;
    
    @FindBy(xpath=".//input[@data-testid='progressive-form-street']")
    public WebElement streetAddress;
    
    @FindBy(xpath=".//input[@data-testid='progressive-form-city']")
    public WebElement city;
    
    @FindBy(xpath=".//div[@id='mui-component-select-state']")
    public WebElement state;
    
    @FindBy(xpath=".//ul[@class='MuiList-root-419 MuiMenu-list-402 MuiList-padding-420']//following::li[contains(text(),'A')]")
    public WebElement chooseState;
    
    @FindBy(xpath=".//input[@data-testid='progressive-form-postalCode']")
    public WebElement zipCode;
    
    @FindBy(xpath=".//div[@id='mui-component-select-declaredIndustry']")
    public WebElement industry;
    
    public SignUpPage(Browser browser, AllCommunityPages allPages) {
        super(browser, allPages);
    }

    @Override
    protected ExpectedCondition getPageLoadExpectedConditions() {
        return ExpectedConditions.and(
                ExpectedConditions.elementToBeClickable(signUpHeader),
                ExpectedConditions.elementToBeClickable(createProfileHeader)
        );
    }
    
    @Step("Get user from Faker")
    public void enroll() {
        User user= new User().populateRandom();
        Assert.assertEquals(signUpHeader.getText().trim(),"Setup free trial" );
        Assert.assertEquals(createProfileHeader.getText().trim(),"Create your profile");
        firstName.sendKeys(user.first);
        lastName.sendKeys(user.last);
        phoneNumber.sendKeys(user.primaryPhone);
        email.sendKeys(user.email);
        step1Continue.click();
        clickElementOnceVisible(companyName);
        companyName.sendKeys(user.getBusinessName());
        streetAddress.sendKeys(user.address.streetAddress1);
        city.sendKeys(user.address.city);
        zipCode.sendKeys(user.address.zip);
        state.click();
        clickElementOnceVisible(chooseState);
        chooseState.click();
    }
    
   
    
    
}
