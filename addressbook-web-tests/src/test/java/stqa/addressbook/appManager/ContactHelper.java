package stqa.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
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

      String allPhones = element.findElement(By.cssSelector("td:nth-child(6)")).getText();
      String allEmails = element.findElement(By.cssSelector("td:nth-child(5)")).getText();

      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contactCache.add(new ContactData().withId(id).withName(name).withLastName(lastName).withAddress(address)
              .withAllPhones(allPhones).withAllEmails(allEmails));

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
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();

    return new ContactData().withId(contact.getId()).withName(firstName).withLastName(lastName)
            .withHomePhone(homePhone).withMobilePhone(mobilePhone).withWorkPhone(workPhone)
            .withAddress(address).withEmail(email).withEmail2(email2).withEmail3(email3);
  }

  public ContactData infoFromDetailedPage(ContactData contact) {
    wd.findElement(By.xpath(String.format("//a[@*='view.php?id=%s']", contact.getId()))).click();

    //there is no way to get separated names from detailed page
    //so this returns all names in one string separated by space
    String contactName = wd.findElement(By.cssSelector("#content > b")).getText();

    String[] phones = wd.findElement(By.cssSelector("#content")).getText().split("\n");

    String allEmails = wd.findElements(By.cssSelector("#content > a"))
            .stream()
            .map(WebElement::getText) //get text from web elements
            .collect(Collectors.toList()) //create String[] with results
            .stream()
            .collect(Collectors.joining("\n")); //concat all result values to string with \n delimiter

    return new ContactData().withAllEmails(allEmails).withHomePhone(parsedPhone(phones, "H"))
            .withMobilePhone(parsedPhone(phones, "M")).withWorkPhone(parsedPhone(phones, "W"))
            .withConcatNames(contactName);
  }

  private String parsedPhone(String[] phones, String phoneType) {
    //3 options allowed for the phoneType: H (home phone), M (mobile phone), W (work phone)
    return Stream.of(phones).filter((s) -> s.matches(String.format("%s:.*", phoneType)))
            .collect(Collectors.joining("\n")).replaceAll(String.format("%s: ", phoneType), "");
  }


}
