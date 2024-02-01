import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  long start() {
    long ans = 0L;
    File file = new File("input.txt");
    try {
      Scanner sc = new Scanner(file);
      String nav = sc.nextLine();
      sc.nextLine();

      Map<String, String[]> map = new HashMap<>();
      List<String> list = new ArrayList<>();

      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String src = line.substring(0, 3);
        String left = line.substring(7, 10);
        String right = line.substring(12, 15);
        map.put(src, new String[]{left, right});
        if (src.charAt(2) == 'A') {
          list.add(src);
        }
      }

      long[] cnt = new long[list.size()];
      int total = 0;

      for (int i = 0; i <= nav.length(); i++) {
        i = i % nav.length();
        if (total == cnt.length) break;

        for (int j = 0; j < list.size(); j++) {
          String curr = list.get(j);
          if (curr.charAt(2) != 'Z') {
            char ch = nav.charAt(i);
            if (ch == 'L') {
              list.set(j, map.get(curr)[0]);
            } else {
              list.set(j, map.get(curr)[1]);
            }

            if (list.get(j).charAt(2) == 'Z') {
              cnt[j] = ans + 1;
              total++;
            }
          }
        }
        ans++;
      }

      return findLcmOfArray(cnt);

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    return ans;
  }

  long findLcmOfArray(long[] vals) {
    long lcm = vals[0];
    for (int i = 1; i < vals.length; i++) {
      lcm = findLcm(lcm, vals[i]);
    }
    return lcm;
  }

  long findLcm(long a, long b) {
    return (a * b) / findGcd(a, b);
  }

  long findGcd(long num1, long num2) {
    long rem = num1 % num2;
    while (rem != 0) {
      num1 = num2;
      num2 = rem;
      rem = num1 % num2;
    }
    return num2;
  }

  public static void main(String[] args) {
    Solution ob = new Solution();
    System.out.println(ob.start());
  }
}
