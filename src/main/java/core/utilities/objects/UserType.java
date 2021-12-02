package core.utilities.objects;

public class UserType {

    String scope;
    String type;
    String parameter;


    /**
     * UserType required from the DataManager.
     * @param scope The test location (environment, org, institution, etc.).
     * @param type The type of user as identified in the database.
     */
    public UserType(String scope, String type){
        this.scope = scope;
        this.type = type;
    }

    public String getScope(){
        return scope;
    }

    public String getType(){
        return type;
    }

    public String getParameter() { return parameter; }
}
