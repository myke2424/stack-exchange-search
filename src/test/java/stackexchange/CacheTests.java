package stackexchange;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class CacheTests {

    private final TestCache cache = new TestCache();

    @Test
    public void cacheHit() {
        cache.set("APPLES", "ORANGES");
        assertEquals(cache.get("APPLES").get(), "ORANGES");
    }

    @Test
    public void overwriteCache() {
        cache.set("APPLES", "ORANGES");
        cache.set("APPLES", "BANANAS");
        assertEquals(cache.get("APPLES").get(), "BANANAS");
    }

    @Test
    public void cacheMiss() {
        Optional<String> value = cache.get("JAVA");
        assertEquals(value.isEmpty(), true);
    }
}


class TestCache implements Cache {
    /**
     * In memory hash-map to simulate a cache
     */
    private Map<String, String> map = new HashMap<>();


    @Override
    public Optional<String> get(String key) {
        Optional<String> opt = Optional.ofNullable(this.map.get(key));
        return opt;
    }

    @Override
    public void set(String key, String value) {
        this.map.put(key, value);
    }
}
