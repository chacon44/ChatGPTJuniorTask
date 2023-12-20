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

    mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(userList.size())));
  }

  @Test
  public void testGetUserById() throws Exception {
    User user = new User();
    user.setUsername("test");
    when(userService.getUserById(1L)).thenReturn(user);

    mockMvc.perform(get("/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is(user.getUsername())));
  }

  @Test
  public void testCreateUser() throws Exception {
    User user = new User();
    user.setUsername("test");
    when(userService.createUser(any(User.class))).thenReturn(user);

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is(Integer.parseInt(user.getUsername()))));
  }

  // Similar kind of tests for other methods, update and delete
}
