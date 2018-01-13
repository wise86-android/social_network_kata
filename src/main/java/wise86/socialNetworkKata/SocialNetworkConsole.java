package wise86.socialNetworkKata;

import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.util.DateProvider;
import wise86.socialNetworkKata.util.PassedTimeFormatter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class SocialNetworkConsole  implements SocialNetworkContract.View {


    private static final String PUBLISH_MESSAGE = "->";
    private static final String SHOW_WALL = "wall";
    private static final String FOLLOWS = "follows";


    private PrintStream out;
    private InputStream in;
    private DateProvider dateProvider;

    private SocialNetworkContract.Presenter presenter;

    public SocialNetworkConsole(InputStream input, OutputStream output, DateProvider dateProvider) {
        out = new PrintStream(output);
        in = input;
        this.dateProvider = dateProvider;
        presenter = new SocialNetworkPresenter(this,dateProvider);
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
                presenter.showUserMessages(command);

        }
    }

    private void startFollowing(String command) {
        String[] parameter = command.split(FOLLOWS);
        String user = parameter[0].trim();
        String followed = parameter[1].trim();
        presenter.startFollow(user,followed);
    }

    private void showWall(String command) {
        String[] parameter = command.split(SHOW_WALL);
        String user = parameter[0].trim();
        presenter.showWallForUser(user);
    }


    private void insertMessage(String command) {
        String[] parameter = command.split(PUBLISH_MESSAGE);
        String user = parameter[0].trim();
        String content = parameter[1].trim();
        presenter.publishMessage(user,content);
    }

    @Override
    public void displayUserMessages(List<Message> messages) {
        if (messages == null || messages.isEmpty())
            return;
        PassedTimeFormatter dateFormatter = new PassedTimeFormatter(dateProvider.now());
        StringBuilder wallContent = new StringBuilder();
        for (Message msg : messages) {
            wallContent
                    .append(msg.getContent())
                    .append(" ( ")
                    .append(dateFormatter.format(msg.getPublicisingTime()))
                    .append(" )\n");
        }
        out.print(wallContent);
    }

    @Override
    public void displayUserWallMessages(List<Message> messages) {
        if (messages == null || messages.isEmpty())
            return;
        PassedTimeFormatter dateFormatter = new PassedTimeFormatter(dateProvider.now());
        StringBuilder wallContent = new StringBuilder();
        for (Message msg : messages) {
            wallContent.append(msg.getAuthor().getName())
                    .append(" - ")
                    .append(msg.getContent())
                    .append(" ( ")
                    .append(dateFormatter.format(msg.getPublicisingTime()))
                    .append(" )\n");
        }
        out.print(wallContent);
    }
}
