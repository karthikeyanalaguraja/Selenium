package core.APIs.BrowserAPIs;

import core.APIs.MobileAPIs.CommunityAPI;
import core.utilities.objects.User;
import io.qameta.allure.Step;
import io.restassured.http.Method;

public class AchBatchesAPI extends CommunityAPI {

    public String entity;
    public String endDate;

    public AchBatchesAPI(String entity, String date) {

        super("/ach-batches?dateEnd="+date+"&dateStart=2018-01-01&focusCustId="+entity+"&historical=true");
        this.entity = entity;
        this.endDate = endDate;

    }
    
    @Step("{method}:")
    public String getResponse(User user) {

        return super.getResponse(user, Method.GET, url);
    }


}
