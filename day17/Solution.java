import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
  long start() {
    long ans = Integer.MAX_VALUE;
    List<int[]> grid = new ArrayList<>();
    List<long[]> dist = new ArrayList<>();

    File file = new File("sample.txt");
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        int[] digs = new int[line.length()];
        long[] d = new long[line.length()];
        for (int i = 0; i < line.length(); i++) {
          digs[i] = Character.getNumericValue(line.charAt(i));
          d[i] = Integer.MAX_VALUE;
        }
        grid.add(digs);
        dist.add(d);
      }

      boolean[][] visited = new boolean[grid.size()][grid.get(0).length];
      dist.get(0)[0] = 0L;
      Queue<Stuff> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.currCost));
      pq.add(new Stuff(0, 0, dist.get(0)[0], "right", 0, 0, 0, 0));

      while (!pq.isEmpty()) {
        Stuff curr = pq.poll();
        assert curr != null;
        int row = curr.row, col = curr.col;
        long currCost = curr.currCost;
        String currDir = curr.dir;
        int left = curr.left, right = curr.right, up = curr.up, down = curr.down;

        if (visited[row][col]) continue;
        visited[row][col] = true;

        if (leftIsValid(grid, dist, row, col, currDir, currCost, left)) {
          dist.get(row)[col - 1] = currCost + grid.get(row)[col - 1];
          int newLeft = currDir.equals("left") ? left + 1 : 1;
          pq.add(new Stuff(row, col - 1, dist.get(row)[col - 1], "left", newLeft, 0, 0, 0));
        }

        if (isRightValid(grid, dist, row, col, currDir, currCost, right)) {
          dist.get(row)[col + 1] = currCost + grid.get(row)[col + 1];
          int newRight = currDir.equals("right") ? right + 1 : 1;
          pq.add(new Stuff(row, col + 1, dist.get(row)[col + 1], "right", 0, newRight, 0, 0));
        }

        if (isUpValid(grid, dist, row, col, currDir, currCost, up)) {
          dist.get(row - 1)[col] = currCost + grid.get(row - 1)[col];
          int newUp = currDir.equals("up") ? up + 1 : 1;
          pq.add(new Stuff(row - 1, col, dist.get(row - 1)[col], "up", 0, 0, newUp, 0));
        }

        if (isDownValid(grid, dist, row, col, currDir, currCost, down)) {
          dist.get(row + 1)[col] = currCost + grid.get(row + 1)[col];
          int newDown = currDir.equals("down") ? down + 1 : 1;
          pq.add(new Stuff(row + 1, col, dist.get(row + 1)[col], "down", 0, 0, 0, newDown));
        }

        if (row == grid.size() - 1 && col == grid.get(0).length - 1) {
          ans = Math.min(ans, currCost);
        }
      }

      for (long[] longs : dist) {
        for (int j = 0; j < dist.get(0).length; j++) {
          System.out.print(longs[j] + "\t");
        }
        System.out.println();
      }

      System.out.println();

      String[][] path = new String[grid.size()][grid.get(0).length];
      for (int i = 0; i < path.length; i++) {
        for (int j = 0; j < path[0].length; j++) {
          path[i][j] = "----";
        }
      }

      int i = grid.size() - 1, j = grid.get(0).length - 1;
      while (i != 0 || j != 0) {
        if (i > 0 && dist.get(i)[j] == grid.get(i)[j] + dist.get(i - 1)[j]) {
          path[i][j] = "up-up";
          i--;
        } else if (i < grid.size() - 1 && dist.get(i)[j] == grid.get(i)[j] + dist.get(i + 1)[j]) {
          path[i][j] = "down";
          i++;
        } else if (j > 0 && dist.get(i)[j] == grid.get(i)[j] + dist.get(i)[j - 1]) {
          path[i][j] = "left";
          j--;
        } else if (j < grid.get(0).length - 1 && dist.get(i)[j] == grid.get(i)[j] + dist.get(i)[j + 1]) {
          path[i][j] = "right";
          j++;
        }
      }

      for (String[] strings : path) {
        for (int k = 0; k < path[0].length; k++) {
          System.out.print(strings[k] + "\t");
        }
        System.out.println();
      }

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    return ans;
  }

  boolean leftIsValid(List<int[]> grid, List<long[]> dist, int row, int col,
                      String currDir, long currCost, int left) {
    if (col == 0 || (currDir.equals("left") && left == 3)) return false;
    return currCost + grid.get(row)[col - 1] < dist.get(row)[col - 1];
  }

  boolean isRightValid(List<int[]> grid, List<long[]> dist, int row, int col,
                       String currDir, long currCost, int right) {
    if (col == grid.get(0).length - 1 || (currDir.equals("right") && right == 3)) return false;
    return currCost + grid.get(row)[col + 1] < dist.get(row)[col + 1];
  }

  boolean isUpValid(List<int[]> grid, List<long[]> dist, int row, int col,
                    String currDir, long currCost, int up) {
    if (row == 0 || (currDir.equals("up") && up == 3)) return false;
    return currCost + grid.get(row - 1)[col] < dist.get(row - 1)[col];
  }

  boolean isDownValid(List<int[]> grid, List<long[]> dist, int row, int col,
                      String currDir, long currCost, int down) {
    if (row == grid.size() - 1 || (currDir.equals("down") && down == 3)) return false;
    return currCost + grid.get(row + 1)[col] < dist.get(row + 1)[col];
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
    Solution ob = new Solution();
    System.out.println(ob.start());
  }
}
