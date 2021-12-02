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


public class SpendingTotalsBrowserAPI extends BrowserAPIManager {

    public List<Object> categoryWatchCategoryName = new ArrayList<>();
    public HashMap<String, Object> categoryWatchTransactionCount = new HashMap<>();
    public HashMap<String, String> categoryWatch_Expense = new HashMap<>();
    public HashMap<String, String> categoryWatch_Income = new HashMap<>();
    public List<String> categoryWatchList = new ArrayList<>();
    public List<String> categoryWatchName = new ArrayList<>();
    float finalAmount = 0;

    public SpendingTotalsBrowserAPI( Browser browser, String url ) {
        super(browser, url);
    }
    
    @Step("{method}:")
    public String getResponse ( Browser browser, Method method, String value, String accIdS, String startDate, String endDate) {

        Set<Cookie> seleniumCookies = browser.driver.manage().getCookies();
        List restAssuredCookies = new ArrayList();

        for (Cookie cookie : seleniumCookies) {
            restAssuredCookies.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        }

        Header acceptJson = new Header("content-type", "application/json");
        RequestSpecification httpRequest = RestAssured.given().header(acceptJson).cookies(new Cookies(restAssuredCookies));

        JSONObject requestParams = new JSONObject();
        requestParams.put("accId",accIdS);
        requestParams.put("endDate",endDate);
        requestParams.put("startDate",startDate);

        httpRequest.body(requestParams.toString());
        Response response = httpRequest.request(method,value);
        String responseBody = response.getBody().asString();

        return responseBody;
    }

    public Object getCategoryWatchCategoryName (Browser browser, String accIds, String startDate, String endDate){
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds, startDate, endDate));
        JSONArray spendingTotal = (JSONArray) response.getJSONArray("spendingTotals");

        for (int i = 0; i < spendingTotal.length(); i++) {
            categoryWatchCategoryName.add(spendingTotal.getJSONObject(i).get("category").toString());
        }

        return categoryWatchCategoryName;
    }

    public Object getCategoryWatchTransactionCount (Browser browser, String accIds, String startDate, String endDate ){
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds,startDate, endDate));
        JSONArray spendingTotal = (JSONArray) response.getJSONArray("spendingTotals");

        for (int i = 0; i < spendingTotal.length(); i++) {
            categoryWatchTransactionCount.put(spendingTotal.getJSONObject(i).get("category").toString(), spendingTotal.getJSONObject(i).get("count"));
        }

        return categoryWatchTransactionCount;
    }

    public Object getCategoryWatchExpense (Browser browser, String accIds, String startDate, String endDate  ){
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds,startDate, endDate));
        JSONArray spendingTotal = (JSONArray) response.getJSONArray("spendingTotals");


        for (int i = 0; i < spendingTotal.length(); i++) {
            if(spendingTotal.getJSONObject(i).get("category").equals("Deposit") || spendingTotal.getJSONObject(i).get("category").equals("Bill") || spendingTotal.getJSONObject(i).get("category").equals("Transfer") ) {
                categoryWatch_Income.put(spendingTotal.getJSONObject(i).get("category").toString(), spendingTotal.getJSONObject(i).get("total").toString());
            } else {
                categoryWatch_Expense.put(spendingTotal.getJSONObject(i).get("category").toString(), spendingTotal.getJSONObject(i).get("total").toString());
            }
        }
        return categoryWatch_Expense;
    }

    public Object getCategoryWatchIncome (Browser browser, String accIds, String startDate, String endDate  ){
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds,startDate, endDate));
        JSONArray spendingTotal = (JSONArray) response.getJSONArray("spendingTotals");


        for (int i = 0; i < spendingTotal.length(); i++) {
            if(spendingTotal.getJSONObject(i).get("category").equals("Deposit") || spendingTotal.getJSONObject(i).get("category").equals("Bill") || spendingTotal.getJSONObject(i).get("category").equals("Transfer") ) {
                categoryWatch_Income.put(spendingTotal.getJSONObject(i).get("category").toString(), spendingTotal.getJSONObject(i).get("total").toString());
            }
        }
        return categoryWatch_Income;
    }

    public Object getCategoryWatchName (Browser browser, String accIds, String startDate, String endDate  ){
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds,startDate, endDate));
        JSONArray spendingTotal = (JSONArray) response.getJSONArray("spendingTotals");


        for (int i = 0; i < spendingTotal.length(); i++) {
            if(spendingTotal.getJSONObject(i).get("category").equals("Deposit") || spendingTotal.getJSONObject(i).get("category").equals("Bill") || spendingTotal.getJSONObject(i).get("category").equals("Transfer") ) {
                categoryWatch_Income.put(spendingTotal.getJSONObject(i).get("category").toString(), spendingTotal.getJSONObject(i).get("total").toString());
            } else {
                categoryWatchName.add(spendingTotal.getJSONObject(i).get("category").toString());
            }
        }
        return categoryWatchName;
    }

    public Object getCategoryWatchList (Browser browser, String accIds, String startDate, String endDate  ){
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds,startDate, endDate));
        JSONArray spendingTotal = (JSONArray) response.getJSONArray("spendingTotals");


        for (int i = 0; i < spendingTotal.length(); i++) {
            if(spendingTotal.getJSONObject(i).get("category").equals("Deposit") || spendingTotal.getJSONObject(i).get("category").equals("Bill") || spendingTotal.getJSONObject(i).get("category").equals("Transfer") ) {
                categoryWatch_Income.put(spendingTotal.getJSONObject(i).get("category").toString(), spendingTotal.getJSONObject(i).get("total").toString());
            } else {
                categoryWatchList.add(spendingTotal.getJSONObject(i).get("total").toString());
            }
        }
        return categoryWatchList;
    }


    public double calculateTotal() {
        for (int j = 0; j < categoryWatchList.size(); j++) {
            String amountS = categoryWatchList.get(j);
            float amount = Float.parseFloat(amountS);
            float amountTemp = Math.round(amount);

            finalAmount = amountTemp + finalAmount;
            
            amount = 0;
            amountTemp = 0;
            
        }
        return Math.ceil(Math.abs(finalAmount));
    }





}