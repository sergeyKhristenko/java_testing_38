package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {

    app.goTo().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Test Name",
              "Last Name", "Address", "555555", "Email", "test1"));

      app.goTo().gotoHomePage();
    }

    List<ContactData> before = app.getContactHelper().getContactList();

    //choose tha last contact
    app.getContactHelper().initContactModification(before.size() - 1);

    ContactData contact = new ContactData(before.get(before.size() -1).getId(),"Test Name",
            "Last Name", "Address", "555555", "Email", "test1");

    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().updateContact();
    app.goTo().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(new HashSet<Object>(after), new HashSet<Object>(before));
  }
}
