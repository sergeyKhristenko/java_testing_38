package stqa.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    if(app.db().contacts().size() == 0){
      app.goTo().homePage();
      app.contact().createContact(new ContactData().withName("Test Name").withLastName("Last Name")
              .withHomePhone("555555").withAddress("Address").withEmail("Email").withGroup("Test Group"));
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();

    app.goTo().homePage();

    app.contact().delete(deletedContact);
    app.goTo().homePage();
    Contacts after = app.db().contacts();

    assertThat(app.contact().count(), equalTo(before.size() - 1));
    assertThat(after, equalTo(before.without(deletedContact)));

    verifyContactListInUI();
  }
}
