package core.utilities.baseTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import core.basePages.AllCommunityPages;
import core.utilities.baseUtilities.Browser;
import core.utilities.baseUtilities.SqlManager;
import core.utilities.baseUtilities.XmlFileWriter;
import core.utilities.extentReport.ExtentManager;
import core.utilities.objects.User;
import io.qameta.allure.Attachment;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class BaseCommunityTest implements IHookable {

    protected String randomValue;

    protected static ExtentReports extent = ExtentManager.createInstance();
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    protected ExtentTest extentTest;

    //objects
    protected AllCommunityPages pages;
    protected SqlManager sqlManager;
    protected User user;
    protected String environment;
    protected Browser browser;
    protected XmlFileWriter xmlFileWriter;
    protected String  suiteName;


    @BeforeClass(alwaysRun = true)
    public void classInitialize(){
        sqlManager = new SqlManager();
    }

    @BeforeMethod (alwaysRun = true)
    public void testInitialize(final XmlTest xml){
        try{
            browser = new Browser();
            browser.setTestLogger(test);
            browser.launchDriver();
//            if(xml != null){
//                this.suiteName = xml.getName();
//            }else{
//                this.suiteName = "";
//            }
            randomValue = UUID.randomUUID().toString().replaceAll("-", "");
            pages = new AllCommunityPages(browser);
            environment = pages.login.getEnvironment();
            System.out.println("Environment: " + System.getProperty("environment"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterMethod (alwaysRun = true)
    public void testFinalize(){
            //System.out.println("Quiting WebDriver after test...");
            try{
                if(browser.driver!=null){
                    browser.driver.quit();
                }
            }catch (Exception e){
                System.out.println(e.getStackTrace());
            }
    }

    @AfterClass (alwaysRun = true)
    public void testClassFinalize(){
        try{
            //System.out.println("Quiting WebDriver after test class...");
            if(browser.driver!=null){
                browser.driver.quit();
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }
    

    @AfterSuite(alwaysRun = true)
    public void suiteFinalizer() throws IOException {
        xmlFileWriter = new XmlFileWriter();
        xmlFileWriter.createAllureEnvXml();
    }
    
   @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null) {
            try {
                takeScreenShot(testResult.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Attachment(value = "Screen shot of {0} when Exception thrown:", type = "image/png")
    private byte[] takeScreenShot(String methodName) throws IOException {
        try
        {
            BufferedImage image  = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(browser.driver).getImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "Unable to Get Screenshot.".getBytes();
    }

}
