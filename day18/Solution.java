import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
  int start() {
    File file = new File("input.txt");
    int[] dirs = {0, 1, 0, -1, 0};
    int ans = 0;
    int prevRow = 0, prevCol = 0, currRow = 0, currCol = 0, s1 = 0, s2 = 0, p = 0;
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] dets = line.split(" ");
        String dir = dets[0];
        int val = Integer.parseInt(dets[1]);
        int idx = dir.equals("R") ? 0 : dir.equals("D") ? 1 : dir.equals("L") ? 2 : 3;
        currRow += val * dirs[idx];
        currCol += val * dirs[idx + 1];
        s1 += prevRow * currCol;
        s2 += prevCol * currRow;
        p += val;
        prevRow = currRow;
        prevCol = currCol;
      }
      ans = Math.abs(s1 - s2) / 2 + p / 2 + 1;
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
