import java.util.List;

class SearchRequest {
    // required parameters
    private final String query;

    // Optional parameters with defaults
    private final List<String> tags;
    private final int num;
    private final String site;
    private final Boolean accepted;

    private SearchRequest(Builder builder) {
        this.query = builder.query;
        this.tags = builder.tags;
        this.num = builder.num;
        this.site = builder.site;
        this.accepted = builder.accepted;
    }

    public static class Builder {
        private final String query;

        private List<String> tags;
        private int num;
        private String site;
        private boolean accepted;

        public Builder(String query) {
            this.query = query;
            this.num = 1;
            this.site = "stackoverflow";
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
            this.accepted = true;
            return this;
        }

        public SearchRequest build() {
            return new SearchRequest(this);
        }

    }
}
