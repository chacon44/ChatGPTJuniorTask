package ServiceTest;

import Model.User;
import Repositories.UserRepository;
import Services.UserServiceImpl;
import org.example.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)   // add your main Spring Boot class here
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  // Test creating a valid user.
  @Test
  public void testCreateUserValid() {
    User testUser = new User();
    testUser.setUsername("test");
    when(userRepository.save(any(User.class))).thenReturn(testUser);

    User result = userService.createUser(testUser);
    assertEquals(testUser.getUsername(), result.getUsername());

    verify(userRepository, times(1)).save(testUser);
  }

  // Test creating a user with null input.
  @Test
  public void testCreateUserNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      userService.createUser(null);
    });
    verify(userRepository, never()).save(any(User.class));
  }

  // Test getting all users when some users exist.
  @Test
  public void testGetAllUsersExist() {
    User user1 = new User();
    user1.setUsername("test1");

    User user2 = new User();
    user2.setUsername("test2");

    List<User> users = Arrays.asList(user1, user2);
    when(userRepository.findAll()).thenReturn(users);

    List<User> results = userService.getAllUsers();
    assertEquals(2, results.size());
    assertTrue(results.containsAll(users));

    verify(userRepository, times(1)).findAll();
  }

  // Test getting all users when there are no users.
  @Test
  public void testGetAllUsersEmpty() {
    when(userRepository.findAll()).thenReturn(Collections.emptyList());

    List<User> results = userService.getAllUsers();
    assertTrue(results.isEmpty());

    verify(userRepository, times(1)).findAll();
  }
}
