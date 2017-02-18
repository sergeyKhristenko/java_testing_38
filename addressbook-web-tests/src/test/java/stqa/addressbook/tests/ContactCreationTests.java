package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {

        app.addContact();
        app.fillContactForm(new ContactData("Test Name", "Middle Name", "Last Name", "Nickname", "Title", "Company", "Address", "Home Phone", "Mobile", "Email"));
        app.saveContact();
    }

}
