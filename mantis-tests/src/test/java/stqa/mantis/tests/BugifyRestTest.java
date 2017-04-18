package stqa.mantis.tests;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.mantis.model.BugifyIssue;

import java.io.IOException;

public class BugifyRestTest extends TestBase {
  private int openedIssue;
  private int resolvedIssue;

  @BeforeClass
  public void init() {
    RestAssured.authentication = RestAssured.basic("LSGjeU4yP1X493ud1hNniA==", "");
  }

  @BeforeMethod
  public void preConditionSteps() throws IOException {
    BugifyIssue closedIssue = new BugifyIssue().withSubject("Test Issue").withDescription("New Issue");
    this.resolvedIssue = app.rest().createIssue(closedIssue);

    BugifyIssue openIssue = new BugifyIssue().withSubject("Test Issue").withDescription("New Issue");
    this.openedIssue = app.rest().createIssue(openIssue);

    app.rest().resolveIssueRestAssured(resolvedIssue);
  }

  @Test
  public void testBugReportOpenedIssue() throws IOException {
    skipIfNotFixedBugify(this.openedIssue);
    System.out.println("This will be skipped");
  }

  @Test
  public void testBugReportResolvedIssue() throws IOException {
    skipIfNotFixedBugify(this.resolvedIssue);
    System.out.println("Bugify issue resolved, let's test it!");
  }
}
