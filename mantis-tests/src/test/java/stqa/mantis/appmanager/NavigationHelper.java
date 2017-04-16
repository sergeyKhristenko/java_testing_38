package stqa.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends BaseHelper {

  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

  public void goTo(String page) {
    wd.get(String.format("%s%s", app.getProperty("web.baseUrl"), page));
  }

  public void login(String username, String password) {
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.cssSelector("input[type='submit']"));
  }

  //admin login
  public void login() {
    type(By.name("username"), app.getProperty("web.adminLogin"));
    type(By.name("password"), app.getProperty("web.adminPassword"));
    click(By.cssSelector("input[type='submit']"));
  }

  public void homePage(){
    wd.get(app.getProperty("web.baseUrl"));
  }

}
