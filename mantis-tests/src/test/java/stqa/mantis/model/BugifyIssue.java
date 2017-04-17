package stqa.mantis.model;

public class BugifyIssue {

  private int id;
  private String subject;
  private String description;
  private String state_name;

  public String getState_name() {
    return state_name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BugifyIssue that = (BugifyIssue) o;

    if (id != that.id) return false;
    if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
    if (description != null ? !description.equals(that.description) : that.description != null) return false;
    return state_name != null ? state_name.equals(that.state_name) : that.state_name == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (subject != null ? subject.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (state_name != null ? state_name.hashCode() : 0);
    return result;
  }

  public BugifyIssue withState_name(String state_name) {
    this.state_name = state_name;
    return this;

  }

  public int getId() {
    return id;
  }

  public BugifyIssue withId(int id) {
    this.id = id;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public BugifyIssue withSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public BugifyIssue withDescription(String description) {
    this.description = description;
    return this;
  }

}
