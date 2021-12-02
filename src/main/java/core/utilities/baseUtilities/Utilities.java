package core.utilities.baseUtilities;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by nick.fields on 6/28/2017.
 */
public class Utilities {

    static File tmpFile;

    private Utilities() {
    }

    public static void workaround(String jira, Runnable runnable) {
        System.out.println(String.format("Executing workaround for https://jensyn.atlassian.net/browse/%s", jira));
        try {
            runnable.run();
        } catch (Exception e) {
            System.out.println(String.format("Workaround for Jira %s failed. Check if it was fixed(https://jensyn.atlassian.net/browse/%s).", jira, jira));
            throw e;
        }
    }

    public static void ignoreInSpecificOrg(String org, String description, Runnable runnable) {
        if (System.getProperty("environment").compareToIgnoreCase(org) == 0) {
            System.out.println(String.format(
                    "Code blocked skipped in %s due to the following: \n\t%s."
                    , org
                    , description
            ));
        } else {
            runnable.run();
        }
    }

    public static void ignoreInSpecificOrgs(String[] org, String description, Runnable runnable) {
        if (Arrays.asList(org).stream().anyMatch(o -> System.getProperty("environment").compareToIgnoreCase(o) == 0)) {
            System.out.println(String.format(
                    "Code block was skipped in %s due to the following: \n\t%s."
                    , org.toString()
                    , description
            ));
        } else {
            runnable.run();
        }
    }

    public static void conditionalRunPerOrg(String org, String description, Runnable ifOrgMatchesRunnable, Runnable elseRunnable) {
        if (System.getProperty("environment").compareToIgnoreCase(org) == 0) {
            System.out.println(String.format(
                    "Org-specific code executed in %s due to the following: \n\t%s."
                    , String.join("|", org)
                    , description
            ));
            ifOrgMatchesRunnable.run();
        } else {
            elseRunnable.run();
        }
    }

    public static void runInSpecificOrgs(String description, Runnable runnable, String... org) {
        if (Arrays.asList(org).stream().anyMatch(o -> System.getProperty("environment").compareToIgnoreCase(o) == 0)) {
            runnable.run();
        }
    }

    public static void launchInNewTab(WebDriver driver, Runnable actionThatLaunchesNewWindow, Runnable actionToCompleteInNewWindow) {
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

    public static void launchNewTabThenSwitchToTab(WebDriver driver, Runnable actionThatLaunchesNewWindow) {
        //get baseline list of windows
        Set<String> currentWindows = driver.getWindowHandles();

        //action that launches the new window
        actionThatLaunchesNewWindow.run();

        //get new list of windows (will include the new one)
        Set<String> newWindows = driver.getWindowHandles();

        //remove original list of windows, leaving just the new
        newWindows.removeAll(currentWindows);

        //switch driver context to new window
        driver.switchTo().window((String) newWindows.toArray()[0]);
    }

    public static void launchNewTab(Browser browser) {
        //get baseline list of windows
        Set<String> currentWindows = browser.driver.getWindowHandles();

        browser.restartBrowser();

        browser.driver.findElement(By.xpath(".//body")).sendKeys(Keys.chord(Keys.CONTROL, "t"));

        //get new list of windows (will include the new one)
        Set<String> newWindows = browser.driver.getWindowHandles();

        //remove original list of windows, leaving just the new
        newWindows.removeAll(currentWindows);

        //switch driver context to new window
        browser.driver.switchTo().window((String) newWindows.toArray()[0]);
    }

    public static String writeToDisk(byte[] bytes) {
        String targetDir = "target/test-attachments/";
        String filename = UUID.randomUUID() + ".png";
        String path = targetDir + filename;

        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(path).getPath();
    }

    public static Long measureTime(String desc, Runnable runnable) {
        Long elapsedTime = measureTime(runnable);
        print("Elapsed time for '%s': %sms", desc, elapsedTime);

        return elapsedTime;
    }

    public static Long measureTime(Runnable runnable) {
        Long startTime = System.currentTimeMillis();
        Long elapsedTime;
        try {
            runnable.run();
        } finally {
            elapsedTime = System.currentTimeMillis() - startTime;
        }

        return elapsedTime;
    }

    public static void log(String message) {
        if (BooleanUtils.toBoolean(System.getProperty("debug"))) {
            System.out.println(message);
        }
    }

    public static void print(String format, Object... params) {

        System.out.println(String.format(format, params));
    }

    public static void executeInFrame(WebDriver driver, WebElement parentFrame, Runnable runnable) {
        try {
            driver.switchTo().frame(parentFrame);
            runnable.run();
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    public static void assertContains(String onErrorMessage, String expectedToContain, String actualContent) {
        Assert.assertTrue(
                StringUtils.containsIgnoreCase(actualContent, expectedToContain),
                String.format("%s\nExpected: %s\nActual: %s\n", onErrorMessage, expectedToContain, actualContent));
    }

    public static void assertEqualsCaseInsensitive(String onErrorMessage, String expectedContent, String actualContent) {
        Assert.assertTrue(
                StringUtils.equalsIgnoreCase(actualContent, expectedContent),
                String.format("%s\nExpected: %s\nActual: %s\n", onErrorMessage, expectedContent, actualContent));
    }

    public static void staleElementWorkaround(Runnable runnable) {
        try {
            runnable.run();
        } catch (StaleElementReferenceException stale) {
            try {
                print("Stale element, trying again after 3s wait");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runnable.run();
        }
    }

    public static String getTestUploadDocPath() {
        if (tmpFile == null) {
            File tmpDir = TemporaryFilesystem.getDefaultTmpFS().createTempDir("testun", "tests");
            System.out.println(tmpDir.getAbsolutePath());
            try {
                BufferedImage img = ImageIO.read(new URL("http://i.imgur.com/tqE84.jpg"));
                tmpFile = new File(tmpDir, "test.png");
                ImageIO.write(img, "png", tmpFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return tmpFile.getAbsolutePath().replaceAll("\\\\", "/");
    }

    public static String convertListToQuotedCommaDelimitedString(List<String> list) {
        return String.format("'%s'", String.join("','", list));
    }

    public static void sendKeysSlowly(WebElement element, String textToType) {
        textToType.chars().forEachOrdered(l -> {
            try {
                Thread.sleep(333);//average typing speed ~3 char/second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            element.sendKeys(String.valueOf((char) l));
        });
    }
}
