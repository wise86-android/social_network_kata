package wise86.socialNetworkKata.repository.onMemory;

import wise86.socialNetworkKata.data.User;
import wise86.socialNetworkKata.repository.FollowerRepository;

import java.util.*;

public class MemoryFollowerRepository implements FollowerRepository {

    private Map<User, List<User>> following = new HashMap<>();

    @Override
    public void addFollowerRelation(User user, User followed) {
        if (!following.containsKey(user))
            following.put(user, new ArrayList<>());
        following.get(user).add(followed);
    }

    @Override
    public List<User> getUsersFollowedBy(User user) {
        List<User> temp = following.get(user);
        if (temp != null)
            return Collections.unmodifiableList(following.get(user));
        else
            return Collections.emptyList();
    }
}
