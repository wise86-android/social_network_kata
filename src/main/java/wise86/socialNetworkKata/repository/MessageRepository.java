package wise86.socialNetworkKata.repository;


import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;

import java.util.List;

/**
 * Class manage a collection of messages
 */
public interface MessageRepository {

    /**
     * store a new message
     *
     * @param msg new message added to the repository
     */
    void addMessage(/*@NotNull*/ Message msg);

    /**
     * retrive all the messages published by a specific author
     *
     * @param user user that published the message
     * @return list of message published by user
     */
    List<Message> getUserMessages(User user);
}
