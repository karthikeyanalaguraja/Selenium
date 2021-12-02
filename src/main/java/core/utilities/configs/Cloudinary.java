package core.utilities.configs;

public class Cloudinary {

    public Cloudinary(String cloudName, String key, String secret){
        this.cloudName = cloudName;
        this.key = key;
        this.secret = secret;
    }

    private String cloudName;
    private String key;
    private String secret;

    private final String envVariableFormat = "cloudinary://%s:%s@%s/";

    public String getCloudName() {
        return cloudName;
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

    public String getEnvVariable() {
        return String.format(envVariableFormat, getKey(), getSecret(), getCloudName());
    }
}
