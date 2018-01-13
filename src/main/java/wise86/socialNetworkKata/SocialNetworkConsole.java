package wise86.socialNetworkKata;

import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.util.DateProvider;
import wise86.socialNetworkKata.util.PassedTimeFormatter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * Implement a console base interface for a simple twitter like social network.
 * the available commands are:
 * - User submits commands to the application:
 * - posting: $user -> $message
 * - show message user: $user
 * - following: $userA follows $userB
 * - show user's wall: $user wall
 */
public class SocialNetworkConsole implements SocialNetworkContract.View {

    private static final String PUBLISH_MESSAGE = "->";
    private static final String SHOW_WALL = "wall";
    private static final String FOLLOWS = "follows";

    private PrintStream out;
    private InputStream in;
    private DateProvider dateProvider;

    private SocialNetworkContract.Presenter presenter;

    /**
     * Create a console social network
     *
     * @param input        stream where read the user command
     * @param output       stream where print the user command
     * @param dateProvider object used to know the current system time
     */
    public SocialNetworkConsole(InputStream input, OutputStream output, DateProvider dateProvider) {
        out = new PrintStream(output);
        in = input;
        this.dateProvider = dateProvider;
        presenter = new SocialNetworkPresenter(this, dateProvider);
    }


    /**
     * Read the input stream, one line a the time and exec the command
     */
    public void run() {
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()) {
            String command = sc.nextLine();
            if (isPublishMessageCommand(command)) {
                publishMessage(command);
            } else if (isShowWallCommand(command)) {
                showWall(command);
            } else if (isFollowingCommand(command)) {
                startFollowing(command);
            } else
                presenter.showUserMessages(command);

        }
    }


    private boolean isFollowingCommand(String command) {
        return command.contains(FOLLOWS);
    }


    /**
     * parse the command string assuming that it is a follows command
     *
     * @param command follows command: with the format $user1 follows $user2
     */
    private void startFollowing(String command) {
        String[] parameter = command.split(FOLLOWS);
        String user = parameter[0].trim();
        String following = parameter[1].trim();
        presenter.startFollow(user, following);
    }


    private boolean isShowWallCommand(String command) {
        return command.contains(SHOW_WALL);
    }

    /**
     * parse the command string assuming that it is a show wall command
     *
     * @param command show wall command: with the format $user wall
     */
    private void showWall(String command) {
        String[] parameter = command.split(SHOW_WALL);
        String user = parameter[0].trim();
        presenter.showWallForUser(user);
    }

    private boolean isPublishMessageCommand(String command) {
        return command.contains(PUBLISH_MESSAGE);
    }

    /**
     * parse the command string assuming that it is a publish message command
     *
     * @param command publish command: $user -> command
     */
    private void publishMessage(String command) {
        String[] parameter = command.split(PUBLISH_MESSAGE);
        String user = parameter[0].trim();
        String content = parameter[1].trim();
        presenter.publishMessage(user, content);
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
