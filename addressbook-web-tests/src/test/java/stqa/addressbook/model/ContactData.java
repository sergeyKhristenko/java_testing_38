package stqa.addressbook.model;

public class ContactData {
  private final String id;
  private final String name;
  private final String lastName;
  private final String address;
  private final String homePhone;
  private final String email;
  private String group;

  public ContactData(String name, String lastName, String address, String homePhone,
                     String email, String group) {
    this.id = null;
    this.name = name;
    this.lastName = lastName;
    this.address = address;
    this.homePhone = homePhone;
    this.email = email;
    this.group = group;
  }

  public ContactData(String id, String name, String lastName, String address, String homePhone,
                     String email, String group) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.address = address;
    this.homePhone = homePhone;
    this.email = email;
    this.group = group;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
    if (address != null ? !address.equals(that.address) : that.address != null) return false;
    if (homePhone != null ? !homePhone.equals(that.homePhone) : that.homePhone != null) return false;
    return email != null ? email.equals(that.email) : that.email == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (homePhone != null ? homePhone.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }

  public String getId() {

    return id;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", lastName='" + lastName + '\'' +
            ", address='" + address + '\'' +
            ", homePhone='" + homePhone + '\'' +
            ", email='" + email + '\'' +
            ", group='" + group + '\'' +
            '}';
  }


  public String getLastName() {
    return lastName;
  }

  public String getAddress() {
    return address;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public String getEmail() {
    return email;
  }

  public String getGroup() {
    return group;
  }
}
