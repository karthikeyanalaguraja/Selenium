package core.utilities.baseUtilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Created by nick.fields on 6/6/2017.
 */
public abstract class BaseSweetAlert {

    protected final String baseSweetAlertParentXpath = "//div[contains(@class,'swal2-modal')]";
    final String header;
    protected Browser browser;
    protected WebDriver driver;
    /**
     * Default WebDriverWait to be used by each sweet alert.  If a value other than the default
     * is required, a new WebDriverWait should be created for that single purpose
     */
    protected WebDriverWait pageWait;
    @FindBy(xpath = baseSweetAlertParentXpath)
    protected WebElement modal;
    @FindBys({@FindBy(xpath = baseSweetAlertParentXpath), @FindBy(xpath = "./div[contains(@class,'swal2-icon') and @style='display: block;']")})
    protected WebElement statusIcon;
    @FindBys({@FindBy(xpath = baseSweetAlertParentXpath), @FindBy(xpath = "./h2")})
    protected WebElement sweetHeader;
    @FindBys({@FindBy(xpath = baseSweetAlertParentXpath), @FindBy(xpath = "./div[contains(@class,'swal2-content')]")})
    protected WebElement sweetContent;
    @FindBys({@FindBy(xpath = baseSweetAlertParentXpath), @FindBy(xpath = "./button[contains(@class,'swal2-confirm')]")})
    protected WebElement confirmBtn;
    @FindBys({@FindBy(xpath = baseSweetAlertParentXpath), @FindBy(xpath = "./button[contains(@class,'swal2-cancel')]")})
    protected WebElement cancelBtn;

    public BaseSweetAlert(Browser browser, String modalH2Text) {
        this.header = modalH2Text;
        this.pageWait = new WebDriverWait(browser.driver, BaseTestConfig.getExplicitTimeout());
        this.browser = browser;
        this.driver = browser.driver;

        PageFactory.initElements(driver, this);
    }

    /**
     * Validates the correct alert status is displayed
     *
     * @param expectedStatus
     */
    public void assertStatus(Status expectedStatus) {
        String displayed = statusIcon.getAttribute("class");

        Assert.assertTrue(
                displayed.matches(expectedStatus.getClassName()),
                String.format("Unexpected status icon found on %s sweet alert: \nExpected: %s \nActual: %s",
                                header,
                                expectedStatus.getClassName(),
                                displayed));
    }

    /**
     * Waits for a sweet alert to be displayed that contains the header passed into the constructor.
     */
    public final void waitForSweetAlert() {
        waitForSweetAlert(90);
    }

    public final void waitForSweetAlert(Integer timeoutIntSeconds) {
        browser.addScreenshot();

        new WebDriverWait(driver, timeoutIntSeconds).until(ExpectedConditions.and(
                ExpectedConditions.visibilityOf(modal),
                ExpectedConditions.attributeContains(modal, "class", "swal2-show"),
                ExpectedConditions.textToBePresentInElement(sweetHeader, header),
                isLoadedExpectedCondition()
        ));

        browser.addScreenshot();
    }

    /**
     * Waits for the sweet alert to be hidden
     */
    public final void waitForSweetAlertToClose() {
        pageWait.until(ExpectedConditions.attributeContains(modal, "class", "swal2-hide"));
        pageWait.until(ExpectedConditions.attributeContains(modal, "style", "display: none;"));
    }

    /**
     * ExpectedCondition to indicates the window is fully loaded
     *
     * @return
     */
    protected abstract ExpectedCondition isLoadedExpectedCondition();

    /**
     * Clicks the confirm button (Typically OK, Continue, etc) and waits for window to be hidden.
     */
    public void confirmSweetAlert() {
        confirmBtn.click();
        waitForSweetAlertToClose();
    }

    /**
     * Clicks the cancel button (Typically Cancel, Back, etc) and waits for window to be hidden.
     */
    public void cancelSweetAlert() {
        cancelBtn.click();
        waitForSweetAlertToClose();
    }

    public void assertSweetContent(String expectedText) {
        waitForSweetAlert();
        Assert.assertEquals(expectedText, sweetContent.getText());
    }

    public void assertSweetContent(String expectedText, Boolean isExact) {
        waitForSweetAlert();
        if (isExact) {
            Assert.assertEquals(expectedText, sweetContent.getText());
        } else {
            Utilities.assertContains("Unexpected sweet alert content", expectedText, sweetContent.getText());
        }
    }

    public void setPageWait(Integer ms) {
        pageWait = new WebDriverWait(browser.driver, ms);
    }

    public void resetPageWaitToDefault() {
        pageWait = new WebDriverWait(browser.driver, BaseTestConfig.getExplicitTimeout());
    }

    public enum Status {
        error("swal2-error"),
        question("swal2-question"),
        warning("swal2-warning pulse-warning"),
        info("swal2-info"),
        success("swal2-success animate");

        String iconClass;
        String className;
        String style;

        Status(String classNameContainsStatus) {
            this.iconClass = "swal2-icon";
            this.className = classNameContainsStatus;
            this.style = "display: block;";
        }

        public String getClassName() {
            return String.format("%s %s",
                    this.iconClass,
                    this.className);
        }
    }
}
