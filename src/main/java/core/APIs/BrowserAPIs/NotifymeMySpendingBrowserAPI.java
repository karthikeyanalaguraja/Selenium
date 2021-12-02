package core.APIs.BrowserAPIs;

import core.utilities.baseUtilities.Browser;
import core.utilities.baseUtilities.BrowserAPIManager;
import core.utilities.objects.User;
import io.restassured.http.Method;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NotifymeMySpendingBrowserAPI extends BrowserAPIManager {

    public HashMap<String, String> notificationsStatus = new HashMap<>();
    public HashMap<String, String> mySpendingCatWatch = new HashMap<>();
    public HashMap<String, String> mySpendingEmail = new HashMap<>();
    public HashMap<String, String> mySpendingSpendLimit = new HashMap<>();
    public List<String> notificationType = new ArrayList<>();


    public NotifymeMySpendingBrowserAPI( Browser browser, String url ) {
        super(browser, url);
    }

    public HashMap<String, String> getNotifyMeStatus( Browser browser, User user){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray notifications = (JSONArray) response.getJSONArray("notifications");

        for(int i=0; i<notifications.length(); i++ ) {
            notificationsStatus.put(notifications.getJSONObject(i).get("type").toString(),notifications.getJSONObject(i).get("active").toString());
        }
       return notificationsStatus;
    }

    public void getmySpendingCatWatchStatus ( Browser browser, User user){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray notifications = (JSONArray) response.getJSONArray("notifications");

        for(int i=0; i<notifications.length(); i++ ) {
            String type = notifications.getJSONObject(i).get("type").toString();
            if(type.equals("mySpendingCatWatch")) {
                mySpendingCatWatch.put(notifications.getJSONObject(i).get("primaryEmail").toString(),notifications.getJSONObject(i).get("secondaryEmail").toString());
            }
        }
        System.out.println(mySpendingCatWatch);
    }

    public void getmySpendingEmailStatus( Browser browser, User user){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray notifications = (JSONArray) response.getJSONArray("notifications");

        for(int i=0; i<notifications.length(); i++ ) {
            String type = notifications.getJSONObject(i).get("type").toString();
            if(type.equals("mySpendingCatWatch")) {
                mySpendingEmail.put(notifications.getJSONObject(i).get("primaryEmail").toString(),notifications.getJSONObject(i).get("secondaryEmail").toString());
            }
        }
        System.out.println(mySpendingEmail);
    }

    public void getmySpendingSpendLimitStatus( Browser browser, User user){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray notifications = (JSONArray) response.getJSONArray("notifications");

        for(int i=0; i<notifications.length(); i++ ) {
            String type = notifications.getJSONObject(i).get("type").toString();
            if(type.equals("mySpendingCatWatch")) {
                mySpendingSpendLimit.put(notifications.getJSONObject(i).get("primaryEmail").toString(),notifications.getJSONObject(i).get("secondaryEmail").toString());
            }
        }
        System.out.println(mySpendingSpendLimit);
    }

    public List<String> getNotificationType( Browser browser, User user){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray notifications = (JSONArray) response.getJSONArray("notifications");

        for(int i=0; i<notifications.length(); i++ ) {
            notificationType.add(notifications.getJSONObject(i).get("type").toString());
        }
        return notificationType;
    }
}