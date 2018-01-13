import wise86.socialNetworkKata.SocialNetworkConsole;
import wise86.socialNetworkKata.util.DateProvider;

public class Main {

    public static void main(String args[]){
        DateProvider dateProvider = new DateProvider();
        SocialNetworkConsole socialNetwork = new SocialNetworkConsole(System.in,System.out,dateProvider);

        socialNetwork.run();
    }

}
