package wise86.socialNetworkKata.repository.onMemory;

import org.junit.Test;
import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.repository.FollowingRepository;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class MemoryFollowingRepositoryTest {

    private static final User USER_A = new User("UserA");
    private static final User USER_B = new User("UserB");
    private static final User USER_C = new User("UserC");

    private FollowingRepository repository = new MemoryFollowingRepository();


    @Test
    public void aFallingRelationCanBeAddAndRetrieved() {
        repository.addFollowingRelation(USER_A, USER_B);
        List<User> followedByA = repository.getUsersFollowedBy(USER_A);
        assertThat(followedByA, is(not(empty())));

        assertThat(followedByA, contains(USER_B));
    }

    @Test
    public void anUserCanFollowMoreUsers() {
        repository.addFollowingRelation(USER_A, USER_B);
        repository.addFollowingRelation(USER_A, USER_C);
        List<User> followedByA = repository.getUsersFollowedBy(USER_A);
        assertThat(followedByA, is(not(empty())));

        assertThat(followedByA, contains(USER_B, USER_C));
    }

    @Test
    public void onlyTheFollowerOfTheUserAreReturned() {
        repository.addFollowingRelation(USER_A, USER_B);
        repository.addFollowingRelation(USER_A, USER_C);
        repository.addFollowingRelation(USER_B, USER_C);
        repository.addFollowingRelation(USER_C, USER_A);

        List<User> followedByB = repository.getUsersFollowedBy(USER_B);
        assertThat(followedByB, is(not(empty())));

        assertThat(followedByB, contains(USER_C));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void theListReturnedIsReadOnly() {
        repository.addFollowingRelation(USER_A, USER_B);
        repository.getUsersFollowedBy(USER_A).add(USER_C);
    }

    @Test
    public void theListIsEmptyTheUserDoesNotFollowAnyone() {
        repository.addFollowingRelation(USER_A, USER_B);
        assertThat(repository.getUsersFollowedBy(USER_B), is(empty()));
    }

}