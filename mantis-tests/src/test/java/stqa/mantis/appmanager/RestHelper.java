package stqa.mantis.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import stqa.mantis.model.BugifyIssue;

import java.io.IOException;
import java.util.Set;

public class RestHelper {
  private ApplicationManager app;

  public RestHelper(ApplicationManager app) {
    this.app = app;
  }


  public int createIssue(BugifyIssue newIssue) throws IOException {
    String json = getExecutor().execute(Request.Post("http://demo.bugify.com/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject())
                    , new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();

    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }


  public void resolveIssueRestAssured(int issueId) {
    RestAssured.given()
            .parameter("method", "update")
            .parameter("issue[state]", "2")
            .post(String.format("http://demo.bugify.com/api/issues/%d.json", issueId));
  }


  //I don't know why it's doesn't work
  public void resolveIssue(int issueId) throws IOException {
    getExecutor().execute(Request.Post(String.format("http://demo.bugify.com/api/issues/%d.json", issueId))
            .bodyForm(new BasicNameValuePair("method", "update"))
            .bodyForm(new BasicNameValuePair("issue[state]", "2")))
            .returnContent();
  }

  public Set<BugifyIssue> getIssues() throws IOException {
    String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json")).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");

    return new Gson().fromJson(issues, new TypeToken<Set<BugifyIssue>>() {
    }.getType());
  }

  public Set<BugifyIssue> getIssue(int issueId) throws IOException {
    String json = getExecutor().execute(Request.Get(String.format("http://demo.bugify.com/api/issues/%s.json", issueId))).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issue = parsed.getAsJsonObject().get("issues");

    return new Gson().fromJson(issue, new TypeToken<Set<BugifyIssue>>() {
    }.getType());
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==", "");
  }
}
