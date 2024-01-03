package ControllerTest;

import Controller.UserController;
import Model.User;
import static org.hamcrest.Matchers.is;
import Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;
  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void testGetAllUsers() throws Exception {
    User user1 = new User();
    user1.setUsername("test1");
    User user2 = new User();
    user2.setUsername("test2");
    List<User> userList = Arrays.asList(user1, user2);
    when(userService.getAllUsers()).thenReturn(userList);

    mockMvc.perform(get("/api/users")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(userList.size())));
  }

  @Test
  public void testGetUserById() throws Exception {
    User user = new User();
    user.setUsername("test");
    when(userService.getUserById(1L)).thenReturn(user);

    mockMvc.perform(get("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is(user.getUsername())));
  }

  @Test
  public void testCreateUser() throws Exception {
    User user = new User();
    user.setUsername("test");
    when(userService.createUser(any(User.class))).thenReturn(user);

    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username", is(user.getUsername())));
  }
  @Test
  public void testDeleteUserById() throws Exception {
    doNothing().when(userService).deleteUserById(1L);

    mockMvc.perform(delete("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(userService, times(1)).deleteUserById(1L);
  }

  @Test
  public void testUpdateUser() throws Exception {
    User user = new User();
    user.setUsername("test");

    User userUpdates = new User();
    userUpdates.setUsername("newTest");

    when(userService.updateUser(1L, userUpdates)).thenReturn(user);

    mockMvc.perform(put("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdates)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is(user.getUsername())));
  }
}
