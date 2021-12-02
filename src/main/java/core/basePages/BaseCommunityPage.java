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
    public WelcomeMenu welcomeMenu;
    public NavigationMenu navigationMenu;
    public NavigationTransferMenu navigationTransferMenu;
    public NavigationCashManagementMenu navigationCashManagementMenu;
    public MessagesMenu messagesMenu;

    @FindBy(xpath = ".//div//spinner")
    public WebElement spinner;

    @FindBy(xpath = ".//tbody//td//*[contains(text(),'Loading...')]")
    public WebElement loading;

    @FindBy (id = "payments_tab")
    public WebElement paymentsBtn;

    public By paymentsIframe = By.name("third-party-frame");
    
    @FindBy(xpath = ".//div[contains(@class,'user-navigation-dropdown')]")
    public WebElement clickCustomerName;
    
    @FindBy(xpath = "//button[@id='profile-settings-toggle']")
    public WebElement clickCustomerProfile;

    @FindBy(xpath=".//span[@id='nickname']")
    public WebElement nicknameClick;
    
    @FindBy(xpath = ".//span[contains(text(),'Log Out')]")
    public WebElement logOut;

    @FindBy(xpath = "//select[@name='from_account_list']")
    public WebElement transferFromAccount;

    @FindBy(xpath = ".//select[@name='to_account_list']")
    public WebElement transferToAccount;

    @FindBy(xpath=".//a[@id='cash_tab_achorigination']")
    WebElement achOrigination;

    @FindBy(xpath = "//div[contains(text(),'Multi-factor authentication is required')]")
    WebElement notificationMFA;

    @FindBy(xpath=".//a[contains(@href,'Optria_iStatements')]")
    WebElement optriaStatements;

    @FindBy(xpath=".//div[@class='deliveryOptionsNote']")
    WebElement optriaStatementsHeading;

    @FindBy(xpath=".//a[contains(@href,'SCCU_CountyServices')]")
    WebElement countryServices;

    @FindBy(xpath=".//iframe[@id='sso-frame']")
    WebElement ssoIframe;

    @FindBy(xpath=".//span[@class='page-title']")
    WebElement countryServicesTitle;

    @FindBy(xpath=".//a[contains(text(),'Debit Card Overdraft Coverage')]")
    WebElement countryServicesLink;

    @FindBy(xpath=".//a[contains(@href,'Oakworth')]")
    WebElement oakworthSungard;

    @FindBy(xpath=".//div[@class='pageTitle ng-binding']")
    WebElement oakworthSunGardHeading;

    @FindBy(xpath=".//div[@ng-hide='invstSummaryMetaDataObject.CATEGORYNAME.isHidden']")
    WebElement oakworthSungardHeading2;

    @FindBy(xpath=".//a[contains(@href,'HighCotton')]")
    WebElement highCottonSSO;

    @FindBy(xpath=".//a[contains(@href,'transactions')]")
    WebElement highCottonTransactions;

    @FindBy(xpath=".//a[contains(@href,'MineralTree')]")
    WebElement mineralTreeSSO;

    @FindBy(xpath=".//table[@id='account-table']//tr[2]/th[1]")
    WebElement mineralTreeHeading1;

    @FindBy(xpath=".//table[@id='account-table']//tr[2]/th[2]")
    WebElement mineralTreeHeading2;

    @FindBy(xpath=".//table[@id='account-table']//tr[2]/th[3]")
    WebElement mineralTreeHeading3;

    @FindBy(xpath=".//a[contains(@href,'ACHAlert')]")
    WebElement achAlertSSO;

    @FindBy(xpath=".//a[contains(@href,'index')]")
    WebElement achAlertHeading1;

    @FindBy(xpath=".//a[contains(text(),'Apply Online')]")
    WebElement merdianLink;

    @FindBy(xpath=".//span[@class='PageTitle']")
    WebElement merdianLinkHeading;

    @FindBy(xpath=".//a[contains(@href,'eStatements')]")
    WebElement printMailSSO;

    @FindBy(xpath=".//h3[@class='marginBottom10']")
    WebElement printMailHeading1;



    public BaseCommunityPage(Browser browser, AllCommunityPages fundsXpressPages) {
        super(browser, "","");
        this.pages = fundsXpressPages;
        welcomeMenu = new WelcomeMenu(browser);
        navigationMenu = new NavigationMenu(browser);
        navigationTransferMenu = new NavigationTransferMenu(browser);
        messagesMenu = new MessagesMenu(browser);
    }

    @Step("Log out user.")
    public void logout() {
        //browser.logTestAction("Click on Log Out button");
        clickElementOnceClickable(clickCustomerName);
        clickElementOnceClickable(logOut);
        pages.login.waitForPageLoad();
    }
    
    @Step("Log out user at cash management Page.")
    public void logoutCashManagement() {
        //browser.logTestAction("Click on Log Out button");
        clickElementOnceClickable(clickCustomerProfile);
        clickElementOnceClickable(logOut);
        pages.login.waitForPageLoad();
    }
    
    @Step("Log out user in validation mode.")
    public void logoutValidationMode() {
        //browser.logTestAction("Click on Log Out button");
        clickElementOnceClickable(logOut);
        browser.logTestPass("In validation mode the redirection url will reach to secure that will be not be up and running");
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

    public class NavigationMenu {

        @FindBy(xpath = ".//a[@id='home_tab']")
        WebElement homeTab;

        @FindBy(xpath = ".//a[@id='accounts_tab']")
        WebElement accountsTab;

        @FindBy(xpath = ".//button[contains(@ng-click, 'allAccounts')]")
        WebElement viewAllAccountsBtn;

        @FindBy(xpath = ".//a[@id='payments_tab']")
        WebElement paymentsTab;

        @FindBy(xpath = ".//a[@id='transfers_tab']")
        WebElement transferTab;

        @FindBy(xpath = ".//a[@id='transfers_tab']")
        List<WebElement> transferTabs;

        @FindBy(xpath = ".//a[@id='my_spending_tab']")
        WebElement mySpendingTab;

        @FindBy(xpath=".//a[@href='/fxweb/app/#/business']")
        WebElement cashTab;

        @FindBy(xpath = "//div[@id='cash_tab-dropdown']")
        List<WebElement> cashTabDropDown;

        @FindBy(xpath = ".//a[@id='wires_tab']")
        WebElement retailWiresTab;

        @FindBy(linkText="Cash Management Menu")
        WebElement cashManagement;

        @FindBy(xpath = ".//a[contains(text(),'Contact Us')]")
        WebElement contactUs;

        @FindBy(xpath = ".//a[@id='cash_tab']")
        WebElement cashManagmentTab;

        @FindBy(xpath = ".//a[@title='Wire Transfers']")
        WebElement wireTransfersCashManagment;

        @FindBy(xpath = ".//a[contains(text(),'Cash Management Menu')]")
        WebElement cashManagementMenu;

        @FindBy(xpath=".//a[@title='EFTPS']")
        WebElement eftpsMenu;

        @FindBy(xpath = ".//div[contains(text(),'Services & Settings')]")
        WebElement servicesSettingsHeader;

        @FindBy(xpath = "//div[@class='btn-group ng-scope']//button[@id='bulk-actions-label']")
        WebElement newBatchDropDownBtn;

        @FindBy(xpath=".//a[contains(@href,'Acculynk')]")
        WebElement acculynk;

        @FindBy(xpath=".//h1[@id='enrSendPageTitle']")
        WebElement acculynkHeading;

        @FindBy(xpath=".//button[@id='btnEnrolledSendPayment']")
        WebElement acculynkContinueBtn;

        @FindBy(xpath = "//span[contains(text(),'Cash Management')]")
        WebElement cashManagementHeader;


        public NavigationMenu(Browser browser) {
            PageFactory.initElements(browser.driver, this);
        }

       @Step("{method}")
        public void navigateToHome(){
            clickElementOnceClickable(homeTab);
            pages.home.waitForPageLoad();
            pageWait.until(ExpectedConditions.invisibilityOf(spinner));
           }

           @Step("{method}")
        public void navigateToAccountsTab(){
            accountsTab.click();
        }

        @Step("{method}")
        public void clickAcculynk() {
            accountsTab.click();
            explicitPageWait();
            acculynk.click();
            pageWait.until(ExpectedConditions.visibilityOf(ssoIframe));
            driver.switchTo().frame(ssoIframe);
            //pageWait.until(ExpectedConditions.visibilityOf(acculynkContinueBtn));
            Assert.assertEquals(acculynkHeading.getText().trim(), "Send Money");
            //acculynkContinueBtn.click();
            System.out.println("Acculynk is up and running");
        }


        @Step("{method}")
        public void navigateToContactUs(){
            clickElementOnceClickable(contactUs);
            pageWait.until(ExpectedConditions.visibilityOf(servicesSettingsHeader));
        }

        @Step("{method}")
        public void navigateToCashManagementMenu(){
            //browser.logTestAction("Click on Cash Management Tab.");
            clickElementOnceClickable(cashManagmentTab);
            //browser.logTestAction("Click on Cash Management menu.");
            clickElementOnceClickable(cashManagementMenu);
            pageWait.until(ExpectedConditions.visibilityOf(cashManagementHeader));
            browser.logTestPass("User landed on Cash Management Page.", cashManagementHeader);
        }
    
        public void navigateToEFTPSHistory(){
            clickElementOnceClickable(cashManagmentTab);
            clickElementOnceClickable(cashManagementMenu);
            pageWait.until(ExpectedConditions.visibilityOf(cashManagementHeader));
        }

        @Step("{method}")
        public void navigateToEFTPSMenu(){
            clickElementOnceClickable(cashManagmentTab);
            browser.logTestAction("User clicks on EFTPS from Cash Management dropdown");
            clickElementOnceClickable(eftpsMenu);
        }

        @Step("{method}")
        public void navigateCommercialToWireTransfersHome() {
            //browser.logTestAction("Click on Cash Management Tab");
            clickElementOnceClickable(cashManagmentTab);
            //browser.logTestAction("Click on Wire Transfers ");
            clickElementOnceClickable(wireTransfersCashManagment);
        }

    }

    public class navigationRetailWiresMenu {

        @FindBy(xpath=".//a[@id='wires_tab_wiretransferhome']")
        WebElement wireTransferHome;

        @FindBy(xpath=".//a[@id='wires_tab_sendsinglewiretransfer']")
        WebElement singleWireTransfer;

        @FindBy(xpath=".//a[@id='wires_tab_wirehistory']")
        WebElement wireTransferHistory;

        public navigationRetailWiresMenu(Browser browser) {
            PageFactory.initElements(browser.driver, this);
        }

        public void navigationToWireTransfer(){
            navigationMenu.retailWiresTab.click();
            wireTransferHome.click();
        }

        public void navigationToSingleWireTransfer(){
            navigationMenu.retailWiresTab.click();
            singleWireTransfer.click();
        }

        public void navigationToWireTransferHistory(){
            navigationMenu.retailWiresTab.click();
            wireTransferHistory.click();
        }
    }

    public class NavigationCashManagementMenu {

        @FindBy(xpath=".//a[@href='/fxweb/app/#/business']")
        WebElement cashManagement;

        @FindBy(xpath = ".//a[@id='cash_tab_achorigination']")
        List<WebElement> achOriginations;

        @FindBy(xpath=".//a[@id='cash_tab_wiretransfers']")
        WebElement wireTransfers;

        @FindBy(xpath=".//a[@id='cash_tab_incomingwires']")
        WebElement incomingWires;

        @FindBy(xpath=".//a[@id='cash_tab_eftps']")
        WebElement eftps;

        @FindBy(xpath=".//a[@id='cash_tab_checkreconciliation']")
        WebElement checkReconciliation;

        @FindBy(xpath=".//a[@id='cash_tab_booktransfers']")
        WebElement bookTransfers;

        @FindBy(xpath=".//a[@id='cash_tab_administration']")
        WebElement adminstration;

        String achOriginationTabStatus;

        public NavigationCashManagementMenu(Browser browser) {
            PageFactory.initElements(browser.driver, this);
        }


        public void wireTransfer(){
            navigationMenu.cashTab.click();
            wireTransfers.click();

        }

        public void taxPayments(){
            navigationMenu.cashTab.click();
            eftps.click();
        }

        public void positivePay(){
            navigationMenu.cashTab.click();
            checkReconciliation.click();
        }


        public void subUserAdminstration(){
            navigationMenu.cashTab.click();
            adminstration.click();
        }

    }

    public class NavigationTransferMenu {

        @FindBy(xpath = ".//button[contains(@id, 'alertsTopNav-dropdown')]")
        WebElement alertbutton;

        @FindBy(xpath = ".//a//div[contains(text(),'Online transfer is processed')]")
        WebElement newAlertFound;

        @FindBy(xpath = "//a[@attr.title='Schedule Single Transfer' or @title='Schedule Single Transfer']")
        WebElement scheduleSingleTransfer;
    
        @FindBy(xpath = ".//span[contains(text(),' Schedule Single Transfer ')]")
        WebElement scheduleSingleTransferProduction;

        @FindBy(xpath = ".//span[contains(text(),' Schedule Multiple Transfers ')]")
        WebElement scheduleMultipleTransfer;

        @FindBy(xpath=".//span[contains(text(),' Schedule Advanced Transfer ')]")
        WebElement scheduleAdvanceTransfer;

        @FindBy(xpath=".//span[contains(text(),'Schedule Loan Payment')]")
        WebElement loanPayment;

        @FindBy(linkText="Transfer Templates")
        WebElement transferTemplate;

        @FindBy(xpath=".//a[contains(@href,'query_history')]")
        WebElement history;

        @FindBy(xpath=".//a[contains(text(),'Add External Transfer Account')]")
        WebElement addExternalTransferAccount;
        
        @FindBy(xpath=".//a[@title='Actions']")
        WebElement iWantTo;
    
        @FindBy(xpath=".//li/a[contains(text(),'Schedule Loan Payment')]")
        WebElement loanPaymentInScheduleTransfer;

        String homeTransferTabStatus;


        public NavigationTransferMenu(Browser browser) {
            PageFactory.initElements(browser.driver, this);
        }

        

        @Step("{method}")
        public void assertNewAlertCreated(){
            clickElementOnceClickable(alertbutton);
            pageWait.until(ExpectedConditions.visibilityOf(newAlertFound));
            Assert.assertTrue(newAlertFound.isDisplayed(), "No new alerts were displayed.");
        }





        @Step("{method}")
        public void navigateToLoans(){
            navigationMenu.transferTab.click();
            pageWait.until(ExpectedConditions.elementToBeClickable(scheduleSingleTransfer));
            navigationTransferMenu.scheduleSingleTransfer.click();
            navigationTransferMenu.iWantTo.click();
            navigationTransferMenu.loanPaymentInScheduleTransfer.click();
        }

        @Step("{method}")
        public void navigateToTransferTemplate(){
            navigationMenu.transferTab.click();
            pageWait.until(ExpectedConditions.elementToBeClickable(transferTemplate));
            transferTemplate.click();
        }

        @Step("{method}")
        public void navigateToHistory(){
            navigationMenu.transferTab.click();
            pageWait.until(ExpectedConditions.elementToBeClickable(history));
            history.click();
        }

        @Step("{method}")
        public void navigateToExternalTransferAccount(){
            navigationMenu.transferTab.click();
            pageWait.until(ExpectedConditions.elementToBeClickable(addExternalTransferAccount));
            addExternalTransferAccount.click();
        }
    }

    public class WelcomeMenu {

        @FindBy(xpath = ".//span[@id='nickname']")
        WebElement welcomeDropdown;
        
        @FindBy(xpath=".//button[@id='profile-settings-toggle']")
        WebElement nicknameDropdown;
    
        @FindBy(xpath=".//button[contains(text(),'Nickname')]")
        WebElement updateNickname;
    
        @FindBy(xpath=" .//div[contains(text(),'Profile Settings')]")
        WebElement nickNamePageHeader;
    
        @FindBy(xpath=".//li/a[contains(text(),'Access')]")
        WebElement changeAccessId;
    
        @FindBy(xpath=".//li/a[contains(text(),'Passcode')]")
        WebElement changePasscode;
    
        @FindBy(xpath=".//a[contains(text(),'Contact')]")
        WebElement changeContactInfo;
    
        @FindBy(xpath=".//li/a[contains(text(),'Security')]")
        WebElement changeSecurityQuestions;

        @FindBy(xpath=".//div[@id='services-mega-menu']")
        WebElement allServicesAndSettings;
        
        @FindBy(xpath=".//span[@id='nickname']")
        WebElement nickName;
        
        public WelcomeMenu(Browser browser) {
            PageFactory.initElements(browser.driver, this);
        }

        @Step("{method}")
        public void selectUpdateNickname(){
            nicknameDropdown.click();
            updateNickname.click();
            explicitPageWait();
        }

       

    
        @Step("{method}")
        public void selectNickname(){
            nickName.click();
        }
        

        private void selectFromMenu(WebElement element){
            if (!getWelcomeDropdown().getAttribute("class").contains("open")){
                getWelcomeDropdown().click();
                pageWait.until(
                        ExpectedConditions.and(
                                ExpectedConditions.attributeContains(getWelcomeDropdown(), "class", "open"),
                                ExpectedConditions.elementToBeClickable(element)
                        )
                );
            }

            element.click();
        }



        public String getNickname(){
            return driver.findElement(By.xpath(".//span[@id='nickname']")).getText();
        }

        public WebElement getWelcomeDropdown() {
            return welcomeDropdown;
        }
    }

    public class MessagesMenu{

        private final String messagesParent = ".//button[contains(@id,'messagesTopNav-dropdown')]";

        @FindBy(xpath = messagesParent)
        WebElement messagesDropdown;

        @FindBy(xpath = ".//span[contains(text(),'New Message')]")
        WebElement selectNewMessage;

        @FindBy(xpath = ".//span[contains(text(),'View All Messages')]")
        WebElement selectViewAllMessages;

        @FindBy(xpath = ".//span[contains(@ng-class,'msg.unread==true')]")
        WebElement selectUnreadMessage;


        public MessagesMenu(Browser browser){PageFactory.initElements(browser.driver,this);}

        private void selectFromMessages(WebElement element){
            if (!getMessagesDropdown().getAttribute("class").contains("open")){
                getMessagesDropdown().click();
                pageWait.until(
                        ExpectedConditions.and(
                                ExpectedConditions.attributeContains(getMessagesDropdown(), "class", "open"),
                                ExpectedConditions.elementToBeClickable(element)
                        )
                );
            }

            element.click();
        }

        public WebElement getMessagesDropdown() {return messagesDropdown;}


    }

    @Step("{method}")
    public void clickMineralTreeSSO(){
        pageWait.until(ExpectedConditions.visibilityOf(mineralTreeSSO));
        explicitPageWait();
        launchInNewTab(driver,()->{enterMineralTreeSSO();},
                ()->{validateMineralTreeSSO();
                });
    }

    @Step("{method}")
    private Runnable enterMineralTreeSSO() {
        System.out.println("entered Mineral Tree SSO");
        mineralTreeSSO.click();
        explicitPageWait();
        return null;
    }

    @Step("{method}")
    private void validateMineralTreeSSO(){
        explicitPageWait();
        String uOpen = driver.getCurrentUrl();
        Assert.assertEquals(uOpen.substring(0,39),"https://businessbillpay.mineraltree.net");
        pageWait.until(ExpectedConditions.visibilityOf(mineralTreeHeading1));
        Assert.assertEquals(mineralTreeHeading1.getText().trim(),"ACCOUNTS");
        Assert.assertEquals(mineralTreeHeading2.getText().trim(),"BANK CASH*");
        Assert.assertEquals(mineralTreeHeading3.getText().trim(),"GENERAL LEDGER CASH†");
        System.out.println("Mineral Tree SSO is Up");
    }

    @Step("{method}")
    public void clickPrintMailSSO() {
        pageWait.until(ExpectedConditions.visibilityOf(printMailSSO));
        explicitPageWait();
        launchInNewTab(driver,()->{enterPrintMailSSO();},
                ()->{validatePrintMailSSO();
                });
    }

    @Step("{method}")
    private Runnable enterPrintMailSSO() {
        printMailSSO.click();
        explicitPageWait();
        return null;
    }

    @Step("{method}")
    private void validatePrintMailSSO(){
        explicitPageWait();
        String uOpen = driver.getCurrentUrl();
        Assert.assertEquals(uOpen.substring(0,29),"https://www.qa.estatements.io");
        pageWait.until(ExpectedConditions.visibilityOf(printMailHeading1));
        Assert.assertEquals(printMailHeading1.getText().trim(),"ELECTRONIC DOCUMENT DELIVERY DISCLOSURE AGREEMENT");
        System.out.println("PrintMail SSO is Up");
    }

    @Step("{method}")
    public void clickMerdianLinkSSO(){
        pageWait.until(ExpectedConditions.visibilityOf(merdianLink));
        explicitPageWait();
        launchInNewTab(driver,()->{enterMerdianSSO();},
                ()->{validateMerdianSSO();
                });
    }

    @Step("{method}")
    private Runnable enterMerdianSSO() {
        merdianLink.click();
        explicitPageWait();
        return null;
    }

    @Step("{method}")
    private void validateMerdianSSO(){
        explicitPageWait();
        String uOpen = driver.getCurrentUrl();
        Assert.assertEquals(uOpen.substring(0,24),"https://demo.loanspq.com");
        pageWait.until(ExpectedConditions.visibilityOf(merdianLinkHeading));
        Assert.assertEquals(merdianLinkHeading.getText().trim(),"Login Verification");
        System.out.println("Merdian SSO is Up");
    }


    @Step("{method}")
    public void clickAchAlertSSO(){
        pageWait.until(ExpectedConditions.visibilityOf(achAlertSSO));
        explicitPageWait();
        launchInNewTab(driver,()->{enterAchAlertSSO();},
                ()->{validateAchAlertSSO();
                });
    }

    @Step("{method}")
    private Runnable enterAchAlertSSO() {
        System.out.println("entered Ach Alert SSO");
        achAlertSSO.click();
        explicitPageWait();
        return null;
    }

    @Step("{method}")
    private void validateAchAlertSSO(){
        explicitPageWait();
        String uOpen = driver.getCurrentUrl();
        if(browser.environment=="QA"){
            Assert.assertEquals(uOpen.substring(0,22),"https://client.fphq.us");
        }else{
            Assert.assertEquals(uOpen.substring(0,22),"https://client.fphq.us");
        }
        pageWait.until(ExpectedConditions.visibilityOf(achAlertHeading1));
        Assert.assertEquals(achAlertHeading1.getText().trim(),"Dashboard");
        System.out.println("ACH Alert SSO is Up");
    }

    @Step("{method}")
    public void clickHighCottonSSO(){
        pageWait.until(ExpectedConditions.visibilityOf(highCottonSSO));
        explicitPageWait();
        launchInNewTab(driver,()->{enterHighCottonSSO();},
                ()->{validateHighCottonSSO();
                });
    }

    @Step("{method}")
    private Runnable enterHighCottonSSO() {
        System.out.println("entered High Cotton SSO");
        highCottonSSO.click();
        explicitPageWait();
        return null;
    }

    @Step("{method}")
    private void validateHighCottonSSO(){
        explicitPageWait();
        String uOpen = driver.getCurrentUrl();
        Assert.assertEquals(uOpen.substring(0,24),"https://fd.account.tools");
        pageWait.until(ExpectedConditions.visibilityOf(highCottonTransactions));
        highCottonTransactions.click();
        System.out.println("High Cotton SSO is Up");
    }


    @Step("{method}")
    public void clickOakworthSunGard(){
        pageWait.until(ExpectedConditions.visibilityOf(oakworthSungard));
        explicitPageWait();
        launchInNewTab(driver,()->{enteroakworthSungard();},
                ()->{validateOakWorth();
                });
    }

    @Step("{method}")
    private Runnable enteroakworthSungard() {
        System.out.println("entered Oakworth Portfolio SSO");
        oakworthSungard.click();
        explicitPageWait();
        return null;
    }

    @Step("{method}")
    private void validateOakWorth(){
        explicitPageWait();
        explicitPageWait();
        pageWait.until(ExpectedConditions.visibilityOf(oakworthSunGardHeading));
        Assert.assertEquals(oakworthSunGardHeading.getText().trim(),"Investment Summary");
        pageWait.until(ExpectedConditions.visibilityOf(oakworthSungardHeading2));
        Assert.assertEquals(oakworthSungardHeading2.getText().trim(),"Cash and Equivalents");
        System.out.println(" Oak Worth Portfolio (sungard) is Up");
    }


    @Step("{method}")
    public void clickOptriaStatements(){
        pageWait.until(ExpectedConditions.visibilityOf(optriaStatements));
        explicitPageWait();
        launchInNewTab(driver,()->{enterOptriaStatements();},
                ()->{validateOptriaStatements();
                });
    }


    @Step("{method}")
    private Runnable enterOptriaStatements(){
        System.out.println("entered Optria Statements SSO");
        optriaStatements.click();
        explicitPageWait();
        return null;
    }

    @Step("{method}")
    private void validateOptriaStatements(){
        explicitPageWait();
        String ensenta = driver.getCurrentUrl();
        Assert.assertEquals(ensenta.substring(0,41),"https://estatements.prosperitybankusa.com");
        pageWait.until(ExpectedConditions.visibilityOf(optriaStatementsHeading));
        Assert.assertEquals(optriaStatementsHeading.getText().trim(),"All of your account statements are automatically available online. You can choose to stop receiving paper copies in the mail.");
        System.out.println("Validated Optria Statements is Up");
    }

    @Step("{method}")
    public void validateCountryServices(){
        countryServices.click();
        pageWait.until(ExpectedConditions.visibilityOf(ssoIframe));
        driver.switchTo().frame(ssoIframe);
        pageWait.until(ExpectedConditions.elementToBeClickable(countryServicesTitle));
        Assert.assertEquals(countryServicesTitle.getText().trim(),"eServices");
        Assert.assertEquals(countryServicesLink.getText().trim(),"Debit Card Overdraft Coverage");
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

    @Step("Checking if {0} ACH falls under holiday.")
    public void skipACHTestIfHoliday(Batch batch){
        if(batch.scheduleDateIsHoliday(batch) == true){
            browser.skipThisTest("Schedule date falls under holiday or weekend, no wire transfer will run...");
        }
    }

    @Step("Check if env is PROD")
    public void runTestIfProdEnvElseSkip(){
        if(!pages.login.getEnvironment().equals("PROD")){
            browser.skipThisTest("This test is only for the Production environment.");
        }
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
