import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  int start() {
    int ans = 0;
    Map<Integer, Map<String, Integer>> boxes = new HashMap<>();
    File file = new File("input.txt");
    try {
      Scanner sc = new Scanner(file);
      String line = sc.nextLine();
      String[] seqs = line.split(",");
      for (String seq : seqs) {
        int len = 0;
        String label;
        if (seq.contains("=")) {
          label = seq.substring(0, seq.length() - 2);
          len = seq.charAt(seq.length() - 1) - '0';
        } else {
          label = seq.substring(0, seq.length() - 1);
        }

        int curr = 0;
        for (char c : label.toCharArray()) {
          curr += c;
          curr *= 17;
          curr %= 256;
        }

        Map<String, Integer> stuff = boxes.getOrDefault(curr, new LinkedHashMap<>());
        if (seq.contains("=")) {
          stuff.put(label, len);
        } else {
          stuff.remove(label);
        }
        boxes.put(curr, stuff);
      }

      for (int box : boxes.keySet()) {
        int i = 1;
        for (String label : boxes.get(box).keySet()) {
          ans += (box + 1) * i * boxes.get(box).get(label);
          i++;
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
