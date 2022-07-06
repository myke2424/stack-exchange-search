package stackexchange;

import stackexchange.model.SearchResult;

import java.util.List;
import java.util.logging.Logger;

// TODO Create exceptions folder

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        var args_ = CommandParser.parseArgs(args);
        var query = args_.getString("query");
        var site = args_.getString("site");
        var interactiveMode = args_.getBoolean("interactive");

        SearchRequest request = new SearchRequest.Builder(query)
                .site(site)
                .accepted()
                .num(10)
                .build();

        StackExchange stackExchange = new StackExchange();
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
