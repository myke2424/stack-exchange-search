package stackexchange;

import stackexchange.model.SearchResult;

import java.net.URISyntaxException;
import java.util.List;

public interface Searchable {
    List<SearchResult> search(SearchRequest request);
}
