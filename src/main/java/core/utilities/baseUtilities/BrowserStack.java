package core.utilities.baseUtilities;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import javax.json.Json;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class BrowserStack {

    String browser;
    String os;
    String os_version;

    public void markBrowserStackMobileAsFailed(String sessionId) {
        try {
            String url = String.format(
                    "%s/sessions/%s.json",
                    BaseTestConfig.getBrowserStack().getAppAutomateUrl(),
                    sessionId
            );

            String postBody = Json.createObjectBuilder()
                    .add("status", "failed")
                    .build()
                    .toString();

            Response response = given().
                    contentType("application/json").
                    body(postBody).
                    when().
                    put(url);

            Assert.assertEquals(200, String.valueOf(response.statusCode()));
        } catch (Exception e) {/*do nothing*/}
    }

    public void markBrowserStackAsFailed(String sessionId) {
        try {
            String url = String.format(
                    "%s/sessions/%s.json",
                    BaseTestConfig.getBrowserStack().getWebAutomateUrl(),
                    sessionId
            );

            String postBody = Json.createObjectBuilder()
                    .add("status", "failed")
                    .build()
                    .toString();

            Response response = given().
                    contentType("application/json").
                    body(postBody).
                    when().
                    put(url);

            Assert.assertEquals(200, String.valueOf(response.statusCode()));
        } catch (Exception e) {/*do nothing*/}
    }

    public DesiredCapabilities getCapabilities(String browser, String os, String os_version) {
        return getCapabilities(browser, os, os_version, System.getProperty("browser_version"));
    }

    public DesiredCapabilities getCapabilities(String browser, String os, String os_version, String browser_version) {
        List<Browser> allBrowsers;
        List<Browser> filteredBrowsers;
        List<String> distinctVersions;
        Browser filteredBrowser;
        Response response;

        this.browser = StringUtils.isNotEmpty(browser) ? browser : "chrome";
        this.os = StringUtils.isNotEmpty(os) ? os : "Windows";
        this.os_version = StringUtils.isNotEmpty(os_version) ? os_version :
                StringUtils.equalsIgnoreCase(this.os, "Windows") ? BaseTestConfig.getBrowserStack().getLatestWindowsVersion() :
                        BaseTestConfig.getBrowserStack().getLatestOsxVersion();

        //get most recent list of supported browsers directly from browserstack
        response = given().when().get(BaseTestConfig.getBrowserStack().getWebAutomateUrl() + "/browsers.json");

        //deserialize into list of browsers to work with
        allBrowsers = new Gson().fromJson(response.asString(), new TypeToken<List<Browser>>() {
        }.getType());

        //filter down list by browser and os, then sort by browser major version (desc) and os version (desc)
        // this is so the newest browser and os are listed first
        filteredBrowsers = allBrowsers.
                stream().
                filter(b -> StringUtils.equalsIgnoreCase(b.browser, this.browser)
                        && StringUtils.equalsIgnoreCase(b.os, this.os)).
                sorted(Comparator.comparing(Browser::getBrowserMajorVersion)
                        .thenComparing(Browser::getOsVersion)
                        .reversed()).
                collect(Collectors.toList());

        //extract all distinct versions so we can ensure that we aren't testing the same version
        distinctVersions = filteredBrowsers.stream().map(Browser::getBrowser_version).distinct().collect(Collectors.toList());

        //if current, current-n are passed in, then we get the newest or the newest minus n version, else we get newest
        if (StringUtils.containsIgnoreCase(browser_version, "current")) {
            String[] versionArray = browser_version.split("-");
            if (versionArray.length > 1) {
                filteredBrowser = filteredBrowsers
                        .stream()
                        .filter(b -> StringUtils.equalsIgnoreCase(b.browser_version, distinctVersions.get(Integer.parseInt(versionArray[1]))))
                        .findFirst()
                        .get();
            } else {
                filteredBrowser = filteredBrowsers
                        .stream()
                        .filter(b -> StringUtils.equalsIgnoreCase(b.browser_version, distinctVersions.get(0)))
                        .findFirst()
                        .get();
            }
        } else {
            filteredBrowser = filteredBrowsers
                    .stream()
                    .filter(b -> StringUtils.equalsIgnoreCase(b.browser_version, distinctVersions.get(0)))
                    .findFirst()
                    .get();
        }

        //set our capabilities directly from the cap's pulled from BS
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", filteredBrowser.browser);
        caps.setCapability("browser_version", filteredBrowser.browser_version);
        caps.setCapability("os", filteredBrowser.os);
        caps.setCapability("os_version", filteredBrowser.os_version);
        caps.setCapability("resolution", "1920x1080");

        if (StringUtils.containsIgnoreCase(this.browser, "opera")) {
            ChromeOptions opts = new ChromeOptions();
            opts.addArguments("-screenheight 1080");
            opts.addArguments("-screenwidth 1920");
            opts.addArguments("-private");
            caps.setCapability(ChromeOptions.CAPABILITY, opts);
        }

        return caps;
    }

    public RemoteWebDriver getDriver() {
        DesiredCapabilities cap = getCapabilities(
                System.getProperty("browser"),
                System.getProperty("os"),
                System.getProperty("os_version"));


        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(BaseTestConfig.getBrowserStack().getHubUrl()), cap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (!StringUtils.containsIgnoreCase(this.browser, "opera")) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }
        driver.manage().timeouts().implicitlyWait(BaseTestConfig.getImplicitTimeout(), TimeUnit.SECONDS);

        driver.setFileDetector(new LocalFileDetector());

        return driver;
    }

    private enum MacOS {
        Cheetah(0),
        Puma(1),
        Jaguar(2),
        Panther(3),
        Tiger(4),
        Leopard(5),
        SnowLeopard(6) {
            @Override
            public String toString() {
                return "Snow Leopard";
            }
        },
        Lion(7),
        MountainLion(8) {
            @Override
            public String toString() {
                return "Mountain Lion";
            }
        },
        Mavericks(9),
        Yosemite(10),
        ElCapitan(11) {
            @Override
            public String toString() {
                return "El Capitan";
            }
        },
        Sierra(12),
        HighSierra(13) {
            @Override
            public String toString() {
                return "High Sierra";
            }
        };

        Integer minorVersion;

        MacOS(Integer minorVersion) {
            this.minorVersion = minorVersion;
        }

        public static MacOS byString(String value) {
            return Arrays
                    .asList(MacOS.values())
                    .stream()
                    .filter(os -> StringUtils.equalsIgnoreCase(os.toString(), value))
                    .findFirst()
                    .get();
        }

        public Integer getMinorVersion() {
            return this.minorVersion;
        }
    }

    private enum WindowsOS {
        XP(1),
        Win7(2) {
            @Override
            public String toString() {
                return "7";
            }
        },
        Win8(3) {
            @Override
            public String toString() {
                return "8";
            }
        },
        Win81(4) {
            @Override
            public String toString() {
                return "8.1";
            }
        },
        Win10(5) {
            @Override
            public String toString() {
                return "10";
            }
        };

        Integer order;

        WindowsOS(Integer sortOrder) {
            order = sortOrder;
        }

        public static WindowsOS byString(String value) {
            return Arrays
                    .asList(WindowsOS.values())
                    .stream()
                    .filter(os -> StringUtils.equalsIgnoreCase(os.toString(), value))
                    .findFirst()
                    .get();
        }

        public Integer getSortOrder() {
            return order;
        }
    }

    private class Browser {
        public String browser_version;

        public String os;

        public String real_mobile;

        public String device;

        public String browser;

        public String os_version;

        public String getBrowser_version() {
            return browser_version;
        }

        public Integer getBrowserMajorVersion() {
            return Integer.parseInt(browser_version.split("\\.")[0]);
        }

        public Integer getOsVersion() {
            if (StringUtils.containsIgnoreCase(os, "Windows")) {
                return WindowsOS.byString(os_version).getSortOrder();
            } else {
                return MacOS.byString(os_version).getMinorVersion();
            }
        }
    }
}
