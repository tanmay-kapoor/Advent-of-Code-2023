import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Solution {
  int getType(String hand) {
    Map<Character, Integer> map = new HashMap<>();
    char ch = hand.charAt(0);
    for (char c : hand.toCharArray()) {
      map.put(c, map.getOrDefault(c, 0) + 1);
      if (c != 'J') {
        if (map.get(c) > map.get(ch)) {
          ch = c;
        } else if (Objects.equals(map.get(c), map.get(ch))) {
          if (compare(c, ch) == 1) {
            ch = c;
          }
        }
      }
    }

    if (map.size() == 1) return 1;
    map.put(ch, map.get(ch) + map.getOrDefault('J', 0));
    map.remove('J');

    int size = map.size();
    int[] vals = map.values().stream().mapToInt(i -> i).toArray();
    Arrays.sort(vals);

    if (size == 1) return 1;

    if (size == 2) {
      if (vals[1] == 4) return 2;
      else return 3;
    }

    if (size == 3) {
      if (vals[2] == 3) return 4;
      else return 5;
    }

    if (size == 4) {
      return 6;
    }
    return 7;
  }

  int start() {
    int ans = 0;
    File file = new File("input.txt");
    Hand[] hands = new Hand[1000];
    int i = 0;
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] temp = line.split("\\s+");
        int type = getType(temp[0]);
        hands[i++] = new Hand(temp[0], Integer.parseInt(temp[1]), type);
      }

      Arrays.sort(hands, (a, b) -> {
        int diff = b.type - a.type;
        if (diff != 0) return diff;
        String hand1 = a.cards;
        String hand2 = b.cards;
        for (int j = 0; j < hand1.length(); j++) {
          if (hand1.charAt(j) != hand2.charAt(j)) {
            return compare(hand1.charAt(j), hand2.charAt(j));
          }
        }
        return 0;
      });

      int j = 1;
      for (Hand hand : hands) {
//        System.out.println(hand.cards + " " + hand.bid + " " + getTypeStr(hand.type));
        ans = ans + (hand.bid * j++);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    return ans;
  }

  String getTypeStr(int type) {
    switch (type) {
      case 1:
        return "Five of a Kind";
      case 2:
        return "Four of a Kind";
      case 3:
        return "Full House";
      case 4:
        return "Three of a kind";
      case 5:
        return "Two pair";
      case 6:
        return "One pair";
      default:
        return "High Card";
    }
  }

  int compare(char ch1, char ch2) {
    char[] arr = {'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'};
    for (char c : arr) {
      if (ch1 == c) return 1;
      if (ch2 == c) return -1;
    }
    return 0;
  }

  public static void main(String[] args) {
    Solution ob = new Solution();
    System.out.println(ob.start());
  }

  static class Hand {
    String cards;
    int bid;
    int type;

    public Hand(String cards, int bid, int type) {
      this.cards = cards;
      this.bid = bid;
      this.type = type;
    }
  }
}
