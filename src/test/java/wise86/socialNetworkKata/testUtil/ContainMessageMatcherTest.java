package wise86.socialNetworkKata.testUtil;

import org.junit.Test;
import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static wise86.socialNetworkKata.testUtil.ContainMessageMatcher.containsMessageWith;

public class ContainMessageMatcherTest {

    private Message mockMessage(String user, String content) {
        Message msg = mock(Message.class);
        when(msg.getContent()).thenReturn(content);
        when(msg.getAuthor()).thenReturn(new User(user));
        return msg;
    }

    @Test
    public void theMatcherFoundCorrectlyTheMessage() {
        List<Message> messages = Arrays.asList(
                mockMessage("userA", "msgA"),
                mockMessage("userC", "msgC"),
                mockMessage("userB", "msgB")
        );

        assertThat(messages, containsMessageWith("userC", "msgC"));
    }

    @Test
    public void theCorrectUserIsNotEnoughToHaveAMatch() {
        List<Message> messages = Collections.singletonList(
                mockMessage("userC", "msgC")
        );

        assertThat(messages, not(containsMessageWith("userC", "msgA")));
    }

    @Test
    public void theCorrectMessageIsNotEnoughToHaveAMatch() {
        List<Message> messages = Collections.singletonList(
                mockMessage("userA", "msgA")
        );

        assertThat(messages, not(containsMessageWith("userC", "msgA")));
    }
}