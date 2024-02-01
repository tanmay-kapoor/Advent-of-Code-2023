import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

class Solution {
  public static void main(String[] args) {
    File file = new File("input.txt");
    int ans = 0;
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] vals = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
        };
        ans += Integer.parseInt(getNumber(line, vals));
      }
      System.out.println(ans);
      sc.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  static String getNumber(String line, String[] vals) {
    Temp[] fi = new Temp[vals.length];
    Temp[] li = new Temp[vals.length];
    for (int i = 0; i < vals.length; i++) {
      fi[i] = new Temp(vals[i], line.indexOf(vals[i]));
      li[i] = new Temp(vals[i], line.lastIndexOf(vals[i]));
    }
    Arrays.sort(fi, (a, b) -> a.idx - b.idx);
    Arrays.sort(li, (a, b) -> a.idx - b.idx);
    String num1 = "", num2 = "";

    for (int i = 0; i < fi.length; i++) {
      if (fi[i].idx == -1) continue;
      num1 = getNumber(fi[i].val);
      break;
    }

    num2 = getNumber(li[li.length - 1].val);

    return num1 + num2;
  }

  static String getNumber(String val) {
    switch (val) {
      case "0":
      case "zero":
        return "0";
      case "1":
      case "one":
        return "1";
      case "2":
      case "two":
        return "2";
      case "3":
      case "three":
        return "3";
      case "4":
      case "four":
        return "4";
      case "5":
      case "five":
        return "5";
      case "6":
      case "six":
        return "6";
      case "7":
      case "seven":
        return "7";
      case "8":
      case "eight":
        return "8";
      case "9":
      case "nine":
        return "9";
    }
    return "";
  }

  static char getSecondDigit(String line, String[] vals) {
    return '0';
  }

  static class Temp {
    String val;
    int idx;

    public Temp(String val, int idx) {
      this.val = val;
      this.idx = idx;
    }
  }
}