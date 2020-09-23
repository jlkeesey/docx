package model;

public class GuideException extends RuntimeException {
  public GuideException() {
  }

  public GuideException(String message) {
    super(message);
  }

  public GuideException(String message, Throwable cause) {
    super(message, cause);
  }

  public GuideException(Throwable cause) {
    super(cause);
  }
}
