package wise86.socialNetworkKata.repository.onMemory;

import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.repository.FollowingRepository;

import java.util.*;

/**
 * Store the following relation in memory
 */
public class MemoryFollowingRepository implements FollowingRepository {

    private Map<User, List<User>> followingRelation = new HashMap<>();

    @Override
    public void addFollowingRelation(User user, User following) {
        if (!followingRelation.containsKey(user))
            followingRelation.put(user, new ArrayList<>());
        followingRelation.get(user).add(following);
    }

    @Override
    public List<User> getUsersFollowedBy(User user) {
        List<User> temp = followingRelation.get(user);
        if (temp != null)
            return Collections.unmodifiableList(followingRelation.get(user));
        else
            return Collections.emptyList();
    }
}
