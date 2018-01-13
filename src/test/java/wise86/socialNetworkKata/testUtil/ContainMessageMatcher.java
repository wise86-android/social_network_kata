package wise86.socialNetworkKata.testUtil;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import wise86.socialNetworkKata.data.Message;

import java.util.Collection;

public class ContainMessageMatcher extends TypeSafeMatcher<Collection<Message>> {

    private final String userName;
    private final String messageContent;

    public ContainMessageMatcher(String userName, String messageContent) {
        this.userName = userName;
        this.messageContent = messageContent;
    }

    @Override
    protected boolean matchesSafely(Collection<Message> items) {
        for (Message msg : items) {
            if (msg.getAuthor().getName().equals(userName) &&
                    msg.getContent().equals(messageContent))
                return true;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("No message from " + userName + " with content: " + messageContent);
    }

    public static Matcher<Collection<Message>> containsMessageWith(String userName, String content) {
        return new ContainMessageMatcher(userName, content);
    }
}
