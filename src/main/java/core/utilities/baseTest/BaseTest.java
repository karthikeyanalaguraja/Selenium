package core.utilities.baseTest;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by nick.fields on 4/24/17.
 */


public abstract class BaseTest {
    static String runStartTime;
    static String runEndTime;

    //protected Browser browser = new Browser();

//    @Rule
//    public TestName name = new TestName();
//    protected String testName;
//    protected Browser browser;
//    Long startTime;
//    Long endTime;
//    String runName;
//    String testResult;
//    //this runs before all derived @Before and after all @After methods
//    @Rule
//    public TestWatcher watcher = new TestWatcher() {
//        //this runs before all test initialize methods
//        @Override
//        protected void starting(Description description) {
//            startTime = System.currentTimeMillis();
//            runName = getRunName();
//            testName = description.getMethodName();
//
//            //TestOwner owner = description.getAnnotation(TestOwner.class);
//            System.out.println(String.format("%s start time: %s", testName, new Date().toString()));
//            //Utilities.print("Owner: %s", ObjectUtils.anyNotNull(owner) ? "N/A" : owner.owner());
//            browser = new Browser(testName, description);
//
//            super.starting(description);
//        }
//
//        @Override
//        protected void failed(Throwable e, Description description) {
//            testResult = "Failed";
//
//            if (browser.useBrowserStack) {
//                browser.markBrowserStackTestAsFailed();
//            }
//
//            //display only last few screenshots
//            Integer screenshotsToUpload = 2;
//            Integer start = 0;
//            if (browser.screenshots.size() > screenshotsToUpload) {
//                start = browser.screenshots.size() - (screenshotsToUpload);
//            }
//
//            for (int i = start; i < browser.screenshots.size(); i++) {
//                String path = browser.screenshots.get(i).getAbsolutePath();
//                String url = Cloudinary.uploadImage(path);
//                Utilities.print("Screenshot %s: %s", i, url);
//            }
//
//            if (!browser.hasDriverQuit()) {
//                try {
//                    String ss = browser.addScreenshot().getAbsolutePath();
//                    String url = Cloudinary.uploadImage(ss);
//                    Utilities.print("Final Screenshot: %s", url);
//                } catch (Exception ex) {
//                    Utilities.print("An error occurred while capturing screenshot: %s", ex.getMessage());
//                }
//            }
//
//            super.failed(e, description);
//        }
//
//        @Override
//        protected void succeeded(Description description) {
//            testResult = "Passed";
//            super.succeeded(description);
//        }
//
//        @Override
//        protected void finished(Description description) {
//            endTime = System.currentTimeMillis();
//
//            System.out.println(String.format("%s end time: %s", testName, new Date().toString()));
//            browser.stopDriver();
//
//            super.finished(description);
//        }
//    };
    String stacktrace;
    String url;
    ByteArrayOutputStream baos;
    PrintStream stream;
    PrintStream old;

    //<editor-fold desc="Class Initializers">
    public static void baseClassInit() {

        runStartTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }
    //</editor-fold>

//    @AfterClass
//    public static void baseClassCleanup() {
//
//        runEndTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//    }

    //<editor-fold desc="Test Initializers">
    //runs after testwatcher.starting, but before derived @Before methods
//    @BeforeMethod
//    public void baseTestInit() {
//
//    }
    //</editor-fold>

    //runs before testwatcher.finished, and after derived @After methods
//    @AfterMethod
//    public void baseTestCleanup() {
//
//    }

    private String getRunName() {
        if (StringUtils.isNotEmpty(System.getProperty("runtitle"))) {
            return System.getProperty("runtitle") + " - " + runStartTime;
        } else {
            Map<String, String> env = System.getenv();
            if (env.containsKey("COMPUTERNAME"))
                return env.get("COMPUTERNAME") + " - " + runStartTime;
            else if (env.containsKey("HOSTNAME"))
                return env.get("HOSTNAME") + " - " + runStartTime;
            else return "Untitled - " + runStartTime;
        }
    }

    private String getTestOwner() {

        return "";
    }
}
