package Repositories;

import Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Returns the User by username
   *
   * @param username - The username of the User
   * @return - The User object if present otherwise null
   */
  User findByUsername(String username);

  /**
   * This method is used to add a follower to a user
   *
   * @param follower - The user that wants to follow
   * @param userToBeFollowed - The user to be followed
   */
  void addFollower(User follower, User userToBeFollowed);
}
