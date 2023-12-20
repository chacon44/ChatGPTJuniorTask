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

import java.util.Arrays;
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

  private User testUser;
  private Post testPost;

  @BeforeEach
  void setUp() {
    testUser = new User();
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
    when(postRepository.findAll()).thenReturn(Arrays.asList(testPost));

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

    assertThrows(ResourceNotFoundException.class, () -> {
      postService.getPostById(1L);
    });
    verify(postRepository, times(1)).findById(any(Long.class));
  }

  // Similar tests for other methods including deletePost, updatePost and likePost
}
