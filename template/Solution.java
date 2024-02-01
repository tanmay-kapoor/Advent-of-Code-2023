import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
  int start() {
    int ans = 0;
    File file = new File("sample.txt");
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();

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
