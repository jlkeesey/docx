package docx;

/**
 * Thrown when the DOCX file cannot be read.
 */
public class DocxReadException extends RuntimeException {
  public DocxReadException() {
    super();
  }

  public DocxReadException(String message) {
    super(message);
  }

  public DocxReadException(String message, Throwable cause) {
    super(message, cause);
  }

  public DocxReadException(Throwable cause) {
    super(cause);
  }
}
