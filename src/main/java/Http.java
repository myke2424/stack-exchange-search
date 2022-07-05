import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

// TODO: Should the Http client be behind an interface... and injected in constructor?
// No need to hide this behind an interface, stdlib http client is good.
public class Http {
    private static final Logger LOGGER = Logger.getLogger(Http.class.getName());

    private HttpClient client;

    public Http() {
        this.client = HttpClient.newHttpClient();
    }

    // TODO: Should params be optional or generic hashmap/
    private String buildURI(String url, Map<String, String> params) {
        String uri = url + "?";
        int i = 0;
        int mapSize = params.size();

        // Manually construct uri with query params...
        for (var entry : params.entrySet()) {
            uri += (entry.getKey() + "=" + entry.getValue().replaceAll("\\s+", ""));
            if (i < mapSize - 1) {
                uri += "&";
            }
            i++;
        }

        return uri;
    }

    // Maybe add headers? We don't need them for stackexchange API however.
    private HttpRequest buildRequest(String url, Map<String, String> params) throws URISyntaxException {
        String uri = this.buildURI(url, params);
        LOGGER.info(String.format("Making request to URI: %s", uri));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .GET()
                .build();


        return request;
    }

    private String parseGzip(HttpResponse<InputStream> response) throws IOException {
        var gzip = new GZIPInputStream(response.body());


        // Read gzip into buffer
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int length;
        while ((length = gzip.read(buffer)) > 0) {
            outStream.write(buffer, 0, length);

        }

        String bytes = new String(outStream.toByteArray(), "UTF-8");
        return bytes;
    }


    private String sendRequest(HttpRequest request) throws IOException,
            InterruptedException {
        var response = this.client.send(request,
                HttpResponse.BodyHandlers.ofInputStream());

        // TODO: Might make sense to move parsing gzip into stack exchange class
        // Since gzip is an implementation detail for stack exchange.
        return this.parseGzip(response);

    }


    public String get(String url, Map<String, String> params) {
        String response = null;
        try {
            HttpRequest request = this.buildRequest(url, params);
            response = this.sendRequest(request);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Failed to make http request...");
            System.exit(0);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }
}