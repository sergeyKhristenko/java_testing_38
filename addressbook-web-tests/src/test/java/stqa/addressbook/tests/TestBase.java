package stqa.addressbook.tests;


import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import stqa.addressbook.appManager.ApplicationManager;

public class TestBase {

  protected static final ApplicationManager app =
          new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeTest
  public void setUp() throws Exception {
    app.init();
  }

  @AfterTest
  public void tearDown() {
    app.stop();
  }

}
