import com.google.gson.Gson;

import java.util.ArrayList;
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

    public String getSearchUrl() {
        return searchUrl;
    }

    public String getAnswersUrl() {
        return answersUrl;
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

    private List<Answer> getAcceptedAnswers(List<String> ids, String site) {
        var params = new HashMap<String, String>() {
            {
                put("site", site);
                put("filter", "withbody");
            }
        };

        StackExchangeResponse answersResponse = this.getAnswers(ids, params);
        List<Answer> answers = answersResponse.items
                .stream()
                .map(item -> new Answer(item))
                .collect(Collectors.toList());

        return answers;

    }

    private List<Question> getQuestions(HashMap<String, String> searchParams, int num) {
        StackExchangeResponse searchResponse = this.getSearchAdvanced(searchParams);

        List<Question> questions = searchResponse.items
                .stream()
                .map(item -> new Question(item))
                .collect(Collectors.toList());
        return questions;
    }

    // TODO: Should this take the search builder? Abstraction needs work.
    @Override
    public List<SearchResult> search(String query, String site, int num) {
        var searchParams = new HashMap<String, String>() {{
            put("site", site);
            put("accepted", "True");
            put("q", query);
            put("filter", "withbody");
        }};

        List<Question> questions = this.getQuestions(searchParams, num);
        List<String> acceptedAnswerIds = questions
                .stream()
                .map(q -> q.accepted_answer_id)
                .collect(Collectors.toList());
        List<Answer> answers = this.getAcceptedAnswers(acceptedAnswerIds, site);

        List<SearchResult> searchResults = new ArrayList<>();

        for (int i = 0; i < answers.size(); i++) {
            searchResults.add(new SearchResult(questions.get(i), answers.get(i)));
        }

        return searchResults;
    }


}

class CachedStackExchange implements Searchable {
    //  Proxy structural design pattern. Use a cache as a proxy object to set and get search results for faster look up time.
    private final Cache cache;
    private final StackExchange service;

    public CachedStackExchange(Cache cache, StackExchange service) {
        this.cache = cache;
        this.service = service;
    }

    // Search interface will be the same as the service interface
    @Override
    public List<SearchResult> search(String query, String site, int num) {
        return null;
    }
}

// TODO: Move data classes into a model directory!?
// TODO: Move stack exchange class to API dir?
// TODO: Exclude fields in API response i don't need, i.e. owner, content_lincense etc.
// TODO: Replace the classs with Records, records are analgous to data classes.


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
    public String accepted_answer_id;
    public int question_id;
    public String title;
    public String link;
    public String content_license;
}


class StackExchangeResponse {
    public List<Item> items;
    public int quota_remaining;
    public int quota_max;
    public int backoff;
    public boolean has_more;
}


class SearchResultItem {
    public String body;
    public int score;
    public String creation_date;

    public SearchResultItem(String body, int score, int creation_date) {
    }
}

class Question extends SearchResultItem {
    public String title;
    public String link;
    public String accepted_answer_id;

    public Question(Item item) {
        super(item.body, item.score, item.creation_date);
        this.title = item.title;
        this.link = item.link;
        this.accepted_answer_id = item.accepted_answer_id;
    }
}

class Answer extends SearchResultItem {
    public boolean is_accepted;


    public Answer(Item item) {
        super(item.body, item.score, item.creation_date);
        this.is_accepted = item.is_accepted;
    }
}


class SearchResult {
    public Question question;
    public Answer answer;

    public SearchResult(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }


}