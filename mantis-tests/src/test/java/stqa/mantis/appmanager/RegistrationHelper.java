package stqa.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import stqa.mantis.model.MailMessage;

import java.util.List;

public class RegistrationHelper extends BaseHelper {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.cssSelector("input[type='submit']"));

  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("button[type='submit']"));
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    //this will use the last email to avoid possible coincidence
    MailMessage mailMessage = mailMessages.stream().filter(m -> m.to.equals(email))
            .reduce((a, b) -> b).get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();

    return regex.getText(mailMessage.text);
  }
}
