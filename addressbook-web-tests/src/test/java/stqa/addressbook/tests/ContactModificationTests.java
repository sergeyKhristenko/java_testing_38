package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification() {

    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Test Name", "Middle Name",
              "Last Name", "Nickname", "Title", "Company",
              "Address", "Home Phone", "Mobile", "Email", "test1"));

      app.getNavigationHelper().gotoHomePage();
    }
    int before = app.getContactHelper().getContactCount();

    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Test Name", "Middle Name",
            "Last Name", "Nickname", "Title", "Company", "Address",
            "Home Phone", "Mobile", "Email", null), false);
    app.getContactHelper().updateContact();
    app.getNavigationHelper().gotoHomePage();

    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before);
  }
}
