import wise86.socialNetworkKata.SocialNetworkConsole;
import wise86.socialNetworkKata.util.DateProvider;

public class SocialNetworkMain {

    public static void main(String args[]) {
        DateProvider dateProvider = new DateProvider();

        SocialNetworkConsole socialNetwork = new SocialNetworkConsole(System.in, System.out, dateProvider);

        //wait command form System.in and print output in the System.out
        //the function return when the user close the input channel (Ctrl + d)
        socialNetwork.run();
    }

}
