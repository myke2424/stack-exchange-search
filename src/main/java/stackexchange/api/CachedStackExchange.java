package stackexchange.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stackexchange.util.Http;
import stackexchange.db.Cache;
import stackexchange.model.SearchRequest;
import stackexchange.model.SearchResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Cache proxy object to set and get search results for faster look up time.
 */
public final class CachedStackExchange implements Searchable {
    private static final Logger logger = LoggerFactory.getLogger(CachedStackExchange.class);

    private final Cache cache;
    private final StackExchange service;

    public CachedStackExchange(Cache cache, StackExchange service) {
        this.cache = cache;
        this.service = service;
    }


    @Override
    public List<SearchResult> search(SearchRequest request) {
        Gson gson = new Gson();
        String requestUri = Http.buildUri(this.service.getUrl(),
                request.toJsonMap());

        Optional<String> cachedSearchResultsJson = this.cache.get(requestUri);
        if (cachedSearchResultsJson.isPresent()) {
            logger.info("Cache hit... returning cached results");
            logger.debug("Cached search results: \n" + cachedSearchResultsJson.get());

            // Deserialize json encoded string into a List of Search Results
            Type listType = new TypeToken<ArrayList<SearchResult>>() {
            }.getType();
            List<SearchResult> searchResults =
                    gson.fromJson(cachedSearchResultsJson.get(), listType);

            return searchResults;
        }

        logger.info("Caching request URI: " + requestUri);
        List<SearchResult> searchResults = this.service.search(request);
        String searchResultsJson = gson.toJson(searchResults);

        // Cache request URI -> search results
        this.cache.set(requestUri, searchResultsJson);

        return searchResults;
    }
}