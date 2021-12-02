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
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class WeeklyTotalsBrowserAPI extends BrowserAPIManager {

    public HashMap<String, String> categoryWatch = new HashMap<>();
    public List<String> categoryWatchList = new ArrayList<>();
    float finalAmount = 0;

    public WeeklyTotalsBrowserAPI( Browser browser, String url ) {
        super(browser, url);
    }
    
    @Step("{method}:")
    public String getResponse ( Browser browser, Method method, String value, String accIdS) {

        Set<Cookie> seleniumCookies = browser.driver.manage().getCookies();
        List restAssuredCookies = new ArrayList();

        for (Cookie cookie : seleniumCookies) {
            restAssuredCookies.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        }

        Header acceptJson = new Header("content-type", "application/json");
        RequestSpecification httpRequest = RestAssured.given().header(acceptJson).cookies(new Cookies(restAssuredCookies));

        JSONObject requestParams = new JSONObject();
        requestParams.put("accId",accIdS);

        httpRequest.body(requestParams.toString());
        Response response = httpRequest.request(method,value);
        String responseBody = response.getBody().asString();

        return responseBody;
    }

    public Object getCategoryWatchMap (Browser browser, String accIds ){
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds));
        JSONArray spendingTotal = (JSONArray) response.getJSONArray("spendingTotals");


        for (int i = 0; i < spendingTotal.length(); i++) {
            if(spendingTotal.getJSONObject(i).get("category").equals("Deposit") || spendingTotal.getJSONObject(i).get("category").equals("Bill") || spendingTotal.getJSONObject(i).get("category").equals("Transfer") ) {
                System.out.println("Ignoring the Bill, Deposit and Transfer amounts");
            } else {
                categoryWatch.put(spendingTotal.getJSONObject(i).get("category").toString(), spendingTotal.getJSONObject(i).get("total").toString());
                categoryWatchList.add(spendingTotal.getJSONObject(i).get("total").toString());
            }
        }

        return categoryWatch;
    }

    public float calculateTotal() {
        for (int j = 0; j < categoryWatchList.size(); j++) {
            String amountS = categoryWatchList.get(j);
            float amount = Float.parseFloat(amountS);
            finalAmount = amount + finalAmount;
            amount = 0;
        }

        System.out.println("Inside the calculate total class");
        System.out.println(finalAmount);
        System.out.println(Math.abs(finalAmount));
        System.out.println(Math.round(Math.abs(finalAmount)));
        //System.out.println(Math.round(finalAmount));
        //System.out.println(Math.abs(Math.round(finalAmount)));
        //System.out.println(Math.round(Math.ceil(finalAmount)));
        //System.out.println(Math.abs(Math.round(Math.ceil(finalAmount))));

        return Math.round(Math.abs(finalAmount));
        //return Math.abs(Math.round(Math.ceil(finalAmount)));
    }


}