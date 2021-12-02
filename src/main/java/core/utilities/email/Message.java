package core.utilities.email;

class Message {

    public Message(String subject, String to) {
        this.subject = subject;
        this.to = to.split("@")[0];
    }

    public String subject;
    public String from;
    public String id;
    public String to;
    public int seconds_ago;
}
