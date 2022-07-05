import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            var config = Config.load("configz.yaml");
        } catch (FileNotFoundException e) {
            LOGGER.info(e.getMessage());
            System.exit(1);
        }


        var arguments = Commands.parseArgs(args);

        SearchRequest request = new SearchRequest.Builder("Merge two dictionaries").site(
                "stackoverflow").accepted().build();

        StackExchange stackExchange = new StackExchange();
        var sr = stackExchange.search("Recursion limit", "stackoverflow", 10);
        System.out.println("YO");

    }
}
