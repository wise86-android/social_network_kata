package wise86.socialNetworkKata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.util.DateProvider;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SocialNetworkViewTest {

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream outStream = new PrintStream(outContent);

    @Mock
    private
    DateProvider dateProvider;

    private SocialNetworkContract.View view;

    @Before
    public void initView() {
        view = new SocialNetworkConsole(null, outStream, dateProvider);
    }


    @Test
    public void correctlyPrintAnWallMessage() {

        Message msg = new Message(new User("USER"), "MESSAGE", new Date(0));

        when(dateProvider.now()).thenReturn(new Date(1000));
        view.displayUserWallMessages(Collections.singletonList(msg));


        Assert.assertEquals("USER - MESSAGE ( 1 second ago )\n", outContent.toString());
    }

    @Test
    public void correctlyPrintAnUserMessage() {

        Message msg = new Message(new User("USER"), "MESSAGE", new Date(0));

        when(dateProvider.now()).thenReturn(new Date(60 * 1000));
        view.displayUserMessages(Collections.singletonList(msg));

        Assert.assertEquals("MESSAGE ( 1 minute ago )\n", outContent.toString());

    }

    private void assertNoOutputInteraction() {
        Assert.assertTrue(outContent.size() == 0);
    }

    @Test
    public void emptyListDoesNotProduceOutput() {
        view.displayUserWallMessages(Collections.emptyList());
        assertNoOutputInteraction();

        view.displayUserMessages(Collections.emptyList());
        assertNoOutputInteraction();
    }

    @Test
    public void nullListDoesNotProduceOutput() {
        view.displayUserWallMessages(null);
        assertNoOutputInteraction();

        view.displayUserMessages(null);
        assertNoOutputInteraction();
    }

}