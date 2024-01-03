package Model;

import java.util.HashSet;
import javax.persistence.*;
import java.util.Set;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String username;

  @OneToMany(mappedBy = "author")
  private Set<Post> posts;

  @ManyToMany
  @JoinTable(
      name = "user_followers",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "follower_id"))
  private Set<User> followers = new HashSet<>();

  @ManyToMany(mappedBy = "followers")
  private Set<User> following = new HashSet<>();

  private Set<Post> likedPosts = new HashSet<>();
  public void likePost(Post post) {
    likedPosts.add(post);
    post.getLikers().add(this);
  }
  public void followUser(User userToFollow) {
    following.add(userToFollow);
    userToFollow.getFollowers().add(this);
  }

  // ... existing methods, such as followUser(User) and likePost(Post)...

  public void unfollowUser(User user) {
    this.following.remove(user);
  }

  public void unlikePost(Post post) {
    this.likedPosts.remove(post);
  }

  @Setter
  private String email;

}
