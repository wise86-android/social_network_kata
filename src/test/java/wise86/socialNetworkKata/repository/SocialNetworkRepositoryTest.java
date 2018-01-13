package wise86.socialNetworkKata.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SocialNetworkRepositoryTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private FollowingRepository followingRepository;

    private SocialNetworkRepository socialNetwork;

    @Before
    public void createSocialNetwork() {
        socialNetwork = new SocialNetworkRepository(messageRepository, followingRepository);
    }


    @Test
    public void aPublishedMessageIsStoreInTheRepository() {
        Message msg = mock(Message.class);
        socialNetwork.publish(msg);

        verify(messageRepository).addMessage(msg);
    }

    @Test
    public void theFollowerRelationIsStoredInTheRepository() {
        User user = mock(User.class);
        User followed = mock(User.class);

        socialNetwork.addFollowerRelation(user, followed);

        verify(followingRepository).addFollowingRelation(user, followed);
    }


    @Test
    public void theUserMessageAreRetrivedFromTheRepository() {
        User user = mock(User.class);
        socialNetwork.getMessagesFromUser(user);
        verify(messageRepository).getUserMessages(user);
    }

    @Test
    public void theUserWallContainsUserMessagesAndFollowedMessages() {

        User userA = mock(User.class);
        List<Message> userAMessages = Arrays.asList(
                mock(Message.class),
                mock(Message.class),
                mock(Message.class)
        );

        User userB = mock(User.class);
        List<Message> userBMessages = Arrays.asList(
                mock(Message.class),
                mock(Message.class)
        );

        when(messageRepository.getUserMessages(userA)).thenReturn(userAMessages);
        when(messageRepository.getUserMessages(userB)).thenReturn(userBMessages);
        when(followingRepository.getUsersFollowedBy(userA)).thenReturn(Collections.singletonList(userB));

        List<Message> wallA = socialNetwork.getWallMessageForUser(userA);
        assertThat(wallA, hasSize(userAMessages.size() + userBMessages.size()));
        assertTrue(wallA.containsAll(userAMessages));
        assertTrue(wallA.containsAll(userBMessages));
    }

}