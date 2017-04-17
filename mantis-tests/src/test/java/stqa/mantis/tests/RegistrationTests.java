package stqa.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import stqa.mantis.model.MailMessage;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

  //  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException {
    long random = Math.round(Math.random() * 1000);
    String email = "user" + random + "@localhost";
    String user = "Test User " + random;
    String password = "password";

    app.james().createUser(user, password);
    app.registration().start(user, email);
//    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);

    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);
    String confirmationLink = app.registration().findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);

    assertTrue(app.newSession().login(user, password));
  }

  //  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
