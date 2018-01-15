package wise86.socialNetworkKata;

import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.repository.SocialNetworkRepository;
import wise86.socialNetworkKata.repository.onMemory.MemoryFollowingRepository;
import wise86.socialNetworkKata.repository.onMemory.MemoryMessageRepository;
import wise86.socialNetworkKata.util.DateProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SocialNetworkPresenter implements SocialNetworkContract.Presenter {


    private SocialNetworkRepository socialNetworkRepository = new SocialNetworkRepository(
            new MemoryMessageRepository(),
            new MemoryFollowingRepository());

    private SocialNetworkContract.View view;
    private DateProvider dateProvider;

    /**
     * build the social network presenter
     *
     * @param view         object what will display the command results
     * @param dateProvider object used to know the current time
     */
    public SocialNetworkPresenter(SocialNetworkContract.View view, DateProvider dateProvider) {
        this.view = view;
        this.dateProvider = dateProvider;
    }

    /**
     * sort the messages list to have in position 0 the most recent post
     *
     * @param messages list to message to sort, after the function call the list content will be sorted
     */
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
        //messages is read only, create a new list to be able to sort it
        List<Message> sortableMessages = new ArrayList<>(messages);
        sortByMostRecentFirst(sortableMessages);
        view.displayUserMessages(sortableMessages);
    }

    @Override
    public void startFollow(String userName, String followingName) {
        User user = new User(userName);
        User followed = new User(followingName);
        socialNetworkRepository.addFollowerRelation(user, followed);
    }

    @Override
    public void publishMessage(String user, String message) {
        socialNetworkRepository.publish(new Message(new User(user), message, dateProvider.now()));
    }
}
