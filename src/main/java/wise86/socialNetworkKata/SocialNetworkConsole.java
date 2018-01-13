package wise86.socialNetworkKata;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class SocialNetworkConsole {

    public static final String PUBLISH_MESSAGE = "->";
    private Map<String,List<String>> userMessages = new HashMap<>();

    private PrintStream out;
    private InputStream in;

    public SocialNetworkConsole(InputStream input, OutputStream output) {
        out = new PrintStream(output);
        in = input;
    }

    public void run(){
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()){
            String command = sc.nextLine();
            if(command.contains(PUBLISH_MESSAGE)){
                insertMessage(command);
            }else{
                printUserMessage(command);
            }

        }
    }

    private void printUserMessage(String user) {
        List<String> messages = userMessages.get(user);
        if(messages==null || messages.isEmpty())
            return;
        for(String msg : messages){
            out.println(msg);
        }
    }

    private void insertMessage(String command) {
        String[] parameter = command.split("PUBLISH_MESSAGE");
        String user = parameter[0].trim();
        String content = parameter[1].trim();
        if(!userMessages.containsKey(user)){
            userMessages.put(user,new ArrayList<>());
        }
        userMessages.get(user).add(content);
    }
}
