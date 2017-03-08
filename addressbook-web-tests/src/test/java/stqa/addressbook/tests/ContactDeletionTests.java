package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {

    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Test Name", "Middle Name",
              "Last Name", "Nickname", "Title", "Company",
              "Address", "Home Phone", "Mobile", "Email", "test1"));

      app.getNavigationHelper().gotoHomePage();
    }

    int before = app.getContactHelper().getContactCount();

    //select the last contact from the list
    app.getContactHelper().selectContact(before - 1);

    app.getContactHelper().deleteSelectedContact();
    app.getNavigationHelper().gotoHomePage();

    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before - 1);
  }

}
