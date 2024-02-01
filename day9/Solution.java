import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  int start() {
    int ans = 0;
    int[][] lists = new int[200][21];
    boolean[] shouldContinue = new boolean[lists.length];
    Arrays.fill(shouldContinue, true);
    File file = new File("input.txt");
    try {
      Scanner sc = new Scanner(file);
      int i = 0;
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] nums = line.split("\\s+");
        for (int j = 0; j < nums.length; j++) {
          lists[i][nums.length - 1 - j] = Integer.parseInt(nums[j]);
        }
        i++;
      }

      int total = 0, k = 0;
      while (total != lists.length) {
        for (i = 0; i < lists.length; i++) {
          if (shouldContinue[i]) {
            boolean allZeroes = true;
            for (int j = 0; j < lists[i].length - 1 - k; j++) {
              lists[i][j] = lists[i][j + 1] - lists[i][j];
              if (lists[i][j] != 0) {
                allZeroes = false;
              }
            }
            if (allZeroes) {
              total++;
            }
            shouldContinue[i] = !allZeroes;
          }
        }
        k++;
      }

      for (i = 0; i < lists.length; i++) {
        for (int j = 0; j < lists[i].length; j++) {
          ans += lists[i][j];
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
