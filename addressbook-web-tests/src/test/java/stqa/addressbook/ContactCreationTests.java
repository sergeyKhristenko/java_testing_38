package stqa.addressbook;

import org.testng.annotations.Test;
import org.openqa.selenium.*;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {

        addContact();
        fillContactForm(new ContactData("Test Name", "Middle Name", "Last Name", "Nickname", "Title", "Company", "Address", "Home Phone", "Mobile", "Email"));
        saveContact();
    }

}
