package Controller;
import Model.Post;
import Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

  @Autowired
  private PostService postService;

  @PostMapping("/posts")
  public ResponseEntity<Post> createPost(@RequestBody Post post) {
    return new ResponseEntity<>(postService.createPost(post), HttpStatus.CREATED);
  }

  @GetMapping("/posts")
  public List<Post> getAllPosts() {
    return postService.getAllPosts();
  }

  @GetMapping("/posts/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable Long id) {
    Post post = postService.getPostById(id);
    if (post != null) {
      return new ResponseEntity<>(post, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/posts/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/posts/{id}")
  public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postUpdates) {
    Post updatedPost = postService.updatePost(id, postUpdates);
    if (updatedPost != null) {
      return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
