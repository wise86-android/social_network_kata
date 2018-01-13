package wise86.socialNetworkKata.repository.onMemory;

import org.junit.Before;
import org.junit.Test;
import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.repository.MessageRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class MemoryMessageRepositoryTest {

    private static final User USER_A = new User("userA");
    private static final User USER_B = new User("userB");
    private static final Date FAKE_DATE = mock(Date.class);

    private MessageRepository repository;

    @Before
    public void initMemoryRepository() {
        repository = new MemoryMessageRepository();
    }

    @Test
    public void theMessageAddedCanBeRetrivedByTheGet() {

        Message msg = new Message(USER_A, "content", FAKE_DATE);
        repository.addMessage(msg);

        List<Message> messages = repository.getUserMessages(USER_A);
        assertThat(messages, is(not(empty())));

        assertEquals(msg, messages.get(0));
    }

    @Test
    public void onlyTheMessageOfASpecificUserAreReturned() {
        Message userAMessages[] = {
                new Message(USER_A, "1", FAKE_DATE),
                new Message(USER_A, "2", FAKE_DATE)
        };

        repository.addMessage(new Message(USER_B, "2", FAKE_DATE));

        Arrays.stream(userAMessages).forEach(msg -> repository.addMessage(msg));

        List<Message> messages = repository.getUserMessages(USER_A);
        assertThat(messages, contains(userAMessages));
    }

    @Test
    public void anEmptyListIsReturnIfTheUserHasNotMessages() {
        repository.addMessage(new Message(USER_A, "", FAKE_DATE));
        assertThat(repository.getUserMessages(USER_B), is(empty()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void theListReturnedIsReadOnly() {
        repository.addMessage(new Message(USER_A, "", FAKE_DATE));
        repository.getUserMessages(USER_A).add(new Message(USER_B, "", FAKE_DATE));
    }

}