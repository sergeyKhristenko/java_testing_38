package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {

    app.goTo().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Test Name", "Last Name",
              "Address", "555555", "Email", "test1"));

      app.goTo().gotoHomePage();
    }

    List<ContactData> before = app.getContactHelper().getContactList();

    //select the last contact from the list
    app.getContactHelper().selectContact(before.size() - 1);

    app.getContactHelper().deleteSelectedContact();
    app.goTo().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();

    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(before.size() - 1);

    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
