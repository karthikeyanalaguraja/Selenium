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
@Epic("Assignment Tests")
@Feature("Assignment TESTS")
public class Assignment extends BaseCommunityTest {


    @Test(description = "This is to create new job for a customer ")
    @TestCase(owner = Owner.Karthik, testId = 301)
    public void create_newJob() {
        pages.login.getEnvironment();
        pages.login.login("xpressmobileqa@gmail.com",
                "Karthikeyan@1");
        pages.getStarted.demoClose.click();
        pages.getStarted.clickNewJob();
        pages.newJobPage.createCustomer();
        pages.newJobPage.createSchedule();
        pages.newJobPage.addLineItems();
        pages.newJobPage.addPrivateNote();
        pages.newJobPage.saveJob();
        pages.newJobPage.saveConfirmationPage();
    }
    
   
    
    
    
    
}
