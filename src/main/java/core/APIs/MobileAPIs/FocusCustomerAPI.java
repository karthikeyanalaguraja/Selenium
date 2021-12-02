package core.APIs.MobileAPIs;

import core.utilities.objects.User;
import io.restassured.http.Method;

public class FocusCustomerAPI extends CommunityAPI {

    public FocusCustomerAPI() {

        super("/customer/focusCustomer");
    }

    public String getResponse(User user) {
        return super.getResponse(user, Method.GET, user.ownedAccount.accountNumber);
    }


}
