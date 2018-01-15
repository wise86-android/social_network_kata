package wise86.socialNetworkKata.testUtil;

import wise86.socialNetworkKata.data.Message;
import wise86.socialNetworkKata.data.User;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageMockUtil {

    public static Message mockMessage(String user, String content) {
        Message msg = mock(Message.class);
        when(msg.getContent()).thenReturn(content);
        when(msg.getAuthor()).thenReturn(new User(user));
        return msg;
    }

    public static Message mockMessage(Date publishingDate) {
        Message msg = mock(Message.class);
        when(msg.getPublicisingTime()).thenReturn(publishingDate);
        return msg;
    }
}
