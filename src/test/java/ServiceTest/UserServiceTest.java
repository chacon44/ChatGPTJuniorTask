package ServiceTest;

import Model.Post;
import Model.User;
import Repositories.PostRepository;
import Repositories.UserRepository;
import Services.UserServiceImpl;
import org.example.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static Exception.CustomExceptions.*;
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
  @Mock
  private PostRepository postRepository;

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
    assertThrows(IllegalArgumentException.class, () -> userService.createUser(null));
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

  @Test
  public void testGetUserById_UserExists() {
    User user = new User();
    user.setId(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    User found = userService.getUserById(1L);

    assertNotNull(found);
    assertEquals(1L, found.getId());
  }

  @Test
  public void testGetUserById_UserDoesNotExist() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> {
      userService.getUserById(1L); // This should throw an exception.
    });
  }

  @Test
  public void testFollowUser_UsersExist() {
    User user = new User();
    user.setId(1L);
    User userToFollow = new User();
    userToFollow.setId(2L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.findById(2L)).thenReturn(Optional.of(userToFollow));

    userService.followUser(1L, 2L);

    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void testFollowUser_UserToFollowDoesNotExist() {
    User user = new User();
    user.setId(1L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.findById(2L)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.followUser(1L, 2L));
  }

  @Test
  public void testFollowUser_UserDoesNotExist() {
    User userToFollow = new User();
    userToFollow.setId(2L);

    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.followUser(1L, 2L));
  }

  @Test
  public void testFollowUser_SameUser() {
    User user = new User();
    user.setId(1L);

    // You'll need to define how you want to handle this case in your service implementation.
    // If you're throwing an IllegalArgumentException, for example, you could test it like this:
    assertThrows(IllegalArgumentException.class, () -> userService.followUser(1L, 1L));
  }


  @Test
  public void testUnfollowUser_UsersExist() {
    User user = new User();
    user.setId(1L);
    User userToUnfollow = new User();
    userToUnfollow.setId(2L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.findById(2L)).thenReturn(Optional.of(userToUnfollow));

    userService.unfollowUser(1L, 2L);

    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void testUnfollowUser_UserToUnfollowDoesNotExist() {
    User user = new User();
    user.setId(1L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.findById(2L)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.unfollowUser(1L, 2L));
  }

  @Test
  public void testUnfollowUser_UserDoesNotExist() {
    User userToUnfollow = new User();
    userToUnfollow.setId(2L);

    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.unfollowUser(1L, 2L));
  }

  @Test
  public void testUnfollowUser_SameUser() {
    User user = new User();
    user.setId(1L);

    // You'll need to define how you want to handle this case in your service implementation.
    // If you're throwing an IllegalArgumentException, for example, you could test it like this:
    assertThrows(IllegalArgumentException.class, () -> userService.unfollowUser(1L, 1L));
  }

  @Test
  public void testLikePost_UserAndPostExist() {
    User user = new User();
    user.setId(1L);
    Post post = new Post();
    post.setId(1L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(postRepository.findById(1L)).thenReturn(Optional.of(post));

    userService.likePost(1L, 1L);

    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void testLikePost_PostDoesNotExist() {
    User user = new User();
    user.setId(1L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(postRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> userService.likePost(1L, 1L));
  }

  @Test
  public void testLikePost_UserDoesNotExist() {
    Post post = new Post();
    post.setId(1L);

    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.likePost(1L, 1L));
  }

  @Test
  public void testUnlikePost_UserAndPostExist() {
    User user = new User();
    user.setId(1L);
    Post post = new Post();
    post.setId(1L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(postRepository.findById(1L)).thenReturn(Optional.of(post));

    userService.unlikePost(1L, 1L);

    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void testUnlikePost_PostDoesNotExist() {
    User user = new User();
    user.setId(1L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(postRepository.findById(1L)).thenReturn(Optional.empty());

    //Assuming ResourceNotFoundException is thrown when post doesn't exist
    assertThrows(ResourceNotFoundException.class, () -> userService.unlikePost(1L, 1L));
  }

  @Test
  public void testUnlikePost_UserDoesNotExist() {
    Post post = new Post();
    post.setId(1L);

    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    //Assuming UsernameNotFoundException is thrown when user doesn't exist
    assertThrows(UsernameNotFoundException.class, () -> userService.unlikePost(1L, 1L));
  }
  @Test
  public void testDeleteUserById_UserExists() {
    when(userRepository.existsById(1L)).thenReturn(true);

    userService.deleteUserById(1L);

    verify(userRepository, times(1)).deleteById(1L);
  }

  @Test
  public void testDeleteUserById_UserDoesNotExist() {
    when(userRepository.existsById(1L)).thenReturn(false);

    // Assuming UsernameNotFoundException is thrown when user doesn't exist
    assertThrows(UsernameNotFoundException.class, () -> userService.deleteUserById(1L));
  }

  @Test
  public void testUpdateUser_UserExists() {
    User user = new User();
    user.setId(1L);

    User userUpdates = new User();
    userUpdates.setId(1L);
    userUpdates.setUsername("newUsername");

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(userUpdates);

    User updatedUser = userService.updateUser(1L, userUpdates);

    assertNotNull(updatedUser);
    assertEquals(userUpdates.getUsername(), updatedUser.getUsername());
  }

  @Test
  public void testUpdateUser_UserDoesNotExist() {
    User userUpdates = new User();
    userUpdates.setId(1L);
    userUpdates.setUsername("newUsername");

    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.updateUser(1L, userUpdates));
  }

}
