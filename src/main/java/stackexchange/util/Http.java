package stackexchange.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


interface RequestSender {
    HttpResponse get(String url, Map<String, String> params) throws IOException, InterruptedException, URISyntaxException;
}

public final class Http implements RequestSender {
    private static Logger logger = LoggerFactory.getLogger(Http.class);

    private HttpClient client;

    public Http() {
        this.client = HttpClient.newHttpClient();
    }

    public static String buildUri(String url, Map<String, String> params) {
        String uri = url + "?";
        int i = 0;
        int mapSize = params.size();

        // Manually construct uri with query params
        for (var entry : params.entrySet()) {
            uri += (entry.getKey() + "=" + entry.getValue().replaceAll("\\s+", "+"));
            if (i < mapSize - 1) {
                uri += "&";
            }
            i++;
        }

        return uri;
    }


    private HttpRequest buildRequest(String url, Map<String, String> params) throws URISyntaxException {
        String uri = this.buildUri(url, params);
        logger.info(String.format("Making request to URI: %s", uri));

        HttpRequest request = HttpRequest.newBuilder().uri(new URI(uri)).GET().build();

        return request;
    }


    private HttpResponse sendRequest(HttpRequest request, HttpResponse.BodyHandler handler) throws IOException, InterruptedException {
        HttpResponse response = this.client.send(request, handler);
        return response;
    }


    public HttpResponse get(String url, Map<String, String> params) {
        HttpResponse response = null;
        try {
            HttpRequest request = this.buildRequest(url, params);
            response = this.sendRequest(request, HttpResponse.BodyHandlers.ofInputStream());
        } catch (Exception e) {
            logger.error("Failed to make request");
            logger.error(e.getMessage());
            System.exit(1);
        }


        return response;
    }
}