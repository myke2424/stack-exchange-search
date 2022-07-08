package stackexchange.exception;

public class ZeroSearchResultException extends Exception {
  public ZeroSearchResultException() {}

  public ZeroSearchResultException(String message) {
    super(message);
  }
}
