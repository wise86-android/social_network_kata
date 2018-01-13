package wise86.socialNetworkKata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import wise86.socialNetworkKata.util.DateProvider;

import java.io.*;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static wise86.socialNetworkKata.testUtil.ContainsSubStringInOrderMatcher.containsInOrder;

@RunWith(MockitoJUnitRunner.class)
public class SocialNetworkConsoleTest {

    private PipedInputStream socialNetworkInput = new PipedInputStream();
    private PipedOutputStream outputStream = new PipedOutputStream();
    private ByteArrayOutputStream socialNetworkOutput = new ByteArrayOutputStream();
    private PrintStream socialNewtorkInputConsole;

    private SocialNetworkConsole socialNetwork;

    @Mock
    private
    DateProvider dateProvider;

    @Before
    public void initStreams() throws IOException {

        //each time it is indicated responds with 1 seconds more
        when(dateProvider.now()).thenAnswer(new Answer<Date>() {
            private int nInvocations = 0;

            @Override
            public Date answer(InvocationOnMock invocation) throws Throwable {
                return new Date(nInvocations++ * 1000);
            }
        });

        outputStream.connect(socialNetworkInput);
        socialNewtorkInputConsole = new PrintStream(outputStream);

        socialNetwork = new SocialNetworkConsole(socialNetworkInput, socialNetworkOutput, dateProvider);
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
    public void writingTheUserNamePrintTheMessagesOrderByMostRecentFirst() {
        String user = "Alice";
        String message1 = "message1";
        String message2 = "message2";

        socialNewtorkInputConsole.print(user + " -> " + message1 + "\n");
        socialNewtorkInputConsole.print(user + " -> " + message2 + "\n");
        socialNewtorkInputConsole.print(user + "\n");
        socialNewtorkInputConsole.close();
        socialNetwork.run();

        String outStr = socialNetworkOutput.toString();

        assertThat(outStr, containsInOrder(message2, "1 second ago",
                message1, "2 seconds ago"));

    }

    @Test
    public void followAUserDoesNotPrintOut() {
        socialNewtorkInputConsole.print("alice follows bob");
        socialNewtorkInputConsole.close();
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

        socialNewtorkInputConsole.print(userA + "->" + userAMessage + "\n");
        socialNewtorkInputConsole.print(userB + "->" + userBMessage + "\n");
        socialNewtorkInputConsole.print(userA + "->" + userAMessage2 + "\n");
        socialNewtorkInputConsole.print(userA + " follows " + userB + "\n");
        socialNewtorkInputConsole.print(userA + " walls" + "\n");
        socialNewtorkInputConsole.close();

        socialNetwork.run();

        String outStr = socialNetworkOutput.toString();

        assertThat(outStr, containsInOrder(userA, userAMessage2, "1 second ago",
                userB, userBMessage, "2 seconds ago",
                userA, userAMessage, "3 seconds ago"));
    }


}
