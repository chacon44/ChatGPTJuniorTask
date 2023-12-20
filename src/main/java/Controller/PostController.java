package Controller;

import Model.Post;
import Services.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {

  @Autowired
  private PostService postService;

  /**
   * Creates a new post in the system
   *
   * @param post - The post to be created
   * @return - The created post
   */
  @PostMapping("/posts")
  public ResponseEntity<Post> createPost(@RequestBody Post post) {
    return new ResponseEntity<>(postService.createPost(post), HttpStatus.CREATED);
  }

  /**
   * Returns all posts in the system
   *
   * @return - A list of all posts
   */
  @GetMapping("/posts")
  public List<Post> getAllPosts() {
    return postService.getAllPosts();
  }
}
