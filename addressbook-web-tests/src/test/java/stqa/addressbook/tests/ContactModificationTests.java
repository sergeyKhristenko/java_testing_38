package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification() {

    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Test Name", "Middle Name", "Last Name", "Nickname", "Title", "Company", "Address", "Home Phone", "Mobile", "Email"));
    app.getContactHelper().updateContact();
  }
}
