package stqa.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import stqa.mantis.tests.TestBase;

public class RegistrationHelper {
  private final ApplicationManager app;
  private WebDriver wd;

  public RegistrationHelper(ApplicationManager app) {
    this.app = app;
    wd = app.getDriver();
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    wd.findElement(By.name("username")).sendKeys(username);
    wd.findElement(By.name("email")).sendKeys(email);
    wd.findElement(By.cssSelector("input[type='submit']")).click();

  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    wd.findElement(By.name("password")).sendKeys(password);
    wd.findElement(By.name("password_confirm")).sendKeys(password);
    wd.findElement(By.cssSelector("button[type='submit']")).click();
  }
}
