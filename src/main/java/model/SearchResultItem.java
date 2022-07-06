package model;

public class SearchResultItem {
    public String body;
    public int score;
    public int creation_date;

    public SearchResultItem(String body, int score, int creation_date) {
        this.body = body;
        this.score = score;
        this.creation_date = creation_date;
    }
}
