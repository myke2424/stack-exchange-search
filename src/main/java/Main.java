
public class Main {

    public static void main(String[] args) {
        var config = Config.load("config.yaml");

        var arguments = Commands.parseArgs(args);

        SearchRequest request = new SearchRequest.Builder("Merge two dictionaries").site(
                "stackoverflow").accepted().build();

        StackExchange stackExchange = new StackExchange();
        var sr = stackExchange.search("Recursion limit", "stackoverflow", 10);
        System.out.println("YO");

    }
}
