package stackexchange.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisAccessControlException;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Optional;

public class RedisCache implements Cache {
  private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

  private final Jedis db;

  public RedisCache(String host, int port, String password) {
    this.db = new Jedis(host, port);
    this.authCheck(password);
  }

  private void authCheck(String password) {
    try {
      this.db.auth(password);
    } catch (JedisConnectionException | JedisAccessControlException e) {
      // TODO: Raise custom exception?
      logger.error("Failed to connect to redis DB... Exiting");
      System.exit(1);
    }
  }

  @Override
  public Optional<String> get(String key) {
    logger.info("Reading cache - key: " + key);
    Optional<String> cachedValue = Optional.ofNullable(this.db.get(key));
    return cachedValue;
  }

  @Override
  public void set(String key, String value) {
    logger.info(String.format("Writing to cache - key: %s | value: %s", key, value));
    this.db.set(key, value);
  }
}
