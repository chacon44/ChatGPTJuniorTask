package ControllerTest;

import Controller.PostController;
import Model.Post;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import Services.PostService;
import java.util.Arrays;
import java.util.List;
import org.example.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class PostControllerTest {

  @InjectMocks
  private PostController postController;

  @Mock
  private PostService postService;

  @Mock
  private List<Post> posts;
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
  }

  @Test
  public void testCreatePost() throws Exception {
    Post post = new Post();
    post.setTitle("test");
    when(postService.createPost(any(Post.class))).thenReturn(post);

    mockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(post)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title", is(post.getTitle())));
  }
  @Test
  public void testGetPostById() throws Exception {
    Post post = new Post();
    post.setTitle("test");
    when(postService.getPostById(1L)).thenReturn(post);

    mockMvc.perform(get("/api/posts/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is(post.getTitle())));
  }

  @Test
  public void testDeletePost() throws Exception {
    doNothing().when(postService).deletePost(1L);

    mockMvc.perform(delete("/api/posts/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(postService, times(1)).deletePost(1L);
  }

  @Test
  public void testUpdatePost() throws Exception {
    Post post = new Post();
    post.setTitle("test");

    Post postUpdates = new Post();
    postUpdates.setTitle("newTest");

    when(postService.updatePost(1L, postUpdates)).thenReturn(post);

    mockMvc.perform(put("/api/posts/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postUpdates)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is(post.getTitle())));
  }

  @Test
  public void getAllPosts() throws Exception {
    Post post1 = new Post();
    post1.setTitle("Test Title 1");

    Post post2 = new Post();
    post2.setTitle("Test Title 2");

    List<Post> posts = Arrays.asList(post1, post2);
    when(postService.getAllPosts()).thenReturn(posts);

    mockMvc.perform(get("/api/posts"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].title", is(post1.getTitle())))
        .andExpect(jsonPath("$[1].title", is(post2.getTitle())));

    verify(postService, times(1)).getAllPosts();
  }

  @Test
  public void getPostById_NotFound() throws Exception {
    mockMvc.perform(get("/posts/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void updatePost_NotFound() throws Exception {
    Post updatePost = new Post();
    updatePost.setTitle("Test");

    mockMvc.perform(put("/posts/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(updatePost)))
        .andExpect(status().isNotFound());
  }
}
