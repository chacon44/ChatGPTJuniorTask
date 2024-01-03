package Services;

import Exception.CustomExceptions.*;
import Model.Post;
import Model.User;
import Repositories.PostRepository;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostRepository postRepository;

  @Override
  public User createUser(User user) throws IllegalArgumentException {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    return userRepository.save(user);
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
  @Override
  public User getUserById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
  }
  @Override
  public void followUser(Long userId, Long userToFollowId) {
    if (userId.equals(userToFollowId)) {
      throw new IllegalArgumentException("A user can't follow themselves.");
    }
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    User userToFollow = userRepository.findById(userToFollowId)
        .orElseThrow(() -> new UsernameNotFoundException("User to follow Not Found"));

    user.followUser(userToFollow);  // use the method defined in the User model
    userRepository.save(user);
  }

  @Override
  public void unfollowUser(Long userId, Long userToUnfollowId) {
    if (userId.equals(userToUnfollowId)) {
      throw new IllegalArgumentException("A user can't unfollow themselves.");
    }
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    User userToUnfollow = userRepository.findById(userToUnfollowId)
        .orElseThrow(() -> new UsernameNotFoundException("User to unfollow Not Found"));

    user.unfollowUser(userToUnfollow);  // use the method defined in the User model
    userRepository.save(user);
  }

  @Override
  public void likePost(Long userId, Long postId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    user.likePost(post);  // use the method defined in the Post model
    userRepository.save(user);
  }
  @Override
  public void unlikePost(Long userId, Long postId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    // Remove the post from the user's liked posts list
    user.unlikePost(post);

    // Save the user with the updated liked posts list
    userRepository.save(user);
  }
  @Override
  public void deleteUserById(Long id) {
    if (!userRepository.existsById(id)) {
      throw new UsernameNotFoundException("User Not Found");
    }
    userRepository.deleteById(id);
  }

  @Override
  public User updateUser(Long id, User userUpdates) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

    // Set new property values here as per userUpdates, for example:
    user.setUsername(userUpdates.getUsername());
    user.setEmail(userUpdates.getEmail());
    // ... repeat this for all properties that can be updated ...

    return userRepository.save(user);
  }
}
