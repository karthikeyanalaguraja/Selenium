package core.utilities.baseUtilities;

import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;

public class Cloudinary {

    //Apiture Cloudinary settings
    private static String cloudName = "apiture-qa";//
    private static String key = "678516867732458";//
    private static String secret = "cJ70zov9ftidGuMiSrWCy_l3Rqg";//
    private static String envVariable = "cloudinary://678516867732458:cJ70zov9ftidGuMiSrWCy_l3Rqg@apiture-qa/";//

    private static Map credMap = ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", key,
            "api_secret", secret
    );

    private Cloudinary() {

    }

    public static String uploadImage(String localFilePath) {
        String url = null;

        try {
            com.cloudinary.Cloudinary cloudinary = new com.cloudinary.Cloudinary(credMap);
            Map result = cloudinary.uploader().upload(localFilePath, null);
            url = result.get("url").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static ApiResponse getCloudinaryUsage() {
        ApiResponse response = null;
        try {
            com.cloudinary.Cloudinary cloudinary = new com.cloudinary.Cloudinary(credMap);
            response = cloudinary.api().usage(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
