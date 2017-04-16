package stqa.mantis.tests;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.mantis.appmanager.HttpSession;
import stqa.mantis.model.MailMessage;
import stqa.mantis.model.UserData;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangeUsersPasswordTest extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    app.mail().start(); //start mail server

    //create a user if there is no anyone
    if (app.db().users().size() == 0) {
      String email = "user@localhost";
      String password = "password";
      String username = "Test User";

      app.registration().start(username, email);
      List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);

      String confirmationLink = app.registration().findConfirmationLink(mailMessages, email);
      app.registration().finish(confirmationLink, password);

      app.navigationHelper().homePage();
    }

    app.navigationHelper().login(); //log in as admin
  }

  @Test
  public void testChangeUserPassword() throws IOException {
    UserData user = app.db().users().iterator().next();     //get a user from database
    String newPassword = "12345";
    //go to edit user page
    app.navigationHelper().goTo(String.format("manage_user_edit_page.php?user_id=%s", user.getId()));

    //click on reset password btn
    app.getDriver().findElement(By.xpath("//*[*[@name='manage_user_reset_token']]//*[@type='submit']"))
            .click();

    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = app.registration().findConfirmationLink(mailMessages, user.getEmail());

    app.registration().finish(confirmationLink, newPassword);

    HttpSession session = app.newSession();
    session.login(user.getUsername(), newPassword);
    assertTrue(session.isLoggedInAs(user.getUsername()));
  }


  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
