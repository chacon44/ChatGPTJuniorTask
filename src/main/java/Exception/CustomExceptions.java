package Exception;

public class CustomExceptions {

  public static class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String message) {
      super(message);
    }
  }

  public static class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
      super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
  }
}
