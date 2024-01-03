package Model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String body;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  @Getter
  @ManyToMany(mappedBy = "likedPosts")
  private Set<User> likers = new HashSet<>();

  // ... getters and setters ...

  public void likePost(User liker) {
    likers.add(liker);
    liker.getLikedPosts().add(this);
  }
}
