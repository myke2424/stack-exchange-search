import java.util.List;

class Search {
    // required parameters
    private final String query;

    // Optional parameters with defaults
    private final List<String> tags;
    private final int num;
    private final String site;
    private final Boolean accepted;

    private Search(Builder builder) {
        this.query = builder.query;
        this.tags = builder.tags;
        this.num = builder.num;
        this.site = builder.site;
        this.accepted = builder.accepted;
    }


    // Creational Builder design pattern so the client code can build complex search
    // objects without having an influx of overloaded search constructors
    // Fluent api to construct a search object
    // * EXAMPLE *
    // Search search = new Search.Builder(query)
    //                         .site("softwareengineering")
    //                         .accepted()
    //                         .build();
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

        public Search build() {
            return new Search(this);
        }

    }
}
