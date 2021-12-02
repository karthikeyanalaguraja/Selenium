package core.APIs.BrowserAPIs;

import core.utilities.baseUtilities.Browser;
import core.utilities.baseUtilities.BrowserAPIManager;
import core.utilities.objects.User;
import io.qameta.allure.Step;
import io.restassured.http.Method;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

import java.util.HashMap;


public class OutsideAccountBrowserAPI extends BrowserAPIManager {

    public HashMap<String, String> outsideAccountStatus = new HashMap<>();
    public HashMap<String, String> outsideAccountBalance = new HashMap<>();

    public OutsideAccountBrowserAPI( Browser browser, String url ) {
        super(browser, url);
    }
    
    @Step("{method}:")
    public String getResponse ( Browser browser, Method method, String value) {

        return super.getResponse(browser, method, value);
    }

    public void getOutsideAccountStatus (Browser browser, User user){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray outsideAccountsList = (JSONArray) response.getJSONArray("accounts");

        for (int i = 0; i < outsideAccountsList.length(); i++) {
            outsideAccountStatus.put(outsideAccountsList.getJSONObject(i).get("institutionName").toString(),outsideAccountsList.getJSONObject(i).get("aggregationStatus").toString());
            outsideAccountBalance.put(outsideAccountsList.getJSONObject(i).get("name").toString(),outsideAccountsList.getJSONObject(i).get("availableBalance").toString());
        }

        System.out.println(outsideAccountStatus);
        System.out.println(outsideAccountBalance);
    }


   }