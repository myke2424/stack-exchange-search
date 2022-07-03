import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
        Search search = new Search.Builder("Merge two dictionaries")
                .site("stackoverflow")
                .accepted()
                .build();

        StackExchange stackExchange = new StackExchange();
        stackExchange.search("Recursion limit");
    }
}
