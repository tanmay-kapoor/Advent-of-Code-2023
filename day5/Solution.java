import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

  long compute(List<Long> vals, List<String[]> list) {
    System.out.println("called");
    long ans = Integer.MAX_VALUE;
    for (int i = 0; i < vals.size(); i++) {
      long val = vals.get(i);
      for (String[] arr : list) {
        long destStart = Long.parseLong(arr[0]);
        long sourceStart = Long.parseLong(arr[1]);
        long length = Long.parseLong(arr[2]);

        if (val >= sourceStart && val <= sourceStart + length) {
          long diff = val - sourceStart;
          vals.set(i, destStart + diff);
          break;
        }
      }
      ans = Math.min(ans, vals.get(i));
    }
    return ans;
  }

  long start() {
    File file = new File("input.txt");
    long ans = Integer.MAX_VALUE;
    try {
      Scanner sc = new Scanner(file);

      String[] seeds = sc.nextLine().split(": ")[1].trim().split(" ");
//      long[] vals = new long[seeds.length];
      List<Long> vals = new ArrayList<>();
//      for (int i = 0; i < seeds.length; i++) {
//        vals[i] = Long.parseLong(seeds[i]);
//      }
      for (int i = 0; i < seeds.length; i += 2) {
        long start = Long.parseLong(seeds[i]);
        long range = Long.parseLong(seeds[i + 1]);
        System.out.println("adding " + range + " values to arraylist");
        for (int j = 0; j < range; j++) {
          vals.add(start + j);
        }
      }
      sc.nextLine();
      sc.nextLine();

      List<String[]> list = new ArrayList<>();

      while (sc.hasNext()) {
        String line = sc.nextLine();
        if (line.trim().isEmpty()) {
          sc.nextLine();

          // call function
          ans = compute(vals, list);
          list = new ArrayList<>();
        } else {
          list.add(line.split(" "));
        }
      }

//      ans = compute(vals, list);

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
