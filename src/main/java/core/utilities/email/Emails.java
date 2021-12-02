package core.utilities.email;

class Emails {

    TemporaryPasscodeEmail temporaryPasscodeEmail;
    SecurityCodeEmail securityCodeEmail;

    public Emails() {
        temporaryPasscodeEmail = new TemporaryPasscodeEmail();
        securityCodeEmail = new SecurityCodeEmail();
    }
}
