package wise86.socialNetworkKata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.testUtil.DateProviderMockUtil;
import wise86.socialNetworkKata.util.DateProvider;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static wise86.socialNetworkKata.testUtil.ContainMessageMatcher.containsMessageWith;
import static wise86.socialNetworkKata.testUtil.MessageMockUtil.mockMessage;

@RunWith(MockitoJUnitRunner.class)
public class SocialNetworkPresenterTest {

    @Mock
    private SocialNetworkContract.View view;

    private DateProvider dateProvider = DateProviderMockUtil.eachInvocationIsIncrementedByMs(1000);

    @Captor
    private ArgumentCaptor<List<Message>> messagesListCaptor;

    private SocialNetworkPresenter socialNetwork;

    @Before
    public void createSocialNetwork() {
        socialNetwork = new SocialNetworkPresenter(view, dateProvider);
    }

    @Test
    public void aPublishingMessageIsDisplayInTheShowUser() {
        String user = "userA";
        String message = "message";

        socialNetwork.publishMessage(user, message);
        socialNetwork.showUserMessages(user);

        verify(view).displayUserMessages(messagesListCaptor.capture());

        assertThat(messagesListCaptor.getValue(),containsMessageWith(user,message));
    }

    @Test
    public void selectTheCorrectMessageToDisplayInTheWall() {
        String userA = "userA";
        String messageA_1 = "MessageA_1";
        String messageA_2 = "MessageA_2";
        String userB = "userB";
        String messageB_1 = "MessageB_1";
        String messageB_2 = "MessageB_2";
        String userC = "userC";
        String messageC_1 = "MessageC_1";

        socialNetwork.publishMessage(userA, messageA_1);
        socialNetwork.publishMessage(userB, messageB_1);
        socialNetwork.publishMessage(userA, messageA_2);
        socialNetwork.publishMessage(userB, messageB_2);
        socialNetwork.publishMessage(userC, messageC_1);

        socialNetwork.startFollow(userA, userB);

        socialNetwork.showWallForUser(userA);
        verify(view).displayUserWallMessages(messagesListCaptor.capture());

        List<Message> userAWall = messagesListCaptor.getValue();

        assertThat(userAWall, hasSize(4));
        assertThat(userAWall, containsMessageWith(userA, messageA_1));
        assertThat(userAWall, containsMessageWith(userA, messageA_2));
        assertThat(userAWall, containsMessageWith(userB, messageB_1));
        assertThat(userAWall, containsMessageWith(userB, messageB_2));

        assertTrue(areSortedByDecreasingDate(userAWall));
    }

    private boolean areSortedByDecreasingDate(List<Message> messages) {
        List<Long> times = messages.stream()
                .map(message -> message.getPublicisingTime().getTime()) //extract the unix time
                .collect(Collectors.toList());
        long prev = Long.MAX_VALUE;
        for (long value : times) {
            if (prev < value)
                return false;
            prev = value;
        }
        return true;

    }


    @Test
    public void returnTrueIfTheMessageAreOrderedByDescendingDate() {
        List<Message> wrongList = Arrays.asList(
                mockMessage(new Date(2)),
                mockMessage(new Date(1)),
                mockMessage(new Date(3)),
                mockMessage(new Date(4)));

        assertFalse(areSortedByDecreasingDate(wrongList));

        List<Message> goodList = Arrays.asList(
                mockMessage(new Date(4)),
                mockMessage(new Date(3)),
                mockMessage(new Date(2)),
                mockMessage(new Date(1)));
        assertTrue(areSortedByDecreasingDate(goodList));
    }

}