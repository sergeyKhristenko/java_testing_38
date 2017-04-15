package stqa.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;

import java.security.acl.Group;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;


public class RemoveContactFromGroupTest extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    GroupData group = new GroupData().withName("RemoveContactFromGroup");
    ContactData contact = new ContactData().withName("contact name");

    //this will create a group if there is no any
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(group);
    }
    //if no contacts, create a contact and put it to any existing group
    if (!app.db().contacts().stream().anyMatch(c->c.getGroups().size() > 0)) {
      app.goTo().homePage();
      app.contact().createContact(contact.inGroup(app.db().groups().iterator().next()));
    }
  }

  @Test
  public void testRemoveContactFromGroup() {
    //get contact included to some group
    ContactData contactBefore = app.db().contacts().stream()
            .filter(c -> c.getGroups().size() > 0)
            .collect(Collectors.toList()).iterator().next();
    //get a group that has the contact
    GroupData group = contactBefore.getGroups().iterator().next();

    app.goTo().homePage();
    app.contact().removeContactFromGroup(contactBefore, group);

    ContactData contactAfter = app.db().contacts().stream() //get all contacts
            .filter(c -> c.getId() == contactBefore.getId()) //filter by id
            .collect(Collectors.toList())
            .iterator().next(); //there must be only one contact, because id's are unique

    assertThat(contactAfter.getGroups(), equalTo(contactBefore.getGroups().without(group)));
  }
}
