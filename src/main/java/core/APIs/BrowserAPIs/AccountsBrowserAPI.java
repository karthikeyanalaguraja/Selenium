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
import java.util.Random;


public class AccountsBrowserAPI extends BrowserAPIManager {
    
    public HashMap<Object, Object> accountMap = new HashMap<>();
    public HashMap<Integer, Object> accountMapBalance = new HashMap<>();
    public HashMap<Object, Object> accountentityMap = new HashMap<>();
    public HashMap<Object,Object> accountMapReverse = new HashMap<>();
    public HashMap<Object, Object> accDescriptionMap = new HashMap<>();
    
    public List<Object> mySpendingMap = new ArrayList<>();
    public List<Object> accId = new ArrayList<>();
    public List<Object> accIdChecking = new ArrayList<>();
    public List<String> accIdFromName = new ArrayList<>();
    public List<Object> accIdSaving = new ArrayList<>();
    public List<Object> accIdOwe = new ArrayList<>();
    public JSONObject response;

    public AccountsBrowserAPI(Browser browser, String url ) {
         super(browser, url);
    }
    
    public Object getAccountIdsChecking (Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if (!isExternalStatus) {
                String accDefNum = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDefNum"));
                if(accDefNum.equals("1")){
                    accIdChecking.add(response.getJSONArray("accounts").getJSONObject(i).get("accId"));
                }
            }
        }
        return accIdChecking;
    }
    
    public Object getAccountIdFromAccountName (Browser browser, String accNumber) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            String accNo = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDisplayNum"));
            if(accNo.equals(accNumber)) {
                accIdFromName.add(response.getJSONArray("accounts").getJSONObject(i).get("accId").toString());
            }
        }
        return accIdFromName;
    }
    
    public Object getAccountIdsSaving (Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if (!isExternalStatus) {
                String accDefNum = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDefNum"));
                if(accDefNum.equals("2") || accDefNum.equals("3") || accDefNum.equals("4") ){
                    accIdSaving.add(response.getJSONArray("accounts").getJSONObject(i).get("accId"));
                }
            }
        }
        return accIdSaving;
    }
    
    public Object getAccountIdsOwe (Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if (!isExternalStatus) {
                String accDefNum = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDefNum"));
                if(accDefNum.equals("5") || accDefNum.equals("6") ){
                    accIdOwe.add(response.getJSONArray("accounts").getJSONObject(i).get("accId"));
                }
            }
        }
        return accIdOwe;
    }
    
    public int getAccountIdsSize (Browser browser, String accountType) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if (!isExternalStatus) {
                String accType = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDefNum"));
                if(accType.equals(accountType)){
                    accId.add(response.getJSONArray("accounts").getJSONObject(i).get("accId"));
                }
            }
        }
        return accId.size();
    }
    
    public int getSavingAccountIdsSize (Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if (!isExternalStatus) {
                String accType = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDefNum"));
                if(accType.equals("4") || accType.equals("3") || accType.equals("2")){
                    accId.add(response.getJSONArray("accounts").getJSONObject(i).get("accId"));
                }
            }
        }
        return accId.size();
    }
    
    public int getLoanAccountIdsSize (Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if (!isExternalStatus) {
                String accType = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDefNum"));
                if(accType.equals("5") || accType.equals("6")){
                    accId.add(response.getJSONArray("accounts").getJSONObject(i).get("accId"));
                }
            }
        }
        return accId.size();
    }
    
    
    
    
    public String getSpendableBalanceAccount ( Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        String accNum=null;
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean sbstatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("primaryDisplayAccount");
            if (sbstatus) {
                accNum = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accId"));
            }
        }
        return accNum;
    }
    
    public Object getAccountIds ( int i){
        return accId.get(i);
    }
    
    public Object getAccountdescription (int i ){
        return accountentityMap.get(i);
    }
    
    public String getSpendableBalanceAccountNum ( Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        String accNum=null;
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean sbstatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("primaryDisplayAccount");
            if (sbstatus) {
                accNum = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDescription"));
            }
        }
        return accNum;
    }
    
    public HashMap<Object, Object> getAccountName ( Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if(!isExternalStatus){
                accountMap.put(response.getJSONArray("accounts").getJSONObject(i).get("accDescription"),i);
            }
        }
        return accountMap;
    }
    
    public HashMap<Object, Object> getAccountNameWithEntity ( Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if(!isExternalStatus){
                accountentityMap.put(response.getJSONArray("accounts").getJSONObject(i).get("accId"), response.getJSONArray("accounts").getJSONObject(i).get("accDescription"));
            }
        }
        return accountentityMap;
    }
    
    public HashMap<Integer, Object> getAccountNameForBalance2 ( Browser browser, String accountType){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if(!isExternalStatus){
                String accType = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDefNum"));
                if(accType.equals(accountType)){
                    accountMapBalance.put(i, response.getJSONArray("accounts").getJSONObject(i).get("accDescription"));
                }
                
            }
        }
        return accountMapBalance;
    }
    
    public HashMap<Integer, Object> getAccountNameForSavingBalance ( Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if(!isExternalStatus){
                String accType = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDefNum"));
                if(accType.equals("4") || accType.equals("3") || accType.equals("2")){
                    accountMapBalance.put(i, response.getJSONArray("accounts").getJSONObject(i).get("accDescription"));
                }
                
            }
        }
        return accountMapBalance;
    }
    
    public HashMap<Integer, Object> getAccountNameForLoanBalance ( Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if(!isExternalStatus){
                String accType = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("accDefNum"));
                if(accType.equals("5") || accType.equals("6")){
                    accountMapBalance.put(i, response.getJSONArray("accounts").getJSONObject(i).get("accDescription"));
                }
                
            }
        }
        return accountMapBalance;
    }
    
    public HashMap<Object, Object> getAccountNameId ( Browser browser){
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if(!isExternalStatus){
                accountMapReverse.put(i,response.getJSONArray("accounts").getJSONObject(i).get("accDescription"));
            }
        }
        return accountMapReverse;
    }
    
    public String searchAccountMap(String name){
        if(accountMap.containsKey(name)) {
            return accountMap.get(name).toString();
        }
        return accountMap.get(name).toString();
    }
    
    public Object searchAccountMap( int i){
        return  accountMapReverse.get(i);
    }
    
    public Object getCheckingAccountNumber ( Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            String accDef = response.getJSONArray("accounts").getJSONObject(i).getString("accDef");
            if(accDef.equals("Checking")){
                boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
                if(!isExternalStatus){
                    accDescriptionMap.put(response.getJSONArray("accounts").getJSONObject(i).get("accDisplayNum"),response.getJSONArray("accounts").getJSONObject(i).get("accId"));
                }
            }
        }
        return accDescriptionMap;
    }
    
    public Object getMySpendingAccountNumber ( Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            String accDef = response.getJSONArray("accounts").getJSONObject(i).getString("accDef");
            if (accDef.equals("Checking")) {
                boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
                if (!isExternalStatus) {
                    mySpendingMap.add(response.getJSONArray("accounts").getJSONObject(i).get("accDisplayNum"));
                }
            }
        }
        return mySpendingMap;
    }
    
    public String getAnotherSBAccount (Browser browser) {
        int size = accDescriptionMap.size();
        String tempSBAcc = getSpendableBalanceAccountNum(browser);
        
        Random generator = new Random();
        Object [] values = accDescriptionMap.keySet().toArray();
        Object randomValue = values[generator.nextInt(values.length)];
        
        while (tempSBAcc.equals(randomValue)){
            randomValue = values[generator.nextInt(values.length)];
        }
        return String.valueOf(randomValue);
    }
    
    public String getFavoriteAccounts (Browser browser) {
        JSONObject response = new JSONObject(getResponse(browser, Method.GET, ""));
        JSONArray pendingTransaction = (JSONArray) response.getJSONArray("accounts");
        
        HashMap<Object, Object> favoriteMap = new HashMap<>();
        
        for (int i = 0; i < pendingTransaction.length(); i++) {
            boolean isExternalStatus = response.getJSONArray("accounts").getJSONObject(i).getBoolean("isExternal");
            if(!isExternalStatus){
                String favorite = String.valueOf(response.getJSONArray("accounts").getJSONObject(i).get("favorite"));
                if(favorite != null && !favorite.isEmpty()){
                    favoriteMap.put(response.getJSONArray("accounts").getJSONObject(i).get("favorite"), response.getJSONArray("accounts").getJSONObject(i).get("accDescription"));
                }
            }
            
        }
        return String.valueOf(favoriteMap);
    }
    
    
    
}
