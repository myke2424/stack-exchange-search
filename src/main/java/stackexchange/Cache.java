package stackexchange;

import java.util.Optional;

/**
 * An interface for a cache keyed by a String with encoded strings as data.
 */
public interface Cache {
    /**
     * Retrieves an entry from the cache.
     *
     * @param key Cache key
     * @return A String or null in the event of a cache miss
     */
    Optional<String> get(String key);

    /**
     * Adds or replaces an entry to the cache.
     *
     * @param key   Cache key
     * @param value Data to store in the cache, associated by the key
     */
    void set(String key, String value);
}

