package wise86.socialNetworkKata.repository;

import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class exposing the social network data
 */
public class SocialNetworkRepository {

    private final MessageRepository messages;
    private final FollowingRepository followingRelation;

    /**
     * @param messages            class used to store the user messages
     * @param followingRepository class use to store the following relationship
     */
    public SocialNetworkRepository(MessageRepository messages, FollowingRepository followingRepository) {
        this.messages = messages;
        this.followingRelation = followingRepository;
    }

    /**
     * insert a message in the social network
     *
     * @param msg message to store in the social network
     */
    public void publish(Message msg) {
        messages.addMessage(msg);
    }

    /**
     * get all the messages published by a user
     *
     * @param user user to query
     * @return messages published by the user
     */
    public List<Message> getMessagesFromUser(User user) {
        return messages.getUserMessages(user);
    }

    /**
     * store in the social network that the user $user will follow the user $followed
     *
     * @param user      user that follow
     * @param followed followed user
     */
    public void addFollowerRelation(User user, User followed) {
        followingRelation.addFollowingRelation(user, followed);
    }

    /**
     * get all the message published by the users
     *
     * @param users list of user
     * @return all the messages posted by the users in the users list.
     */
    private List<Message> getAllMessagesFromUsers(Collection<User> users) {
        ArrayList<Message> messagesCollection = new ArrayList<>();
        for (User user : users) {
            List<Message> userMessages = messages.getUserMessages(user);
            if (userMessages != null)
                messagesCollection.addAll(userMessages);
        }
        return messagesCollection;
    }


    /**
     * get the user wall messages
     *
     * @param user user to query
     * @return user's massages and messages form all the users that it follow
     */
    public List<Message> getWallMessageForUser(User user) {
        List<User> followingUserList = followingRelation.getUsersFollowedBy(user);
        List<Message> wallMessages = new ArrayList<>(messages.getUserMessages(user));
        if (followingUserList != null) {
            wallMessages.addAll(getAllMessagesFromUsers(followingUserList));
        }
        return wallMessages;
    }
}
