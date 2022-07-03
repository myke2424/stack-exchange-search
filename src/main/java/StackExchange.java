public class StackExchange implements Searchable {
    private static final String SEARCH_ENDPOINT = "/search/advanced";
    private static final String ANSWERS_ENDPOINT = "/answers/endpoint";

    private final String version;
    private final String url;
    private final String searchUrl;
    private final String answersUrl;

    public StackExchange(String version) {
        this.version = version;
        this.url = "https://api.stackexchange.com/" + version;
        this.searchUrl = url + SEARCH_ENDPOINT;
        this.answersUrl = url + ANSWERS_ENDPOINT;
    }

    public StackExchange() {
        this("2.3");
    }

    @Override
    public void search(String query) {

    }
}
