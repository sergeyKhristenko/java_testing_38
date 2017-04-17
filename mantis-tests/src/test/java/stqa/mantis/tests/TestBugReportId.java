package stqa.mantis.tests;

import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestBugReportId extends TestBase {

  @Test
  public void testBugReportId() throws RemoteException, ServiceException, MalformedURLException {
    int issueId = 0000001;
    skipIfNotFixed(issueId);
    System.out.println("Issue resolved, let's test it!");
  }
}
