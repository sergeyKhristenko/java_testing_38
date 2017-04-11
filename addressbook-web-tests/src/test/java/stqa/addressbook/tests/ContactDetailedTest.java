package stqa.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDetailedTest extends TestBase {
  @BeforeMethod
  public void checkPreconditions() {

    if(app.db().contacts().size() == 0){
      app.goTo().homePage();
      app.contact().createContact(new ContactData().withName("Test Name")
              .withLastName("Last Name")
              .withAddress("Address")
              .withHomePhone("555555").withMobilePhone("+7 (123) 23-23-232").withWorkPhone("234(234)")
              .withEmail("test@email.address").withEmail2("test+2@email.address").withEmail3("test+3@email.address")
              .withGroup("Test Group"));
    }
  }

  @Test
  public void TestContactDetailed() {
    ContactData contact = app.contact().all().iterator().next();
    ContactData editPage = app.contact().infoFromEditForm(contact);
    ContactData detailedPage = app.contact().infoFromDetailedPage(contact);

    app.goTo().homePage();

    //assert names
    assertThat(mergeNames(editPage.getFirstName(), editPage.getLastName()),
            equalTo(detailedPage.getConcatNames()));
    //assert phones
    assertThat(editPage.getHomePhone(), equalTo(detailedPage.getHomePhone()));
    assertThat(editPage.getMobilePhone(), equalTo(detailedPage.getMobilePhone()));
    assertThat(editPage.getWorkPhone(), equalTo(detailedPage.getWorkPhone()));
    //assert emails
    assertThat(contact.getAllEmails(), equalTo(detailedPage.getAllEmails()));
  }

  public String mergeNames(String... names) {
    return Stream.of(names).collect(Collectors.joining(" "));
  }

}
