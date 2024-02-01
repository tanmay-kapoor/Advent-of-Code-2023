import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
  int start() {
    int ans = 0;
    List<String> list = new ArrayList<>();
    File file = new File("input.txt");
    int i = 0, lineIndex = 0, charIndex = 0;
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        if (line.contains("S")) {
          lineIndex = i;
          charIndex = line.indexOf("S");
        }
        list.add(line);
        i++;
      }
      int[][] vals = new int[list.size()][list.get(0).length()];
      vals[lineIndex][charIndex] = 1;
      int currLi, currCi;
      Queue<int[]> q = new LinkedList<>();
      boolean[][] visited = new boolean[list.size()][list.get(0).length()];
      List<String> dirs = new ArrayList<>();

      if (charIndex > 0) {
        char left = list.get(lineIndex).charAt(charIndex - 1);
        if (left == '-' || left == 'L' || left == 'F') {
          dirs.add("left");
          q.add(new int[]{lineIndex, charIndex - 1, lineIndex, charIndex});
          visited[lineIndex][charIndex - 1] = true;
        }
      }

      if (charIndex < list.get(0).length() - 1) {
        char right = list.get(lineIndex).charAt(charIndex + 1);
        if (right == '-' || right == 'J' || right == '7') {
          dirs.add("right");
          q.add(new int[]{lineIndex, charIndex + 1, lineIndex, charIndex});
          visited[lineIndex][charIndex + 1] = true;
        }
      }

      if (lineIndex > 0) {
        char up = list.get(lineIndex - 1).charAt(charIndex);
        if (up == '|' || up == '7' || up == 'F') {
          dirs.add("up");
          q.add(new int[]{lineIndex - 1, charIndex, lineIndex, charIndex});
          visited[lineIndex - 1][charIndex] = true;
        }
      }

      if (lineIndex < list.size() - 1) {
        char down = list.get(lineIndex + 1).charAt(charIndex);
        if (down == '|' || down == 'L' || down == 'J') {
          dirs.add("down");
          q.add(new int[]{lineIndex + 1, charIndex, lineIndex, charIndex});
          visited[lineIndex + 1][charIndex] = true;
        }
      }

      while (!q.isEmpty()) {
        int[] temp = q.poll();
        currLi = temp[0];
        currCi = temp[1];
        int prevLi = temp[2], prevCi = temp[3];

        vals[currLi][currCi] = vals[prevLi][prevCi] + 1;
//        ans = Math.max(ans, vals[currLi][currCi] - 1);
        char ch = list.get(currLi).charAt(currCi);

        if (currCi - prevCi == -1) {
          // - L F
          if (ch == '-' && !visited[currLi][currCi - 1]) {
            q.add(new int[]{currLi, currCi - 1, currLi, currCi});
            visited[currLi][currCi - 1] = true;
          } else if (ch == 'L' && !visited[currLi - 1][currCi]) {
            q.add(new int[]{currLi - 1, currCi, currLi, currCi});
            visited[currLi - 1][currCi] = true;
          } else if (ch == 'F' && !visited[currLi + 1][currCi]) {
            q.add(new int[]{currLi + 1, currCi, currLi, currCi});
            visited[currLi + 1][currCi] = true;
          }
        } else if (currCi - prevCi == 1) {
          // - J 7
          if (ch == '-' && !visited[currLi][currCi + 1]) {
            q.add(new int[]{currLi, currCi + 1, currLi, currCi});
            visited[currLi][currCi + 1] = true;
          } else if (ch == 'J' && !visited[currLi - 1][currCi]) {
            q.add(new int[]{currLi - 1, currCi, currLi, currCi});
            visited[currLi - 1][currCi] = true;
          } else if (ch == '7' && !visited[currLi + 1][currCi]) {
            q.add(new int[]{currLi + 1, currCi, currLi, currCi});
            visited[currLi + 1][currCi] = true;
          }
        } else if (currLi - prevLi == -1) {
          // | 7 F
          if (ch == '|' && !visited[currLi - 1][currCi]) {
            q.add(new int[]{currLi - 1, currCi, currLi, currCi});
            visited[currLi - 1][currCi] = true;
          } else if (ch == '7' && !visited[currLi][currCi - 1]) {
            q.add(new int[]{currLi, currCi - 1, currLi, currCi});
            visited[currLi][currCi - 1] = true;
          } else if (ch == 'F' && !visited[currLi][currCi + 1]) {
            q.add(new int[]{currLi, currCi + 1, currLi, currCi});
            visited[currLi][currCi + 1] = true;
          }
        } else if (currLi - prevLi == 1) {
          // | L J
          if (ch == '|' && !visited[currLi + 1][currCi]) {
            q.add(new int[]{currLi + 1, currCi, currLi, currCi});
            visited[currLi + 1][currCi] = true;
          } else if (ch == 'L' && !visited[currLi][currCi + 1]) {
            q.add(new int[]{currLi, currCi + 1, currLi, currCi});
            visited[currLi][currCi + 1] = true;
          } else if (ch == 'J' && !visited[currLi][currCi - 1]) {
            q.add(new int[]{currLi, currCi - 1, currLi, currCi});
            visited[currLi][currCi - 1] = true;
          }
        }
      }

      String l = list.get(lineIndex), newL = "";
      char ch = 'S';
      if (dirs.contains("left") && dirs.contains("right")) {
        ch = '-';
      } else if (dirs.contains("up") && dirs.contains("down")) {
        ch = '|';
      } else if (dirs.contains("left") && dirs.contains("up")) {
        ch = 'J';
      } else if (dirs.contains("left") && dirs.contains("down")) {
        ch = '7';
      } else if (dirs.contains("right") && dirs.contains("up")) {
        ch = 'L';
      } else {
        ch = 'F';
      }
      list.set(lineIndex, l.substring(0, charIndex) + ch + l.substring(charIndex + 1));

      for (int j = 0; j < list.size(); j++) {
        boolean isInside = false;
        for (int k = 0; k < list.get(0).length(); k++) {
          if (vals[j][k] == 0 && isInside) {
            ans++;
          }
          char c = list.get(j).charAt(k);
          if (vals[j][k] != 0 && (c == '|' || c == 'L' || c == 'J')) {
            isInside = !isInside;
          }
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

// | - L J 7 F .
