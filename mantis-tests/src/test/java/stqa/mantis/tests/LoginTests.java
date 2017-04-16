package stqa.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase {

  @Test
  public void testLogin() throws IOException {
    HttpSession session = app.newSession();
    app.getDriver().get(app.getProperty("web.baseUrl"));
    assertTrue(session.login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword")));
    assertTrue(session.isLoggedInAs("administrator"));
  }
}
