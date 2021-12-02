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


public class SpendingTransactionBrowserAPI extends BrowserAPIManager {

    public List<String> shortDescriptionName = new ArrayList<>();
    public List<String> amountList = new ArrayList<>();
    public HashMap<String, String> shortDesc_Amount = new HashMap<>();
    float finalAmount = 0;

    public SpendingTransactionBrowserAPI( Browser browser, String url ) {
        super(browser, url);
    }
    
    @Step("{method}:")
    public String getResponse( Browser browser, Method method, String value, String accIdS, String startDate, String endDate, String category ) {

        Set<Cookie> seleniumCookies = browser.driver.manage().getCookies();
        List restAssuredCookies = new ArrayList();

        for (Cookie cookie : seleniumCookies) {
            restAssuredCookies.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        }

        Header acceptJson = new Header("content-type", "application/json");
        RequestSpecification httpRequest = RestAssured.given().header(acceptJson).cookies(new Cookies(restAssuredCookies));

        JSONObject requestParams = new JSONObject();
        requestParams.put("accId", accIdS);
        requestParams.put("endDate", endDate);
        requestParams.put("startDate", startDate);
        requestParams.put("category", category);

        httpRequest.body(requestParams.toString());
        Response response = httpRequest.request(method, value);
        String responseBody = response.getBody().asString();

        return responseBody;
    }

    public Object getShortDescriptionName( Browser browser, String accIds, String startDate, String endDate, String category ) {
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds, startDate, endDate, category));
        JSONArray spendingTransactions = (JSONArray) response.getJSONArray("transactions");

        for (int i = 0; i < spendingTransactions.length(); i++) {
            shortDescriptionName.add(spendingTransactions.getJSONObject(i).get("shortDescription").toString());
            amountList.add(spendingTransactions.getJSONObject(i).get("amount").toString());

        }
        return shortDescriptionName;
    }

    public Object getShortDescAndAmount( Browser browser, String accIds, String startDate, String endDate, String category ) {

        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds, startDate, endDate, category));
        JSONArray spendingTransactions = (JSONArray) response.getJSONArray("transactions");

        for (int i = 0; i < spendingTransactions.length(); i++) {
            shortDesc_Amount.put(spendingTransactions.getJSONObject(i).get("shortDescription").toString(), spendingTransactions.getJSONObject(i).get("amount").toString());

        }
        return shortDesc_Amount;
    }

    public double calculateTotal() {
        for (int j = 0; j < amountList.size(); j++) {
            String amountS = amountList.get(j);
            float amount = Float.parseFloat(amountS);
            float amountTemp = amount;

            finalAmount = amountTemp + finalAmount;
            amount = 0;
            amountTemp = 0;
        }
        return finalAmount;
    }


}