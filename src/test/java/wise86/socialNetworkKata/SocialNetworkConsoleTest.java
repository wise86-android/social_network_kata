package wise86.socialNetworkKata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class SocialNetworkConsoleTest {

    private PipedInputStream socialNetworkInput = new PipedInputStream();
    private PipedOutputStream outputStream = new PipedOutputStream();
    private ByteArrayOutputStream socialNetworkOutput = new ByteArrayOutputStream();
    private PrintStream socialNewtorkInputConsole;

    private SocialNetworkConsole socialNetwork;

    @Before
    public void initStreams() throws IOException {

        outputStream.connect(socialNetworkInput);
        socialNewtorkInputConsole = new PrintStream(outputStream);

        socialNetwork = new SocialNetworkConsole(socialNetworkInput, socialNetworkOutput);
    }

    private void assertNoOutputInteraction() {
        Assert.assertTrue(socialNetworkOutput.size() == 0);
    }

    @Test
    public void publishingMessageDoesNotPrintOut() {
        socialNewtorkInputConsole.print("alice -> ciao\n");
        socialNewtorkInputConsole.close();
        socialNetwork.run();
        assertNoOutputInteraction();
    }

    @Test
    public void writingTheUserNamePrintTheMessages() {
        String user = "Alice";
        String message1 = "message1";
        String message2 = "message2";

        socialNewtorkInputConsole.print(user + " -> " + message1 + "\n");
        socialNewtorkInputConsole.print(user + " -> " + message2 + "\n");
        socialNewtorkInputConsole.print(user + "\n");
        socialNewtorkInputConsole.close();
        socialNetwork.run();

        String outStr = socialNetworkOutput.toString();

        assertThat(outStr, containsString(message1));
        assertThat(outStr, containsString(message2));
    }

    @Test
    public void followAUserDoesNotPrintOut() {
        socialNewtorkInputConsole.print("alice follows bob");
        socialNewtorkInputConsole.close();
        socialNetwork.run();
        assertNoOutputInteraction();
    }


}
