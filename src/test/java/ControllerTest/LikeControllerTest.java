package ControllerTest;

import Controller.LikeController;
import org.example.Application;
import Services.UserService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class LikeControllerTest {

  @InjectMocks
  private LikeController likeController;

  @Mock
  private UserService userService;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();
  }

  @Test
  public void testLikePost() throws Exception {
    doNothing().when(userService).likePost(1L, 1L);

    mockMvc.perform(post("/api/like/1/1"))
        .andExpect(status().isCreated());

    verify(userService, times(1)).likePost(1L, 1L);
  }

  @Test
  public void testUnlikePost() throws Exception {
    doNothing().when(userService).unlikePost(1L, 1L);

    mockMvc.perform(delete("/api/like/1/1"))
        .andExpect(status().isOk());

    verify(userService, times(1)).unlikePost(1L, 1L);
  }
}
