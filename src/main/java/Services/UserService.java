package Services;

import Model.User;
import java.util.List;
import Exception.CustomExceptions.*;

public interface UserService {
  User createUser(User user);
  List<User> getAllUsers();
  User getUserById(Long id);

  void followUser(Long userId, Long userToFollowId);
  void likePost(Long userId, Long postId);
  /**
   * Enables the user with the given userId to unfollow another user.
   *
   * @param userId - The id of the user that wants to unfollow
   * @param userToUnfollowId - The id of the user to be unfollowed
   * @throws UsernameNotFoundException if either user is not found
   */
  void unfollowUser(Long userId, Long userToUnfollowId);

  /**
   * Enables the user with the given userId to unlike a post.
   *
   * @param userId - The id of the user that wants to unlike the post
   * @param postId - The id of the post to be unliked
   * @throws UsernameNotFoundException if the user is not found
   * @throws ResourceNotFoundException if the post is not found
   */
  void unlikePost(Long userId, Long postId);
  /**
   * Deletes the user with the specified id.
   *
   * @param id - The id of the user to delete.
   * @throws UsernameNotFoundException if the user is not found
   */
  void deleteUserById(Long id);

  /**
   * Updates the details of the user with the specified id.
   *
   * @param id - The id of the user to update.
   * @param userUpdates - The User object containing updated details of the user.
   * @return the updated User object.
   * @throws UsernameNotFoundException if the user is not found
   */
  User updateUser(Long id, User userUpdates);
}
