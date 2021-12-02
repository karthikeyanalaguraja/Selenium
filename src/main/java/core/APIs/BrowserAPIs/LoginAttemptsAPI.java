package core.APIs.BrowserAPIs;

import core.utilities.baseUtilities.Browser;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.openqa.selenium.Cookie;
import org.testng.Assert;

import static io.restassured.path.json.JsonPath.from;
import java.util.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;


public class LoginAttemptsAPI  {
    private static String requestURL = null;
    List<Map<String, ?>> dateTimeOfLoginFromAPI;
    List<Map<String, ?>> dayOfWeekFromAPI;
    List<Map<String, ?>> channelFromAPI;
    List<Map<String, ?>> ipAddressFromAPI;
    List<Map<String, ?>> statusFromAPI;


    public LoginAttemptsAPI(Browser browser, String url) {
        RestAssured.baseURI =  browser.environment;
        RestAssured.basePath = url;
        requestURL =  RestAssured.baseURI + RestAssured.basePath;
        System.out.println("The Url trying to reach " + RestAssured.baseURI + RestAssured.basePath);
    }

    /**
     * This method will fetch loginAttempts records from an API and verify them with UI records
     * @param browser : browser
     * @param dateTimeUI : date and Time of login from UI
     * @param dayUI : Day od the week from UI
     * @param channelUI : Channel from UI
     * @param statusUI : Login Status from UI
     * @param ipUI : Ip Address of a user
     */
    public void getLoginAttemptsAPI(Browser browser, List<String> dateTimeUI, List<String> dayUI,List<String> channelUI, List<String> statusUI,List<String> ipUI) {
        Set<Cookie> seleniumCookies = browser.driver.manage().getCookies();
        List restAssuredCookies = new ArrayList();

        for (Cookie cookie : seleniumCookies) {
            restAssuredCookies.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        }

        Response response = given()
                .header("Accept", "application/json")
                .cookies(new Cookies(restAssuredCookies))
                .when()
                .get(requestURL)
                .then()
                .statusCode(200)
                .extract().response();
        HashMap<String, ?> resp = from(response.asString()).get("");
        assertThat(resp.size(), greaterThan(0));
        dateTimeOfLoginFromAPI = from(response.asString()).get("loginAttempts.time");
        dayOfWeekFromAPI       = from(response.asString()).get("loginAttempts.day");
        channelFromAPI         = from(response.asString()).get("loginAttempts.channel");
        ipAddressFromAPI       = from(response.asString()).get("loginAttempts.ip");
        statusFromAPI          = from(response.asString()).get("loginAttempts.status");
        for (int i = 0; i < dateTimeOfLoginFromAPI.size(); i++) {
            Assert.assertEquals(dateTimeOfLoginFromAPI.get(i), dateTimeUI.get(i), "Verifying time stamps");
            Assert.assertEquals(dayOfWeekFromAPI.get(i),       dayUI.get(i), "Verifying day of the week");
            Assert.assertEquals(channelFromAPI.get(i),         channelUI.get(i), "Verifying channel ");
            Assert.assertEquals(ipAddressFromAPI.get(i),       statusUI.get(i), "Verifying ip address");
            Assert.assertEquals(statusFromAPI.get(i),          ipUI.get(i), "Verifying status");
        }
    }
}
