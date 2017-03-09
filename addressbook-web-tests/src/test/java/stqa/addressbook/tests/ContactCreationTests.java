package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {

    app.getNavigationHelper().gotoHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().addContact();
    app.getContactHelper().fillContactForm(new ContactData("Test Name", "Last Name",
                    "Address", "555555", "Email", "test1"),
            true);
    app.getContactHelper().saveContact();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(new ContactData("Test Name", "Last Name",
            "Address", "555555", "Email", "test1"));

    Assert.assertEquals(after, before);

  }

}
