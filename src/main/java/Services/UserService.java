package Services;

import Model.User;
import java.util.List;

public interface UserService {
  User createUser(User user);
  List<User> getAllUsers();
  User getUserById(Long id);

  void followUser(Long userId, Long userToFollowId);
  void likePost(Long userId, Long postId);
}
