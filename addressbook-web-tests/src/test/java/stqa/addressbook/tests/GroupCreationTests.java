package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    GroupData group = new GroupData().withName("test3");

    app.goTo().groupPage();
    Groups before = app.group().all();

    app.group().create(group);
    Groups after = app.group().all();

    assertThat(app.group().count(), equalTo(before.size() + 1));

    assertThat(after, equalTo(before
            .withAdded(group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadGroupCreation() {
    GroupData group = new GroupData().withName("test3'");

    app.goTo().groupPage();
    Groups before = app.group().all();

    app.group().create(group);
    Groups after = app.group().all();

    assertThat(app.group().count(), equalTo(before.size()));

    assertThat(after, equalTo(before
            .withAdded(group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
  }
}
