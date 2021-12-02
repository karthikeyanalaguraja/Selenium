package core.APIs.MobileAPIs;

import core.utilities.objects.User;
import io.restassured.http.Method;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

public class AccountAPI extends CommunityAPI {
    
    public AccountAPI() {
        super("/accounts");
    }
    
    public String getResponse (User user) {
        return super.getResponse(user, Method.GET, user.ownedAccount.accountNumber);
    }
    
    public Double getAvailableBalance(User user) {
        JSONObject response = new JSONObject(getResponse(user));
        return response.getJSONObject("accInfo").getDouble("balAvailable");
    }
    
    public Double getCurrentBalance(User user) {
        JSONObject response = new JSONObject(getResponse(user));
        return response.getJSONObject("accInfo").getDouble("balCurrent");
    }
    
    public Double getAdjustedBalance(User user) {
        JSONObject response = new JSONObject(getResponse(user));
        return response.getJSONObject("accInfo").getDouble("balAdjustment");
    }
    
    public Double getAccountID(User user){
        JSONObject response = new JSONObject(getResponse(user));
        return response.getJSONObject("accInfo").getDouble("accId");
    }
    
    public void getPrimaryDisplayAccount(User user){
        JSONObject response = new JSONObject(getResponse(user));
        JSONObject myResponse = response.getJSONObject("spendableBalDetails");
        JSONArray pendingTransaction = (JSONArray) myResponse.get("pendingTransfersPayments");
    }
}

