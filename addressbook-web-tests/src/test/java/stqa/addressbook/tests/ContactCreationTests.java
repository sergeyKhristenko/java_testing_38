package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    ContactData contact = new ContactData().withName("Test Name").withLastName("Last Name").withAddress("Address")
            .withHomePhone("555555").withMobilePhone("+7 (123) 23-23-232").withWorkPhone("234(234)")
            .withEmail("Email").withGroup("Test Group");

    app.goTo().homePage();
    Contacts before = app.contact().all();

    app.contact().createContact(contact);
    app.goTo().homePage();
    Contacts after = app.contact().all();

    assertThat(app.contact().count(), equalTo(before.size() + 1));
    assertThat(after, equalTo(before.withAdded(contact
            .withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}
