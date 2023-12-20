package Services;

import Model.Post;
import java.util.List;

public interface PostService {

  Post createPost(Post post);

  List<Post> getAllPosts();

  Post getPostById(Long id);

  void deletePost(Long id);

  Post updatePost(Long id, Post post);

  void likePost(Long postId, Long userId);
}
