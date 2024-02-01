import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  private final File file;
  private final Map<String, Integer> map;
  private int ans;

  public Solution() {
    file = new File("input.txt");
    map = new HashMap<>();
    map.put("red", 12);
    map.put("green", 13);
    map.put("blue", 14);
    ans = 0;
  }

  boolean isValidGame(String game) {
    String[] details = game.split("; ");
    for (String detail : details) {
      String[] cubesInfo = detail.split(", ");
      for (String cube : cubesInfo) {
        String[] info = cube.split(" ");
        int quantity = Integer.parseInt(info[0]);
        String color = info[1];
        if (quantity > map.get(color)) {
          return false;
        }
      }
    }
    return true;
  }

  int getPowerSet(String game) {
    Map<String, Integer> maxMap = new HashMap<>();
    maxMap.put("red", 0);
    maxMap.put("green", 0);
    maxMap.put("blue", 0);
    String[] details = game.split("; ");
    for (String detail : details) {
      String[] cubesInfo = detail.split(", ");
      for (String cube : cubesInfo) {
        String[] info = cube.split(" ");
        int quantity = Integer.parseInt(info[0]);
        String color = info[1];
        maxMap.put(color, Math.max(maxMap.get(color), quantity));
      }
    }
    return maxMap.get("red") * maxMap.get("green") * maxMap.get("blue");
  }

  int getSolution() {
    try {
      Scanner sc = new Scanner(this.file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] tokens = line.split(": ");
        int gameId = Integer.parseInt(tokens[0].split(" ")[1]);
        ans += getPowerSet(tokens[1]);
      }
      sc.close();
      return ans;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static void main(String[] args) {
    Solution ob = new Solution();
    System.out.println(ob.getSolution());
  }
}
