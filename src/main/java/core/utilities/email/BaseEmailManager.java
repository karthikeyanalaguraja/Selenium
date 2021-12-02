package core.utilities.email;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.get;

/**
 * Created by nick.fields on 5/5/2017.
 */
public abstract class BaseEmailManager {

    private Integer timeout;
    private Integer pollingIntervalInMs;

    private final String apiKey = "5ce6b30b777c4efc9e1ef484fef876e5";
    private final String urlBase = "https://api.mailinator.com/api";
    private final String inboxUrl = urlBase + "/inbox?private_domain=true&token=" + apiKey;
    private final String emailUrlFormat = urlBase + "/email?private_domain=true&id=%s&token=" + apiKey;

    public BaseEmailManager() {
        this.setTimeout(1000);
        this.setPollingIntervalInMs(2000);
    }

    /*
    public String getDepositInvitationUrl(String recipientEmail, Runnable triggeringMethod) {
        Message message = waitForNewEmail(Emails.DepositPortalInvitation, recipientEmail, triggeringMethod);

        String html = getEmailHtmlContent(message);

        return Jsoup.parse(html).select("a[href*=Portal_SiteLogin]").attr("href");
    }
    */

    protected String getEmailHtmlContent(Message message){
        Response response = get(String.format(emailUrlFormat, message.id));
        ResponseBody responseBody = response.getBody();
        if (StringUtils.isNotEmpty(responseBody.path("data.parts[1].body"))){
            return responseBody.path("data.parts[1].body");
        } else {
            return responseBody.path("data.parts[0].body");
        }
    }

    protected Email waitForNewEmail(Email email, String recipientEmail, Runnable triggeringMethod) {

        List<Message> newEmails = new ArrayList<>();
        Boolean isFound = false;
        LocalDateTime stopTime = LocalDateTime.now().plusSeconds(getTimeout());

        //get baseline of expected emails
        int existingEmails = getAllEmails().getMessagesByRecipient(email, recipientEmail).size();

        //run method(s) that will trigger the email
        Long start = System.currentTimeMillis();
        triggeringMethod.run();

        //poll mail server until email is found or timeout reached
        while (!isFound && stopTime.isAfter(LocalDateTime.now())) {
            Messages msgs = getAllEmails();

            if (msgs.messages.size() > 0) {
                newEmails = msgs.getMessagesByRecipient(email, recipientEmail);
            }

            if (newEmails.size() > existingEmails) {
                isFound = true;
                newEmails.sort(Comparator.comparing(m -> m.seconds_ago));
            }

            try {
                Thread.sleep(getPollingIntervalInMs());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //verify an email was received
        Assert.assertTrue(
                isFound,
                String.format("'%s' email expected to be sent to %s but not received.", email.getSubject(), recipientEmail));

        System.out.println(String.format("Time to receive %s email: %sms", email.getSubject(), (System.currentTimeMillis() - start)));

        //get newest email
        Message message = newEmails.get(0);

        //extract html from message
        String content = getEmailHtmlContent(message);

        //add html to email object
        email.setHtmlContent(content);

        //return modified email
        return email;
    }

    private Messages getAllEmails() {
        Response response = get(inboxUrl);

        return new Gson().fromJson(response.body().asString(), Messages.class);
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getPollingIntervalInMs() {
        return pollingIntervalInMs;
    }

    public void setPollingIntervalInMs(Integer pollingIntervalInMs) {
        this.pollingIntervalInMs = pollingIntervalInMs;
    }
}
