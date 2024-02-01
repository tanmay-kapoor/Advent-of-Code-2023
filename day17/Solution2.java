import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution2 {
  long start() {
    long ans = Integer.MAX_VALUE;
    List<int[]> grid = new ArrayList<>();
    List<Map<String, Long>[]> dist2 = new ArrayList<>();

    File file = new File("input.txt");
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        int[] digs = new int[line.length()];
        Map<String, Long>[] temp = new HashMap[line.length()];
        for (int i = 0; i < line.length(); i++) {
          digs[i] = Character.getNumericValue(line.charAt(i));
          temp[i] = new HashMap<>();
        }
        grid.add(digs);
        dist2.add(temp);
      }

      Queue<Stuff> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.currCost));
      pq.add(new Stuff(0, 0, 0, "right", 0, 0, 0, 0));

      while (!pq.isEmpty()) {
        Stuff curr = pq.poll();
        assert curr != null;
        int row = curr.row, col = curr.col;
        long currCost = curr.currCost;
        String currDir = curr.dir;
        int left = curr.left, right = curr.right, up = curr.up, down = curr.down;

        String key = currDir + "-" + left + "-" + right + "-" + up + "-" + down;
        if (dist2.get(row)[col].containsKey(key)) continue;
        dist2.get(row)[col].put(key, currCost);

        if (leftIsValid(grid, dist2, row, col, currDir, currCost, left)) {
          int newLeft = currDir.equals("left") ? left + 1 : 1;
          pq.add(new Stuff(row, col - 1, currCost + grid.get(row)[col - 1], "left", newLeft, 0, 0, 0));
        }

        if (isRightValid(grid, dist2, row, col, currDir, currCost, right)) {
          int newRight = currDir.equals("right") ? right + 1 : 1;
          pq.add(new Stuff(row, col + 1, currCost + grid.get(row)[col + 1], "right", 0, newRight, 0, 0));
        }

        if (isUpValid(grid, dist2, row, col, currDir, currCost, up)) {
          int newUp = currDir.equals("up") ? up + 1 : 1;
          pq.add(new Stuff(row - 1, col, currCost + grid.get(row - 1)[col], "up", 0, 0, newUp, 0));
        }

        if (isDownValid(grid, dist2, row, col, currDir, currCost, down)) {
          int newDown = currDir.equals("down") ? down + 1 : 1;
          pq.add(new Stuff(row + 1, col, currCost + grid.get(row + 1)[col], "down", 0, 0, 0, newDown));
        }

        if (row == grid.size() - 1 && col == grid.get(0).length - 1) {
          ans = Math.min(ans, currCost);
        }
      }

      Map<String, Long> vals = dist2.get(dist2.size() - 1)[dist2.get(0).length - 1];
      System.out.println(vals);

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    return ans;
  }

  boolean leftIsValid(List<int[]> grid, List<Map<String, Long>[]> dist2, int row, int col,
                      String currDir, long currCost, int left) {
    if (col == 0 || (currDir.equals("left") && left == 3)) return false;
    String nextKey;
    if (currDir.equals("left")) {
      nextKey = "left-" + (left + 1) + "-0-0-0";
    } else {
      nextKey = "left-1-0-0-0";
    }
    if (!dist2.get(row)[col - 1].containsKey(nextKey)) return true;
    return currCost + grid.get(row)[col - 1] < dist2.get(row)[col - 1].get(nextKey);
  }

  boolean isRightValid(List<int[]> grid, List<Map<String, Long>[]> dist2, int row, int col,
                       String currDir, long currCost, int right) {
    if (col == grid.get(0).length - 1 || (currDir.equals("right") && right == 3)) return false;
    String nextKey;
    if (currDir.equals("right")) {
      nextKey = "right-" + "0-" + (right + 1) + "-0-0";
    } else {
      nextKey = "right-0-1-0-0";
    }
    if (!dist2.get(row)[col + 1].containsKey(nextKey)) return true;
    return currCost + grid.get(row)[col + 1] < dist2.get(row)[col + 1].get(nextKey);
  }

  boolean isUpValid(List<int[]> grid, List<Map<String, Long>[]> dist2, int row, int col,
                    String currDir, long currCost, int up) {
    if (row == 0 || (currDir.equals("up") && up == 3)) return false;
    String nextKey;
    if (currDir.equals("up")) {
      nextKey = "up-0-0-" + (up + 1) + "-0";
    } else {
      nextKey = "up-0-0-1-0";
    }
    if (!dist2.get(row - 1)[col].containsKey(nextKey)) return true;
    return currCost + grid.get(row - 1)[col] < dist2.get(row - 1)[col].get(nextKey);
  }

  boolean isDownValid(List<int[]> grid, List<Map<String, Long>[]> dist2, int row, int col,
                      String currDir, long currCost, int down) {
    if (row == grid.size() - 1 || (currDir.equals("down") && down == 3)) return false;
    String nextKey;
    if (currDir.equals("down")) {
      nextKey = "down-0-0-0-" + (down + 1);
    } else {
      nextKey = "down-0-0-0-1";
    }
    if (!dist2.get(row + 1)[col].containsKey(nextKey)) return true;
    return currCost + grid.get(row + 1)[col] < dist2.get(row + 1)[col].get(nextKey);
  }

  static class Stuff {
    int row, col;
    long currCost;
    String dir;
    int left, right, up, down;

    Stuff(int row, int col, long currCost, String dir, int left, int right, int up, int down) {
      this.row = row;
      this.col = col;
      this.currCost = currCost;
      this.dir = dir;
      this.left = left;
      this.right = right;
      this.up = up;
      this.down = down;
    }
  }

  public static void main(String[] args) {
    Solution2 ob = new Solution2();
    System.out.println(ob.start());
  }
}
