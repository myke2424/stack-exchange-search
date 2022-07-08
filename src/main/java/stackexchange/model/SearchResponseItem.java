package stackexchange.model;

/** Model representation of the items in the raw response from a GET Request to /search/advanced */
public class SearchResponseItem {
  public boolean is_accepted;
  public String body;
  public int score;
  public int creation_date;
  public String accepted_answer_id;
  public int question_id;
  public String title;
  public String link;

  @Override
  public String toString() {
    return "SearchResponseItem{"
        + "is_accepted="
        + is_accepted
        + ", body='"
        + body
        + '\''
        + ", score="
        + score
        + ", creation_date="
        + creation_date
        + ", accepted_answer_id='"
        + accepted_answer_id
        + '\''
        + ", question_id="
        + question_id
        + ", title='"
        + title
        + '\''
        + ", link='"
        + link
        + '\''
        + '}';
  }
}
