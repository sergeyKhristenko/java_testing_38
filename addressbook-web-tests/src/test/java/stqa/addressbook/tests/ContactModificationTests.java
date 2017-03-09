package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {

    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Test Name", "Middle Name",
              "Last Name", "Nickname", "Title", "Company",
              "Address", "Home Phone", "Mobile", "Email", "test1"));

      app.getNavigationHelper().gotoHomePage();
    }

    List<ContactData> before = app.getContactHelper().getContactList();

    //choose tha last contact
    app.getContactHelper().initContactModification(before.size() - 1);

    app.getContactHelper().fillContactForm(new ContactData("Test Name", "Middle Name",
            "Last Name", "Nickname", "Title", "Company", "Address",
            "Home Phone", "Mobile", "Email", null), false);
    app.getContactHelper().updateContact();
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    Assert.assertEquals(after, before);
  }
}
