package first.lesson;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceTest {

  @Test
  public void testDistance() {
    Point a = new Point(-4, 0);
    Point b = new Point(100, 100);

    Assert.assertEquals(a.distance(b), 144.27751037497146);

  }
}
