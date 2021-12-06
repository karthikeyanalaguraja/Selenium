package pages;

import core.basePages.AllCommunityPages;
import core.basePages.BaseCommunityPage;
import core.utilities.baseUtilities.Browser;
import core.utilities.objects.User;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;


public class NewJobPage extends BaseCommunityPage {
    
    public NewJobPage(Browser browser, AllCommunityPages allPages) {
        super(browser, allPages);
    }
    
    @FindBy(xpath=".//div[contains(text(),'Customer')]")
    public WebElement customerSection;
    
    @FindBy(xpath=".//div[contains(text(),'Schedule')]")
    public WebElement ScheduleSection;
    
    @FindBy(xpath=".//span[contains(text(),'New customer')]")
    public WebElement createNewCustomer;
    
    @FindBy(xpath=".//input[@name='first_name']")
    public WebElement firstName;
    
    @FindBy(xpath=".//input[@name='last_name']")
    public WebElement lastName;
    
    @FindBy(xpath=".//input[@name='mobile_number']")
    public WebElement mobileNumber;
    
    @FindBy(xpath=".//input[@name='email']")
    public WebElement email;
    
    @FindBy(xpath=".//button[@type='submit']")
    public WebElement createCustomer;
    
    private String customerInfo = ".//p[contains(text(),'%s')]";
    
    @FindBy(xpath=".//span[contains(text(),'Schedule')]//following::span[4]")
    public WebElement schedule;
    
    @FindBy(xpath=".//tr[@role='row']//td[20]")
    public WebElement scheduleTime;
    
    @FindBy(xpath = ".//button/span[contains(text(),'Use this time')]")
    public WebElement useTime;
    
    @FindBy(xpath=".//label[contains(text(),'Item name')]//following::input[1]")
    public WebElement itemName;
    
    @FindBy(xpath=".//input[@class='MuiInputBase-input MuiFilledInput-input' and @value='$0.00']")
    public WebElement unitPrice;
    
    @FindBy(xpath=".//label[contains(text(),'Unit price')]//following::input[2]")
    public WebElement unitCost;
    
    @FindBy(xpath=".//p[contains(text(),'Private notes')]//following::div[1]")
    public WebElement privateNote;
    
    @FindBy(xpath=".//span[contains(text(),'Private notes')]//following::textarea[1]")
    public WebElement privateNoteTextarea;
    
    @FindBy(xpath=".//button/span[contains(text(),'Save job')]")
    public WebElement saveJob;
    
    @FindBy(xpath=".//h2[@class='MuiTypography-root jss562 MuiTypography-h2']")
    public WebElement saveConfirmationErrorMessage;
    
    @Override
    protected ExpectedCondition getPageLoadExpectedConditions() {
        return ExpectedConditions.and(
                ExpectedConditions.elementToBeClickable(customerSection),
                ExpectedConditions.elementToBeClickable(ScheduleSection)
        );
    }
    
    
    @Step("Create customer at the new job level")
    public void createCustomer() {
        User user= new User().populateRandom();
    
        clickElementOnceVisible(createNewCustomer);
        
        firstName.sendKeys(user.first);
        lastName.sendKeys(user.last);
        mobileNumber.sendKeys("5124932500");
        email.sendKeys(user.email);
        createCustomer.click();
     
    }
    
    public void createSchedule() {
        explicitPageWait();
        clickElementOnceVisible(schedule);
        clickElementOnceVisible(scheduleTime);
        clickElementOnceVisible(useTime);
    }
    
    public void addLineItems(){
        explicitPageWait();
        itemName.sendKeys("this is for automation");
        //TODO : this piece of code is not working, i expecting to handle this mouse over at the specfic spot to enter the amount
        unitPrice.sendKeys("500");
        unitCost.sendKeys("20");
        
    }
    
    public void addPrivateNote() {
        explicitPageWait();
        clickElementOnceVisible(privateNote);
        privateNoteTextarea.sendKeys("This is wonderful time of the year");
    }
    
    public void saveJob() {
        explicitPageWait();
        saveJob.click();
    }
    
    public void saveConfirmationPage() {
        explicitPageWait();
        Assert.assertEquals(saveConfirmationErrorMessage.getText().trim(), "Hmmmmm, looks like something went wrong...");
    }
    
   
    
    
}
