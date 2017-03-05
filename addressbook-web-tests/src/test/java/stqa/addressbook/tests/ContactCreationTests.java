package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {

    app.getNavigationHelper().gotoHomePage();
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().addContact();
    app.getContactHelper().fillContactForm(new ContactData("Test Name", "Middle Name",
                    "Last Name", "Nickname", "Title", "Company",
                    "Address", "Home Phone", "Mobile", "Email", "test1"),
            true);
    app.getContactHelper().saveContact();
    app.getNavigationHelper().gotoHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before + 1);

  }

}
