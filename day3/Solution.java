import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
  File file;
  List<String> lines;

  public Solution() {
    file = new File("input.txt");
    lines = new ArrayList<>();
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        lines.add(sc.nextLine());
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  String getNumberOnLeft(int lineIndex, int idx) {
    String line = lines.get(lineIndex);
    StringBuilder num = new StringBuilder();
    for (int i = idx - 1; i >= 0; i--) {
      if (Character.isDigit(line.charAt(i))) {
        num.insert(0, line.charAt(i));
      } else {
        break;
      }
    }
    return num.toString();
  }

  String getNumberOnRight(int lineIndex, int idx) {
    String line = lines.get(lineIndex);
    StringBuilder num = new StringBuilder();
    for (int i = idx + 1; i < line.length(); i++) {
      if (Character.isDigit(line.charAt(i))) {
        num.append(line.charAt(i));
      } else {
        break;
      }
    }
    return num.toString();
  }

  String[] getNumberOnTop(int lineIndex, int idx) {
    if (lineIndex <= -1) {
      return new String[]{""};
    }
    String line = lines.get(lineIndex);
    if (!Character.isDigit(line.charAt(idx))) {
      String a = getNumberOnLeft(lineIndex, idx);
      String b = getNumberOnRight(lineIndex, idx);
      return new String[]{a, b};
    }

    StringBuilder num = new StringBuilder();
    for (int i = idx; i <= line.length(); i++) {
      if (Character.isDigit(line.charAt(i))) {
        num.append(line.charAt(i));
      } else {
        break;
      }
    }

    for (int i = idx - 1; i >= 0; i--) {
      if (Character.isDigit(line.charAt(i))) {
        num.insert(0, line.charAt(i));
      } else {
        break;
      }
    }

    return new String[]{num.toString()};
  }

  String[] getNumberOnBottom(int lineIndex, int idx) {
    if (lineIndex >= lines.size()) {
      return new String[]{""};
    }
    String line = lines.get(lineIndex);
    if (!Character.isDigit(line.charAt(idx))) {
      String a = getNumberOnLeft(lineIndex, idx);
      String b = getNumberOnRight(lineIndex, idx);
      return new String[]{a, b};
    }

    StringBuilder num = new StringBuilder();
    for (int i = idx; i <= line.length(); i++) {
      if (Character.isDigit(line.charAt(i))) {
        num.append(line.charAt(i));
      } else {
        break;
      }
    }

    for (int i = idx - 1; i >= 0; i--) {
      if (Character.isDigit(line.charAt(i))) {
        num.insert(0, line.charAt(i));
      } else {
        break;
      }
    }

    return new String[]{num.toString()};
  }

  int start() {
    int ans = 0;
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      int idx = line.indexOf("*");
      while (idx != -1) {
        List<Integer> numbers = new ArrayList<>();

        String a = getNumberOnLeft(i, idx);
        if (!a.equals("")) {
          numbers.add(Integer.parseInt(a));
        }

        String b = getNumberOnRight(i, idx);
        if (!b.equals("")) {
          numbers.add(Integer.parseInt(b));
        }

        String[] c = getNumberOnTop(i - 1, idx);
        for (String x : c) {
          if (!x.equals("")) {
            numbers.add(Integer.parseInt(x));
          }
        }

        String[] d = getNumberOnBottom(i + 1, idx);
        for (String x : d) {
          if (!x.equals("")) {
            numbers.add(Integer.parseInt(x));
          }
        }

        if (numbers.size() == 2) {
          ans += numbers.get(0) * numbers.get(1);
        }

        idx = line.indexOf("*", idx + 1);
      }
    }
    return ans;
  }

  public static void main(String[] args) {
    Solution ob = new Solution();
    System.out.println(ob.start());
  }
}