package core.APIs.BrowserAPIs;

import core.utilities.baseUtilities.Browser;
import core.utilities.baseUtilities.BrowserAPIManager;
import io.qameta.allure.Step;
import io.restassured.http.Method;


public class CategoriesBrowserAPI extends BrowserAPIManager {

    public CategoriesBrowserAPI( Browser browser, String url ) {
        super(browser, url);
    }
    
    @Step("{method}:")
    public String getResponse ( Browser browser, Method method, String value) {

        return super.getResponse(browser, method, value);
    }




}