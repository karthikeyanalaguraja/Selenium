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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class GetAccSpendingPrefBrowserAPI extends BrowserAPIManager {

    public HashMap<String, String> categoryMap = new HashMap<>();
    public List<String> categoryName = new ArrayList<>();


    public GetAccSpendingPrefBrowserAPI( Browser browser, String url ) {
        super(browser, url);
    }
    
    @Step("{method}:")
    public String getResponse( Browser browser, Method method, String value, String accIdS ) {

        Set<Cookie> seleniumCookies = browser.driver.manage().getCookies();
        List restAssuredCookies = new ArrayList();

        for (Cookie cookie : seleniumCookies) {
            restAssuredCookies.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        }

        Header acceptJson = new Header("content-type", "application/json");
        RequestSpecification httpRequest = RestAssured.given().header(acceptJson).cookies(new Cookies(restAssuredCookies));

        JSONObject requestParams = new JSONObject();
        requestParams.put("accId", accIdS);

        httpRequest.body(requestParams.toString());
        Response response = httpRequest.request(method, value);
        String responseBody = response.getBody().asString();

        return responseBody;
    }

    
    public int calculateTSWeekly( String day ) {
        int dayValue = 0;

        if (day.equals("Monday")) {
            dayValue = 1;
        }
        if (day.equals("Tuesday")) {
            dayValue = 2;
        }
        if (day.equals("Wednesday")) {
            dayValue = 3;
        }
        if (day.equals("Thursday")) {
            dayValue = 4;
        }
        if (day.equals("Friday")) {
            dayValue = 5;
        }
        if (day.equals("Saturday")) {
            dayValue = 6;
        }
        if (day.equals("Sunday")) {
            dayValue = 7;
        }
        return dayValue;
    }

    
    public String getTSFrequency( Browser browser, String accIds ) {
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds));
        JSONObject totalSpending = response.getJSONObject("totalSpending");

        return String.valueOf(totalSpending.get("frequency"));
    }

    public String getTSdate( Browser browser, String accIds ) {
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds));
        JSONObject totalSpending = response.getJSONObject("totalSpending");
        int daysLeft = 0;
        int finalDaysLeft = 0;
        String date = null;

        if (totalSpending.get("frequency").equals("Weekly")) {
            LocalDate localDate = LocalDate.now();
            int today = localDate.getDayOfWeek().getValue();
            String tsDay = String.valueOf(totalSpending.get("value"));
            int todayTs = calculateTSWeekly(tsDay);
            if (today < todayTs) {
                daysLeft = today - todayTs;
                finalDaysLeft = 7 - Math.abs(daysLeft);
                date = String.valueOf(localDate.minusDays(Math.abs(finalDaysLeft)));
            } else {
                daysLeft = today - todayTs;
                date = String.valueOf(localDate.minusDays(Math.abs(daysLeft)));
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH);
            LocalDate ld = LocalDate.parse(date, dtf);
            String formated_date = dtf2.format(ld).toUpperCase();

            return formated_date;
        } else {
            date = String.valueOf(totalSpending.get("value"));

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH);
            LocalDate ld = LocalDate.parse(date, dtf);
            String formated_date = dtf2.format(ld).toUpperCase();
    
            return formated_date;
        }
    }

    public String getTSLimit( Browser browser, String accIds ) {
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds));
        JSONObject totalSpending = response.getJSONObject("totalSpending");
        
        return String.valueOf(totalSpending.get("limit"));
        
    }

    public HashMap<String, String> getCategoryWatchMap( Browser browser, String accIds ) {
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds));
        JSONObject categoryWatch = response.getJSONObject("categoryWatch");
        JSONArray watchCategories = categoryWatch.getJSONArray("watchCategories");

        for (int i = 0; i < watchCategories.length(); i++) {
            categoryMap.put(watchCategories.getJSONObject(i).get("category").toString(), watchCategories.getJSONObject(i).get("limit").toString());
        }

        return categoryMap;
    }

    public List<String> getCategoryNameList( Browser browser, String accIds ) {
        JSONObject response = new JSONObject(getResponse(browser, Method.POST, "", accIds));
        JSONObject categoryWatch = response.getJSONObject("categoryWatch");
        JSONArray watchCategories = categoryWatch.getJSONArray("watchCategories");

        for (int i = 0; i < watchCategories.length(); i++) {
            categoryName.add(watchCategories.getJSONObject(i).get("category").toString());
        }

        return categoryName;
    }


}