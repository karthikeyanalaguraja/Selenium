package core.APIs.MobileAPIs;

import core.utilities.baseUtilities.APIManager;
import core.utilities.objects.User;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.testng.Assert;

import java.util.ArrayList;

public abstract class CommunityAPI extends APIManager {

    protected String token;
    public String response;
    
    private final String authEndpoint = "https://cat2-lb-fxws.apiture-comm-preprod.com/fdobi";
    private final String authUrl = "/auth-token";
    private ArrayList mfaChallengeId;

    private final String mfaUrl = "/mfa";

    private final String authAppId = "MFNP";
    private final String value = System.getProperty("Denvironment");
    
    public CommunityAPI(String url) {
        super("https://cat2-lb-fxws.apiture-comm-preprod.com/fdobi", url, "src/main/java/core/APIs/certs/fxws-dev-2019-2020.p12", "letmein123");
    }

    /**
     * Call to auth-token then use the token that it returns to access the individial APIs.
     */
    protected void login(User user) {
        JSONObject response = null;
        if (token == null) {
            try {
                // Get Auth Token
                RequestSpecBuilder authTokenRequestBuilder = new RequestSpecBuilder();
                authTokenRequestBuilder.setBaseUri(authEndpoint);
                authTokenRequestBuilder.setBasePath(authUrl);
                authTokenRequestBuilder.addParam("appId", authAppId);
                authTokenRequestBuilder.addParam("institutionId", user.institutionID);
                authTokenRequestBuilder.addParam("username", user.accessId);
                authTokenRequestBuilder.addParam("password", user.password);
                authTokenRequestBuilder.addParam("customerId", user.customerId);
                authTokenRequestBuilder.addParam("deviceScore",705);
                RequestSpecification rspec = authTokenRequestBuilder.build();

                response = new JSONObject(RestAssured.given().spec(rspec).post().getBody().asString());
                this.token = response.getString("authToken");

                // get MFA questions
                // TODO: figure out a way to add a client cert
                RequestSpecBuilder getMfaQuestionsBuilder = new RequestSpecBuilder();
                getMfaQuestionsBuilder.setBaseUri(authEndpoint);
                getMfaQuestionsBuilder.addParam("Authorization",this.token);
                rspec = getMfaQuestionsBuilder.build();

                RestAssured.config = config;
                // RestAssured.given() seems to be overriding RestAssured.config in RestAssured.createTestSpecification()
                // RestAssuredConfig restAssuredConfig = config();

                response = new JSONObject(RestAssured.given().spec(rspec).get().getBody().asString());
                JSONArray verifyChallenge = response.getJSONObject("mfaOptions").getJSONArray("verifyChallenge");
                for(int i=0; i<verifyChallenge.length(); i++){
                    mfaChallengeId.add(verifyChallenge.getJSONObject(i).getString("id"));
                }

                // Answer MFA questions
            } catch (Exception ex) {
                ex.printStackTrace();
                Assert.fail("Error getting auth_token:\n"+response.toString());
            }
        }
        else {
            //Do nothing
        }
    }

    public String getResponse(User user, Method method, String value) {
        login(user);
        try {
            if (response == null) {
                response = super.getResponse(method, value);
            } else {

            }
            if (new JSONObject(response).has("error")) {
                throw new Error("Error in request. Response: \n" + response);
            } else {
                return response;
            }
        } catch (Exception ex) {
            System.out.println("Could not retrieve response.");
            ex.printStackTrace();
            return "";
        }
    }
    
    public String getResponsewithNoValue(User user, Method method) {
        login(user);
        try {
            if (response == null) {
                response = super.getResponse(method, this.token);
            } else {
            }
            if (new JSONObject(response).has("error")) {
                throw new Error("Error in request. Response: \n" + response);
            } else {
                return response;
            }
        } catch (Exception ex) {
            System.out.println("Could not retrieve response.");
            ex.printStackTrace();
            return "";
        }
    }

}
