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
@RequestMapping("/api/follower")
public class FollowController {

  @Autowired
  private UserService userService;

  @PostMapping("/{userId}/{userToFollowId}")
  public ResponseEntity<Void> followUser(
      @PathVariable Long userId,
      @PathVariable Long userToFollowId) {

    userService.followUser(userId, userToFollowId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @DeleteMapping("/{userId}/{userToUnfollowId}")
  public ResponseEntity<Void> unfollowUser(
      @PathVariable Long userId,
      @PathVariable Long userToUnfollowId) {

    userService.unfollowUser(userId, userToUnfollowId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
