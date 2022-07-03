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
    private final Http http;

    public StackExchange(String version) {
        this.version = version;
        this.url = "https://api.stackexchange.com/" + version;
        this.searchUrl = url + SEARCH_ENDPOINT;
        this.answersUrl = url + ANSWERS_ENDPOINT;
        this.http = new Http(); // IDK about this as an instance variable..
    }

    public StackExchange() {
        this("2.3");
    }

    private StackExchangeResponse getRequest(String url, HashMap<String, String> params) {
        String response = this.http.get(url, params);
        StackExchangeResponse stackExchangeResponse = new Gson().fromJson(response,
                StackExchangeResponse.class);

        return stackExchangeResponse;
    }

    private StackExchangeResponse getSearchAdvanced(HashMap<String, String> params) {
        StackExchangeResponse searchResponse = this.getRequest(this.getSearchUrl(), params);
        return searchResponse;
    }

    private StackExchangeResponse getAnswers(List<String> ids, HashMap<String, String> params) {
        String commaDelimitedIds = String.join(";", ids);
        StackExchangeResponse answersResponse = this.getRequest(this.getAnswersUrl(),
                params);

        return answersResponse;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public String getAnswersUrl() {
        return answersUrl;
    }

    @Override
    public void search(String query) {

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


class StackExchangeResponse {
    public List<Item> items;
    public int quota_remaining;
    public int quota_max;
    public int backoff;
    public boolean has_more;
}
