import redis.clients.jedis.Jedis;

public interface Cache {
    public String get(String key);

    public void set(String key, String value);
}


class RedisCache implements Cache {

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
    public String get(String key) {
        String cachedValue = this.db.get(key);
        return cachedValue;
    }

    @Override
    public void set(String key, String value) {
        this.db.set(key, value);
    }
}