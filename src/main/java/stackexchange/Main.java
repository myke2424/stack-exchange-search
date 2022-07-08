package stackexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stackexchange.api.CachedStackExchange;
import stackexchange.api.Searchable;
import stackexchange.api.StackExchange;
import stackexchange.config.Config;
import stackexchange.db.RedisCache;
import stackexchange.model.SearchRequest;
import stackexchange.model.SearchResult;
import stackexchange.util.CommandParser;

import java.io.FileNotFoundException;
import java.util.List;


// TODO Create exceptions folder
// TODO: Refactor Main logic
// TODO: Test tags functionality, not sure if working correctly
// TODO: stack exchange requires tags to be semi colon delimited
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String CONFIG_FILE_PATH = "config.yaml";
    private static final int N_RESULTS = 10; // TODO: Add cmd arg for this?

    public static void main(String[] args) throws FileNotFoundException {
        var args_ = CommandParser.parseArgs(args);
        var query = args_.getString("query");
        var site = args_.getString("site");
        var tags = args_.getString("tags");
        var interactiveMode = args_.getBoolean("interactive");
        Config config = Config.fromYaml(CONFIG_FILE_PATH);

        RedisCache redis = null;
        if (config.redis.host != null && config.redis.port != null && config.redis.host != null) {
            redis = new RedisCache(config.redis.host, config.redis.port,
                    config.redis.password);
        }

        Searchable stackExchange;

        if (redis != null) {
            stackExchange = new CachedStackExchange(redis, new StackExchange());
        } else {
            stackExchange = new StackExchange();
        }

        SearchRequest request = new SearchRequest.Builder(query)
                .site(site)
                .tags(tags)
                .accepted()
                .num(N_RESULTS)
                .build();

        List<SearchResult> searchResults = stackExchange.search(request);

        if (interactiveMode) {
            System.out.println("Implement Interactive Search");
        } else {
            // Fast search, get the top result
            // TODO: Raise exception if no search RESULT!?
            SearchResult topResult = searchResults.get(0);

            System.out.println(String.format("***QUESTION***: %s \n",
                    topResult.question.title));
            System.out.println(topResult.question.body + "\n");


            System.out.println("***ANSWER*** \n \n" + topResult.answer.body);
        }


        System.out.println("Done");


    }

    public static void run() {


    }
}
