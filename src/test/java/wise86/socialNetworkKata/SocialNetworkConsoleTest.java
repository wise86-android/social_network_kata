package wise86.socialNetworkKata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import wise86.socialNetworkKata.testUtil.DateProviderMockUtil;
import wise86.socialNetworkKata.util.DateProvider;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static wise86.socialNetworkKata.testUtil.ContainsSubStringInOrderMatcher.containsInOrder;

@RunWith(MockitoJUnitRunner.class)
public class SocialNetworkConsoleTest {

    private PipedInputStream socialNetworkInput = new PipedInputStream();
    private PipedOutputStream outputStream = new PipedOutputStream();
    private ByteArrayOutputStream socialNetworkOutput = new ByteArrayOutputStream();
    private PrintStream socialNetworkInputConsole;

    private SocialNetworkConsole socialNetwork;

    private DateProvider dateProvider = DateProviderMockUtil.eachInvocationIsIncrementedByMs(1000);

    @Before
    public void initStreams() throws IOException {

        outputStream.connect(socialNetworkInput);
        socialNetworkInputConsole = new PrintStream(outputStream);

        socialNetwork = new SocialNetworkConsole(socialNetworkInput, socialNetworkOutput, dateProvider);
    }

    private void assertNoOutputInteraction() {
        Assert.assertTrue(socialNetworkOutput.size() == 0);
    }

    @Test
    public void publishingMessageDoesNotPrintOut() {
        socialNetworkInputConsole.print("alice -> ciao\n");
        socialNetworkInputConsole.close();
        socialNetwork.run();
        assertNoOutputInteraction();
    }

    @Test
    public void writingTheUserNamePrintTheMessagesOrderByMostRecentFirst() {
        String user = "Alice";
        String message1 = "message1";
        String message2 = "message2";

        socialNetworkInputConsole.print(user + " -> " + message1 + "\n");
        socialNetworkInputConsole.print(user + " -> " + message2 + "\n");
        socialNetworkInputConsole.print(user + "\n");
        socialNetworkInputConsole.close();
        socialNetwork.run();

        String outStr = socialNetworkOutput.toString();

        assertThat(outStr, containsInOrder(message2, "1 second ago",
                message1, "2 seconds ago"));

    }

    @Test
    public void followAUserDoesNotPrintOut() {
        socialNetworkInputConsole.print("alice follows bob");
        socialNetworkInputConsole.close();
        socialNetwork.run();
        assertNoOutputInteraction();
    }

    @Test
    public void theWallCommandShowTheUserAndTheFollowedUserMessagesOrderByMostRecentFirst() {
        String userA = "Alice";
        String userAMessage = "first message By Alice";
        String userAMessage2 = "second message By Alice";
        String userB = "Bob";
        String userBMessage = "first message By Bob";

        socialNetworkInputConsole.print(userA + "->" + userAMessage + "\n");
        socialNetworkInputConsole.print(userB + "->" + userBMessage + "\n");
        socialNetworkInputConsole.print(userA + "->" + userAMessage2 + "\n");
        socialNetworkInputConsole.print(userA + " follows " + userB + "\n");
        socialNetworkInputConsole.print(userA + " walls" + "\n");
        socialNetworkInputConsole.close();

        socialNetwork.run();

        String outStr = socialNetworkOutput.toString();

        assertThat(outStr, containsInOrder(userA, userAMessage2, "1 second ago",
                userB, userBMessage, "2 seconds ago",
                userA, userAMessage, "3 seconds ago"));
    }


}
