import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  int start() {
    int ans = 0;
    File file = new File("input.txt");
    List<char[]> list = new ArrayList<>();
    Map<String, Integer> map = new HashMap<>();
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        line = "#" + line + "#";
        list.add(line.toCharArray());
      }
      list.add(0, "#".repeat(list.get(0).length).toCharArray());
      list.add("#".repeat(list.get(0).length).toCharArray());

      for (int k = 0; k < 1000000000; k++) {
        moveNorth(list);
        moveWest(list);
        moveSouth(list);
        moveEast(list);

        StringBuilder key = new StringBuilder();
        for (char[] ch : list) {
          key.append(new String(ch)).append("\n");
        }

        // prev + (diff*x) <= 1000000000
        // x <= (1000000000 - prev) / diff
        if (map.containsKey(key.toString())) {
          int prev = map.get(key.toString());
          int diff = k - prev;
          int x = (1000000000 - prev) / diff;
          k = (prev + (diff * x)); // +1 from for loop
        } else {
          map.put(key.toString(), k);
        }
      }

      list.remove(0);
      list.remove(list.size() - 1);

      for (int i = 0; i < list.size(); i++) {
        char[] ch = list.get(i);
        int cnt = 0;
        for (char c : ch) {
          if (c == 'O') cnt++;
        }
        ans += (cnt * (list.size() - i));
      }

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    return ans;
  }

  void moveNorth(List<char[]> list) {
    for (int j = 0; j < list.get(0).length; j++) {
      int start = 0, cnt = 0;
      for (int i = 0; i < list.size(); i++) {
        char ch = list.get(i)[j];
        if (ch == 'O') {
          cnt++;
        } else if (ch == '#') {
          if (cnt > 0) {
            while (cnt-- > 0) {
              list.get(start++)[j] = 'O';
            }
            while (start < i) {
              list.get(start++)[j] = '.';
            }
          }
          start = i + 1;
          cnt = 0;
        }
      }
    }
  }

  void moveWest(List<char[]> list) {
    for (char[] chars : list) {
      int start = 0, cnt = 0;
      for (int j = 0; j < chars.length; j++) {
        char ch = chars[j];
        if (ch == 'O') {
          cnt++;
        } else if (ch == '#') {
          if (cnt > 0) {
            while (cnt-- > 0) {
              chars[start++] = 'O';
            }
            while (start < j) {
              chars[start++] = '.';
            }
          }
          start = j + 1;
          cnt = 0;
        }
      }
    }
  }

  void moveSouth(List<char[]> list) {
    for (int j = 0; j < list.get(0).length; j++) {
      int start = list.size() - 1, cnt = 0;
      for (int i = list.size() - 1; i >= 0; i--) {
        char ch = list.get(i)[j];
        if (ch == 'O') {
          cnt++;
        } else if (ch == '#') {
          if (cnt > 0) {
            while (cnt-- > 0) {
              list.get(start--)[j] = 'O';
            }
            while (start > i) {
              list.get(start--)[j] = '.';
            }
          }
          start = i - 1;
          cnt = 0;
        }
      }
    }
  }

  void moveEast(List<char[]> list) {
    for (char[] chars : list) {
      int start = chars.length - 1, cnt = 0;
      for (int j = chars.length - 1; j >= 0; j--) {
        char ch = chars[j];
        if (ch == 'O') {
          cnt++;
        } else if (ch == '#') {
          if (cnt > 0) {
            while (cnt-- > 0) {
              chars[start--] = 'O';
            }
            while (start > j) {
              chars[start--] = '.';
            }
          }
          start = j - 1;
          cnt = 0;
        }
      }
    }
  }

  public static void main(String[] args) {
    Solution ob = new Solution();
    System.out.println(ob.start());
  }
}
