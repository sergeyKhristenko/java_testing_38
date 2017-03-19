package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

import java.util.Set;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();

    if (app.contact().all().size() == 0) {
      app.contact().createContact(new ContactData().withName("Test Name").withLastName("Last Name")
              .withHomePhone("555555").withAddress("Address").withEmail("Email").withGroup("test1"));

      app.goTo().homePage();
    }
  }

  @Test
  public void testContactDeletion() {
    Set<ContactData> before = app.contact().all();
    ContactData deletedContact = before.iterator().next();

    app.contact().delete(deletedContact);
    app.goTo().homePage();
    Set<ContactData> after = app.contact().all();

    Assert.assertEquals(after.size(), before.size() - 1); //check contacts length
    before.remove(deletedContact);
    Assert.assertEquals(before, after); //check that contacts are equal by names
  }


}
