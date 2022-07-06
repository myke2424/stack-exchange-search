import com.google.gson.Gson;
import model.Answer;
import model.Question;
import model.SearchResult;
import model.StackExchangeResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;


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


    /**
     * Decompress GZIP Http Response
     * All Stack Exchange HTTP Responses are compressed with gzip
     * Read more here: https://api.stackexchange.com/docs/compression
     *
     * @param response
     * @return
     * @throws IOException
     */
    private String decompressGzip(HttpResponse<InputStream> response) throws IOException {
        var gzip = new GZIPInputStream(response.body());


        // Read gzip response stream into buffer
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int length;
        while ((length = gzip.read(buffer)) > 0) {
            outStream.write(buffer, 0, length);

        }

        String bytes = new String(outStream.toByteArray(), "UTF-8");
        return bytes;
    }

    private StackExchangeResponse getRequest(String url, Map<String, String> params) {
        HttpResponse response = this.http.get(url, params);
        String decompressedResponse = "";
        // TODO: Fix decompress
        try {
            decompressedResponse = this.decompressGzip(response);
        } catch (IOException e) {
            System.out.println("FAILED TO DECOMPRESS RESPONSE... exiting");
            System.exit(1);
        }

        //String decompressedResponse = this.decompressGzip(response);
        StackExchangeResponse stackExchangeResponse =
                new Gson().fromJson(decompressedResponse,
                        StackExchangeResponse.class);

        return stackExchangeResponse;
    }

    private StackExchangeResponse getSearchAdvanced(Map<String, String> params) {
        StackExchangeResponse searchResponse = this.getRequest(this.getSearchUrl(), params);
        return searchResponse;
    }

    private StackExchangeResponse getAnswers(List<String> ids, Map<String, String> params) {
        String commaDelimitedIds = String.join(";", ids);
        String url = this.getAnswersUrl() + "/" + commaDelimitedIds;
        StackExchangeResponse answersResponse = this.getRequest(url, params);

        return answersResponse;
    }

    private List<Answer> getAcceptedAnswers(List<String> ids, String site) {
        Map<String, String> params = new HashMap<String, String>() {
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

    private List<Question> getQuestions(Map<String, String> searchParams, int num) {
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
        Map<String, String> searchParams = new HashMap<String, String>() {{
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

    @Override
    public List<SearchResult> search(String query, String site, int num) {
        return null;
    }


}





