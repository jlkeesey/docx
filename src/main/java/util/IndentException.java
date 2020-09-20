package util;

public class IndentException extends RuntimeException {
  public IndentException() {
  }

  public IndentException(String message) {
    super(message);
  }

  public IndentException(String message, Throwable cause) {
    super(message, cause);
  }

  public IndentException(Throwable cause) {
    super(cause);
  }
}
