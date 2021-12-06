package communityUiTests;

import core.utilities.annotations.TestCase;
import core.utilities.baseTest.BaseCommunityTest;
import core.utilities.enums.Owner;
import core.utilities.listeners.MyTestListener;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(MyTestListener.class)
@Epic("Main Tests")
@Feature("Main TESTS")
public class BrokenLinks extends BaseCommunityTest {
    
    
    @Test(groups = "smoke", description = "Validate all the links are working under Get started")
    @TestCase(owner = Owner.Karthik, testId = 201)
    public void validate_getstarted_links() {
        pages.login.getEnvironment();
        pages.login.login("xpressmobileqa@gmail.com",
                "Karthikeyan@1");
        pages.getStarted.demoClose.click();
        pages.getStarted.getStarted.click();
        pages.getStarted.validateAllWebLinks();
        
    }
    
    @Test(groups = "smoke", description = "Validate all the links are working under Dash board")
    @TestCase(owner = Owner.Karthik, testId = 202)
    public void validate_dashboard_links() {
        pages.login.getEnvironment();
        pages.login.login("xpressmobileqa@gmail.com",
                "Karthikeyan@1");
        pages.getStarted.demoClose.click();
        pages.getStarted.dash.click();
        pages.getStarted.validateAllWebLinks();
        
    }
    
    @Test(groups = "smoke", description = "Validate all the links are working under Customer")
    @TestCase(owner = Owner.Karthik, testId = 203)
    public void validate_customer_links() {
        pages.login.getEnvironment();
        pages.login.login("xpressmobileqa@gmail.com",
                "Karthikeyan@1");
        pages.getStarted.demoClose.click();
        pages.getStarted.customers.click();
        pages.getStarted.validateAllWebLinks();
        
    }
    
    @Test(groups = "smoke", description = "Validate all the links are working under Reporting")
    @TestCase(owner = Owner.Karthik, testId = 204)
    public void validate_reporting_links(){
        pages.login.getEnvironment();
        pages.login.login("xpressmobileqa@gmail.com",
                "Karthikeyan@1");
        pages.getStarted.demoClose.click();
        pages.getStarted.reporting.click();
        pages.getStarted.validateAllWebLinks();
        
    }
    
    @Test(groups = "smoke", description = "Validate all the links are working under priceBook")
    @TestCase(owner = Owner.Karthik, testId = 205)
    public void validate_priceBook_links() {
        pages.login.getEnvironment();
        pages.login.login("xpressmobileqa@gmail.com",
                "Karthikeyan@1");
        pages.getStarted.demoClose.click();
        pages.getStarted.priceBook.click();
        pages.getStarted.validateAllWebLinks();
        
    }
    
    @Test(groups = "smoke", description = "Validate all the links are working under newJob")
    @TestCase(owner = Owner.Karthik, testId = 206)
    public void validate_newJob_links()  {
        pages.login.getEnvironment();
        pages.login.login("xpressmobileqa@gmail.com",
                "Karthikeyan@1");
        pages.getStarted.demoClose.click();
        pages.getStarted.clickNewJob();
        pages.getStarted.validateAllWebLinks();
        
    }
    
    
}

    
