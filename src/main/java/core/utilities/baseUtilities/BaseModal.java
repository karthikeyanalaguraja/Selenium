package core.utilities.baseUtilities;

import core.basePages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by nick.fields on 6/6/2017.
 */
public abstract class BaseModal extends BasePage {

    final String modalXpath;
    public WebDriverWait pageWait;
    protected WebDriver driver;
    By modalBlock;

    public BaseModal(Browser browser, String modalParentXpath) {
        super(browser, "", "");
        this.modalXpath = modalParentXpath;
        this.driver = browser.driver;
        this.pageWait = new WebDriverWait(driver, BaseTestConfig.getExplicitTimeout());
        this.modalBlock = By.xpath(modalParentXpath);
        PageFactory.initElements(driver, this);
    }

    /**
     * Waits for base modal to be displayed as well as the isLoadedExpectedCondition.
     */
    public final void waitForModal() {
        browser.addScreenshot();
        pageWait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(modalBlock),
                ExpectedConditions.attributeContains(modalBlock, "class", "modal-title"),
                ExpectedConditions.visibilityOfElementLocated(By.xpath(modalXpath)),
                ExpectedConditions.textToBePresentInElementLocated(modalBlock, "Success"),
                isLoadedExpectedCondition()
        ));
    }

    /**
     * Waits for the blocking background to disappear
     */
    public final void waitForModalToClose() {
        pageWait.until(
                ExpectedConditions.or(
                        ExpectedConditions.attributeContains(modalBlock, "style", "display: none;"),
                        ExpectedConditions.invisibilityOfElementLocated(modalBlock)
                )
        );
    }

    @Override
    public void waitForPageLoad() {
        waitForModal();
    }

    /**
     * ExpectedCondition that signifies that the modal is fully loaded
     *
     * @return
     */
    protected abstract ExpectedCondition isLoadedExpectedCondition();

    @Override
    protected ExpectedCondition getPageLoadExpectedConditions() {
        return isLoadedExpectedCondition();
    }
}
