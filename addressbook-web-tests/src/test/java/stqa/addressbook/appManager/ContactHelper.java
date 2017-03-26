package stqa.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;
import stqa.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());

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

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[id='" + id + "']")).click();
  }

  public void deleteSelectedContact() {
    click(By.cssSelector("[value='Delete']"));
    wd.switchTo().alert().accept();
  }

  //choose contact by index
  public void initContactModification(int id) {
    wd.findElement(By.xpath(String.format("//tr[.//*[@id='%s']]//*[@title='Edit']", id))).click();
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
    contactCache = null;
  }

  public void modify(ContactData contact) {
    initContactModification(contact.getId());
    fillContactForm(contact, false);
    updateContact();
    contactCache = null;
  }

  public void delete(ContactData contact) {
    //select the last contact from the list
    selectContactById(contact.getId());
    deleteSelectedContact();
    contactCache = null;

  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("[name='entry']"));
    for (WebElement element : elements) {

      String name = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      String lastName = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String address = element.findElement(By.cssSelector("td:nth-child(4)")).getText();
      String email = element.findElement(By.cssSelector("td:nth-child(5)")).getText();

      //create an array with phone numbers - home phone, mobile, work
      String[] phones = element.findElement(By.cssSelector("td:nth-child(6)")).getText().split("\n");

      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contactCache.add(new ContactData().withId(id).withName(name).withLastName(lastName).withAddress(address)
              .withHomePhone(phones[0]).withMobilePhone(phones[1]).withWorkPhone(phones[2]).withEmail(email));

    }
    return contactCache;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModification(contact.getId());
    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
    String homePhone = wd.findElement(By.name("home")).getAttribute("value");
    String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
    String workPhone = wd.findElement(By.name("work")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withName(firstName).withLastName(lastName)
            .withHomePhone(homePhone).withMobilePhone(mobilePhone).withWorkPhone(workPhone);
  }


}
