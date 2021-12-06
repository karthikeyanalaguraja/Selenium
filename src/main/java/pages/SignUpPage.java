package pages;

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


public class GetStartedPage extends BaseCommunityPage {
    
    @FindBy(xpath=".//span[contains(text(),'Get Started')]")
    public WebElement getStarted;
    
    @FindBy(xpath=".//span[contains(text(),'Dash')]")
    public WebElement dash;
    
    
    public GetStartedPage(Browser browser, AllCommunityPages allPages) {
        
        super(browser, allPages);
        
    }

    @Override
    protected ExpectedCondition getPageLoadExpectedConditions() {
        return ExpectedConditions.and(
                ExpectedConditions.elementToBeClickable(getStarted),
                ExpectedConditions.elementToBeClickable(dash)
        );
    }
    
   
    
    
}
