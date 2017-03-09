package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {

    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Test Name",
              "Last Name", "Address", "555555", "Email", "test1"));

      app.getNavigationHelper().gotoHomePage();
    }

    List<ContactData> before = app.getContactHelper().getContactList();

    //choose tha last contact
    app.getContactHelper().initContactModification(before.size() - 1);

    ContactData contact = new ContactData(before.get(before.size() -1).getId(),"Test Name",
            "Last Name", "Address", "555555", "Email", "test1");

    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().updateContact();
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(contact);

    Assert.assertEquals(new HashSet<Object>(after), new HashSet<Object>(before));
  }
}
