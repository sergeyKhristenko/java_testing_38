package stqa.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {
  @BeforeMethod
  public void checkPreconditions() {

    if(app.db().contacts().size() == 0){
      app.goTo().homePage();

      app.contact().createContact(new ContactData().withName("Test Name")
              .withLastName("Last Name")
              .withAddress("Address")
              .withHomePhone("555555").withMobilePhone("+7 (123) 23-23-232").withWorkPhone("234(234)")
              .withEmail("test@email.address").withEmail2("test+2@email.address").withEmail3("test+3@email.address"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withName("Test Name")
            .withLastName("Last Name")
            .withAddress("Address")
            .withHomePhone("555555").withMobilePhone("+7 (123) 23-23-232").withWorkPhone("234(234)")
            .withEmail("test@email.address").withEmail2("test+2@email.address").withEmail3("test+3@email.address");

    app.goTo().homePage();
    app.contact().modify(contact);
    app.goTo().homePage();
    Contacts after = app.db().contacts();

    assertThat(app.contact().count(), equalTo(before.size()));
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));

    verifyContactListInUI();
  }
}
