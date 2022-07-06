package stackexchange;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SearchRequest {
    // required parameters
    private final String query;

    // Optional parameters with defaults
    private final List<String> tags;
    private final int num;
    private final String site;
    private final String accepted;
    private final String filter;

    private SearchRequest(Builder builder) {
        this.query = builder.query;
        this.tags = builder.tags;
        this.num = builder.num;
        this.site = builder.site;
        this.accepted = builder.accepted;
        this.filter = builder.filter;
    }

    public Map<String, String> toJsonMap() {
        Map<String, String> requestMap = new LinkedHashMap<>() {{
            put("q", query);
            put("site", site);
            put("accepted", accepted);
            put("filter", filter);
        }};

        // remove nulls
        while (requestMap.values().remove(null)) ;

        return requestMap;
    }

    public String getQuery() {
        return query;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getNum() {
        return num;
    }

    public String getSite() {
        return site;
    }

    public String getAccepted() {
        return accepted;
    }

    public String getFilter() {
        return filter;
    }


    @Override
    public String toString() {
        return "SearchRequest{" +
                "query='" + query + '\'' +
                ", tags=" + tags +
                ", num=" + num +
                ", site='" + site + '\'' +
                ", accepted='" + accepted + '\'' +
                ", filter='" + filter + '\'' +
                '}';
    }

    public static class Builder {
        private final String query;

        private List<String> tags;
        private int num;
        private String site;
        private String accepted;
        private String filter;

        public Builder(String query) {
            this.query = query;
            this.filter = "withbody"; // Default filter to include response text body
        }


        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder num(Integer n) {
            this.num = n;
            return this;
        }

        public Builder site(String site) {
            this.site = site;
            return this;
        }

        public Builder accepted() {
            this.accepted = "True";
            return this;
        }

        public Builder filter(String filter) {
            this.filter = filter;
            return this;
        }

        public SearchRequest build() {
            return new SearchRequest(this);
        }

    }
}
