package stackexchange.api;

import stackexchange.model.SearchRequest;
import stackexchange.model.SearchResult;

import java.util.List;

public interface Searchable {
  List<SearchResult> search(SearchRequest request);
}
