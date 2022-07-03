import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class StackExchange implements Searchable {
    private static final String SEARCH_ENDPOINT = "/search/advanced";
    private static final String ANSWERS_ENDPOINT = "/answers";

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
        String url = this.getAnswersUrl() + "/" + commaDelimitedIds;
        StackExchangeResponse answersResponse = this.getRequest(url, params);

        return answersResponse;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public String getAnswersUrl() {
        return answersUrl;
    }

    // TODO: Should this take the search builder? Abstraction needs work.
    @Override
    public void search(String query) {
        var searchParams = new HashMap<String, String>() {{
            put("site", "stackoverflow");
            put("accepted", "True");
            put("q", query);
            put("filter", "withbody");
        }};

        StackExchangeResponse questions = this.getSearchAdvanced(searchParams);
        List<String> acceptedAnswerIds = questions.items
                .stream()
                .map(q -> Integer.toString(q.accepted_answer_id))
                .collect(Collectors.toList());

        var answerParams = new HashMap<String, String>() {{
            put("site", "stackoverflow");
            put("filter", "withbody");
        }};

        StackExchangeResponse answers = this.getAnswers(acceptedAnswerIds, answerParams);
        System.out.println("DEBUG");
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
    public String body;
    public int score;
    public int last_activity_date;
    public int creation_date;
    public int accepted_answer_id;
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
