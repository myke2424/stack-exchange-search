package model;

public class Question extends SearchResultItem {
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