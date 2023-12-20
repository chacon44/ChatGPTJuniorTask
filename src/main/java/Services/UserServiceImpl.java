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
  public User createUser(User user) {
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
    User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    User userToFollow = userRepository.findById(userToFollowId).orElseThrow(() -> new UsernameNotFoundException("User to follow Not Found"));
    user.followUser(userToFollow);  // use the method defined in the User model
    userRepository.save(user);
  }

  @Override
  public void likePost(Long userId, Long postId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    user.likePost(post);  // use the method defined in the Post model
    userRepository.save(user);
  }
}
