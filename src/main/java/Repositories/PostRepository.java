package Repositories;

import Model.Post;
import Model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  /**
   * Finds all posts created by a specific user.
   *
   * @param userId - The id of the User
   * @return - A list of all posts created by the user
   */
  List<Post> findByUserId(Long userId);

  /**
   * This method is used to add a like to a post
   *
   * @param liker - The user that wants to like the post
   * @param postId - The id of the post to be liked
   */
  void addLike(User liker, Long postId);
}
