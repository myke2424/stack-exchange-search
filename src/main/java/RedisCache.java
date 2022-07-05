import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.logging.Logger;

class RedisCache implements Cache {
    private static final Logger LOGGER = Logger.getLogger(RedisCache.class.getName());

    private final String host;
    private final int port;
    private final String password;
    private final Jedis db;


    public RedisCache(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.db = new Jedis(host, port);
        this.db.auth(password);
    }


    @Override
    public Optional<String> get(String key) {
        LOGGER.info("Reading cache - key: " + key);
        Optional<String> cachedValue = Optional.ofNullable(this.db.get(key));
        return cachedValue;
    }

    @Override
    public void set(String key, String value) {
        LOGGER.info(String.format("Writing to cache - key: %s | value: %s", key,
                value));
        this.db.set(key, value);
    }
}