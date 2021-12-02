package core.APIs.MobileAPIs;

import core.utilities.objects.User;
import io.restassured.http.Method;
import org.cloudinary.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class PageDataAPI extends CommunityAPI {

    public PageDataAPI() {
        super("/fxweb/page_data/account_detail");
    }

    /**
     * When passing in the user, ensure the account number is actually the account Id
     */
    public String getResponse(User user) {
        return super.getResponse(user, Method.GET, user.ownedAccount.accountNumber);
    }

    public ArrayList<String> getDownloadFormats(User user) {
        JSONObject response = new JSONObject(getResponse(user)).getJSONObject("downloadFormats");
        ArrayList<String> results = new ArrayList<>();
        Iterator<?> keys = response.keys();
        while (keys.hasNext()) {
            String key = (String)keys.next();
            results.add(response.getString(key));
        }
        return results;
    }
}
