package stackexchange.model;

/** Base class with common fields stack exchange entities can inherit */
public class StackExchangeEntity {
  public String body;
  public int score;
  public int creation_date;

  public StackExchangeEntity(String body, int score, int creation_date) {
    this.body = body;
    this.score = score;
    this.creation_date = creation_date;
  }

  @Override
  public String toString() {
    return "SearchResultItem{"
        + "body='"
        + body
        + '\''
        + ", score="
        + score
        + ", creation_date="
        + creation_date
        + '}';
  }
}
