package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {

        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().addContact();
        app.getContactHelper().fillContactForm(new ContactData("Test Name", "Middle Name",
                "Last Name", "Nickname", "Title", "Company",
                "Address", "Home Phone", "Mobile", "Email", "test1"),
                true);
        app.getContactHelper().saveContact();
    }

}
