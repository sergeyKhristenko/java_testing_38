package first.lesson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

  public static void main(String[] args) {
    String[] langs = {"java", "php", "c", "python"};

    List<String> languages = Arrays.asList("java", "php", "c", "python");

    for (String l: languages) {
      System.out.println(l);
    }
  }
}
