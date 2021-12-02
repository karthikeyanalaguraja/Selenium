package core.utilities.baseUtilities;

import core.basePages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseFrame extends BasePage {

    final String frameParentXpath;

    public WebDriverWait pageWait;

    protected BaseFrame(Browser browser, String frameParentXpath) {
        super(browser, "", "");
        this.frameParentXpath = frameParentXpath;
        this.pageWait = new WebDriverWait(browser.driver, BaseTestConfig.getExplicitTimeout());
        PageFactory.initElements(browser.driver, this);
    }

    public final void waitForFrame() {
        browser.addScreenshot();
        pageWait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(frameParentXpath)),
                isLoadedExpectedCondition()
        ));
    }

    @Override
    public void waitForPageLoad() {
        waitForFrame();
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
