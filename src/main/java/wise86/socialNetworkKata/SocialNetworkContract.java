package wise86.socialNetworkKata;

import wise86.socialNetworkKata.data.Message;

import java.util.List;

/**
 * Interfaced used to define the Model View presenter architecture to rappresent a social network
 */
public class SocialNetworkContract {


    interface View {

        /**
         * display the list of messages of a specific user
         *
         * @param messages list of messages to display
         */
        void displayUserMessages(List<Message> messages);

        /**
         * display the list of messages that are inside an user wall
         *
         * @param messages list of messages to display
         */
        void displayUserWallMessages(List<Message> messages);
    }

    interface Presenter {

        /**
         * display the user's wall messages
         *
         * @param user user whose wall will be displayed
         */
        void showWallForUser(String user);

        /**
         * display the user's messages
         *
         * @param user user whose messages will be displayed
         */
        void showUserMessages(String user);

        /**
         * the 'user' will start to follow the 'following' user
         *
         * @param user      user that will follow another user
         * @param following user that will be follower
         */
        void startFollow(String user, String following);

        /**
         * publish a message to the social network
         *
         * @param user    user that is posting the message
         * @param message message content
         */
        void publishMessage(String user, String message);
    }

}
