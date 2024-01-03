package ServiceTest;

import Exception.CustomExceptions.*;
import Model.Post;
import Model.User;
import Repositories.PostRepository;
import Repositories.UserRepository;
import Services.PostServiceImpl;
import org.example.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

  @InjectMocks
  private PostServiceImpl postService;

  @Mock
  private PostRepository postRepository;

  @Mock
  private UserRepository userRepository;

  private Post testPost;

  @BeforeEach
  void setUp() {
    User testUser = new User();
    testUser.setId(1L);
    testUser.setUsername("testuser");

    testPost = new Post();
    testPost.setId(1L);
    testPost.setTitle("test title");
    testPost.setBody("test body");
    testPost.setAuthor(testUser);
  }

  @Test
  void getAllPostsTest() {
    when(postRepository.findAll()).thenReturn(Collections.singletonList(testPost));

    List<Post> posts = postService.getAllPosts();

    assertEquals(1, posts.size());
    verify(postRepository, times(1)).findAll();
  }

  @Test
  void getAllPostsEmptyTest() {
    when(postRepository.findAll()).thenReturn(Collections.emptyList());

    List<Post> posts = postService.getAllPosts();

    assertTrue(posts.isEmpty());
    verify(postRepository, times(1)).findAll();
  }

  @Test
  void getPostByIdTest() {
    when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(testPost));

    Post resultPost = postService.getPostById(1L);

    assertEquals(testPost.getId(), resultPost.getId());
    verify(postRepository, times(1)).findById(any(Long.class));
  }

  @Test
  void getPostByIdNotFoundTest() {
    when(postRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> postService.getPostById(1L));
    verify(postRepository, times(1)).findById(any(Long.class));
  }

  @Test
  public void testDeletePost_NonexistentPost() {
    when(postRepository.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.deletePost(1L));
    assertEquals("Post not found with id: '1'", exception.getMessage());

    verify(postRepository, times(0)).delete(any(Post.class));
  }

  @Test
  public void testDeletePost_NullId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> postService.deletePost(null));
    assertEquals("id must not be null", exception.getMessage());

    verify(postRepository, times(0)).findById(any());
    verify(postRepository, times(0)).delete(any(Post.class));
  }

  @Test
  public void testUpdatePost_NonexistentPost() {
    Post postUpdate = new Post();
    postUpdate.setTitle("New Title");
    postUpdate.setBody("New Body");

    when(postRepository.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(1L, postUpdate));
    assertEquals("Post not found with id: '1'", exception.getMessage());

    verify(postRepository, times(0)).save(any(Post.class));
  }

  @Test
  public void testUpdatePost_NullId() {
    Post postUpdate = new Post();
    postUpdate.setTitle("New Title");
    postUpdate.setBody("New Body");

    Exception exception = assertThrows(IllegalArgumentException.class, () -> postService.updatePost(null, postUpdate));
    assertEquals("Arguments must not be null", exception.getMessage());

    verify(postRepository, times(0)).findById(any());
    verify(postRepository, times(0)).save(any(Post.class));
  }

  @Test
  public void testUpdatePost_NullUpdates() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> postService.updatePost(1L, null));
    assertEquals("Arguments must not be null", exception.getMessage());

    verify(postRepository, times(0)).findById(any());
    verify(postRepository, times(0)).save(any(Post.class));
  }

  @Test
  public void testLikePost_NonexistentPost() {
    User user = new User();
    user.setId(1L);

    when(postRepository.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.likePost(1L, 1L));
    assertEquals("Post not found with id: '1'", exception.getMessage());

    verify(userRepository, times(0)).save(any(User.class));
  }

  @Test
  public void testLikePost_NonexistentUser() {
    Post post = new Post();
    post.setId(1L);

    when(postRepository.findById(1L)).thenReturn(Optional.of(post));
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(UsernameNotFoundException.class, () -> postService.likePost(1L, 1L));
    assertEquals("User Not Found", exception.getMessage());

    verify(userRepository, times(0)).save(any(User.class));
  }

  @Test
  public void testLikePost_NullPostId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> postService.likePost(null, 1L));
    assertEquals("postId and userId must not be null", exception.getMessage());

    verify(postRepository, times(0)).findById(any());
    verify(userRepository, times(0)).findById(any());
    verify(userRepository, times(0)).save(any(User.class));
  }

  @Test
  public void testLikePost_NullUserId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> postService.likePost(1L, null));
    assertEquals("postId and userId must not be null", exception.getMessage());

    verify(postRepository, times(0)).findById(any());
    verify(userRepository, times(0)).findById(any());
    verify(userRepository, times(0)).save(any(User.class));
  }
}
