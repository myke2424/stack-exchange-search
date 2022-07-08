package stackexchange.exception;

import java.io.IOException;

/**
 * Throw when Gzip decompression fails.
 */
public class GzipDecompressException extends IOException {
  public GzipDecompressException() {}

  public GzipDecompressException(String message) {
    super(message);
  }
}
