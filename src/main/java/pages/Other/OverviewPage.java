package pages.Other;

import core.APIs.BrowserAPIs.AccountsBrowserAPI;
import core.APIs.BrowserAPIs.GetAccSpendingPrefBrowserAPI;
import core.basePages.AllCommunityPages;
import core.basePages.BaseCommunityPage;
import core.utilities.baseUtilities.Browser;
import core.utilities.objects.Home;
import io.qameta.allure.Step;
import io.restassured.http.Method;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class OverviewPage extends BaseCommunityPage {

    public String homeTransferTabStatus;
    public String homeSpendableBalance;
    public double negativeBalanceCheck;
    public String entity;
    public static String timeStampOnHome = "";

    public HashMap<String, String> accountMap = new HashMap<String, String>();
    List<String> accountTitle = new ArrayList<>();

    @FindBy(xpath = ".//div[contains(@class,'pull-right')]//a[contains(@href,'transfers')]")
    WebElement transferButton;

    @FindBy(xpath = ".//div[contains(@class,'account-name-wrap')]")
    WebElement accountNicknameFirstAccountDisplayed;

    @FindBy(xpath = "(.//div[contains(@class,'account-name-wrap')])[2]")
    WebElement accountNicknameSecondAccountDisplayed;

    @FindBy(xpath = ".//button[contains(@id,'alertsTopNav-dropdown')]")
    WebElement alertsNotificationsBtn;

    @FindBy(xpath = ".//button[contains(@id,'messagesTopNav-dropdown')]")
    WebElement messagesTopNavBtn;

    @FindBy(xpath = ".//span[contains(text(),'New')]")
    WebElement newMessageBtn;

    @FindBy(xpath = "//button[@name='Cancel']")
    WebElement newMessageCancelBtn;

    @FindBy(xpath = ".//span[contains(text(),'All Messages')]")
    WebElement viewAllMessages;

    @FindBy(xpath = ".//div[@class='urgent-messages-content']")
    WebElement urgentMessages;

    @FindBy(xpath = ".//span[contains(text(),'View All')]")
    WebElement viewAllAlertsBtn;

    @FindBy(xpath = ".//a[@id='home_tab']")
    WebElement homeOverview;

    @FindBy(xpath = ".//a[@class='box-title' and contains(@href,'/messages')]/span")
    WebElement unreadMessagesBtn;
    
    @FindBy(xpath=".//a[@class='box-title' and contains(@href,'/messages')]/span[2]")
    WebElement unreadMessageHeader;

    @FindBy(xpath=".//a[@class='box-title' and contains(@href,'/messages/')]//following::div[@class='messages-widget']")
    WebElement unreadMessagesBoxText;

    @FindBy(xpath=".//button[contains(text(),'Delete')]")
    WebElement deleteMessage;

    @FindBy(xpath=".//button[contains(text(),'Delete')]")
    WebElement confirmDeleteMessage;

    @FindBy(xpath=".//div[@class='page-help']//span[2]")
    WebElement headerDate;

    @FindBy(xpath=".//a[contains(@title,'Checking')]//ancestor::table/thead//th[contains(@class,'pc45')]")
    WebElement checkingHeaderName;

    @FindBy(xpath=".//a[contains(text(),'Checking')]")
    WebElement checkingHeaderNameComm;

    @FindBy(xpath="(//td[@class='text-right accountBalanceTotalAmount show-logos'])[1]")
    WebElement checkingAvailableTotal;

    @FindBy(xpath="(//td[@class='text-right accountBalanceTotalAmount show-logos'])[2]")
    WebElement checkingCurrentTotal;

    @FindBy(xpath=".//a[contains(@title,'Saving')]//ancestor::table/thead//th[contains(@class,'pc45')]")
    WebElement savingHeaderName;

    @FindBy(xpath=".//a[contains(text(),'Savings')]")
    WebElement savingHeaderNameComm;

    @FindBy(xpath="(//td[@class='text-right accountBalanceTotalAmount show-logos'])[3]")
    WebElement savingAvailableTotal;

    @FindBy(xpath="(//td[@class='text-right accountBalanceTotalAmount show-logos'])[4]")
    WebElement savingCurrentTotal;

    @FindBy(xpath=".//a[contains(text(),'Loans & Credit Cards')]")
    WebElement loanHeaderName;

    @FindBy(xpath="(.//td[@class='text-right accountBalanceTotalAmount show-logos'])[8]")
    WebElement loanAvailableTotal;

    @FindBy(xpath="(//td[@class='text-right accountBalanceTotalAmount show-logos'])[7]")
    WebElement loanCurrentTotal;

    @FindBy(xpath=".//a[contains(@title,'Saving')]//ancestor::table/tfoot//div[@class='pull-left viewingPagination']")
    WebElement savingPagination;

    @FindBy(xpath=".//a[contains(@title,'Checking')]//ancestor::table/tfoot//div[@class='pull-left viewingPagination']")
    WebElement checkingPagination;

    //*****************************   Account Related POM ************************************

    @FindBy(xpath=".//a[@id='accounts_tab']")
    WebElement accountsTab;

    @FindBy(xpath=".//button[contains(@href,'outsideAccounts')]")
    WebElement outsideAccountsTab;

    @FindBy(xpath = ".//tr//a[contains(@href,'accounts/details')]")
    List<WebElement> listOfAccounts;

    @FindBy(xpath=".//tr//a[contains(@href,'/accounts/details/')]")
    WebElement accountDetail;

    @FindBy(xpath = ".//tr/td/div[@class='account-name-wrap']")
    List<WebElement> listOfAccountsTitle;

    @FindBy(xpath = ".//div[contains(text(),'All ACCOUNTS')]")
    WebElement allAccountsTab;

    @FindBy(xpath = ".//div[contains(text(),'FAVORITES')]")
    WebElement favoritesTab;

    @FindBy(xpath="//input[@id='typeahead-template']")
    WebElement accountLocator;

    @FindBy(xpath=".//span[@id='nickname']")
    WebElement nicknameTopNav;

    @FindBy(xpath=".//button[@id='messagesTopNav-dropdown']")
    WebElement messagesTopNav;

    @FindBy(xpath=".//button[@id='alertsTopNav-dropdown']")
    WebElement alertsTopNav;

    @FindBy(xpath = ".//button[@class='login-history-modal btn-link']")
    WebElement lastLoginButton;

    @FindBy(xpath = ".//button[@class='login-history-modal btn-link']//span[2]")
    WebElement lastLoginText;

    private String account                   = ".//tr[@id='%s']";
    private String accountName               = ".//tr[@id='%s']//span[contains(@class,'account-name-font')]";
    private String maskedAccountNum          = ".//tr[@id='%s']//span[@class='account-number-masked']";
    private String favoriteIndicator         = ".//tr[@id='%s']//span/a[contains(@aria-controls,'favorite')]";
    private String availableBalance          = ".//tr[@id='%s']//td[contains(@class,'accountBalanceAmount') and contains(@id,'acc-balance-one')]";
    private String currentBalance            = ".//tr[@id='%s']//td[contains(@class,'accountBalanceAmount') and contains(@id,'acc-balance-two')]";
    private String accountLinkFormat         = ".//span[contains(text(), '%s')]/parent::div/span[contains(@class, 'account-name-font')]";
    private String accountDetailSpecfic      = ".//tr//a[contains(text(),'%s')]";
    private String accountList               = ".//tr//a[contains(@href,'/accounts/details/%s')]";
    private String header1                   = "available-balance";
    private String header2                   = "current-balance";
    private String mySpendingNavCategoryName = ".//div[contains(@class,'category-watcher')]//strong[contains(text(),'%s')]";

    @FindBy(xpath=".//th[contains(text(),'I Can Spend')]//following::tfoot//pagination//a[contains(text(),'Next')]")
    WebElement moneySpendPaginationNext;

    @FindBy(xpath=".//th[@class='table-title _pc45' and contains(text(),'Saving')]//following::tfoot//pagination//a[contains(text(),'Next')]")
    WebElement moneySavingPaginationNext;

    @FindBy(xpath=".//div[@class='col-sm-7']")
    WebElement outsideAccountText;

    @FindBy(xpath=".//span[contains(text(),'Outside')]")
    WebElement addOutsideAccountButton;

    @FindBy(xpath=".//a/span[contains(text(),'More Details')]")
    WebElement moreDetailAndRoutingNo;

    @FindBy(xpath=".//button[contains(text(),'Download File')]")
    WebElement downloadFile;

    @FindBy(xpath=".//button[@name='categoryTxn']")
    WebElement categoryDropdown;

    //***************************  My Spending NAV *******************************

    @FindBy(xpath=".//a[@id='left-navigation-spending-teaser-title']")
    WebElement mySpendingNavHeader;

    @FindBy(xpath=".//span[@class='spending-teaser-title']")
    WebElement mySpendingNavTitle;

    @FindBy(xpath=".//div[contains(@class,'pay-period')]//div/div[1]")
    WebElement mySpendingNavPayPeriod;

    @FindBy(xpath=".//div[contains(@class,'pay-period')]//div/div[2]//span")
    WebElement mySpendingNavTotalSpendingDate;

    @FindBy(css=".set-state-spent")
    WebElement mySpendingNavTotalSpendingAmountLeft;

    @FindBy(xpath=".//div[@class='category-watch-info clearfix']/div")
    WebElement mySpendingNavCategoryWatchHeader;

    @FindBy(xpath=".//div[@class='category-watch-info clearfix']/div/span")
    WebElement mySpendingNavCategoryWatchMonth;

    @FindBy(xpath=".//div[@class='pull-right text-right']/span")
    WebElement mySpendingNavCategoryWatchDaysLeft;

    //*****************************   Favorites Related POM **********************************

    private String clickFavorite = ".//tr[@id='%s']//a[@ng-click='olbTable.toggleFavorite( account )']";
    private String alreadyFavorite = ".//tr[@id='%s']//div[contains(@id,'favorite')]";

    //***************************** Spendable Balance POM *************************************

    private String findSBAccountText = ".//tr[@id='%s']//span[contains(@class,'account-name-font')]//following::div/em";

    @FindBy(xpath = "//span[contains(@class, 'masked-font')]")
    WebElement sbPrimaryAccount;

    @FindBy(xpath = ".//a[@class='fa fa-expand col-xs-1 no-padding pull-right']")
    WebElement expandSB;

    @FindBy(xpath = ".//div[@class='main-label']/h5")
    WebElement sbMainLabel;

    @FindBy(xpath = ".//button[@name='spendableBalance']")
    WebElement sbAccountDropdown;

    private String changeSBAccount = ".//span[contains(@class,'spendable-balance-list-account-number') and contains(text(),'%s')]";

    private String changeSBFrequency = ".//span[contains(@class,'spendable') and contains(text(),'%s')]";

    @FindBy(xpath = ".//div/span[@class='allow-wrap break-word spendable-truncate']")
    WebElement sbAccountSingleAccount;

    @FindBy(xpath = "//div[@class='form-group spendable-dropdown-available-balance clearfix']//span[contains(@class, 'acct_checking_usable_bal')]")
    WebElement sbOldFinalAmount;

    @FindBy(xpath = "//div[@class='form-group spendable-dropdown-available-balance clearfix']//span[contains(@class, 'labeled')]")
    WebElement sbTextUnderAccountDropdown;

    @FindBy(xpath = ".//input[@id='transferPayments']")
    WebElement pendingTransactionCheckbox;

    @FindBy(xpath = ".//input[@id='transferPayments' and @aria-checked='false']")
    List<WebElement> pendingTransactionCheckboxStatus;

    @FindBy(xpath=".//div[@class='module spendable-balance-box spendable-balance-box-gray']")
    List<WebElement> sbBoxColorGray;

    @FindBy(xpath=".//div[@class='module spendable-balance-box spendable-balance-box-red']")
    List<WebElement> sbBoxColorRed;

    @FindBy(xpath=".//div[@class='labeled col-xs-11 no-padding']")
    WebElement sbBoxTextGray;

    @FindBy(xpath=".//div[@class='labeled col-xs-11 no-padding overflow-ellipsis']")
    WebElement getSbBoxTextRed;

    @FindBy(xpath = ".//span[@class='pending-text']")
    WebElement textUnderPendingTransactionCheckbox;

    @FindBy(xpath = ".//button[@name='pendingTransfers']")
    WebElement sbTransactionFrequency;

    @FindBy(xpath = ".//div[@class='date-amt']")
    List<WebElement> transactionDates;

    String transactionDate = ".//div[@id='pendingTransaction']/div[%s]//div[@class='date-amt']";

    String transactionDescription = ".//div/div[%s]//div[@class='description']";

    String transactionAmount = ".//div/div[%s]//div[@class='date-amt right']";

    @FindBy(xpath = "//span[@id='popover']//span[contains(@class, 'dollarSymbol')][1]")
    WebElement transactionTotalAmount;

    @FindBy(xpath = ".//span[@id='balance']")
    WebElement totalSubtracted;

    @FindBy(xpath = "//div[@class='spendable-grow-footer']//span[contains(@class, 'acct_checking_usable_bal')][2]")
    WebElement sbFinalAmount;

    @FindBy(xpath = ".//ngb-popover-window//div[@class='header']/span")
    WebElement popupHeader;

    @FindBy(xpath = ".//ngb-popover-window")
    List <WebElement> popupHeaders;

    @FindBy(xpath = ".//ngb-popover-window//div[@class='content']")
    WebElement popupContent;

    @FindBy(xpath = ".//span[@class='fa fa-times-circle']")
    WebElement closeSB;

    @FindBy(xpath = ".//tooltip")
    WebElement sbTooltip;

    @FindBy(xpath = ".//div[@class='popover-body']")
    WebElement toolTipText;

    @FindBy(xpath=".//span[@class='account-name-font']//a[contains(@href,'27658027')]")
    WebElement accountDetailInfoSSOView;

    @FindBy(xpath=".//a[contains(text(),'Get Info')]")
    WebElement getInfoSSO;

    @FindBy(xpath=".//a[contains(text(),'Statements')]")
    WebElement globalPointSSO;

    @FindBy(xpath=".//td[@id='SectionSubClientID']")
    WebElement globalPointHeading;

    @FindBy(xpath=".//iframe[@name='sso']")
    WebElement ssoIframe;

    @FindBy(xpath="//frame[@name='content']")
    WebElement FISIframe;

    @FindBy(linkText = "Manage HSA Account")
    WebElement wexSSO;

    @FindBy(xpath="//a[contains(text(),'Statements ')]")
    WebElement FidelityStatements;

    @FindBy(xpath=".//div/h1[@class='pageTitle']")
    WebElement infoSSOHeading;

    @FindBy(xpath=".//span[@class='account-name-font']//a[contains(@href,'#/accounts/detail')]")
    WebElement accountDetailFutureView;

    @FindBy(xpath="//a[.='Future view']")
    WebElement futureView;

    @FindBy(xpath=".//a[contains(@href,'select_document')]")
    WebElement statementAndDocuments;

    @FindBy(xpath=".//div[@class='page-content legacy']/p[1]")
    WebElement fvMainHeading;

    @FindBy(xpath=".//a[@href='javascript://']")
    WebElement fvIwantTo;

    @FindBy(xpath=".//li/a[contains(@href,'future_trans_view')]")
    WebElement fvAddTransaction;

    @FindBy(xpath=".//div[@class='module-inner']/h3")
    WebElement fvAddPageHeading;

    @FindBy(xpath=".//input[@name='new_tran']")
    WebElement fvAdd;

    @FindBy(xpath=".//div[@class='module-inner']/h3")
    WebElement fvAddPageHeading1;

    @FindBy(xpath=".//input[@name='trans1::next_date']")
    WebElement fvNextTransactionDate;

    @FindBy(xpath=".//input[@name='trans1::description']")
    WebElement fvNextTransactionDescription;

    @FindBy(xpath=".//input[@name='trans1::amount']")
    WebElement fvNextTransactionAmount;

    @FindBy(xpath=".//input[@value='Submit']")
    WebElement fvSubmit;

    @FindBy(xpath=".//tr[@class='ledger1']/td[2]")
    WebElement fvValidateDate;

    @FindBy(xpath=".//tr[@class='ledger1']/td[3]")
    WebElement fvTransactionDescription;

    @FindBy(xpath=".//tr[@class='ledger1']/td[4]")
    WebElement fvTransactionType;

    @FindBy(xpath=".//tr[@class='ledger1']/td[5]")
    WebElement fvTransactionAmount;

    @FindBy(xpath=".//tr[@class='ledger1']/td[6]")
    WebElement fvTransactionFrequency;

    @FindBy(xpath="//table[@class='module-rows']//tbody//input")
    WebElement fvCheckBox;

    @FindBy(xpath=".//input[@value='Delete']")
    WebElement fvDelete;

    @FindBy(xpath=".//input[@value='Return to Future View']")
    WebElement fvReturn2Fv;

    @FindBy(xpath=".//div[@id='edoc_name_range']")
    WebElement estatementHeading;

    @FindBy(xpath = ".//select[@id='time_filter']")
    WebElement estatementTimeDropdown;

    @FindBy(xpath=".//table[@class='module-rows']//tr[2]//td[2]")
    WebElement estatementId1;

    @FindBy(xpath=".//table[@class='module-rows']//tr[3]//td[2]")
    WebElement estatementId2;

    @FindBy(xpath=".//table[@class='module-rows']//tr[2]//td[1]")
    WebElement estatementOpenPDF;

    //***************************** Account Detail POM *************************************

    @FindBy(xpath =".//a[@id='cards-tab=title']")
    WebElement cardControl;

    //***************************** Other Helpful  POM *************************************

    @FindBy(xpath = ".//a[@id='my_spending_tab']")
    WebElement mySpendingTab;

    @FindBy(xpath = ".//div[@class='set-state-spent']")
    WebElement payperiodLimit;

    @FindBy(xpath = ".//div[contains(@class,'pull-right')]//span[2]")
    WebElement numDaysLeft;

    @FindBy(xpath = ".//a[@id='left-navigation-spending-teaser-title']")
    WebElement mySpendingLinkOnTeaser;

    @FindBy(xpath = ".//a[@id='wires_tab']")
    WebElement wireTransfersTab;

    @FindBy(xpath = ".//a[@title='Wire Transfer Home']")
    WebElement wireTransferHome;

    @FindBy(xpath = ".//a[@id='wires_tab_sendsinglewiretransfer']")
    WebElement wireTransferSendSingleWireTransfer;

    @FindBy(xpath = ".//a[@id='wires_tab_wirehistory']")
    WebElement wireTransfersWireHistory;

    @FindBy(xpath = ".//h2[contains(text(),'Wire Transfer History')]")
    WebElement wireTransfersHistoryTitle;

    @FindBy(xpath = ".//h3[contains(text(),'Schedule Single Wire Transfer')]")
    WebElement wireTransferHomeTitle;

    @FindBy(xpath = ".//h3[contains(text(),'Send Single Wire Transfer')]")
    WebElement wireTransferSendSingleTitle;

    //****************************************** Outside accounts POM *****************************

    @FindBy(xpath=".//div[contains(@class,'outside-accounts')]//button")
    WebElement outsideAccountModal;

    @FindBy(xpath=".//tr[@id='']//td[4]//a[@role='button']")
    WebElement firstOutsideAccount;

    @FindBy(xpath=".//div[@class='modal-content']//ul//li[2]")
    WebElement outsideAccountSettings;

    public OverviewPage(Browser browser, AllCommunityPages fundsXpressPages ) {
        super(browser, fundsXpressPages);
    }

    public void logOntoFiservPayments(){
        paymentsBtn.click();
        // Selenium can't access elements withing an iframe directly so for now we are simply asserting that the iframe is present
        // To access data within the frame driver.SwitchTo().Frame() needs to be called
        Assert.assertTrue(isElementPresent(paymentsIframe,10), "third-party-frame should be present");
    }

    @Override
    public ExpectedCondition getPageLoadExpectedConditions() {
        return ExpectedConditions.and(
                ExpectedConditions.visibilityOf(homeOverview)
        );
    }

    @Step("{method}")
    public void clickTransfer() {
        boolean visible = driver.findElements(By.xpath("//div[contains(@class,'pull-right')]//a[contains(@href,'transfers')]")).size() > 0;
        if (!visible) {
            homeTransferTabStatus = "Unable to find Transfer tab in Home Page";
        } else if (visible) {
            transferButton.click();
        }

    }

    public String getAccountTitle( int i ) {
        List<WebElement> list = listOfAccountsTitle;
        List<String> accountTitle = new ArrayList<>();
        for (WebElement we : list) {
            String String1 = we.getText().trim();
            String String2 = String1.split("Toggle")[0].trim();
            accountTitle.add(String2);
        }
        return accountTitle.get(i);
    }

    /**
     * User getAccountEntity directly
     */
    @Deprecated
    public String getAccountTitleWithString( String search ) {
        List<WebElement> list = listOfAccountsTitle;

        for (WebElement we : list) {
            String string1 = we.getText().trim();
            String string2 = string1.split("Toggle")[0].trim();
            if (string2.contains(search)) {
                String string3 = string1.split("Spendable Balance Account")[0].trim();
                accountTitle.add(string3);
                System.out.println(String.format("AccountTitle:%s",string3));
            }
        }
        return accountTitle.get(0);
    }

    public String getAccountEntity( String search ) {
        List<WebElement> list = listOfAccounts;
        for (WebElement we : list) {
            String href = we.getAttribute("href");
            String title = we.getAttribute("title");
            String accountNumStart = href.split("/accounts/details/")[1];
            String accountNumOnly = accountNumStart.split("/")[0];
            if(title.contains(search)) {
                return accountNumOnly;
            }
        }
        System.out.println("Account not found :"+search);
        return null;
    }

    /**
     * Navigates to the account page with the given account number.
     * This method has been deprecated because the ownedAccounts table does not exist in the community_ui_customers table
     * Use clickAccountDetail() instead
     * @param acctNumber - Full account number
     */
    @Deprecated
    public void navigateToAccountPage( String acctNumber ) {
        System.out.println(acctNumber.substring(acctNumber.length()));
//         pageWait.until(ExpectedConditions.visibilityOf(accountName));
//         accountName.click();
        driver.findElement(By.xpath(String.format(accountLinkFormat, acctNumber.substring(acctNumber.length() - 4)))).click();
        //pages.activityPage.waitForPageLoad();
        pageWait.until(ExpectedConditions.elementToBeClickable(downloadFile));
    }

    public void urgentMessagesVisible(){
        pageWait.until(ExpectedConditions.visibilityOf(urgentMessages));
    }

    public void navigateToMessagesTab() {
        pageWait.until(ExpectedConditions.visibilityOf(messagesTopNavBtn));
        messagesTopNavBtn.click();
    }


    @Step("{method}")
    public void navigateToWireTransfersHome() {
        pageWait.until(ExpectedConditions.visibilityOf(wireTransfersTab));
        wireTransfersTab.click();
        wireTransferHome.click();
        pageWait.until(ExpectedConditions.visibilityOf(wireTransferHomeTitle));
    }

    @Step("{method}")
    public void navigateToWireTransfersSendSingleWireTransfer() {
        pageWait.until(ExpectedConditions.visibilityOf(wireTransfersTab));
        wireTransfersTab.click();
        wireTransferSendSingleWireTransfer.click();
        pageWait.until(ExpectedConditions.visibilityOf(wireTransferSendSingleTitle));
    }

    @Step("{method}")
    public void navigateToWireTransfersWireHistory() {
        pageWait.until(ExpectedConditions.visibilityOf(wireTransfersTab));
        wireTransfersTab.click();
        wireTransfersWireHistory.click();
        pageWait.until(ExpectedConditions.visibilityOf(wireTransfersHistoryTitle));
    }

    @Step("{method}")
    public String getLoanAccountNickname() {
        pageWait.until(ExpectedConditions.visibilityOf(accountNicknameFirstAccountDisplayed));
        return accountNicknameFirstAccountDisplayed.getText();
    }

    public String getSecondAccountDisplayed() {
        return accountNicknameSecondAccountDisplayed.getText();
    }

    public int getHomeAccountSize() {
        return (listOfAccounts.size());
    }

    public void clickAccountDetail(){
        System.out.println("Account Detail Page");
        explicitPageWait();
        explicitPageWait();
        //browser.logTestAction("Click on Account Detail");
        clickElementOnceClickable(accountDetail);
        explicitPageWait();
    }

    public void clickAccountDetail(String name){
        System.out.println("Account Detail Page");
        explicitPageWait();
        driver.findElement(By.xpath(String.format(accountDetailSpecfic, name))).click();
        explicitPageWait();
    }

    public void clickAccountDetailAndMoreDetail(String name){
        clickAccountDetail(name);
        moreDetailAndRoutingNo.click();
        explicitPageWait();
    }

    public void clickAccountDetailAndDownloadFile(String name){
        clickAccountDetail(name);
        explicitPageWait();
        pageWait.until(ExpectedConditions.elementToBeClickable(downloadFile));
        downloadFile.click();
        explicitPageWait();
    }

    public void clickCategoryDropdown(String name) {
        clickAccountDetail(name);
        explicitPageWait();
        pageWait.until(ExpectedConditions.elementToBeClickable(categoryDropdown));
        categoryDropdown.click();
        explicitPageWait();
    }

    public void clickOutSideAccounts(){
        accountsTab.click();
        pageWait.until(ExpectedConditions.elementToBeClickable(outsideAccountsTab));
        outsideAccountsTab.click();
        explicitPageWait();

    }

    public void clickOutsideAccountModal() {
        pageWait.until(ExpectedConditions.elementToBeClickable(outsideAccountModal));
        outsideAccountModal.click();
        explicitPageWait();
    }

    public void clickSpecficOutsideAccount() {
        pageWait.until(ExpectedConditions.elementToBeClickable(firstOutsideAccount));
        firstOutsideAccount.click();
        explicitPageWait();
    }

    public void clickOutsideAccountSettings() {
        pageWait.until(ExpectedConditions.elementToBeClickable(outsideAccountSettings));
        outsideAccountSettings.click();
        explicitPageWait();

    }

    public void clickEStatementandDocuments(){
        clickElementOnceClickable(accountDetailFutureView);
        System.out.println("entered Estatements and Documents View");
        pageWait.until(ExpectedConditions.visibilityOf(statementAndDocuments));
        statementAndDocuments.click();
        explicitPageWait();
        pageWait.until(ExpectedConditions.visibilityOf(estatementHeading));
        Assert.assertEquals(estatementHeading.getText().trim().substring(0,22),"Statements & Documents");
        estatementTimeDropdown.sendKeys("Last Year");
        explicitPageWait();
        Assert.assertEquals(estatementId1.getText().trim(),"187252849");
        Assert.assertEquals(estatementId2.getText().trim(),"187254021");

    }

    public void clickInfoLinkPlusAccount(){
        clickElementOnceClickable(accountDetailInfoSSOView);
        clickElementOnceClickable(getInfoSSO);
        pageWait.until(ExpectedConditions.visibilityOf(ssoIframe));
        driver.switchTo().frame(ssoIframe);
        Assert.assertEquals(infoSSOHeading.getText().trim(),"Account Home");
        System.out.println("InfoLinkPlus SSO is working");
    }

    @Step("{method}")
    public void validateGlobalPointSSO(){
        clickElementOnceClickable(accountDetailFutureView);
        launchInNewTab(driver,()->{clickElementOnceClickable(globalPointSSO);},
                ()->{
                    explicitPageWait();
                    String pdfUrl = driver.getCurrentUrl();
                    Assert.assertEquals(pdfUrl.substring(0,35),"https://gvuat-us.computershare.com/");
                    pageWait.until(ExpectedConditions.visibilityOf(globalPointHeading));
                    Assert.assertEquals(globalPointHeading.getText().trim(),"eStatement History");
                    System.out.println("GlobalPoint SSO is working");
                });
    }

    @Step("{method}")
    public void validateWexSSO(){
        clickAccountDetail("HSA Account");
        launchInNewTab(driver,()->{clickElementOnceClickable(wexSSO);},
        () -> {
            String currentURL = driver.getCurrentUrl();
            Assert.assertTrue(currentURL.contains("https://stgpinnacleparticipant.lh1ondemand.com"), String.format("Validating url. CurrentUrl:%s",currentURL));
            isElementPresent(By.linkText("Home"),5);
            isElementPresent(By.linkText("Accounts"),1);
        });
    }

    @Step("{method}")
    public void clickEStatement(){
        launchInNewTab(driver,()->{enterEstatmentsPDFView();},
                ()->{validatPDFView();
                });
    }

    @Step("{method}")
    private Runnable enterEstatmentsPDFView(){
        System.out.println("entered Estatements PDF View");
        pageWait.until(ExpectedConditions.visibilityOf(estatementOpenPDF));
        estatementOpenPDF.click();
        explicitPageWait();
        return null;
    }

    @Step("{method}")
    private void validatPDFView(){
        explicitPageWait();
        String pdfUrl = driver.getCurrentUrl();
        Assert.assertEquals(pdfUrl.substring(0,35),"https://cat-imaging.fundsxpress.com");
        System.out.println("Estatement is working");
    }

    @Step("{method}")
    private Runnable enterFutureView(){
        browser.logTestAction("entered Future View");
        waitForJsCallsToComplete();
        clickElementOnceClickable(futureView);
        explicitPageWait();
        System.out.println("Clicked future under account detail");
        return null;
    }

    public void clickFutureView(){
        clickElementOnceClickable(accountDetailFutureView);
        launchInNewTab(driver,()->{enterFutureView();},
                ()->{validateFutureView();
                addFutureViewTransaction();
                });
    }

    @Step("{method}")
    private void validateFutureView(){
        explicitPageWait();
        pageWait.until(ExpectedConditions.visibilityOf(fvMainHeading));
        Assert.assertEquals(fvMainHeading.getText().trim(),"Account Future View");
        pageWait.until(ExpectedConditions.visibilityOf(fvIwantTo));
        fvIwantTo.click();
        explicitPageWait();
        fvAddTransaction.click();
        System.out.println("Trying to add a new transaction for future view");
        explicitPageWait();
        pageWait.until(ExpectedConditions.visibilityOf(fvAddPageHeading));
        Assert.assertEquals(fvAddPageHeading.getText().trim(),"Transactions");
        fvAdd.click();
        explicitPageWait();
        System.out.println("Clicked on add on the next screen for adding new transaction");
        pageWait.until(ExpectedConditions.visibilityOf(fvAddPageHeading1));
        Assert.assertEquals(fvAddPageHeading1.getText().trim(),"Add a Future View Transaction");
        explicitPageWait();
    }

    @Step("{method}")
    private void addFutureViewTransaction(){
        explicitPageWait();
        pageWait.until(ExpectedConditions.visibilityOf(fvAddPageHeading1));
        Assert.assertEquals(fvAddPageHeading1.getText().trim(),"Add a Future View Transaction");
        String date = addDate(2);
        System.out.println("About the enter the date for transaction");
        fvNextTransactionDate.sendKeys(date);
        fvNextTransactionDescription.sendKeys("FutureViewTest");
        fvNextTransactionAmount.sendKeys("5.00");
        fvSubmit.click();
        explicitPageWait();
        System.out.println("Added transaction moving to confirmation page");
        pageWait.until(ExpectedConditions.visibilityOf(fvValidateDate));
        Assert.assertEquals(fvValidateDate.getText().trim(),date);
        Assert.assertEquals(fvTransactionDescription.getText().trim(),"FutureViewTest");
        Assert.assertEquals(fvTransactionType.getText().trim(),"Deposit");
        Assert.assertEquals(fvTransactionAmount.getText().trim(),"$5.00");
        Assert.assertEquals(fvTransactionFrequency.getText().trim(),"One-time");

        pageWait.until(ExpectedConditions.visibilityOf(fvCheckBox));
        WebElement checkbox = fvCheckBox;
        if(!checkbox.isSelected()){
            fvCheckBox.click();
        }
        WebElement checkbox1 = fvCheckBox;

        System.out.println("Validation completed. Selected the transaction for cleaning up");
        pageWait.until(ExpectedConditions.elementToBeClickable(fvDelete));
        fvDelete.click();
        System.out.println("clicked on delete, waiting for confirmation");
        explicitPageWait();
        pageWait.until(ExpectedConditions.elementToBeClickable(fvSubmit));
        fvSubmit.click();
        System.out.println("sucessfully deleted the transaction");
        explicitPageWait();
        pageWait.until(ExpectedConditions.visibilityOf(fvReturn2Fv));
        fvReturn2Fv.click();
        explicitPageWait();

    }

    public String getAccountName( int i ) {
        String nickName = getAccountTitle(i);
        return nickName;
    }

    public String addDate(int date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (date == 0) {
            LocalDate localDate = LocalDate.now();
            return dtf.format(localDate);
        } else {
            LocalDate localDate = LocalDate.now().plusDays(date);
            return dtf.format(localDate);
        }
    }

    public void clickAccountDetailWithEntity() {
        String nickName = getAccountTitle(0);
        String accountEntity = getAccountEntity(nickName);
        System.out.println(nickName);
        System.out.println(accountEntity);
        //driver.findElement(By.xpath(String.format(accountList,accountEntity))).click();
        System.out.println("test case passed with entity");
    }

    public void clickAccountDetailWithString( String search ) {

        getAccountEntity("");
        System.out.println(accountMap);
        int accountSize = accountMap.size();
        System.out.println(accountSize);

        getAccountTitleWithString(search);
        System.out.println(accountTitle);
        System.out.println(accountTitle.size());

        System.out.println("test case passed with string");
    }

    public void markFavorite( String entity ) {

        if (driver.findElement(By.xpath(String.format(alreadyFavorite, entity))).isEnabled()) {
            driver.findElement(By.xpath(String.format(clickFavorite, entity))).click();
        } else {
            System.out.println("Account Already Favorite");
        }

    }

    public void favorite() {
        String entity = getAccountEntity(getAccountTitleWithString("Checking"));
        markFavorite(entity);
        entity = getAccountEntity(getAccountTitleWithString("Saving"));
        markFavorite(entity);
        entity = getAccountEntity(getAccountTitleWithString("Loan"));
        markFavorite(entity);
        favoritesTab.click();
    }

    public void validateSpendableBalanceCommmercial( Home home ) {
        boolean visible = driver.findElements(By.xpath(".//span[@class='masked-font']")).size() > 0;
        if (!visible) {
            homeSpendableBalance = "Unable to find SB in Home Page";
            Assert.assertEquals(homeSpendableBalance, "Unable to find SB in Home Page");
        }
    }

    public void clickComplexSB () {
        expandSB.click();
    }

    public void clickTooltip() {
        sbTooltip.click();
    }

    public void clickFavorites () {
        favoritesTab.click();
    }

    public void clickPagination () {
        moneySpendPaginationNext.click();
    }

    public void clickAccountLocator () {
        accountLocator.click();
    }

    public void clickNicknameTopNav () {
        nicknameTopNav.click();
    }

    public void clickMessageTopNav () {
        messagesTopNav.click();
    }

    public void clickAlertsTopNav () {
        alertsTopNav.click();
    }

    public void enableSimpleSpendableBalance( Home home ) {
        expandSB.click();
        boolean checked = pendingTransactionCheckboxStatus.size() > 0;
        if (checked) {
            pendingTransactionCheckbox.click();
        }
        closeSB.click();
    }

    public void disableSimpleSpendableBalance(Home home){
        expandSB.click();
        boolean checked = pendingTransactionCheckboxStatus.size() > 0;
        if (!checked) {
            pendingTransactionCheckbox.click();
        }
        closeSB.click();
    }

    public void validateBoxColorRed(){
        boolean boxColor = sbBoxColorRed.size() >0;
        if(boxColor) {
            homeSpendableBalance = "Spendable and Available Balances are NEGATIVE";
            Assert.assertEquals(homeSpendableBalance, getSbBoxTextRed.getText());
            System.out.println("Validation Successful");
        }
    }

    public void validateBoxColorGrey(){
        boolean boxColor = sbBoxColorGray.size() > 0;
        if (boxColor) {
            homeSpendableBalance = "Spendable Balance is Negative";
            Assert.assertEquals(homeSpendableBalance, sbBoxTextGray.getText());
            System.out.println("Validation Successful");
        }
    }

    public void selectAnotherSBAccount (Home home){
        expandSB.click();
        sbAccountDropdown.click();
        driver.findElement(By.xpath(String.format(changeSBAccount, home.anotherSBAccount))).click();
        closeSB.click();

    }

    public void checkNewCustomer(Home home) {
        boolean newUser = popupHeaders.size() > 0;
        if (newUser) {
            home.newUser=true;
        }
    }

    @Step("{method}")
    public void validateSpendableBalanceText(Home home){
        String verifySBAccount;
        System.out.println(home.sbAccountEntity);
        verifySBAccount = driver.findElement(By.xpath(String.format(findSBAccountText, home.sbAccountEntity))).getText();
        Assert.assertEquals(verifySBAccount,"Spendable Balance Account");

    }

    @Step("{method}")
    public void validateSpendableBalance( Home home )  {

        pageWait.until(ExpectedConditions.visibilityOf(sbPrimaryAccount));
        browser.logTestPass("Comparing primary account number", sbPrimaryAccount.getText().replace(".",""), home.sbAccountNum.replace(" ",""));
        checkNewCustomer(home);
        browser.logTestPass("Validating Spendable Balance");
        if (!home.newUser && !home.newUserPopup) {
            clickElementOnceClickable(expandSB);
        }

        boolean multipleAccount = driver.findElements(By.xpath(".//button[@name='spendableBalance']")).size() != 0;
        if(!multipleAccount) {
            home.oneAccount = true;
        }

        Assert.assertEquals(sbMainLabel.getText(), "Starts with my primary checking");
        if (home.oneAccount) {
            Assert.assertEquals(sbAccountSingleAccount.getText(), home.sbAccountNum);
        } else {
            Assert.assertEquals(sbAccountDropdown.getText(), home.sbAccountNum);
        }
        DecimalFormat df = new DecimalFormat("###.#");
        double balance = Math.floor(home.availableBalance);
        Assert.assertEquals(sbOldFinalAmount.getText().replace("$", "").replace(",", "").replace(" ",""), df.format(balance));
        Assert.assertEquals(sbTextUnderAccountDropdown.getText(), "Available balance (rounded down)");

        if(!home.simpleSB) {
            Assert.assertEquals(textUnderPendingTransactionCheckbox.getText(), "Amounts do not include any additional fees.");
            validateTransactionFrequency(home);
            validatePendingTransaction(home);
            pageWait.until(ExpectedConditions.visibilityOf(transactionTotalAmount));
            Assert.assertEquals(transactionTotalAmount.getText().replace("- $", ""), home.transactionTotal.replace("-", ""));
            String popupAmount = transactionTotalAmount.getText().replace("- ", "");
            String popup = "Now includes " + popupAmount + " in payments.";
            if (home.newUserPopup) {
                System.out.println(popupHeader.getText());
                Assert.assertEquals(popupHeader.getText(), popup);
                Assert.assertEquals(popupContent.getText(), "Until they process, future payments are included in Spendable Balance.");
            }
        }

        validateTotalSubtractedAndSpendableBalance(home);
        //Assert.assertEquals(sbGuideLineText.getText(),"Just a guideline – you can still use your whole Available Balance.");
        if(closeSB.isDisplayed()){
            clickElementOnceClickable(closeSB);
        }
        
        if(!browser.environment.contains("sandbox")){
            clickElementOnceClickable(sbTooltip);
            pageWait.until(ExpectedConditions.visibilityOf(toolTipText));
            //Assert.assertEquals(toolTipText.getText(),"A balance you can adjust yourself by subtracting money you don't want to spend. It's only a guideline - you can still use your whole Available Balance.");
            clickElementOnceClickable(sbTooltip);
        }

        if(negativeBalanceCheck < 0) {
            System.out.println("Validating the BOX Color GRAY");
            validateBoxColorGrey();
        }

        if(home.availableBalance < 0){
            System.out.println("Validating the BOX Color RED");
            validateBoxColorRed();
        }
    }

    @Step("{method}")
    public void validateTotalSubtractedAndSpendableBalance(Home home){
        double transactionT;
        double total;
        DecimalFormat df = new DecimalFormat("###.#");
        double holdA = home.holdAmount;
        if(!home.simpleSB){
            transactionT = Double.parseDouble(home.transactionTotal);
            total = holdA - transactionT;
        } else {
            total = holdA;
            System.out.println(sbFinalAmount.getText().replace("- $","").replace(",",""));
            negativeBalanceCheck = Double.parseDouble(sbFinalAmount.getText().replace("- $","").replace(",","").replace("$",""));
        }
        Assert.assertEquals(totalSubtracted.getText().replace("-$","").replace(",",""),df.format(total));
        double balance = Math.floor(home.availableBalance);
        double spendableBalance = balance - Math.round(total);
        Assert.assertEquals(sbFinalAmount.getText().replace("$","").replace(",","").replace(" ",""),df.format(spendableBalance));

    }

    @Step("{method}")
    public void validatePendingTransaction(Home home)  {
        home.totalPendingTransaction=0;
        int transactionSize = transactionDates.size();
        for(int i=1; i<=transactionSize; i++) {
            double amountFormat = Double.parseDouble(home.amountTransactionMap.get(i-1));
            DecimalFormat decimalFormat = new DecimalFormat("##.00");

            String pendingTransactionAmount = driver.findElement(By.xpath(String .format(transactionAmount, i))).getText();
            home.totalPendingTransaction = Double.parseDouble(home.amountTransactionMap.get(i-1)) + home.totalPendingTransaction;
            Assert.assertEquals(pendingTransactionAmount.replace("$","-"),decimalFormat.format(amountFormat));
        }
        home.transactionTotal = String.valueOf((int)Math.floor(home.totalPendingTransaction));

        for(int i=1; i<=transactionSize; i++) {
            String pendingTransactionDescription = driver.findElement(By.xpath(String .format(transactionDescription, i))).getText();
            Assert.assertEquals(pendingTransactionDescription,home.descriptionTransactionMap.get(i-1));
        }

        SimpleDateFormat month_date = new SimpleDateFormat("MMM d", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        for(int i=1; i<=transactionSize; i++) {
            String pendingTransactionDate = driver.findElement(By.xpath(String .format(transactionDate, i))).getText();
            String actualDate = home.dateTransactionMap.get(i - 1);
            Date date = null;
            try {
                date = sdf.parse(actualDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String month_name = month_date.format(date);
            Assert.assertEquals(pendingTransactionDate,month_name);
        }
    }

    @Step("{method}")
    public void changeFrequency(Home home){

        checkNewCustomer(home);
        if (!home.newUser) {
            expandSB.click();
        }
        sbTransactionFrequency.click();
        driver.findElement(By.xpath(String.format(changeSBFrequency, home.frequency))).click();
        closeSB.click();

    }

    @Step("{method}")
    public void validateTransactionFrequency(Home home){
        String dateRange = null;
        if(home.dateRange == 1) {
            dateRange = "Clearing Today";
        }
        if(home.dateRange == 2){
            dateRange = "Within 2 days";
        }
        if(home.dateRange == 3){
            dateRange = "Within 3 days";
        }
        if(home.dateRange == 5){
            dateRange = "Within 5 days";
        }
        if(home.dateRange == 7){
            dateRange = "Within 7 days";
        }
        if(home.dateRange == 14){
            dateRange = "Next 2 weeks";
        }
        Assert.assertEquals(sbTransactionFrequency.getText().trim(), dateRange);
        
    }

    @Step("{method}")
    public String gatherMySpendingData(){
        explicitPageWait();
         String totalLimitLeft = payperiodLimit.getAttribute("title")
                 .replace("$","")
                 .replace(" ","");
         String daysLeftInPeriod = numDaysLeft.getText();

         return totalLimitLeft+":"+daysLeftInPeriod;
    }

    @Step("{method}")
    public void validatePagination (Home home) {
        System.out.println(home.internalAccountSize);

        for(int i=0; i<=10; i++) {
            if(i>=10) {
                if(home.accountType == "2"){
                    String paginationText = "Viewing  1 - 10 of "+home.internalAccountSize+" accounts";
                    Assert.assertEquals(savingPagination.getText(),paginationText);
                     moneySavingPaginationNext.click();
                    String paginationTextNext = "Viewing  11 - "+home.internalAccountSize+" of "+home.internalAccountSize+" accounts";
                    Assert.assertEquals(savingPagination.getText(),paginationTextNext);

                }
                if(home.accountType == "1") {
                    String paginationText = "Viewing  1 - 10 of "+home.internalAccountSize+" accounts";
                    Assert.assertEquals(checkingPagination.getText(),paginationText);
                    moneySpendPaginationNext.click();
                    String paginationTextNext = "Viewing  11 - "+home.internalAccountSize+" of "+home.internalAccountSize+" accounts";
                    Assert.assertEquals(checkingPagination.getText(),paginationTextNext);
                }
            }
        }
    }

    public void validateAccountBalance (Home home) {
        pageWait.until(ExpectedConditions.invisibilityOf(spinner));

        if(home.accountType != "2"){
            System.out.println("ACCOUNT SIZE: "+ home.internalAccountSize);
            for(int i=0; i<home.internalAccountSize; i++) {
                System.out.println("i >>>>>> " + i);
                if(i>=10) {
                    moneySpendPaginationNext.click();
                    explicitPageWait();
                }
                validateBalance(home,i);
            }
        } else {
            for(int i=home.internalAccountSize; i<home.internalAccountSize; i--) {
                validateBalance(home,i);
            }
        }
    }

    public void validateBalance(Home home, int i) {
        double aBalance;
        double cBalance;

        DecimalFormat df = new DecimalFormat("###.##");

        boolean moreAccounts = driver.findElements(By.xpath(String .format(account, home.accountEntityMap.get(i)))).size() > 0;
        if (!moreAccounts) {
            if(home.accountType == "2"){
                moneySavingPaginationNext.click();

            } else {
                moneySpendPaginationNext.click();
            }
        }

        String accountNum = driver.findElement(By.xpath(String.format(accountName, home.accountEntityMap.get(i)))).getText()+" " +driver.findElement(By.xpath(String.format(maskedAccountNum, home.accountEntityMap.get(i)))).getText();
        Assert.assertEquals(accountNum, home.accountDescriptionMap.get(i));

        String availableBalanceText = driver.findElement(By.xpath(String.format("//tbody/tr[@id='%1$s']/td[2]/span", home.accountEntityMap.get(i)))).getText();
        System.out.println("AVAILABLE BAL: "+ i +" -> "+ availableBalanceText);
        String currentBalanceText = driver.findElement(By.xpath(String.format("//tbody/tr[@id='%1$s']/td[3]/span", home.accountEntityMap.get(i)))).getText();
        System.out.println("CURRENT BAL: "+ i +" -> "+ currentBalanceText);

        aBalance = Double.parseDouble(availableBalanceText.replace(",","").replace("$",""));
        cBalance = Double.parseDouble(currentBalanceText.replace(",","").replace("$",""));

        if(home.accountType.equals("5") || home.accountType.equals("6")){
            Assert.assertEquals(cBalance,home.balanceAvailableMap.get(i));
            Assert.assertEquals(aBalance,home.balanceCurrentMap.get(i));
        } else {
            Assert.assertEquals(aBalance,home.balanceAvailableMap.get(i));
            Assert.assertEquals(cBalance,home.balanceCurrentMap.get(i));
        }

    }

    @Step("{method}")
    public void validateTotalBalance (Home home){
        double availableBalanceTotal=0;
        double currentBalanceTotal=0;

        for(int i=0; i<home.accountEntityMap.size(); i++){
            double availableBalanceText = Double.parseDouble(driver.findElement(By.xpath(String.format(availableBalance, home.accountEntityMap.get(i)))).getText().trim().replace("$","").replace(",",""));
            double currentBalanceText = Double.parseDouble(driver.findElement(By.xpath(String.format(currentBalance, home.accountEntityMap.get(i)))).getText().trim().replace("$","").replace(",",""));
            availableBalanceTotal = availableBalanceText + availableBalanceTotal;
            currentBalanceTotal = currentBalanceText + currentBalanceTotal;
        }
        DecimalFormat df = new DecimalFormat("###.##");

        String availableBalanceTotalString = String.valueOf(df.format(availableBalanceTotal));
        String currentBalanceTotalString = String.valueOf(df.format(currentBalanceTotal));

        if(home.accountType == "1"){
            Assert.assertEquals(checkingAvailableTotal.getText().trim().replace("$","").replace(",",""),availableBalanceTotalString);
            Assert.assertEquals(checkingCurrentTotal.getText().trim().replace("$","").replace(",",""),currentBalanceTotalString) ;
        }

        if(home.accountType == "2"){
            Assert.assertEquals(savingAvailableTotal.getText().trim().replace("$","").replace(",",""),availableBalanceTotalString);
            Assert.assertEquals(savingCurrentTotal.getText().trim().replace("$","").replace(",",""),currentBalanceTotalString) ;

        }

        if(home.accountType == "5"){
            Assert.assertEquals(loanAvailableTotal.getText().trim().replace("$", "").replace(",", ""), currentBalanceTotalString);
            Assert.assertEquals(loanCurrentTotal.getText().trim().replace("$", "").replace(",", ""), availableBalanceTotalString);
        }
    }

    @Step("{method}")
    public void validateOutsideAccount(Home home) {
        Assert.assertEquals(outsideAccountText.getText(),"Want to see your account balances from other financial institutions without leaving Apiture Institution ? You can!");
        Assert.assertEquals(addOutsideAccountButton.getText(),"Sync Outside Balances");
        favoritesTab.click();
        Assert.assertEquals(outsideAccountText.getText(),"Want to see your account balances from other financial institutions without leaving Apiture Institution ? You can!");
        Assert.assertEquals(addOutsideAccountButton.getText(),"Sync Outside Balances");
        allAccountsTab.click();
    }


    @Step("{method}")
    public void validateLeftMessageBox(Home home){
        if(!home.message){
            Assert.assertEquals(unreadMessagesBtn.getText(),"Unread Messages");
            //Assert.assertEquals(unreadMessagesBoxText.getText(),"You have no unread messages at this time.");
        } else {
            Assert.assertTrue(((String)unreadMessageHeader.getText()).trim().contains("Unread Message"));
            System.out.println(unreadMessagesBoxText.getText());
            unreadMessagesBoxText.click();
            pageWait.until(ExpectedConditions.elementToBeClickable(deleteMessage));
            explicitPageWait();
            deleteMessage.click();
            pageWait.until(ExpectedConditions.elementToBeClickable(confirmDeleteMessage));
            confirmDeleteMessage.click();

        }
    }

    @Step("{method}")
    public void validateMySpendingInHomePage (Home home) {
        pageWait.until(ExpectedConditions.visibilityOf(mySpendingNavHeader));
        explicitPageWait();
        Assert.assertEquals(mySpendingNavHeader.getText().trim().substring(0,11),"My Spending");
        Assert.assertEquals(mySpendingNavTitle.getText().trim(),"Spendable Balance Account");

        AccountsBrowserAPI accountsBrowserAPI = new AccountsBrowserAPI(browser,"/fdobi/accounts");
        accountsBrowserAPI.getResponse(browser, Method.GET,"");
        home.sbAccountEntity = accountsBrowserAPI.getSpendableBalanceAccount(browser);

        GetAccSpendingPrefBrowserAPI getAccSpendingPrefBrowserAPI = new GetAccSpendingPrefBrowserAPI(browser,"myspending/v1/getAccSpendingPrefs");
        getAccSpendingPrefBrowserAPI.getResponse(browser,Method.POST,"",home.sbAccountEntity);
        String frequency = getAccSpendingPrefBrowserAPI.getTSFrequency(browser,home.sbAccountEntity);
        if(frequency.equals("Weekly")){
            Assert.assertEquals(mySpendingNavPayPeriod.getText().trim(),"This Week");
            Assert.assertEquals(mySpendingNavTotalSpendingDate.getText().trim(),getAccSpendingPrefBrowserAPI.getTSdate(browser,home.sbAccountEntity));
        } else {
            Assert.assertEquals(mySpendingNavPayPeriod.getText().trim(),"This Pay Period");
            //TODO: i have validated only weekly payperiod needs some logical change
            System.out.println("Total Spending since " + mySpendingNavTotalSpendingDate.getText().trim());
        }

        //TODO : Need to validate the no of days left, currently just printing the daysleft
        //System.out.println("Total Spending no of days left " + mySpendingNavTotalSpendingDaysLeft.getText().trim());
    
        String tsLeft;
        int value = 0;
        value = Integer.parseInt(getAccSpendingPrefBrowserAPI.getTSLimit(browser,home.sbAccountEntity));
    
        if(home.totalSpendingSpent >= value){
            tsLeft = String.valueOf(0);
        } else {
            tsLeft = getAccSpendingPrefBrowserAPI.getTSLimit(browser,home.sbAccountEntity);
        }

        Assert.assertEquals(mySpendingNavTotalSpendingAmountLeft.getText().trim().replace("$",""), tsLeft);
        Assert.assertEquals(mySpendingNavCategoryWatchHeader.getText().trim().substring(0,16),"Category Watch -" );
       
        int lastDayOfThisMonth = 0;
        int todayDate = 0;
        int daysLeft = 0;

        LocalDate localDate = LocalDate.now().plusDays(0);
        todayDate = localDate.getDayOfMonth();
        daysLeft = lastDayOfThisMonth - (todayDate-1);

        Assert.assertEquals(mySpendingNavCategoryWatchDaysLeft.getText().trim(), String.valueOf(daysLeft));

        home.categoryMap = getAccSpendingPrefBrowserAPI.getCategoryWatchMap(browser,home.sbAccountEntity);
        home.categoryName = getAccSpendingPrefBrowserAPI.getCategoryNameList(browser,home.sbAccountEntity);

        for(int i=0; i<home.categoryName.size(); i++) {
            Assert.assertEquals(driver.findElement(By.xpath(String.format(mySpendingNavCategoryName,home.categoryName.get(i)))).getText().trim(),home.categoryName.get(i));
            //TODO: amount left needs to be calculated
            //Assert.assertEquals(driver.findElement(By.xpath(String.format(mySpendingNavCategoryAmountLeft,home.categoryName.get(i)))).getText().trim(),home.categoryMap.get(home.categoryName.get(i)));
        }

    }

    @Step("{method}")
    public void validateHeaders( Home home ) {

        String heading1 = driver.findElement(By.id(String.format(header1, home.accountDesc))).getText();
        String heading2 = driver.findElement(By.id(String.format(header2, home.accountDesc))).getText();

        if(home.accountType == "1") {
            if(!home.commercial){
                Assert.assertEquals("Money I Can Spend", checkingHeaderName.getText());
            } else {
                Assert.assertEquals("Checking",checkingHeaderNameComm.getText());
            }
            Assert.assertEquals("Available", heading1);
            Assert.assertEquals("Previous Day", heading2);
        }

        if(home.accountType == "2") {
            if(!home.commercial){
                Assert.assertEquals("Money I'm Saving", savingHeaderName.getText());
            } else {
                Assert.assertEquals("Savings", savingHeaderNameComm.getText());
            }
            Assert.assertEquals("Available", heading1);
            Assert.assertEquals("Previous Day", heading2);
        }

        if(home.accountType == "5") {
            if(!home.commercial){
                Assert.assertEquals("Money I Owe", loanHeaderName.getText());
            } else {
                Assert.assertEquals("Loans & Credit Cards", loanHeaderName.getText().trim());
            }

            Assert.assertEquals("Available", heading1);
            Assert.assertEquals("Previous Day", heading2);
        }





    }

    @Step("{method}")
    public void clickLastLoginModal(){
        timeStampOnHome = lastLoginText.getText();
        lastLoginButton.click();
        System.out.println(timeStampOnHome);
    }

    public void ValidateFidelityStatements() {
        launchInNewTab(driver, () -> {
                    clickElementOnceClickable(FidelityStatements); },
                () -> {
                    explicitPageWait();
                    String uOpen = driver.getCurrentUrl();
                    Assert.assertEquals(uOpen.substring(0, 24), "https://www.internet-est");
                    browser.logTestPass("Navigated successfully to Fidelity statements SSO Page");
                    pageWait.until(ExpectedConditions.visibilityOf(FISIframe));
                    driver.switchTo().frame(FISIframe);
//                   Assert.assertEquals(FidelityStatementsHeading, "First National Bank and Trust Company of Ardmore eStatements");
                    Boolean documents = isElementPresent(By.xpath("//b/font[contains(text(),'Documents')]"),10);
                    Assert.assertTrue(documents);
                    browser.logTestAction("Documents menu is present in the SSO page");
                    Boolean Accounts=  isElementPresent(By.xpath("//b/font[contains(text(),'Account')]"),10);
                    Assert.assertTrue(Accounts);
                    browser.logTestAction("Accounts menu is present in the SSO page");
                    System.out.println("Fidelity SSO is Up");
                });
    }
    
    public void validateAllFxWebLinks() {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("No of links are " + links.size());
        System.out.println(links);
        
        List<String> urllist = new ArrayList<String>();
        String url=null;
        
        for(WebElement e : links) {
            url = e.getAttribute("href");
            urllist.add(url);
        }
    
        urllist.parallelStream().forEach(e -> checkBrokenLink(e));
        
        System.out.println(url);
    }
    
    public void checkBrokenLink (String linkUrl) {
        
        try{
            URL url = new URL(linkUrl);
            HttpURLConnection httpURLConnection =  (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            
            if(httpURLConnection.getResponseCode() >=400) {
                System.out.println(linkUrl + "---> " + httpURLConnection.getResponseMessage() + " is a broken link");
                browser.logTestAction(linkUrl + "---> " + httpURLConnection.getResponseMessage() + " is a broken link");
            } else {
                System.out.println(linkUrl + "--> " + httpURLConnection.getResponseMessage());
            }
            
        } catch (Exception e) {
        
        }
    
    }
}
