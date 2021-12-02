package core.utilities.baseUtilities;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.apache.http.conn.ssl.SSLSocketFactory;

import javax.net.ssl.KeyManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;


public abstract class APIManager {
    
    public String endpoint;
    public String url;
    public RestAssuredConfig config = null;
    
    //This class should handle everything pertaining to requests, including getting the response. The class that inherits
    // should handle parsing the response and returning meaningful information. This class gets passed the url to the endpoint,
    // the url after the endpoint (telling which info to grab from the endpoint), and the method (GET, POST, PUT, DELETE).
    
    public APIManager(String endpoint, String url) {
        this.endpoint = endpoint;
        this.url = url;
        RestAssured.baseURI = endpoint;
        RestAssured.basePath = url;
        
    }
    
    public APIManager(String endpoint, String url, String pathToCert, String password) {
        this(endpoint, url);
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            clientStore.load(new FileInputStream(pathToCert), password.toCharArray());
            
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(clientStore, password.toCharArray());
            
            SSLSocketFactory lSchemeSocketFactory = new SSLSocketFactory(clientStore, password);

            // Try RestAssured.certificate()
            config = RestAssured.config().sslConfig(SSLConfig.sslConfig().with().sslSocketFactory(lSchemeSocketFactory).and().allowAllHostnames());
            RestAssured.config = config;
            
        } catch (Exception ex) {
            System.out.println("Keystore failure ********");
            ex.printStackTrace();
        }
    }
    
    public String getResponse (Method method, String value) {
        
        return RestAssured.given().contentType(ContentType.JSON).request(method, value).body().asString();
    }
    
    public String getResponse (Method method, String value, String token) {
        RequestSpecification httpRequest = RestAssured.given();
        return httpRequest.header("Authorization", token).request(method,value).body().asString();
    }
    

  }
