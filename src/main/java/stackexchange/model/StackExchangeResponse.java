package stackexchange.model;

import java.util.List;

/**
 * Model representation of the raw response from a GET Request to /search/advanced
 */
public class StackExchangeResponse {
    public List<SearchResponseItem> items;
    public int quota_remaining;
    public int quota_max;
    public int backoff;
    public boolean has_more;
}
