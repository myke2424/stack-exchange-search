package stackexchange.model;

/** Model representation of a question and answer on a stack exchange thread */
public class SearchResult {
  public Question question;
  public Answer answer;

  public SearchResult(Question question, Answer answer) {
    this.question = question;
    this.answer = answer;
  }

  @Override
  public String toString() {
    return "SearchResult{" + "question=" + question + ", answer=" + answer + '}';
  }
}
