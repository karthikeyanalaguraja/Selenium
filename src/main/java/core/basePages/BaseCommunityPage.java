package core.basePages;

import core.utilities.baseUtilities.Browser;
import core.utilities.objects.Batch;
import core.utilities.objects.User;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class  BaseCommunityPage extends BasePage {

    protected AllCommunityPages pages;
    
    public BaseCommunityPage(Browser browser, AllCommunityPages fundsXpressPages) {
        super(browser, "","");
        this.pages = fundsXpressPages;
    }

   
    public int getFromAccountSize (WebElement dropdown) {
        Select select = new Select(dropdown);
        int fromAccountSize = select.getOptions().size();
        return fromAccountSize;
    }

    public String getFromAccount (WebElement dropdown, int i) {
        Select select = new Select(dropdown);
        List<WebElement> list=select.getOptions();
        List<String> fromAccount = new ArrayList<>();
        for(WebElement we:list) {
            fromAccount.add(we.getText().trim());
        }
        System.out.println(fromAccount.get(i)+"ACCOUNT");
        return fromAccount.get(i);
    }

    public int getToAccountSize (WebElement dropdown) {
        Select select = new Select(dropdown);
        int toAccountSize = select.getOptions().size();
        return toAccountSize;
    }

    public String getToAccount (WebElement dropdown, int i) {
        Select select = new Select(dropdown);
        List<WebElement> list = select.getOptions();
        List<String> toAccount = new ArrayList<>();
        for (WebElement we : list) {
            toAccount.add(we.getText().trim());
        }
        return toAccount.get(i);
    }


    public int getFromExternalAccountSize(WebElement dropdown) {
        Select select = new Select(dropdown);
        List<WebElement> list = select.getOptions();
        List<String> fromExternalAccount = new ArrayList<>();
        String search = "External";
        for (WebElement we : list) {
            if (we.getText().trim().contains(search)) {
                fromExternalAccount.add(we.getText());
            }
        }
        return fromExternalAccount.size();
    }

    public String getFromExternalAccount( WebElement dropdown) {
        Select select = new Select(dropdown);
        List<WebElement> list = select.getOptions();
        List<String> fromExternalAccount = new ArrayList<>();
        String search = "External";
        for (WebElement we : list) {
            if (we.getText().trim().contains(search)) {
                fromExternalAccount.add(we.getText());
            }
        }
        return fromExternalAccount.get(0);
    }

    public int getToExternalAccountSize(WebElement dropdown) {
        Select select = new Select(dropdown);
        List<WebElement> list=select.getOptions();
        List<String> toExternalAccount = new ArrayList<>();
        String search = "External";
        for(WebElement we:list) {
            if(we.getText().trim().contains(search)) {
                toExternalAccount.add(we.getText());
            }
        }
        return toExternalAccount.size();
    }

    public String getToExternalAccount(WebElement dropdown) {
        Select select = new Select(dropdown);
        List<WebElement> list = select.getOptions();
        List<String> toExternalAccount = new ArrayList<>();
        String search = "External";
        for (WebElement we : list) {
            if (we.getText().trim().contains(search)) {
                toExternalAccount.add(we.getText());
            }
        }
        return toExternalAccount.get(0);
    }
    public int getLoanAccountSize(WebElement dropdown) {
        Select select = new Select(dropdown);
        List<WebElement> list=select.getOptions();
        List<String> loanAccount = new ArrayList<>();
        String search1 = "Credit";
        String search2 = "Card";
        String search3 = "Loan";
        for(WebElement we:list) {
            if(we.getText().trim().contains(search1) || we.getText().trim().contains(search2) || we.getText().trim().contains(search3)) {
                loanAccount.add(we.getText());
            }
        }
        return loanAccount.size();
    }

    public String getToLoanAccount(WebElement dropdown) {
        Select select = new Select(dropdown);
        List<WebElement> list=select.getOptions();
        List<String> loanAccount = new ArrayList<>();
        String search1 = "Credit";
        String search2 = "Card";
        String search3 = "Loan";
        for(WebElement we:list) {
            if(we.getText().trim().contains(search1) || we.getText().trim().contains(search2) || we.getText().trim().contains(search3)) {
                loanAccount.add(we.getText().trim());
            }
        }
        return loanAccount.get(0);
    }

    public void bootStrapDropDown (String searchParameter, String actionMenu, String actionMenuIndex) {
        List <WebElement> dropDown = driver.findElements(By.xpath(String.format(actionMenu,searchParameter)));
        for(int i =0; i<dropDown.size(); i++) {
            WebElement element=dropDown.get(i);
            String innerhtml=element.getAttribute("innerHTML");
            if(innerhtml.contains(actionMenuIndex)) {
                element.click();
                break;
            }
        }
    }

    @Override
    public void waitForPageLoad() {
        super.waitForPageLoad();
       // browser.addScreenshot();
        /* Todo: Get the url to the apiture logo resolved then uncomment this.
        List<LogEntry> severeErrors = driver.manage().logs().get("browser").filter(Level.SEVERE);
        Assert.assertTrue("A severe browser error has occurred: " + severeErrors.toString(),
                severeErrors.size() == 0);
        */
    }

    public void explicitPageWait(){
        /**
         * used explicit page wait to avoid error regarding loading of the page, elements
         * registering as visible but not showing up on the ui, this wait gives necessary
         * time for the element mySpendingCategory to display.
         */
        try{
            Thread.sleep(3000);
        }catch (Exception e){
            System.out.println(e);
        }

    }

    @Step("{method}")
    public void launchInNewTab( WebDriver driver, Runnable actionThatLaunchesNewWindow, Runnable actionToCompleteInNewWindow) {
        //get baseline list of windows
        Set<String> currentWindows = driver.getWindowHandles();

        //the current window is portal
        String originalWindowHandle = driver.getWindowHandle();

        //action that launches the new window
        actionThatLaunchesNewWindow.run();

        //get new list of windows (will include the new one)
        Set<String> newWindows = driver.getWindowHandles();

        //remove original list of windows, leaving just the new
        newWindows.removeAll(currentWindows);

        //switch driver context to new window
        driver.switchTo().window((String) newWindows.toArray()[0]);

        //do stuff in new window
        actionToCompleteInNewWindow.run();

        //close new window when complete
        driver.close();

        //switch back to original window
        driver.switchTo().window(originalWindowHandle);
    }

    @Step("Scroll to the bottom fo the page")
    public void scrollToTheBottomOfPage(){
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    @Step("{method}: Env: {0}, testType: {1}")
    public void runTestIfEnvAndUserTypeIsElseSkip(String env, String testType){
        if(!pages.login.getEnvironment().equals(env.toUpperCase())){
            browser.skipThisTest(testType+" tests are expected to run in "+env.toUpperCase()+" environment only");
        }
    }

    // If run date falls under holiday or weekend, change date to next business day

    @Step("{method}: Batch: {0}")
    public void validateBatchRunDate(Batch batch){
        if (batch.validDateToRunBatch_Ach() == false) {
            batch.days = 3;
        } else {
            batch.days = 2;
        }
    }
}
