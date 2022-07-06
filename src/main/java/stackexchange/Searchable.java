import model.SearchResult;
import java.util.List;

public interface Searchable {
    List<SearchResult> search(String query, String site, int num);
}
