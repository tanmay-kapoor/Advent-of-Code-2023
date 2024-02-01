import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Solution {
  int cnt = 0;

  int start() {
    int ans = Integer.MIN_VALUE;
    File file = new File("input.txt");
    List<String> grid = new ArrayList<>();
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        grid.add(line);
      }

      for (int i = 0; i < grid.size(); i++) {
        cnt = 0;
        Set<String>[][] set = new HashSet[grid.size()][grid.get(0).length()];
        findEnergized(grid, i, 0, set, "right");
        ans = Math.max(ans, cnt);

        cnt = 0;
        set = new HashSet[grid.size()][grid.get(0).length()];
        findEnergized(grid, i, grid.get(i).length() - 1, set, "left");
        ans = Math.max(ans, cnt);
      }

      for (int j = 0; j < grid.get(0).length(); j++) {
        cnt = 0;
        Set<String>[][] set = new HashSet[grid.size()][grid.get(0).length()];
        findEnergized(grid, 0, j, set, "down");
        ans = Math.max(ans, cnt);

        cnt = 0;
        set = new HashSet[grid.size()][grid.get(0).length()];
        findEnergized(grid, grid.size() - 1, j, set, "up");
        ans = Math.max(ans, cnt);
      }

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    return ans;
  }

  void findEnergized(List<String> grid, int i, int j, Set<String>[][] set, String direction) {
    if (i < 0 || i >= grid.size() || j < 0 || j >= grid.get(0).length() || (set[i][j] != null && set[i][j].contains(direction))) {
      return;
    }
    char ch = grid.get(i).charAt(j);
    if (set[i][j] == null) {
      set[i][j] = new HashSet<>();
      cnt++;
    }
    switch (ch) {
      case '.':
        set[i][j].add(direction);
        findEnergized(grid, getI(i, direction), getJ(j, direction), set, direction);
        break;

      case '/':
        set[i][j].add(direction);
        switch (direction) {
          case "right":
            findEnergized(grid, getI(i, "up"), getJ(j, "up"), set, "up");
            break;

          case "left":
            findEnergized(grid, getI(i, "down"), getJ(j, "down"), set, "down");
            break;

          case "up":
            findEnergized(grid, getI(i, "right"), getJ(j, "right"), set, "right");
            break;

          case "down":
            findEnergized(grid, getI(i, "left"), getJ(j, "left"), set, "left");
            break;
        }
        break;

      case '\\':
        set[i][j].add(direction);
        switch (direction) {
          case "right":
            findEnergized(grid, getI(i, "down"), getJ(j, "down"), set, "down");
            break;

          case "left":
            findEnergized(grid, getI(i, "up"), getJ(j, "up"), set, "up");
            break;

          case "up":
            findEnergized(grid, getI(i, "left"), getJ(j, "left"), set, "left");
            break;

          case "down":
            findEnergized(grid, getI(i, "right"), getJ(j, "right"), set, "right");
            break;
        }
        break;

      case '|':
        set[i][j].add(direction);
        switch (direction) {
          case "right":
          case "left":
            findEnergized(grid, getI(i, "up"), getJ(j, "up"), set, "up");
            findEnergized(grid, getI(i, "down"), getJ(j, "down"), set, "down");
            break;

          case "up":
          case "down":
            findEnergized(grid, getI(i, direction), getJ(j, direction), set, direction);
            break;
        }
        break;

      case '-':
        set[i][j].add(direction);
        switch (direction) {
          case "right":
          case "left":
            findEnergized(grid, getI(i, direction), getJ(j, direction), set, direction);
            break;

          case "up":
          case "down":
            findEnergized(grid, getI(i, "left"), getJ(j, "left"), set, "left");
            findEnergized(grid, getI(i, "right"), getJ(j, "right"), set, "right");
            break;
        }
        break;
    }
  }

  int getI(int i, String direction) {
    switch (direction) {
      case "right":
      case "left":
        return i;
      case "up":
        return i - 1;
      case "down":
        return i + 1;
    }
    return -1;
  }

  int getJ(int j, String direction) {
    switch (direction) {
      case "right":
        return j + 1;
      case "left":
        return j - 1;
      case "up":
      case "down":
        return j;
    }
    return -1;
  }

  public static void main(String[] args) {
    Solution ob = new Solution();
    System.out.println(ob.start());
  }
}
