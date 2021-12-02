package core.basePages;

import core.utilities.baseUtilities.Browser;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public abstract class BaseFXIMPage extends BasePage {

    protected FXIMPages pages;
    public FXIMNavigationMenu navigationMenu;

    private String currentPage ;
    private String currentTab ;

    @FindBy(xpath = "//html/body")
    public WebElement htmlBody;

    @FindBy(xpath = "//div[@class='alert alert-danger']")
    private WebElement htmlBodyWarningBox;

    @FindBy(xpath = "//div[@class='alert alert-danger']")
    private List<WebElement> htmlBodyWarningBoxes;

    @FindBy(id = "logout")
    WebElement logOut;

    @FindBy(xpath = "//div[@class='navigation--left--table']//a")
    List<WebElement> menuLinks;

    @FindBy(xpath = "//li[@class='selected']//a")
    List<WebElement> menuItemLinks;

    @FindBy(xpath = "//ul[@id='secondry-nav-ul']/li[@class='opened']//li[@class='opened']//a")
    List<WebElement> menuItemInnerLinks;

    @FindBy(xpath = "//table[@class='table table-striped']//a")
    List<WebElement> innerBodyLinks;

    @FindBy(xpath = "//span[contains(text(),'On Demand/Ad Hoc Report Access')]")
    WebElement fximReportsLink;

    public BaseFXIMPage(Browser browser, FXIMPages fximPages) {
        super(browser, "", "");
        this.pages = fximPages;
        navigationMenu = new FXIMNavigationMenu(browser);
    }

    public void logout() {
        logOut.click();
    }

    public class FXIMNavigationMenu {

        @FindBy(xpath = ".//a[text()='Customer']")
        WebElement customerTab;

        @FindBy(xpath = ".//a[text()='Institution']")
        WebElement institutionTab;

        @FindBy(xpath = ".//a[text()='FXIM Administration']")
        WebElement adminTab;

        @FindBy(xpath = ".//a[text()='Messages']")
        WebElement messagesTab;

        @FindBy(xpath = ".//a[text()='Control Panel']")
        WebElement controlPanelTab;

        @FindBy(xpath = ".//h3[contains(text(),'Control Panel Menu')]")
        WebElement controlPanelMenu;

        @FindBy(xpath = "//a[contains(@href, 'cust_open')]")
        WebElement openTab;

        @FindBy(name = "access_id")
        WebElement findCustomerAccessId;

        @FindBy(xpath = "//div//h2[contains(text(),'Open - Find Customer')]")
        WebElement findCustomerTitle;

        @FindBy(xpath = ".//input[@name='submit' and @type='submit']")
        WebElement openFindCustomerFindButton;

        @FindBy(xpath = "//div//h2[contains(text(),'View Customer Information')]")
        WebElement viewCustomerInformation;

        @FindBy(xpath = ".//li//a//span[contains(text(),'Wizard')]")
        WebElement cashMgmtWizardButton;

        @FindBy(xpath = ".//li//a//span[contains(text(),'Limits')]")
        WebElement cashMgmtLimitsButton;

        @FindBy(xpath = ".//div//h2[contains(text(),'View Customer Risk Level')]")
        WebElement cashManagementLimitsTitle;

        @FindBy(xpath = "//div//h2[contains(text(),'Cash Management Wizard')]")
        WebElement cashManagementWizardTitle;

        @FindBy(xpath=".//tr[2]//input[@name='cust_is_focus']")
        WebElement selectAgent2Focus;

        @FindBy(xpath=".//input[@name='csc_access_id']")
        WebElement enterFocusAccessId;

        @FindBy(xpath = ".//*[@type='submit']")
        WebElement submitButton;

        public FXIMNavigationMenu(Browser browser) { PageFactory.initElements(browser.driver, this); }

        @Step("{method}")
        public void navigateToMessages(){
            //browser.logTestAction("Click on Messages tab.");
            messagesTab.click();
        }

        @Step("{method}")
        public void navigateToControlPanel(){
            //browser.logTestAction("Click on Control Panel tab.");
            clickElementOnceClickable(controlPanelTab);
            pageWait.until(ExpectedConditions.visibilityOf(controlPanelMenu));
        }

        @Step("{method}")
        public void navigateToAdminTab(){
            //browser.logTestAction("Click on Admin tab.");
            adminTab.click();
            explicitPageWait();
        }

        @Step("{method}")
        public void navigateToCashManagementWizard(String accessId){

            //TODO WHen this code runs and sets the institution to foo the enableIncomingWires breaks
            Select institutionGroupSelector = new Select(driver.findElement(By.name("inst_group_id")));
            institutionGroupSelector.selectByValue("/inst/apiture");
            clickElementOnceClickable(submitButton);

            clickElementOnceClickable(openTab);
            explicitPageWait();
            pageWait.until(ExpectedConditions.visibilityOf(findCustomerTitle));
            findCustomerAccessId.sendKeys(accessId);
            openFindCustomerFindButton.click();
            pageWait.until(ExpectedConditions.visibilityOf(viewCustomerInformation));
            cashMgmtWizardButton.click();
            pageWait.until(ExpectedConditions.visibilityOf(cashManagementWizardTitle));
            submitButton.click();
        }

        @Step("{method}")
        public void navigateToCashManagement(String accessId){
            openTab.click();
            pageWait.until(ExpectedConditions.visibilityOf(findCustomerTitle));
            findCustomerAccessId.sendKeys(accessId);
            openFindCustomerFindButton.click();
            pageWait.until(ExpectedConditions.visibilityOf(viewCustomerInformation));
            cashMgmtWizardButton.click();
            pageWait.until(ExpectedConditions.visibilityOf(cashManagementWizardTitle));
            submitButton.click();
        }

        @Step("{method}")
        public void navigateToCMWizardChooseAgent( String focusId){
            cashMgmtWizardButton.click();
            pageWait.until(ExpectedConditions.visibilityOf(cashManagementWizardTitle));
            selectAgent2Focus.click();
            enterFocusAccessId.sendKeys(focusId);
            submitButton.click();
        }


        @Step("{method}")
        public void navigateToCashManagementLimitsOfFocusCustomer(){
            cashMgmtLimitsButton.click();
            pageWait.until(ExpectedConditions.visibilityOf(cashManagementLimitsTitle));
        }
    }

    public void explicitPageWait() {
        /**
         * used explicit page wait to avoid error regarding loading of the page, elements
         * registering as visible but not showing up on the ui, this wait gives necessary
         * time for the element mySpendingCategory to display.
         */
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Step("{method}")
    public void checkFximMenuLinks(){
        Assert.assertTrue(menuLinks.size()>0);
        //browser.logTestAction("Clicking on all links under menu.");
        checkLinksHttpResponses(menuLinks);
        currentPage = StringUtils.substringAfter(driver.getCurrentUrl(), "fxim.pile/");
        currentTab = StringUtils.substringBefore(currentPage, "/");
        //Get text of each link
        String[] linksTextsA = new String[menuLinks.size()];
        int i = 0;
        for(WebElement link : menuLinks) {
            linksTextsA[i] = link.getAttribute("href");
            linksTextsA[i] = StringUtils.substringAfter(linksTextsA[i], ".com");
            if(linksTextsA[i].contains("analytics")){
                linksTextsA[i] = StringUtils.substringBefore(linksTextsA[i], "%");
            }
            System.out.println(linksTextsA[i]);
            i++;
        }
        //Click each link
        for(String l : linksTextsA){
            checkHtmlBody( l);
            if(!driver.getCurrentUrl().toLowerCase().contains(currentTab)) {
                driver.navigate().back();
                driver.navigate().refresh();
                explicitPageWait();
            }
            if(!currentTab.equalsIgnoreCase("customer")){
                checkFximMenuItemLinks();
            }
        }
    }

    @Step("{method}")
    public void checkFximMenuItemLinks(){
        //browser.logTestAction("Clicking on each link under Menu Items.");
        if(menuItemLinks.size() > 0){
            checkLinksHttpResponses(menuItemLinks);
            //Get text of each link
            String[] linksTextsB = new String[menuItemLinks.size()];
            int i = 0;
            for(WebElement link2 : menuItemLinks) {
                linksTextsB[i] = link2.getAttribute("href");
                linksTextsB[i] = StringUtils.substringAfter(linksTextsB[i], ".com");
                if(linksTextsB[i].contains("analytics")){
                    linksTextsB[i] = StringUtils.substringBefore(linksTextsB[i], "%");
                }
                i++;
            }
            //Click each link
            for(String l : linksTextsB){
                checkHtmlBody(l);
                checkFximMenuItemInnerLinks();
            }
        }
    }

    @Step("{method}")
    public void checkFximMenuItemInnerLinks(){
        if(menuItemInnerLinks.size() > 0){
            checkLinksHttpResponses(menuItemInnerLinks);
            //Get text of each link
            String[] linksTextsC = new String[menuItemInnerLinks.size()];
            int i = 0;
            for(WebElement link3 : menuItemInnerLinks) {
                linksTextsC[i] = link3.getAttribute("href");
                linksTextsC[i] = StringUtils.substringAfter(linksTextsC[i], ".com");
                if(linksTextsC[i].contains("analytics")){
                    linksTextsC[i] = StringUtils.substringBefore(linksTextsC[i], "%");
                }
                i++;
            }
            //Click each link
            for(String l : linksTextsC){
                checkHtmlBody(l);
            }
        }
    }

    @Step("{method}")
    public void checkFximReportsLinks(){
        fximReportsLink.click();
        Assert.assertTrue(innerBodyLinks.size()>0);
            checkLinksHttpResponses(innerBodyLinks);
            //Get text of each link
            String[] linksTextsC = new String[innerBodyLinks.size()];
            int i = 0;
            for(WebElement link3 : innerBodyLinks) {
                linksTextsC[i] = link3.getAttribute("href");
                linksTextsC[i] = StringUtils.substringAfter(linksTextsC[i], ".com");
                i++;
            }
            //Click each link
            for(String l : linksTextsC){
                checkHtmlBody(l);
                driver.navigate().back();
                driver.navigate().refresh();
            }
    }

    @Step("{method}")
    public void checkHtmlBody(String link){
        waitForPresencesOfElementThenClick(driver.findElement(By.xpath(".//a[contains(@href, '"+link+"')]")), 2000);
        if(driver.getTitle().toLowerCase().contains("error")){
            System.out.println("========================================"+
                    "\n"+"The following link responded with error:"+"\n"+
                    link +"\n"+htmlBody.getText()+
                    "\n"+"========================================");
        }
        //TODO autmation@fundsxpress.com has no privs for some links (check log)
        if(htmlBodyWarningBoxes.size()>0 && (htmlBodyWarningBox.getText().contains("You do not have privs") | htmlBodyWarningBox.getText().contains("Priv Required."))){
            System.out.println("Ignoring: "+link+" link for now as automation@fundsxpress.com has no privs for it");
            driver.navigate().back();
            explicitPageWait();
        }else{
            Assert.assertTrue(!driver.getTitle().toLowerCase().contains("error"));
        }
    }

    @Step("{method}")
    public void checkLinksHttpResponses(List<WebElement> allLinks){
        HttpURLConnection huc = null;
        int respCode = 200;
        String url = "";

        //Get Http Response on each link
        String[] linkTexts = new String[allLinks.size()];
        int i = 0;
        for(WebElement link : allLinks){
            linkTexts[i] = link.getText();
            i++;

            url = link.getAttribute("href");

            try{
                huc = (HttpURLConnection)(new URL(url)).openConnection();
                huc.setRequestMethod("HEAD");
                huc.connect();
                respCode = huc.getResponseCode();
                if(respCode>=400){
                    System.out.println(url.toUpperCase()+" is a broken link!");
                }
                Assert.assertTrue(respCode<=400);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
