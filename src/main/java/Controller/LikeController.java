package Controller;

import Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
public class LikeController {

  @Autowired
  private UserService userService;

  @PostMapping("/{userId}/{postId}")
  public ResponseEntity<Void> likePost(
      @PathVariable Long userId,
      @PathVariable Long postId) {

    userService.likePost(userId, postId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @DeleteMapping("/{userId}/{postId}")
  public ResponseEntity<Void> unlikePost(
      @PathVariable Long userId,
      @PathVariable Long postId) {

    userService.unlikePost(userId, postId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
