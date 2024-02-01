import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
  int start() {
    int ans = 0;
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    File file = new File("input.txt");
    try {
      Scanner sc = new Scanner(file);
      int k = 0;
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        if (!line.isEmpty()) {
          list1.add(line);
          for (int i = 0; i < line.length(); i++) {
            if (k == 0) {
              list2.add(line.charAt(i) + "");
            } else {
              list2.set(i, list2.get(i) + line.charAt(i));
            }
          }
        } else {
          int cnt = getColRef(list1);
          if (cnt == 0) {
            cnt = getColRef(list2) * 100;
          }
          ans += cnt;

          list1.clear();
          list2.clear();
          k = -1;
        }
        k++;
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    return ans;
  }

  int getColRef(List<String> list) {
    int len = list.get(0).length();
    for (int j = 1; j < len; j++) {
      int isReflection = 0;
      for (String s : list) {
        StringBuilder left, right;
        if (j <= len / 2) {
          left = new StringBuilder(s.substring(0, j));
          right = new StringBuilder(s.substring(j, j + j));
        } else {
          left = new StringBuilder(s.substring(j - (len - j), j));
          right = new StringBuilder(s.substring(j));
        }

        if (left.compareTo(right.reverse()) != 0) {
          isReflection++;
          if (isReflection > 1)
            break;
        }
      }
      if (isReflection == 1) {
        return j;
      }
    }
    return 0;
  }

  public static void main(String[] args) {
    Solution ob = new Solution();
    System.out.println(ob.start());
  }
}
