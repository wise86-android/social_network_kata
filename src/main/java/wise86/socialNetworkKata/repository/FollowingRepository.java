package wise86.socialNetworkKata.repository;

import wise86.socialNetworkKata.data.User;

import java.util.List;

/**
 * Interface to manage the following relation between the social network users
 */
public interface FollowingRepository {

    /**
     * store in the social network that the user $user will follow the user $followed
     *
     * @param user      user that follow
     * @param followed followed user
     */
    void addFollowingRelation(User user, User followed);

    /**
     * get all the users following the specific user
     *
     * @param user user to query
     * @return all user's following users
     */
    List<User> getUsersFollowedBy(User user);
}
