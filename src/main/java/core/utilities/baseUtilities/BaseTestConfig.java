package core.utilities.baseUtilities;

import core.utilities.configs.BrowserStack;
import core.utilities.configs.Cloudinary;
import core.utilities.configs.Config;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class BaseTestConfig {

    private static Cloudinary cloudinary;
    private static BrowserStack browserStack;
    private static Integer explicitTimeout;
    private static Integer implicitTimeout;
    private static String seleniumGrid;
    private static String zaleniumGrid;
    private static List<Config> configs;

    private static List<Config> getConfigs() {
        if (configs == null) {
            configs = new Sql().getConfig();
        }

        return configs;
    }

    protected static String getValue(String scope, String key1, String key2) {
        return getConfigs()
                .stream()
                .filter(c ->
                        StringUtils.equalsIgnoreCase(c.scope, scope)
                                && StringUtils.equalsIgnoreCase(c.key1, key1)
                                && StringUtils.equalsIgnoreCase(c.key2, key2))
                .findFirst()
                .get()
                .value;
    }

    protected static Integer getIntValue(String scope, String key1, String key2) {

        return Integer.parseInt(getValue(scope, key1, key2));
    }

    public static Cloudinary getCloudinary(){
        if (cloudinary == null) {
            cloudinary = new Cloudinary(
                    getValue("GLOBAL", "Cloudinary", "CloudName"),
                    getValue("GLOBAL", "Cloudinary", "Key"),
                    getValue("GLOBAL", "Cloudinary", "Secret")
            );
        }
        return cloudinary;
    }

    public static BrowserStack getBrowserStack(){
        if (browserStack == null) {
            browserStack = new BrowserStack(
                    getValue("GLOBAL", "Browserstack", "Username"),
                    getValue("GLOBAL", "Browserstack", "Key")
            );
        }
        return browserStack;
    }

    public static Integer getImplicitTimeout() {
        return getIntValue("GLOBAL", "Timeout", "Implicit");
    }

    public static Integer getExplicitTimeout() {
        return getIntValue("GLOBAL", "Timeout", "Explicit");
    }

    public static String getSeleniumGridUrl(){
        return getValue("Xpress", "Grid", "Selenium");
    }

    public static String getZaleniumGridUrl(){
        return getValue("GLOBAL", "Grid", "Zalenium");
    }

    public abstract String getBaseUrl();
}