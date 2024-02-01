import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
  long start() {
    File file = new File("sample.txt");
    long ans = 1L;

    try {
      Scanner sc = new Scanner(file);
      String[] t = sc.nextLine().split(":")[1].trim().split("\\s+");
      String[] d = sc.nextLine().split(":")[1].trim().split("\\s+");

      StringBuilder tt = new StringBuilder();
      StringBuilder dd = new StringBuilder();
      for (int i = 0; i < t.length; i++) {
        tt.append(t[i]);
        dd.append(d[i]);
      }

      long time = Long.parseLong(tt.toString());
      long dist = Long.parseLong(dd.toString());

      long low = 0L, high = time / 2L, start = low, end = high;
      while (low <= high) {
        long mid = (low + high) / 2L;
        if (mid * (time - mid) <= dist) {
          start = mid;
          low = mid + 1;
        } else {
          high = mid - 1;
        }
      }

      low = 0L;
      high = time / 2L;
      while (low <= high) {
        long mid = (low + high) / 2L;
        if (mid * (time - mid) > dist) {
          end = mid;
          low = mid + 1;
        } else {
          high = mid - 1;
        }
      }

      ans = 2 * (end - start);
      if ((long) (time / 2.0) == time / 2L) ans--;

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    return ans;
  }

  public static void main(String[] args) {
    Solution ob = new Solution();
    System.out.println(ob.start());
  }
}
