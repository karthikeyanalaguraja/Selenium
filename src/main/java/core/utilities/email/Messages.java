package core.utilities.email;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Messages {

    public Messages() {
        messages = new ArrayList<Message>();
    }

    public List<Message> messages;

    public List<Message> getMessagesByRecipient(Email email, String to) {
        return messages.stream()
                .filter(m ->email.getIsExactMatch() ? m.subject.equalsIgnoreCase(email.getSubject()) : m.subject.contains(email.getSubject()))
                .filter(m -> m.to.equalsIgnoreCase(to.split("@")[0]))
                .collect(Collectors.toList());
    }
}
