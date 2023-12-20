package Services;

import Exception.CustomExceptions.*;
import Model.Post;
import Model.User;
import Repositories.PostRepository;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.stereotype.Service;

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
    Post postToDelete = getPostById(id);
    postRepository.delete(postToDelete);
  }

  @Override
  public Post updatePost(Long id, Post postUpdate) {
    Post existingPost = getPostById(id);
    existingPost.setTitle(postUpdate.getTitle());
    existingPost.setBody(postUpdate.getBody());
    return postRepository.save(existingPost);
  }

  @Override
  public void likePost(Long postId, Long userId) {
    Post post = getPostById(postId);
    User user = userRepository.findById(userId).orElseThrow(() ->
        new UsernameNotFoundException("User Not Found"));
    user.likePost(post);
    userRepository.save(user);
  }
}
