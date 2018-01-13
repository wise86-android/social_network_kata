package wise86.socialNetworkKata;

import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.repository.SocialNetworkRepository;
import wise86.socialNetworkKata.repository.onMemory.MemoryFollowerRepository;
import wise86.socialNetworkKata.repository.onMemory.MemoryMessageRepository;
import wise86.socialNetworkKata.util.DateProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SocialNetworkPresenter implements SocialNetworkContract.Presenter {

    private SocialNetworkRepository socialNetworkRepository = new SocialNetworkRepository(
            new MemoryMessageRepository(),
            new MemoryFollowerRepository());

    private SocialNetworkContract.View view;
    private DateProvider dateProvider;

    public SocialNetworkPresenter(SocialNetworkContract.View view, DateProvider dateProvider) {
        this.view = view;
        this.dateProvider = dateProvider;
    }

    private void sortByMostRecentFirst(List<Message> messages) {
        messages.sort(Comparator.comparing(Message::getPublicisingTime).reversed());
    }

    @Override
    public void showWallForUser(String user) {
        List<Message> messages = socialNetworkRepository.getWallMessageForUser(new User(user));
        sortByMostRecentFirst(messages);
        view.displayUserWallMessages(messages);
    }

    @Override
    public void showUserMessages(String user) {
        List<Message> messages = socialNetworkRepository.getMessagesFromUser(new User(user));
        List<Message> sortableMessages = new ArrayList<>(messages);
        sortByMostRecentFirst(sortableMessages);
        view.displayUserMessages(sortableMessages);
    }

    public void startFollow(String userName, String followedName) {
        User user = new User(userName);
        User followed = new User(followedName);
        socialNetworkRepository.addFollowerRelation(user, followed);
    }

    @Override
    public void publishMessage(String user, String message) {
        socialNetworkRepository.publish(new Message(new User(user), message, dateProvider.now()));
    }
}
