package core.APIs.BrowserAPIs;

import core.utilities.baseUtilities.Browser;
import core.utilities.baseUtilities.BrowserAPIManager;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.cloudinary.json.JSONObject;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class AccountHistoryBrowserAPI extends BrowserAPIManager {

    public AccountHistoryBrowserAPI( Browser browser, String url ) {
        super(browser, url);
    }

    public String getResponse ( Browser browser, Method method, String value) {

       return super.getResponse(browser, method, value);
    }
    
    @Step("{method}:")
    public String getResponse ( Browser browser, Method method, String value, String startDate, String endDate) {

        Set<Cookie> seleniumCookies = browser.driver.manage().getCookies();
        List restAssuredCookies = new ArrayList();

        for (Cookie cookie : seleniumCookies) {
            restAssuredCookies.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        }

        Header acceptJson = new Header("content-type", "application/json");
        RequestSpecification httpRequest = RestAssured.given().header(acceptJson).cookies(new Cookies(restAssuredCookies));

        JSONObject requestParams = new JSONObject();
        requestParams.put("dateEnd",endDate);
        requestParams.put("dateStart",startDate);

        httpRequest.body(requestParams.toString());
        Response response = httpRequest.request(method,value);
        String responseBody = response.getBody().asString();

        return responseBody;
    }


}