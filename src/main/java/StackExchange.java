import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;


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

    // TODO: Should the Http client be behind an interface... and injected in constructor?
    private HttpRequest getRequest(String url, HashMap<String, String> queryParams) {
        String uri = url + "?";
        int i = 0;
        int mapSize = queryParams.size();

        // Manually construct uri with query params...
        for (var entry : queryParams.entrySet()) {
            uri += (entry.getKey() + "=" + entry.getValue().replaceAll("\\s+", ""));
            if (i < mapSize - 1) {
                uri += "&";
            }
            i++;
        }

        System.out.println("URI: " + uri);

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException("Invalid Request...");
        }


        return request;

    }

    public void testRequest(String query) {
        var params = new HashMap<String, String>();
        params.put("q", query);
        params.put("accepted", "true");
        params.put("site", "stackoverflow");
        this.getRequest(this.searchUrl, params);
    }
}
