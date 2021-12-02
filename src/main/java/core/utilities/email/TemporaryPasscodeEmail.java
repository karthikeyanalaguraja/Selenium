package core.utilities.email;


/**
 * The email that is sent to a user when initially registering for the system.
 */
class TemporaryPasscodeEmail extends Email {

    public String getSubject() { return "Digital Banking Temporary Passcode"; }

    public String parseCode(){ return getHtmlContent().split("Your temporary passcode is:")[1].trim().split(" ")[0]; }


}
