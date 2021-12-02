package core.utilities.enums;

public enum UserProfileInfo {
    Password("password"),
    Email("email"),
    Ssn("ssn"),
    StreetAddress1("street_address_1"),
    StreetAddress2("street_address_2"),
    City("city"),
    State("state"),
    Zip("zip"),
    Birthday("birthday"),
    PrimaryPhone("primary_phone"),
    SecondaryPhone("secondary_phone"),
    FirstSecurityAnswer("first_security_answer"),
    SecondSecurityAnswer("second_security_answer"),
    ThirdSecurityAnswer("third_security_answer"),
    OwnedAccountNumber("owned_account"),
    ReceiveElectronicStatements("electronic_statements");

    String fieldName;

    UserProfileInfo(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }
}
