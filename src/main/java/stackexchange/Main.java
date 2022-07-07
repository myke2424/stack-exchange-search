package stackexchange;

import stackexchange.model.SearchResult;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

// TODO Create exceptions folder
// TODO: Refactor config into object, instead of map.
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String CONFIG_FILE_PATH = "config.yaml";

    public static void main(String[] args) throws FileNotFoundException {
        var args_ = CommandParser.parseArgs(args);
        var query = args_.getString("query");
        var site = args_.getString("site");
        var interactiveMode = args_.getBoolean("interactive");
        var config = Config.load(CONFIG_FILE_PATH);

        Map<String, Object> redisConfig = (Map<String, Object>) config.get("redis");
        Object host = redisConfig.get("host"), port = redisConfig.get("host"), password = redisConfig.get("password");

        RedisCache redis = null;
        if (host != null && port != null && password != null) {
            redis = new RedisCache((String) host, (int) port, (String) password);
        }

        Searchable stackExchange;

        if (redis != null) {
            stackExchange = new CachedStackExchange(redis, new StackExchange());
        } else {
            stackExchange = new StackExchange();
        }

        SearchRequest request = new SearchRequest.Builder(query)
                .site(site)
                .accepted()
                .num(10)
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
