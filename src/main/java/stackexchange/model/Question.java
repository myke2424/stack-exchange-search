package stackexchange.model;

/**
 * Model representation of a Stack Exchange Question
 */
public class Question extends StackExchangeEntity {
    public int id;
    public String title;
    public String link;
    public String accepted_answer_id;

    public Question(SearchResponseItem item) {
        super(item.body, item.score, item.creation_date);
        this.id = item.question_id;
        this.title = item.title;
        this.link = item.link;
        this.accepted_answer_id = item.accepted_answer_id;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", accepted_answer_id='" + accepted_answer_id + '\'' +
                ", body='" + body + '\'' +
                ", score=" + score +
                ", creation_date=" + creation_date +
                '}';
    }
}