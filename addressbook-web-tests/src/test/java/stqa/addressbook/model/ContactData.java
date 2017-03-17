package stqa.addressbook.model;

public class ContactData {
  private int id;
  private String name;
  private String lastName;
  private String address;
  private String homePhone;
  private String email;
  private String group;

  public ContactData(String name, String lastName, String address, String homePhone,
                     String email, String group) {
    this.id = 0;
    this.name = name;
    this.lastName = lastName;
    this.address = address;
    this.homePhone = homePhone;
    this.email = email;
    this.group = group;
  }
 
  public ContactData(int id, String name, String lastName, String address, String homePhone,
                     String email, String group) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.address = address;
    this.homePhone = homePhone;
    this.email = email;
    this.group = group;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    return result;
  }

  public String getName() {
    return name;
  }

  public int getId() {

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


  public void setId(int id) {
    this.id = id;
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
