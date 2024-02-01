import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  Map<String, Long> map = new HashMap<>();

  long start() {
    long ans = 0;
    int folds = 5;
    File file = new File("input.txt");
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();

        String[] temp = line.split(" ");

        String str = (temp[0] + "?").repeat(folds);
        str = str.substring(0, str.length() - 1);

        temp[1] = (temp[1] + ",").repeat(folds);
        temp[1] = temp[1].substring(0, temp[1].length() - 1);
        String[] idk = temp[1].split(",");

        int[] vals = new int[idk.length];
        for (int i = 0; i < idk.length; i++) {
          vals[i] = Integer.parseInt(idk[i]);
        }

        map.clear();
        long cnt = getVal(str, 0, vals, 0, 0);
        ans += cnt;
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    return ans;
  }

  long getVal(String str, int strIdx, int[] vals, int valsIdx, long cnt) {
    if (strIdx == str.length()) {
      if (vals[vals.length - 1] == 0) {
        return 1;
      }
      return 0;
    }

    String key = str.substring(strIdx) + "-" + Arrays.toString(vals) + "-" + valsIdx;
    if (map.containsKey(key)) {
      return map.get(key);
    }

    if (valsIdx == vals.length) {
      if (str.indexOf("#", strIdx) == -1) {
        return 1;
      }
      return 0;
    }

    if (vals[valsIdx] == 0) {
      if (str.charAt(strIdx) == '#') {
        map.put(key, 0L);
        return 0;
      }
      String s = str.substring(0, strIdx) + "." + str.substring(strIdx + 1);
      cnt = getVal(s, strIdx + 1, vals, valsIdx + 1, cnt);
      map.put(key, cnt);
      return cnt;
    }

    if (strIdx > 0 && str.charAt(strIdx) == '.' && str.charAt(strIdx - 1) == '#') {
      map.put(key, 0L);
      return 0;
    }

    while (strIdx < str.length() && str.charAt(strIdx) == '.') {
      strIdx++;
    }
    if (strIdx == str.length()) {
      map.put(key, 0L);
      return 0;
    }

    char ch = str.charAt(strIdx);
    if (vals[valsIdx] > 0) {
      vals[valsIdx]--;
      String s = str.substring(0, strIdx) + "#" + str.substring(strIdx + 1);
      cnt = getVal(s, strIdx + 1, vals, valsIdx, cnt);
      vals[valsIdx]++;
    }
    if (ch != '#' && (strIdx == 0 || str.charAt(strIdx - 1) != '#')) {
      String s = str.substring(0, strIdx) + "." + str.substring(strIdx + 1);
      cnt += getVal(s, strIdx + 1, vals, valsIdx, cnt);
    }
    map.put(key, cnt);
    return cnt;
  }

  public static void main(String[] args) {
    Solution ob = new Solution();
    System.out.println(ob.start());
  }
}
