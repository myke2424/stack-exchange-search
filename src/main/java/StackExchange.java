import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;


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

    private StackResponse sendRequest(HttpRequest request) {
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<InputStream> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofInputStream());

            var gzipResponse = new GZIPInputStream(response.body());

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int length;
            while ((length = gzipResponse.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            String bytesResponse = new String(outStream.toByteArray(), "UTF-8");
            StackResponse stackResponse = new Gson().fromJson(bytesResponse,
                    StackResponse.class);

            return stackResponse;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;
    }

    public void testRequest(String query) {
        var params = new HashMap<String, String>();
        params.put("q", query);
        params.put("accepted", "true");
        params.put("site", "stackoverflow");
        var req = this.getRequest(this.searchUrl, params);
        var stackResp = sendRequest(req);
        System.out.println("TESTING");
    }
}

class ErrorResponse {
    public Integer error_id;
    public String error_message;
    public String error_name;
}


class Owner {
    public int account_id;
    public int reputation;
    public int user_id;
    public String user_type;
    public String profile_image;
    public String display_name;
    public String link;
}

class Item {
    public Owner owner;
    public boolean is_accepted;
    public int score;
    public int last_activity_date;
    public int creation_date;
    public int answer_id;
    public int question_id;
    public String content_license;
}


class StackResponse {
    public List<Item> items;
    public int quota_remaining;
    public int quota_max;
    public int backoff;
    public boolean has_more;
}
