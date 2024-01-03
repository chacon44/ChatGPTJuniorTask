package ControllerTest;

import Controller.FollowController;
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
public class FollowControllerTest {

  @InjectMocks
  private FollowController followerController;

  @Mock
  private UserService userService;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(followerController).build();
  }

  @Test
  public void testFollowUser() throws Exception {
    doNothing().when(userService).followUser(1L, 1L);

    mockMvc.perform(post("/api/follower/1/1"))
        .andExpect(status().isCreated());

    verify(userService, times(1)).followUser(1L, 1L);
  }

  @Test
  public void testUnfollowUser() throws Exception {
    doNothing().when(userService).unfollowUser(1L, 1L);

    mockMvc.perform(delete("/api/follower/1/1"))
        .andExpect(status().isOk());

    verify(userService, times(1)).unfollowUser(1L, 1L);
  }
}
