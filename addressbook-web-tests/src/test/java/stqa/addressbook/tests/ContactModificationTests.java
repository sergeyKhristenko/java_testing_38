package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactModificationTests extends TestBase {
  @BeforeMethod
  public void checkPreconditions() {
    app.goTo().homePage();

    if (app.contact().all().size() == 0) {
      app.contact().createContact(new ContactData().withName("Test Name").withLastName("Last Name")
              .withAddress("Address").withHomePhone("555555").withEmail("Email").withGroup("test1"));

      app.goTo().homePage();
    }
  }

  @Test
  public void testContactModification() {
    Set<ContactData> before = app.contact().all();
    ContactData modifiedGroup = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedGroup.getId()).withName("Test Name")
            .withLastName("Last Name").withAddress("Address").withHomePhone("555555").withEmail("Email")
            .withGroup("test1");

    app.contact().modify(contact);
    app.goTo().homePage();

    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedGroup);
    before.add(contact);

    Assert.assertEquals(after, before);
  }


}
