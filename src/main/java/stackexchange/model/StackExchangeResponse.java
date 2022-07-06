package model;

import java.util.List;

public class StackExchangeResponse {
    public List<Item> items;
    public int quota_remaining;
    public int quota_max;
    public int backoff;
    public boolean has_more;
}
