package core.utilities.baseUtilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.types.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import static io.restassured.RestAssured.get;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Browser {

    public final Boolean runLocal;
    public final Boolean useDocker;
    public final Boolean useBrowserStack;
    public final String environment;
    public final SupportedBrowsers browser;
    public WebDriver driver;
    private ThreadLocal<ExtentTest> test;
    List<File> screenshots;
    private int implicitTimeout;

    public SessionId getSessionId() {
        return sessionId;
    }

    private SessionId sessionId;
    private String testName;
    private Description description;
    private String filePath;

    /**
     * Launch the appropriate Selenium driver passed in from Jenkins or VM commands.  Contains helper methods for both
     * browser and driver interaction.
     */
    public Browser(String testName, Description description) {
        screenshots = new ArrayList<>();

        browser = SupportedBrowsers.getBrowser(System.getProperty("browser"));
        runLocal = Boolean.parseBoolean(System.getProperty("run_locally"));
        useDocker = Boolean.parseBoolean(System.getProperty("useDocker"));
        useBrowserStack = Boolean.parseBoolean(System.getProperty("useBrowserStack"));
        environment = System.getProperty("environment");

        implicitTimeout = BaseTestConfig.getImplicitTimeout();

        //Log the capabilities
       // System.out.println("Environment: " + environment);
        this.testName = testName;
        this.description = description;

       // launchDriver();

        //sessionId = ((RemoteWebDriver) driver).getSessionId();
    }

    public Browser() {
        this(null, null);
    }

    public void setTestLogger(ThreadLocal<ExtentTest> test){
        this.test = test;
    }

    @Step ("{0}")
    public void logTestAction(String message){
        this.test.get().info(message);
    }

    public void logTestSkip(String message){
        this.test.get().log(Status.SKIP, message);
    }

    public void skipThisTest(String skipReason){
        String str = ">>> SKIP REASON: "+skipReason;
        System.out.println(str);
        logTestSkip(str);
        throw new SkipException(str);
    }

   @Step ("{0}")
    public void logTestPass(String message){
        this.test.get().log(Status.PASS, message);
    }
    
    public void logTestPass(String message, WebElement e){
        if(e.isDisplayed()){
            this.test.get().log(Status.PASS, message);
        }else{
            this.test.get().log(Status.FAIL, message);
        }
    }

    public void logTestPass(String message, String text1, String text2){
        logTestAction("Checking if equals: "+text1+" vs: "+text2);
        Assert.assertEquals(text1.trim(), text2.trim());
        this.test.get().log(Status.PASS, message);
    }

    public void logTestFailure(String message){
        this.test.get().log(Status.FAIL, message);
    }

    private static ExecutionLocation getExecutionLocation(Boolean run_locally, Boolean useDocker) {
        if (run_locally) {
            return ExecutionLocation.Local;
        } else if (useDocker) {
            return ExecutionLocation.Docker;
        } else {
            return ExecutionLocation.BrowserStack;
        }
    }

    public void launchDriver() {
        if (runLocal) {
            driver = startLocalDriver(browser);
        } else if (useBrowserStack) {
            driver = new BrowserStack().getDriver();
        } else {
            System.out.println("Launching remote browser...");
            driver = startDockerRemoteDriver(browser);
        }
    }

    private WebDriver startLocalDriver(SupportedBrowsers browser) {
        File file;
        if(System.getProperty("os.name").toLowerCase().contains("windows")){
            filePath="C:/browsers/";
        }else{
            filePath="/Library/browsers/";
        }
        switch (browser) {
            case InternetExplorer:
                file = new File(macify(filePath+"IEDriverServer.exe"));
                System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
                driver = new InternetExplorerDriver();
                break;
            case Firefox:
                file = new File(macify(filePath+"geckodriver.exe"));
                System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
                driver = new FirefoxDriver();
                break;
            case Safari:
                file = new File(macify(filePath+"Safari.exe"));
                System.setProperty("webdriver.safari.driver", file.getAbsolutePath());
                driver = new SafariDriver();
                break;
            case Edge:
                file = new File(macify(filePath+"MicrosoftWebDriver.exe"));
                System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
                driver = new EdgeDriver();
                break;
            case Chrome:
            default:
                file = new File(macify(filePath+"chromedriver.exe"));
                System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
                driver = new ChromeDriver(getChromeOptions());
                break;
        }

        if (!isMac() || browser == SupportedBrowsers.Safari) {
            driver.manage().window().maximize();
        }

        //Set implicit wait
        //driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);

        return driver;
    }

    private String macify(String executable) {
        return !isMac()
                ? executable
                : executable.replace(".exe", "");
    }

    private Boolean isMac() {
        return StringUtils.containsIgnoreCase(System.getProperty("os.name"), "mac");
    }

    private WebDriver  startDockerRemoteDriver(SupportedBrowsers browser) {
        String logMsg = "Running on Docker using ";

        DesiredCapabilities caps = null;

        //Set the logging preferences to get browser logs
//        LoggingPreferences logs = new LoggingPreferences();
//        logs.enable(LogType.BROWSER, Level.SEVERE);

        //Set desired capabilities for remote webdriver
        switch (browser) {
            case Safari:
                caps = new DesiredCapabilities().safari();
                logMsg += "safari";
            case InternetExplorer:
                caps = new DesiredCapabilities().internetExplorer();
                logMsg += "internet explorer";
                break;
            case Firefox:
                caps = new DesiredCapabilities().firefox();
                logMsg += "firefox";
                break;
            case Edge:
                caps = new DesiredCapabilities().edge();
                logMsg += "MicrosoftEdge";
                break;
            case Opera:
                caps = new DesiredCapabilities().operaBlink();

                //yeah. i know what it says. it's hacky, but so is opera and it's the only thing that works
                System.setProperty("webdriver.chrome.driver", "C:/browsers/operadriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--private");
                options.setBinary("C:/Program Files (x86)/Opera/47.0.2631.55/opera.exe");
                caps.setCapability(ChromeOptions.CAPABILITY, options);

                logMsg += "operaBlink";
                break;
            case Chrome:
            default:
                caps = new DesiredCapabilities().chrome();
                caps.setBrowserName("chrome");
                caps.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                caps.setCapability("name", testName);
                logMsg += "chrome";
                break;
        }

      //  caps.setCapability(CapabilityType.LOGGING_PREFS, logs);
        System.out.println(logMsg);
        ITestContext ctx = null;
        //Instantiate driver variable to be used throughout tests
        try {
            String url = BaseTestConfig.getSeleniumGridUrl();
            if (BooleanUtils.toBoolean(System.getProperty("useZalenium"))) {
                url = BaseTestConfig.getZaleniumGridUrl();
                caps.setCapability("name", testName);
            }
          //  String testName = ctx.getCurrentXmlTest().getName();
           // caps.setCapability("name", testName);
//            caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
//                    UnexpectedAlertBehaviour.IGNORE);
            driver = new RemoteWebDriver(new URL(url), caps);

            sessionId = ((RemoteWebDriver) driver).getSessionId();
            System.out.println("SessionId: " + sessionId);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //Set size and maximize window
        driver.manage().window().maximize();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        //driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());

       return driver;
    }

    public void restartBrowser() {
        driver.close();
        launchDriver();
    }

    public void stopDriver() {
        try {
            if (!hasDriverQuit()) {
                driver.close();
                driver.quit();
            }
        } catch (Exception e) {
            /*We don't care at the moment if an error occurs when stopping the driver*/
        }
    }

    public File addScreenshot() {
        File file = getScreenshot();
        screenshots.add(file);

        return file;
    }

    private File getScreenshot() {
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        return ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
    }

    public byte[] addScreenshotAsBytes() {
        byte[] bytes = getScreenshotAsBytes();
        //screenshots.add(bytes);

        return bytes;
    }

    private byte[] getScreenshotAsBytes() {
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        return ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
    }

    public Boolean hasDriverQuit() {

        return driver == null ? true : ((RemoteWebDriver) driver).getSessionId() == null;
    }

    public void click(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);
        element.click();
    }

    //"--disable-dev-shm-usage" -> overcome limited resource problems in docker
    //        "start-maximized" -> open Browser in maximized mode
    //   "--disable-extensions" -> disabling extensions
    //          "--disable-gpu" -> applicable to windows os only
    //           "--no-sandbox" -> Bypass OS security model
    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(new String[]{"--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-infobars",
                "--start-fullscreen",
                //"--headless",
                //"--whitelisted-ips",
               "--disable-extensions"
        });
        System.setProperty("webdriver.chrome.silentOutput", "true");

        //disable save password prompts
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "externalFiles" + File.separator + "downloadFiles");
        options.setExperimentalOption("prefs", prefs);

        return options;
    }

    public void waitUntilInvalid(ExpectedCondition condition) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        new WebDriverWait(driver, BaseTestConfig.getExplicitTimeout()).until(condition);
        driver.manage().timeouts().implicitlyWait(BaseTestConfig.getImplicitTimeout(), TimeUnit.SECONDS);
    }

    public void markBrowserStackTestAsFailed() {
        new BrowserStack().markBrowserStackAsFailed(sessionId.toString());
    }

    public void waitForInvisibilityOf(By by) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        new WebDriverWait(driver, BaseTestConfig.getExplicitTimeout()).until(ExpectedConditions.invisibilityOfElementLocated(by));
        driver.manage().timeouts().implicitlyWait(BaseTestConfig.getImplicitTimeout(), TimeUnit.SECONDS);
    }

    public enum SupportedBrowsers {
        Chrome("chrome"),
        Firefox("firefox"),
        InternetExplorer("internet explorer"),
        Edge("MicrosoftEdge"),
        Opera("operablink"),
        Safari("safari");

        final String browserName;

        SupportedBrowsers(String driverName) {
            this.browserName = driverName;
        }

        public static SupportedBrowsers getBrowser(String driverName) {
            SupportedBrowsers browser = null;

            Assert.assertNotNull(driverName, ">>> Browser was NULL. Update -Dbrowser parameter from Configurations/VM Options and try again.");

            switch (driverName.toLowerCase()) {
                case "chrome":
                    browser = SupportedBrowsers.Chrome;
                    break;
                case "firefox":
                    browser = SupportedBrowsers.Firefox;
                    break;
                case "internet explorer":
                case "ie":
                    browser = SupportedBrowsers.InternetExplorer;
                    break;
                case "microsoftedge":
                case "edge":
                    browser = SupportedBrowsers.Edge;
                    break;
                case "operablink":
                case "opera":
                    browser = SupportedBrowsers.Opera;
                    break;
                case "safari":
                    browser = SupportedBrowsers.Safari;
                    break;
                default:
                    Assert.fail(String.format("Browser '%s' not recognized.  " +
                                    "Update -Dbrowser parameter and try again.\n" +
                                    "Supported Browsers: %s",
                            driverName,
                            String.join(", ", Arrays.stream(SupportedBrowsers.values())
                                    .map(SupportedBrowsers::getName)
                                    .toArray(String[]::new))
                    ));
                    break;
            }

            return browser;
        }

        private String getName() {
            return this.browserName;
        }
    }

    private enum ExecutionLocation {
        Local,
        Docker,
        BrowserStack
    }
    //@AfterSuite
    public void tearDownDriver(){
     //   this.driver.close();
        this.driver.quit();
    }

    //Gets remote node IP from selenium grid specifict to test session
    public void getNodeIp(String suiteTestName) {
        if(suiteTestName.equalsIgnoreCase("smoketest")){
            try {
                String gridUrl = BaseTestConfig.getSeleniumGridUrl();
                gridUrl = gridUrl.substring(0, gridUrl.lastIndexOf("wd/hub"));
                Response response = get(gridUrl+"grid/api/testsession?session="+ getSessionId());
                String nodeIp =  response.getBody().jsonPath().getString("proxyId");
                logTestAction("Node IP: "+nodeIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
