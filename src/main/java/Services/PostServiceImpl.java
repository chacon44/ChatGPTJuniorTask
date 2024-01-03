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
public class PostServiceImpl implements PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public Post createPost(Post post) {
    return postRepository.save(post);
  }

  @Override
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  @Override
  public Post getPostById(Long id) {
    return postRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Post", "id", id));
  }

  @Override
  public void deletePost(Long id) {
    if(id == null){
      throw new IllegalArgumentException("id must not be null");
    }
    Post postToDelete = getPostById(id);
    postRepository.delete(postToDelete);
  }

  @Override
  public Post updatePost(Long id, Post postUpdate) {
    if (id == null || postUpdate == null) {
      throw new IllegalArgumentException("Arguments must not be null");
    }

    Post existingPost = getPostById(id);
    existingPost.setTitle(postUpdate.getTitle());
    existingPost.setBody(postUpdate.getBody());
    return postRepository.save(existingPost);
  }

  @Override
  public void likePost(Long postId, Long userId) {
    if (postId == null || userId == null) {
      throw new IllegalArgumentException("postId and userId must not be null");
    }

    Post post = getPostById(postId);
    User user = userRepository.findById(userId).orElseThrow(() ->
        new UsernameNotFoundException("User Not Found"));
    user.likePost(post);
    userRepository.save(user);
  }
}
