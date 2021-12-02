package core.utilities.enums;

public enum CustomerTypes {
    Commerical("Commercial"),
    Retail("Retail"),
    SubUser("Sub-User"),
    SubUserAdmin("Sub-User Administrator"),
//    Agent("Agent"),
    Unknown("Unknown");

    public String field;

    CustomerTypes (String field) {
        this.field = field;
    }

}
