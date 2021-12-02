package core.utilities.configs;

public class BrowserStack {

    private String username;
    private String password;

    private String hubUrlFormat = "https://%s:%s@hub-cloud.browserstack.com/wd/hub";
    private String webAutomateUrlFormat = "https://%s:%s@api.browserstack.com/automate";
    private String appAutomateUrlFormat = "https://%s:%s@api.browserstack.com/app-automate";

    private String latestWindowsVersion = "10";
    private String latestOsxVersion = "High Sierra";

    public BrowserStack(String user, String pass) {
        this.username = user;
        this.password = pass;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHubUrl() {
        return String.format(hubUrlFormat, getUsername(), getPassword());
    }

    public String getAppAutomateUrl() {
        return String.format(appAutomateUrlFormat, getUsername(), getPassword());
    }

    public String getWebAutomateUrl() {
        return String.format(webAutomateUrlFormat, getUsername(), getPassword());
    }

    public String getLatestWindowsVersion() {
        return latestWindowsVersion;
    }

    public String getLatestOsxVersion() {
        return latestOsxVersion;
    }
}