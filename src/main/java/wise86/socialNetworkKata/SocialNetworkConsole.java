package wise86.socialNetworkKata;

import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.util.DateProvider;
import wise86.socialNetworkKata.util.PassedTimeFormatter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class SocialNetworkConsole {

    private static final String PUBLISH_MESSAGE = "->";
    private static final String SHOW_WALL = "wall";
    private static final String FOLLOWS = "follows";
    private Map<String,List<Message>> userMessages = new HashMap<>();
    private Map<String,List<String>> userFollowed = new HashMap<>();

    private PrintStream out;
    private InputStream in;
    private DateProvider dateProvider;

    public SocialNetworkConsole(InputStream input, OutputStream output, DateProvider dateProvider) {
        out = new PrintStream(output);
        in = input;
        this.dateProvider = dateProvider;
    }

    public void run(){
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()){
            String command = sc.nextLine();
            if(command.contains(PUBLISH_MESSAGE)){
                insertMessage(command);
            }else if (command.contains(SHOW_WALL)){
                showWall(command);
            }else if (command.contains(FOLLOWS)){
                startFollowing(command);
            }else
                printUserMessage(command);

        }
    }

    private void startFollowing(String command) {
        String[] parameter = command.split(FOLLOWS);
        String user = parameter[0].trim();
        String followed = parameter[1].trim();
        if(!userFollowed.containsKey(user)){
            userFollowed.put(user,new ArrayList<>());
        }
        userFollowed.get(user).add(followed);
    }

    private void showWall(String command) {
        String[] parameter = command.split(SHOW_WALL);
        String user = parameter[0].trim();
        PassedTimeFormatter formatter= new PassedTimeFormatter(dateProvider.now());
        List<Message> messages =new ArrayList<>( userMessages.get(user));
        List<String> following = userFollowed.get(user);
        if(following!=null && !following.isEmpty())
            for(String followedUser : following){
                messages.addAll(userMessages.get(followedUser));
            }
        messages.sort(Comparator.comparing(Message::getPublicisingTime).reversed());
        for(Message msg : messages){
            out.println(msg.getAuthor().getName()+" - "+msg.getContent()+"("+formatter.format(msg.getPublicisingTime())+")");
        }
    }

    private void printUserMessage(String user) {
        List<Message> messages = userMessages.get(user);
        if(messages==null || messages.isEmpty())
            return;
        PassedTimeFormatter formatter= new PassedTimeFormatter(dateProvider.now());
        messages.sort(Comparator.comparing(Message::getPublicisingTime).reversed());
        for(Message msg : messages){
            out.println(msg.getContent()+"("+formatter.format(msg.getPublicisingTime())+")");
        }
    }

    private void insertMessage(String command) {
        String[] parameter = command.split(PUBLISH_MESSAGE);
        String user = parameter[0].trim();
        String content = parameter[1].trim();
        if(!userMessages.containsKey(user)){
            userMessages.put(user,new ArrayList<>());
        }
        userMessages.get(user).add(new Message(new User(user),content,dateProvider.now()));
    }
}
