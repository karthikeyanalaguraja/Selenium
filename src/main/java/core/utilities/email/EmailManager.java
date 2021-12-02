package core.utilities.email;

import io.qameta.allure.Step;

public class EmailManager extends BaseEmailManager {

    Emails emails;

    public EmailManager(){
        emails = new Emails();
    }
    public String getTemporaryPasscode(String recipient, Runnable action) {
        Email email = waitForNewEmail(emails.temporaryPasscodeEmail, recipient, action);
        return ((TemporaryPasscodeEmail) email).parseCode();
    }

    @Step("{method} for: {recipient}")
    public String getSecurityCode(String recipient, Runnable action) {
        Email email = waitForNewEmail(emails.securityCodeEmail, recipient, action);
        return ((SecurityCodeEmail) email).parseSecurityCode();
    }
}
