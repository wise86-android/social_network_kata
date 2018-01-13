package wise86.socialNetworkKata.repository.onMemory;

import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.repository.MessageRepository;

import java.util.*;

/**
 * Class realizing an in memory storage of the social network's messages
 */
public class MemoryMessageRepository implements MessageRepository {

    private Map<User, ArrayList<Message>> userMessages = new HashMap<>();

    @Override
    public void addMessage(Message msg) {
        User author = msg.getAuthor();
        if (!userMessages.containsKey(author))
            userMessages.put(author, new ArrayList<>());
        userMessages.get(author).add(msg);
    }

    @Override
    public List<Message> getUserMessages(User user) {
        List<Message> messages = userMessages.get(user);
        if (messages != null)
            return Collections.unmodifiableList(messages);
        else
            return Collections.emptyList();
    }
}
