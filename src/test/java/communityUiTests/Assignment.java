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
@Epic("Login Tests")
@Feature("LOGIN TESTS")
public class LoginTests extends BaseCommunityTest {


    @Test(groups = "smoke", description = "Login with valid customer")
    @TestCase(owner = Owner.Karthik, testId = 1)
    public void login_ValidCustomer() {
        pages.login.getEnvironment();
        pages.login.login("xpressmobileqa@gmail.com",
                "Karthikeyan@1");
    }
    
    @Test(groups = "top20", description = "Login with Invalid customer")
    @TestCase(owner = Owner.Karthik, testId = 2)
    public void login_InvalidCustomer() {
        pages.login.getEnvironment();
        pages.login.invalidLogin("dummy@gmail.com",
                "foo@1");
    }
    
    @Test(description = "Click on Forgot Password")
    @TestCase(owner = Owner.Karthik, testId = 3)
    public void forgotPassword() {
        pages.login.getEnvironment();
        pages.login.forgotPassword("dummy@gmail.com");
        
    }
    
    @Test(groups = "ignore", description = "New User Sign up")
    @TestCase(owner = Owner.Karthik, testId = 4)
    public void signupNewUser() {
        pages.login.getEnvironment();
        pages.login.navigateToUrl();
        pages.login.signUpLink.click();
        pages.signUpPage.enroll();
        //TOOD: this test is still in progress
        
    }
    
    
    
    
}
