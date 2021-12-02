package core.basePages;

import core.utilities.baseUtilities.BaseTestConfig;
import core.utilities.baseUtilities.Browser;
import core.utilities.baseUtilities.Utilities;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public abstract class BasePage {

    protected final String urlPath; //URL path to the specific page.  Ex:users/#user_address
    final String urlBase; //Base URL of the page.  Ex: http://www.testpage.com
    /**
     * Default WebDriverWait to be used by each page.  If a value other than the default
     * is required, a new WebDriverWait should be created for that single purpose
     */
    protected WebDriverWait pageWait;
    protected Browser browser;
    protected WebDriver driver;

    protected BasePage(Browser browser, String urlBase, String urlPath) {
        this.browser = browser;
        this.driver = browser.driver;
        this.urlBase = urlBase;
        this.urlPath = urlPath;
        this.pageWait = new WebDriverWait(driver, BaseTestConfig.getExplicitTimeout());
        PageFactory.initElements(driver, this);
    }

    /**
     * Determine if text is present
     * @param tag element tag
     * @param text element text
     * @param timeoutInSeconds timeout in seconds
     */
    protected boolean isTextPresent(String tag, String text, long timeoutInSeconds) {
        try {
            findByPresence(By.xpath(String.format("//%s[contains(text(),'%s')]",tag,text)),timeoutInSeconds);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * Check if element found by locator is present.
     * @param locator By object used to locate element
     * @param timeoutInSeconds Timeout in seconds
     * @return
     */
  protected boolean isElementPresent(By locator, long timeoutInSeconds) {
        try {
            findByPresence(locator,timeoutInSeconds);
        } catch (Exception e){
         return false;
        }  return true;
  }

    /**
     * Find an Element element to be present in the DOM
     * @param locator locator describing pageObject
     * @param timeoutInSeconds Timeout in seconds
     * @return WebElement
     */
    protected WebElement findByPresence(By locator, long timeoutInSeconds) {
        return  new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for element to be present in the DOM
     * @param webElement WebElement of a PageObject
     * @param timeoutInSeconds Timeout in seconds
     * @return WebElement
     */
    protected WebElement waitForPresencesOfElement(WebElement webElement, long timeoutInSeconds) {
        return  new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOf(webElement));
    }
    /**
     * Returns the value of an input field of an element
     * @param element WebElement
     * @return String Element attribute value
     */
    protected String getInputValue(WebElement element) {
        // For reasons I don't understand the value of this input field isn't populated when the page loads.
        element.sendKeys(Keys.ENTER);
        return element.getAttribute("value");
    }

    /**
     * Scroll element into view using javascript
     * @param element WebElement for pageObject
     */
    protected void scrollElementIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        pageWait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for the page to be fully loaded and ready for interaction.
     */
    public void waitForPageLoad(){
        pageWait.until(getPageLoadExpectedConditions());
    };

    /**
     * <h1>findByClickability</h1>
     * <p>purpose: Verify that the pageObject described by locator is refreshed and clickable on screen within timeout of searching.
     * Return the WebElement if clickable and found. Fail test if pageObject is not clickable or
     * WebElement for pageObject is not found within timeout</p>
     *
     * @param locator = locator to create WebElement for pageObject
     * @param timeout = how long to search for the pageObject, in seconds
     * @return WebElement
     */
    protected WebElement findByClickability(By locator, int timeout) {
        return new WebDriverWait(driver, timeout)
                .withMessage("Test Timed out: Object was not clickable (enabled and visible) after " + timeout + " seconds\n" +
                        "object xpath = \"" + locator.toString() + "\"")
                .ignoring(org.openqa.selenium.ElementClickInterceptedException.class)
                .until(ExpectedConditions.refreshed(
                        ExpectedConditions.elementToBeClickable(locator)));
    }

    public void waitForPresencesOfElementThenClick(WebElement element, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.visibilityOf(element)).click();
    }

    /**
     * <h1>findAllOrNoElements</h1>
     * <p>purpose: Find all WebElements or no WebElements (will not fail out if no elements are returned)<br>
     * specifically: List [WebElement] elements = driver.findElements(locator)<br>
     * Note: This is a useful method when you need to determine the number of elements that each use the same locator<br>
     * Note: This finder is subject to a timeout of 3 seconds.
     *
     * @param driver  = WebDriver
     * @param locator = By locator
     * @return list of WebElements if they exist | empty list of WebElements if none exist
     */
    protected List<WebElement> findAllOrNoElements(WebDriver driver, By locator) {
        try {
            this.findByPresence(locator,10);
        } catch (Exception e) {
            return null;
        }
        return driver.findElements(locator);
    }

    /**
     * The condition that determines that a page has fully loaded.
     *
     * @return
     */
    protected abstract ExpectedCondition getPageLoadExpectedConditions();

    /**
     * Navigates to page via urlBase+urlPath then calls waitForPageLoad()
     */
    public final void navigateToPage() {
        //Builds URI from urlBase and urlPath.
        //This handles character conversion and improper leading/trailing spaces
        //URI uri = UriBuilder.fromPath(urlBase).build(urlPath);
        URI uri = null;
        try {
            uri = new URI(urlBase + urlPath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        driver.navigate().to(uri.toString());
        waitForPageLoad();
    }

    /**
     * Wait for JS calls to complete
     * @return boolean
     */
    protected final boolean waitForJsCallsToComplete() {
        pageWait = new WebDriverWait(driver, 30);
        // wait for jQuery to complete
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
            } catch (Exception e) {
                return true;
            }
        };
        // wait for Javascript to complete
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState")
                .toString().equals("complete");
        return pageWait.until(
                ExpectedConditions.and(
                        jQueryLoad,
                        jsLoad
                )
        );
    }

    /**
     * Select the vale from a dropdown
     * @param element WebElement for pageObject
     * @param value object
     */
    protected final void selectFromDropdown(WebElement element, Object value) {
        Select select = new Select(element);
        pageWait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            select.selectByVisibleText(value.toString());
        } catch (Exception ex) {
            WebElement match = select
                    .getOptions()
                    .stream()
                    //get a list of potential matches
                    .filter(e -> value.toString().toUpperCase().startsWith(e.getText().substring(0, (value.toString().length() / 2))))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(String.format("Dropdown value %s not found and nothing close was found.", value), String.valueOf(match));
            System.out.println(String.format("Unable to find exact value '%s' in dropdown, but close value '%s' found and selected.", value, match.getText()));
            select.selectByVisibleText(match.getText());
        } finally {
            driver.manage().timeouts().implicitlyWait(BaseTestConfig.getImplicitTimeout(), TimeUnit.SECONDS);
        }
    }

    protected void waitForToastAlert(Toast toastType, String message, Runnable triggeringMethod) {
        By msgXpath = By.xpath(String.format(".//div[@id='toast-container']/div[@class='toast toast-%s']/div[@class='toast-message']", toastType));
        triggeringMethod.run();
        WebElement toast = driver.findElement(msgXpath);
        String toastText = toast.getText();

        Assert.assertTrue(
                toastText.contains(message),
                String.format("Unexpected toast message found: /nExpected: %s /nActual: %s", message, toastText));

        toast.click();
        pageWait.until(ExpectedConditions.stalenessOf(toast));
    }

    protected void waitForToastAlert(Toast toastType, String message) {
        By msgXpath = By.xpath(String.format(".//div[@id='toast-container']/div[@class='toast toast-%s']/div[@class='toast-message']", toastType));
        WebElement toast = driver.findElement(msgXpath);
        String toastText = toast.getText();

        Assert.assertTrue(
                toastText.contains(message),
                String.format("Unexpected toast message found: /nExpected: %s /nActual: %s", message, toastText));

        toast.click();
        pageWait.until(ExpectedConditions.stalenessOf(toast));
    }

    /**
     * Send text to an input element and tab out
     * @param webElement WebElement of a pageObject
     * @param inputText text to be send to an element
     */
    protected void sendKeysThenTab(WebElement webElement, String inputText) {
        webElement.sendKeys(inputText);
        webElement.sendKeys(Keys.TAB);
    }

    protected WebElement databindContains(String tag, String contains) {
        return findElementByXpathContaining(tag, "@data-bind", contains);
    }

    /**
     * Find an element by xpath containing
     * @param tag tag of an element
     * @param attribute attribute name of an element
     * @param contains text
     * @return WebElement
     */
    protected WebElement findElementByXpathContaining(String tag, String attribute, String contains) {
        return driver.findElement(By.xpath(String.format(".//%s[contains(%s,'%s')]", tag, attribute, contains)));
    }

    /**
     * Find List of an element by xpath containing
     * @param tag tag of an element
     * @param attribute attribute name of an element
     * @param contains text
     * @return WebElement
     */
    protected List<WebElement> findElementsByXpathContaining(String tag, String attribute, String contains) {
        return driver.findElements(By.xpath(String.format(".//%s[contains(%s,'%s')]", tag, attribute, contains)));
    }

    /**
     * Set the dynamic wait for a page
     * @param timeInSeconds
     */
    protected void setPageWait(Integer timeInSeconds) {
        pageWait = new WebDriverWait(browser.driver, timeInSeconds);
    }

    /**
     * Reset a page wait to default wait
     */
    protected void resetPageWaitToDefault() {
        pageWait = new WebDriverWait(browser.driver, BaseTestConfig.getExplicitTimeout());
    }

    public boolean isLoaded() {
        boolean isLoaded = false;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            Utilities.measureTime(this.getClass().getSimpleName() + ".isLoaded()", () -> {
                new WebDriverWait(driver, 0).until(getPageLoadExpectedConditions());
            });
            isLoaded = true;
        } catch (Exception e) {
            //ignore
        }
        driver.manage().timeouts().implicitlyWait(BaseTestConfig.getImplicitTimeout(), TimeUnit.SECONDS);
        return isLoaded;
    }

    protected enum Toast {
        warning,
        error,
        success
    }

    @Step("{method}")
    public void clickElementOnceClickable(WebElement e){
        pageWait.until(ExpectedConditions.elementToBeClickable(e)).click();
    }
    

    @Step("{method}")
    public void clickElementOnceClickable(WebElement e, int waitInSeconds){
        WebDriverWait w = new WebDriverWait(driver, waitInSeconds);
        w.until(ExpectedConditions.elementToBeClickable(e)).click();
    }

    @Step("{method}")
    public void clickElementOnceVisible(WebElement e){
        pageWait.until(ExpectedConditions.elementToBeClickable(e)).click();
    }

    /**
     * <h1>switchToFrame</h1>
     * <p>purpose: Assuming a frame exists within your page, switch to frame with name or id</p>
     *
     * @param nameOrId = name or id of frame to switch to
     */
    public void switchToFrame(String nameOrId) {
        driver.switchTo().defaultContent();
        driver.switchTo().frame(nameOrId);
    }

    /**
     * <h1>switchToFrame</h1>
     * <p>purpose: Assuming a frame exists within your page, switch to frame with name or id</p>
     *
     * @param locator = locator of the frame to switch to
     */
    public void switchToFrame(By locator) {
        driver.switchTo().defaultContent();
        driver.switchTo().frame((WebElement) locator);
    }
}
