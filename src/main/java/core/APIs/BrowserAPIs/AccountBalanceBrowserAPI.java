package core.APIs.BrowserAPIs;

import core.utilities.baseUtilities.Browser;
import core.utilities.baseUtilities.BrowserAPIManager;
import core.utilities.objects.User;
import io.qameta.allure.Step;
import io.restassured.http.Method;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AccountBalanceBrowserAPI extends BrowserAPIManager {

    public HashMap<String, Object> balanceAvailableMap = new HashMap<>();
    public HashMap<String, Object> balanceCurrentMap = new HashMap<>();
    public List<Object> sample = new ArrayList<>();

    public AccountBalanceBrowserAPI( Browser browser, String url ) {
         super(browser, url);
    }
    
    @Step("{method}:")
    public String getResponse ( Browser browser, Method method, String value) {

        return super.getResponse(browser, method, value);
    }
    
    public Object getAccountBalance(Browser browser, User user){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accountsBalanceInfoList");

        for (int i = 0; i < pendingTransaction.length(); i++) {
            String responseEntity = String.valueOf(response.getJSONArray("accountsBalanceInfoList").getJSONObject(i).get("accId"));
            if(user.accountEntity.equals(responseEntity) ) {
                balanceAvailableMap.put(response.getJSONArray("accountsBalanceInfoList").getJSONObject(i).get("accId").toString(), response.getJSONArray("accountsBalanceInfoList").getJSONObject(i).getJSONObject("balDetails").get("balAvailable"));
                balanceCurrentMap.put(response.getJSONArray("accountsBalanceInfoList").getJSONObject(i).get("accId").toString(),response.getJSONArray("accountsBalanceInfoList").getJSONObject(i).getJSONObject("balDetails").get("balCurrent"));
            }
        }
        return balanceAvailableMap;
    }

    public HashMap<String, Object> getBalanceCurrentMap ( Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accountsBalanceInfoList");

        for(int i = 0; i < pendingTransaction.length(); i++) {
            balanceCurrentMap.put(response.getJSONArray("accountsBalanceInfoList").getJSONObject(i).get("accId").toString(),response.getJSONArray("accountsBalanceInfoList").getJSONObject(i).getJSONObject("balDetails").get("balCurrent").toString());
        }

        return balanceCurrentMap;
    }

    public HashMap<String, Object> getBalanceAvailableMap ( Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accountsBalanceInfoList");

        for(int i = 0; i < pendingTransaction.length(); i++) {
            balanceAvailableMap.put(response.getJSONArray("accountsBalanceInfoList").getJSONObject(i).get("accId").toString(),response.getJSONArray("accountsBalanceInfoList").getJSONObject(i).getJSONObject("balDetails").get("balAvailable").toString());
        }

        return balanceAvailableMap;
    }


}
