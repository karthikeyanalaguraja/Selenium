package core.utilities.baseUtilities;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.Cookie;
import org.testng.Assert;

import java.util.*;

import static io.restassured.RestAssured.given;

public class BrowserAPIManager {

    public String endpoint;
    public String url;


    public BrowserAPIManager(Browser browser, String url) {
        this.endpoint = browser.environment;
        this.url = url;
        RestAssured.baseURI = this.endpoint;
        RestAssured.basePath = this.url;
        System.out.println("The Url trying to reach " + this.endpoint + this.url);
    }

    @Step("{method}:")
    public String getResponse ( Browser browser, Method method, String value) {
        
        Set<Cookie> seleniumCookies = browser.driver.manage().getCookies();
        List restAssuredCookies = new ArrayList();
        
        for (Cookie cookie : seleniumCookies) {
            restAssuredCookies.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        }
        
        Header acceptJson = new Header("content-type", "application/json");
        RequestSpecification httpRequest = given().header(acceptJson).cookies(new Cookies(restAssuredCookies));
        Response response = httpRequest.given().filter(new AllureRestAssured()).request(method,value);
        String responseBody = response.getBody().asString();
        Assert.assertEquals(response.statusCode(), 200);
        
        return responseBody;

    }
}
