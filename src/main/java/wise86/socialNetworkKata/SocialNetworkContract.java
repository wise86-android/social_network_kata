package wise86.socialNetworkKata;

import wise86.socialNetworkKata.data.Message;

import java.util.List;

public class SocialNetworkContract {

    interface View {
        void displayUserMessages(List<Message> messages);

        void displayUserWallMessages(List<Message> messages);
    }

    interface Presenter {
        void showWallForUser(String user);

        void showUserMessages(String user);

        void startFollow(String user, String followed);

        void publishMessage(String user, String message);
    }

}
