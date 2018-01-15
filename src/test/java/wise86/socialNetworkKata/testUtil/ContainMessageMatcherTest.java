package wise86.socialNetworkKata.testUtil;

import org.junit.Test;
import wise86.socialNetworkKata.data.Message;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static wise86.socialNetworkKata.testUtil.ContainMessageMatcher.containsMessageWith;
import static wise86.socialNetworkKata.testUtil.MessageMockUtil.mockMessage;

public class ContainMessageMatcherTest {

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