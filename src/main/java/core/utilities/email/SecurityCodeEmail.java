package core.utilities.email;


/**
 * The email that is sent when a user chooses to get a code sent as their additional verification
 */
class SecurityCodeEmail extends Email {

    public String getSubject() {
        return "Security code sent";
    }

    public String parseSecurityCode() {
        return getHtmlContent().split("Security Code:")[1].trim().split(" ")[0];
    }
}