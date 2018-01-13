package wise86.socialNetworkKata.repository.onMemory;

import org.junit.Test;
import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.repository.FollowerRepository;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class MemoryFollowerRepositoryTest {

    private static final User USER_A = new User("UserA");
    private static final User USER_B = new User("UserB");
    private static final User USER_C = new User("UserC");

    private FollowerRepository repository = new MemoryFollowerRepository();


    @Test
    public void aFollwoerRelationCanBeAddAndRetrived() {
        repository.addFollowerRelation(USER_A, USER_B);
        List<User> followedByA = repository.getUsersFollowedBy(USER_A);
        assertThat(followedByA, is(not(empty())));

        assertThat(followedByA, contains(USER_B));
    }

    @Test
    public void anUserCanFollowMoreUsers() {
        repository.addFollowerRelation(USER_A, USER_B);
        repository.addFollowerRelation(USER_A, USER_C);
        List<User> followedByA = repository.getUsersFollowedBy(USER_A);
        assertThat(followedByA, is(not(empty())));

        assertThat(followedByA, contains(USER_B, USER_C));
    }

    @Test
    public void onlyTheFollowerOfTheUserAreReturned() {
        repository.addFollowerRelation(USER_A, USER_B);
        repository.addFollowerRelation(USER_A, USER_C);
        repository.addFollowerRelation(USER_B, USER_C);
        repository.addFollowerRelation(USER_C, USER_A);

        List<User> followedByB = repository.getUsersFollowedBy(USER_B);
        assertThat(followedByB, is(not(empty())));

        assertThat(followedByB, contains(USER_C));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void theListReturnedIsReadOnly() {
        repository.addFollowerRelation(USER_A, USER_B);
        repository.getUsersFollowedBy(USER_A).add(USER_C);
    }

    @Test
    public void theListIsEmptyTheUserDoesntFollowAnyone() {
        repository.addFollowerRelation(USER_A, USER_B);
        assertThat(repository.getUsersFollowedBy(USER_B), is(empty()));
    }

}