package stackexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stackexchange.api.CachedStackExchange;
import stackexchange.api.Searchable;
import stackexchange.api.StackExchange;
import stackexchange.config.Config;
import stackexchange.db.RedisCache;
import stackexchange.exception.ZeroSearchResultException;
import stackexchange.model.SearchRequest;
import stackexchange.model.SearchResult;
import stackexchange.util.CommandParser;
import stackexchange.util.HtmlToMarkdown;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  private static final String CONFIG_FILE_PATH = "config.yaml";
  private static final int N_RESULTS = 1;

  public static void main(String[] args) {
    try {
      run(args);
    } catch (Exception e) {
      logger.error(e.getMessage());
      System.exit(1);
    }
  }

  private static void run(String[] args) throws FileNotFoundException, ZeroSearchResultException {
    Config config = Config.fromYaml(CONFIG_FILE_PATH);
    Searchable stackExchange = getStackExchange(config);
    SearchRequest request = buildRequest(args);
    List<SearchResult> searchResults = stackExchange.search(request);

    if (searchResults.isEmpty()) {
      logger.info("No search results found for request: " + request);
      throw new ZeroSearchResultException("No search result found");
    }

    SearchResult topResult = searchResults.get(0);
    printResult(topResult);
  }

  private static void printResult(SearchResult result) {
    System.out.println(String.format("***Question Title***: %s \n", result.question.title));
    System.out.println("***Question Body*** \n");
    System.out.println(HtmlToMarkdown.parse(result.question.body) + "\n");
    System.out.println("***Answer Body*** \n \n" + HtmlToMarkdown.parse(result.answer.body));
  }

  private static SearchRequest buildRequest(String[] args) {
    var args_ = CommandParser.parseArgs(args);
    var query = args_.getString("query");
    var site = args_.getString("site");
    var tags = args_.getString("tags");

    SearchRequest request =
        new SearchRequest.Builder(query).site(site).tags(tags).accepted().num(N_RESULTS).build();
    return request;
  }

  private static Searchable getStackExchange(Config config) {
    // If redis configuration is set in config.yaml, cache search requests with proxy obj
    if (config.redis.host != null && config.redis.port != null && config.redis.host != null) {
      RedisCache redis =
          new RedisCache(config.redis.host, config.redis.port, config.redis.password);
      return new CachedStackExchange(redis, new StackExchange());
    }
    return new StackExchange();
  }
}
