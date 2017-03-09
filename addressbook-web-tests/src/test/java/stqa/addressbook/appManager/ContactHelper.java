package stqa.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import stqa.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends BaseHelper {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void saveContact() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      try {
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      } catch (NoSuchElementException ex) {
        return;
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }

  }

  public void addContact() {
    click(By.linkText("add new"));
  }

  //will select contact by index
  public void selectContact(int index) {
    wd.findElements(By.xpath("//input[@type='checkbox']")).get(index).click();
  }

  public void deleteSelectedContact() {
    click(By.cssSelector("[value='Delete']"));
    wd.switchTo().alert().accept();
  }

  //choose contact by index
  public void initContactModification(int index) {
    wd.findElements(By.cssSelector("[title='Edit']")).get(index).click();
  }

  public void updateContact() {
    click(By.name("update"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void createContact(ContactData contact) {
    addContact();
    fillContactForm(contact, true);
    saveContact();
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("[name='entry']"));
    for (WebElement element : elements) {

      String name = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      String lastName = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String address = element.findElement(By.cssSelector("td:nth-child(4)")).getText();
      String email = element.findElement(By.cssSelector("td:nth-child(5)")).getText();
      String homePhone = element.findElement(By.cssSelector("td:nth-child(6)")).getText();

      ContactData contact = new ContactData(name, lastName, address,
              homePhone, email, null);
      contacts.add(contact);
    }
    return contacts;
  }
}
