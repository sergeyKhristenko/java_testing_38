package stqa.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    if(app.db().groups().size() == 0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1").withFooter("test3").withHeader("test3"));
    }
  }

  @Test
  public void testGroupDeletion() {
    Groups before = app.db().groups();
    GroupData deletedGroup = before.iterator().next();

    app.goTo().groupPage();

    app.group().delete(deletedGroup);
    Groups after = app.db().groups();

    assertThat(app.group().count(), equalTo(before.size() - 1));
    assertThat(after, equalTo(before.without(deletedGroup)));
    verifyGroupListInUI();
  }

}
