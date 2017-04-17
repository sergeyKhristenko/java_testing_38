package stqa.mantis.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.mantis.model.BugifyIssue;

import java.io.IOException;

public class BugifyRestTest extends TestBase {
  private int issueId;

  @BeforeMethod
  public void preConditionSteps() throws IOException {
    BugifyIssue newIssue = new BugifyIssue().withSubject("Test Issue").withDescription("New Issue");
    this.issueId = app.rest().createIssue(newIssue);
  }

  @Test
  public void testBugReportResolvedIssue() throws IOException {
    skipIfNotFixedBugify(this.issueId);
    System.out.println("Bugify issue resolved, let's test it!");
  }

}
