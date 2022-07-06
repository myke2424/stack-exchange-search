package model;

public class Answer extends SearchResultItem {
    public boolean is_accepted;


    public Answer(Item item) {
        super(item.body, item.score, item.creation_date);
        this.is_accepted = item.is_accepted;
    }
}