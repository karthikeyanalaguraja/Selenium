package core.APIs.BrowserAPIs;

import core.utilities.baseUtilities.Browser;
import core.utilities.baseUtilities.BrowserAPIManager;
import io.restassured.http.Method;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

public class TransferListBrowserAPI extends BrowserAPIManager {



    public TransferListBrowserAPI(Browser browser, String parameters) {
        super(browser, "/fdobi/transfersList?"+parameters);
    }

    public JSONObject getTransferFromTransferId(Browser browser, String transferId) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("transfers");
        for (int i = 0; i < pendingTransaction.length(); i++) {
            JSONObject transaction = pendingTransaction.getJSONObject(i);
            String currentTransferId = String.valueOf(transaction.get("transferId"));
            if(transferId.equals(currentTransferId)){
                return transaction;
            }
        }
        return null;
    }
}
