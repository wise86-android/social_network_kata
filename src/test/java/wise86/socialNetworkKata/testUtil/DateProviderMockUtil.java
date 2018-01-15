package wise86.socialNetworkKata.testUtil;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import wise86.socialNetworkKata.util.DateProvider;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DateProviderMockUtil {

    static public DateProvider eachInvocationIsIncrementedByMs(long delay) {
        DateProvider dateProvider = mock(DateProvider.class);
        //each time it is indicated responds with 1 seconds more
        when(dateProvider.now()).thenAnswer(new Answer<Date>() {
            private int nInvocations = 0;

            @Override
            public Date answer(InvocationOnMock invocation) throws Throwable {
                return new Date(nInvocations++ * delay);
            }
        });
        return dateProvider;
    }
}
