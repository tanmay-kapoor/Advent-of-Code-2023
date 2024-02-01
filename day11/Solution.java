import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Solution {

  long start() {
    List<String> list = new ArrayList<>();
    Set<Integer> set = new HashSet<>();
    long ans = 0L, multiplier = 1000000L;
    File file = new File("input.txt");
    try {
      int lineNum = 0;
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        list.add(line);
        if (!line.contains("#")) {
          set.add(lineNum);
        }
        lineNum++;
      }

      List<long[]> locs = new ArrayList<>();
      long k = 0L;
      for (int j = 0; j < list.get(0).length(); j++) {
        boolean found = false;
        long i = 0L;
        for (int x = 0; x < list.size(); x++) {
          String line = list.get(x);
          if (line.charAt(j) == '#') {
            found = true;
            locs.add(new long[]{i, k});
          }
          i += set.contains(x) ? multiplier : 1;
        }
        k += !found ? multiplier : 1;
      }

      for (int i = 0; i < locs.size(); i++) {
        for (int j = i + 1; j < locs.size(); j++) {
          long[] loc1 = locs.get(i);
          long[] loc2 = locs.get(j);
          long dist = Math.abs(loc1[0] - loc2[0]) + Math.abs(loc1[1] - loc2[1]);
          ans += dist;
        }
      }

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
