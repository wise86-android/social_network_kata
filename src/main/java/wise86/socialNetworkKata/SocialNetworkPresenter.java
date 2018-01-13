package wise86.socialNetworkKata;

import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.util.DateProvider;

import java.util.*;

public class SocialNetworkPresenter implements SocialNetworkContract.Presenter {

    private Map<String,List<Message>> userMessages = new HashMap<>();
    private Map<String,List<String>> userFollowed = new HashMap<>();
    private SocialNetworkContract.View view;
    private DateProvider dateProvider;

    public SocialNetworkPresenter(SocialNetworkContract.View view, DateProvider dateProvider){
        this.view = view;
        this.dateProvider = dateProvider;
    }

    private void sortByMostRecentFirst(List<Message> messages){
        messages.sort(Comparator.comparing(Message::getPublicisingTime).reversed());
    }

    @Override
    public void showWallForUser(String user) {
        List<Message> messages =new ArrayList<>( userMessages.get(user));
        List<String> following = userFollowed.get(user);
        if(following!=null && !following.isEmpty())
            for(String followedUser : following){
                messages.addAll(userMessages.get(followedUser));
            }
        sortByMostRecentFirst(messages);
        view.displayUserWallMessages(messages);
    }

    @Override
    public void showUserMessages(String user) {
        List<Message> messages = userMessages.get(user);
        sortByMostRecentFirst(messages);
        view.displayUserMessages(messages);
    }

    @Override
    public void startFollow(String user, String followed) {
        if(!userFollowed.containsKey(user)){
            userFollowed.put(user,new ArrayList<>());
        }
        userFollowed.get(user).add(followed);
    }

    @Override
    public void publishMessage(String user, String message) {
        if(!userMessages.containsKey(user)){
            userMessages.put(user,new ArrayList<>());
        }
        userMessages.get(user).add(new Message(new User(user),message,dateProvider.now()));
    }
}
