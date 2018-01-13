package wise86.socialNetworkKata.repository;

import wise86.socialNetworkKata.data.User;

import java.util.List;

public interface FollowerRepository {

    void addFollowerRelation(User user, User followed);

    List<User> getUsersFollowedBy(User user);

}
