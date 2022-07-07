package stackexchange.model;

public class Question extends SearchResultItem {
    public int id;
    public String title;
    public String link;
    public String accepted_answer_id;

    public Question(Item item) {
        super(item.body, item.score, item.creation_date);
        this.id = item.question_id;
        this.title = item.title;
        this.link = item.link;
        this.accepted_answer_id = item.accepted_answer_id;
    }


}