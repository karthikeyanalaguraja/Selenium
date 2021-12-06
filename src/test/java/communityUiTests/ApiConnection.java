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
@Epic("API Tests")
@Feature("API TESTS")
public class ApiConnection extends BaseCommunityTest {


    @Test(description = "This is to get the session cookie for API validation")
    @TestCase(owner = Owner.Karthik, testId = 401)
    public void getSessionCookie() {
        pages.login.getEnvironment();
        pages.login.login("xpressmobileqa@gmail.com",
                "Karthikeyan@1");
        
    }
    
   
    
    
    
    
}
