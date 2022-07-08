package stackexchange.model;

/**
 * Model representation of a Stack Exchange Answer
 */
public class Answer extends StackExchangeEntity {
    public boolean is_accepted;


    public Answer(SearchResponseItem item) {
        super(item.body, item.score, item.creation_date);
        this.is_accepted = item.is_accepted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "is_accepted=" + is_accepted +
                ", body='" + body + '\'' +
                ", score=" + score +
                ", creation_date=" + creation_date +
                '}';
    }
}