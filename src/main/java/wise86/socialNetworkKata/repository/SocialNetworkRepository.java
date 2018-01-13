package wise86.socialNetworkKata.repository;

import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SocialNetworkRepository {

    private final MessageRepository messages;
    private final FollowerRepository followerRelation;

    public SocialNetworkRepository(MessageRepository messages, FollowerRepository followerRelation) {
        this.messages = messages;
        this.followerRelation = followerRelation;
    }


    public void publish(Message msg) {
        messages.addMessage(msg);
    }

    public List<Message> getMessagesFromUser(User user) {
        return messages.getUserMessages(user);
    }

    public void addFollowerRelation(User user, User followed) {
        followerRelation.addFollowerRelation(user, followed);
    }

    private List<Message> getAllMessagesFromUsers(Collection<User> users) {
        ArrayList<Message> messagesCollection = new ArrayList<>();
        for (User user : users) {
            List<Message> userMessages = messages.getUserMessages(user);
            if (userMessages != null)
                messagesCollection.addAll(userMessages);
        }
        return messagesCollection;
    }


    public List<Message> getWallMessageForUser(User user) {
        List<User> followingUserList = followerRelation.getUsersFollowedBy(user);
        List<Message> wallMessages = new ArrayList<>(messages.getUserMessages(user));
        if (followingUserList != null) {
            wallMessages.addAll(getAllMessagesFromUsers(followingUserList));
        }
        return wallMessages;
    }
}
