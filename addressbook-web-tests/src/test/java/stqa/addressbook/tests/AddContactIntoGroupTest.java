package stqa.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactIntoGroupTest extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    //this will create a group if there is no any
    if (app.db().groups().size() == 0) {
      GroupData group = new GroupData().withName("AddContactIntoGroupTest");
      app.goTo().groupPage();
      app.group().create(group);
    }
    //this will create a contact if there is no any
    if (app.db().contacts().size() == 0) {
      ContactData contact = new ContactData().withName("contact name");
      app.goTo().homePage();
      app.contact().createContact(contact);
    }
  }

  @Test
  public void testAddContactIntoGroup() {
    //get random contact
    ContactData contactBefore = app.db().contacts().iterator().next();
    GroupData group = app.db().groups().iterator().next();

    app.goTo().homePage();
    app.contact().addContactIntoGroup(contactBefore, group);

    //get contact from the database by id
    ContactData contactAfter = app.db().contacts().stream() //get all contacts
            .filter(c -> c.getId() == contactBefore.getId()) //filter by id
            .collect(Collectors.toList())
            .iterator().next(); //there must be only one contact, because id's are unique

    //hasItem because the contact could be included into more then one group
    assertThat(contactAfter.getGroups(), hasItem(group));
  }

  @Test
  public void testCreateContactAndAddItIntoGroup() {
    GroupData group = app.db().groups().iterator().next();
    //create a contact and put it into the group
    ContactData contact = new ContactData().withName("Added To Group " + group.getName()).inGroup(group);

    app.goTo().homePage();
    app.contact().createContact(contact);

    //these contact can't has more then one group
    assertThat(contact.getGroups().iterator().next(), equalTo(group));
  }
}
