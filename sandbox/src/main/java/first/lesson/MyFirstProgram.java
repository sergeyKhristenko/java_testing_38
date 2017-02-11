package first.lesson;


public class MyFirstProgram {

  public static void main(String[] args) {

    Point p1 = new Point(-4, 0);
    Point p2 = new Point(100, 100);

    System.out.println(Point.distance(p1, p2));

  }

}