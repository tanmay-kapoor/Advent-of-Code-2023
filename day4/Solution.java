import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {
  int start() {
    int ans = 0, points = 0;
    File file = new File("sample.txt");
    int[] arr = new int[196];

    try {
      Scanner sc = new Scanner(file);
      int i = 0;
      while (sc.hasNextLine()) {
        arr[i]++;

        String line = sc.nextLine();
        String[] nums = line.split(": ")[1].split(" \\| ");
        Set<String> winningNums = new HashSet<>(Arrays.asList(nums[0].trim().split(" ")));
        String[] myNums = nums[1].trim().split("\\s+");

        int cnt = 0;
        for (String num : myNums) {
          if (winningNums.contains(num)) {
            cnt++;
          }
        }
        points += (int) (Math.pow(2, cnt - 1));
        for (int j = 1; j <= cnt; j++) {
          arr[i + j] += arr[i];
        }
        i++;
      }
      for (int val : arr) {
        ans += val;
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }

    System.out.println(points);
    return ans;
  }

  public static void main(String[] args) {
    Solution ob = new Solution();
    System.out.println(ob.start());
  }
}
