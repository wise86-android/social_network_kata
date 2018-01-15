package wise86.socialNetworkKata.testUtil;

import wise86.socialNetworkKata.data.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserMockUtil {
    /**
     * create a mock object for a user with a specific name
     *
     * @param name user name
     * @return user class that will return the correct string for the getName method
     */
    public static User mockUserWithName(String name) {
        User user = mock(User.class);
        when(user.getName()).thenReturn(name);
        return user;
    }

}
