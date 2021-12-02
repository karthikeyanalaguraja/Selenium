package communityUiTests;

import core.utilities.annotations.TestCase;
import core.utilities.baseTest.BaseCommunityTest;
import core.utilities.enums.Owner;
import core.utilities.listeners.MyTestListener;
import core.utilities.objects.User;
import core.utilities.objects.UserTypes;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;



@Listeners(MyTestListener.class)
@Epic("Login Tests")
@Feature("LOGIN TESTS")
public class LoginTests extends BaseCommunityTest {


    @Test(groups = "smoke", description = "Login and log out retail customer.")
    @TestCase(owner = Owner.Karthik, testId = 834089)
    public void loginLogout_RetailCustomer() {
        loginUserType(UserTypes.HomeTestRetail);
        pages.home.logout();
    }

    @Test(groups ={ "smoke"}, description = "Login and log out commercial customer.")
    @TestCase(owner = Owner.Karthik, testId = 2154101)
    public void loginLogout_CommercialCustomer() {
        loginUserType(UserTypes.CommercialAccountTest);
        pages.home.logout();
    }

    @Test(groups = { "smoke"},description = "Login and log out an agent.")
    @TestCase(owner = Owner.Karthik, testId = 834089)
    public void loginLogout_Agent() {
        loginUserType(UserTypes.TacticalAgent2);
        pages.home.logout();
    }

    @Test(groups = {"production"}, description = "Login and log out agent in production environment.")
    @TestCase(owner = Owner.Karthik, testId = 834089)
    public void loginLogout_Agent_Production() {
        environment = pages.login.getEnvironment();

        pages.login.runTestIfProdEnvElseSkip();

        user = sqlManager.getCustomer(environment, UserTypes.Automation3);
        pages.login.navigateToUpdate(user.institutionID);
        pages.login.login(user);
        pages.home.logout();
    }

    @Test(groups={"smoke"}, description = "Login and log out sub-user.")
    @TestCase(owner = Owner.Karthik, testId = 834089)
    public void loginLogout_SubUser() {
        loginUserType(UserTypes.TacticalSUCreate);
        pages.home.logout();
    }

    @Test(groups = {"production"}, description = "Login and log out sub-user in production environment.")
    @TestCase(owner = Owner.Karthik, testId = 834089)
    public void loginLogout_SubUser_Production() {

        pages.login.runTestIfProdEnvElseSkip();

        loginUserType(UserTypes.AutomationS1);
        pages.home.logout();
    }

 
    @Test(description = "Login with incorrect password.")
    @TestCase(owner = Owner.Kevin, testId = 834222)
    public void failedLogin_Apiture() {
        user = sqlManager.getCustomer(environment, UserTypes.SecurityTest);
        pages.login.navigateToUpdate(user.institutionID);
        user.password = "letmein12FAIL";
        pages.login.loginFail(user);
    }


    @Test(description = "Login with incorrect password.")
    @TestCase(owner = Owner.Kevin, testId = 2154101)
    public void failedLogin_Foo() {
        user = new User().populateRandom();
        user.institutionID = "FOO";
        pages.login.navigateToUpdate(user.institutionID);
        pages.login.loginFail(user);
    }

    @Test(description = "Login with incorrect password more than 3 times")
    @TestCase(owner = Owner.Rachana, testId = 123)
    public void tooManyFailedLogin_Foo() {
        user = new User().populateRandom();
        user.institutionID = "FOO";
        pages.login.navigateToUpdate(user.institutionID);
        for (int i = 0; i<=2; i++) {
            pages.login.loginFail(user);
        }
        pages.login.tooManyFailedLogin(user,"letmein12FAIL");
    }
}
