package stqa.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.mantis.model.Issue;
import stqa.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTests extends TestBase {

  @Test
  public void testGetProjects() throws RemoteException, ServiceException, MalformedURLException {
    Set<Project> projects = app.soap().getProjects();

    System.out.println("projects.length = " + projects.size());
    for (Project project : projects) {
      System.out.println("Project: " + project.getName());
    }
  }

  @Test
  public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
    Set<Project> projects = app.soap().getProjects();
    Project project = projects.iterator().next();

    Issue issue = new Issue().withSummary("Test Issue")
            .withDescription("Test Description")
            .withProject(project);

    Issue createdIssue = app.soap().addIssue(issue);

    Assert.assertEquals(issue.getSummary(), createdIssue.getSummary());
  }
}
