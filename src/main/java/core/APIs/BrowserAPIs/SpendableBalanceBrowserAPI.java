package core.APIs.BrowserAPIs;

import core.utilities.baseUtilities.Browser;
import core.utilities.baseUtilities.BrowserAPIManager;
import io.restassured.http.Method;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

import java.util.HashMap;


public class SpendableBalanceBrowserAPI extends BrowserAPIManager {

    public String entity;
    public static String spendableBalanceUrl1;

      public SpendableBalanceBrowserAPI( Browser browser, String url ) {

        super(browser, url);
    }

    public double getBalAvailable (Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        return response.getJSONObject("spendableBalDetails").getDouble("balAvailable");
    }
    public double getHoldAmount (Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        return  response.getJSONObject("spendableBalDetails").getDouble("balHoldAmount");
    }
    public int getDateRange(Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        return  response.getJSONObject("spendableBalDetails").getInt("dateRange");
    }

    public boolean getToolTipSeen (Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        return  response.getJSONObject("spendableBalDetails").getBoolean("toolTipSeen");
    }

    public int getPendingTransactionSize (Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONObject myResponse = response.getJSONObject("spendableBalDetails");
        JSONArray pendingTransaction = (JSONArray) myResponse.get("pendingTransfersPayments");

        HashMap<Integer,String> dateMap = new HashMap<Integer,String>();

        for(int i = 0; i < pendingTransaction.length(); i++){
            dateMap.put(i,pendingTransaction.getJSONObject(i).getString("date"));
        }
        return dateMap.size();
    }

    public Object getPendingTransactionDate (Browser browser, int j) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONObject myResponse = response.getJSONObject("spendableBalDetails");
        JSONArray pendingTransaction = (JSONArray) myResponse.get("pendingTransfersPayments");

        HashMap<Integer,String> dateMap = new HashMap<Integer,String>();

        for(int i = 0; i < pendingTransaction.length(); i++){
            dateMap.put(i,pendingTransaction.getJSONObject(i).getString("date"));
        }
        return dateMap.get(j);
    }

    public Object getPendingTransactionDescription (Browser browser, int j) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONObject myResponse = response.getJSONObject("spendableBalDetails");
        JSONArray pendingTransaction = (JSONArray) myResponse.get("pendingTransfersPayments");

        HashMap<Integer,String> descriptionMap = new HashMap<Integer,String>();

        for(int i = 0; i < pendingTransaction.length(); i++){
            descriptionMap.put(i,pendingTransaction.getJSONObject(i).getString("description"));
        }
        return descriptionMap.get(j);
    }

    public Object getPendingTransactionAmount (Browser browser, int j) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONObject myResponse = response.getJSONObject("spendableBalDetails");
        JSONArray pendingTransaction = (JSONArray) myResponse.get("pendingTransfersPayments");

        HashMap<Integer,String> amountMap = new HashMap<Integer,String>();

        for(int i = 0; i < pendingTransaction.length(); i++){
            amountMap.put(i, String.valueOf(pendingTransaction.getJSONObject(i).getDouble("amount")));
        }
        return amountMap.get(j);
    }


}