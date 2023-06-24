package nz.ac.auckland.se281;

import java.util.Random;
import java.util.Scanner;

// cannto change this class the only thing you can do is to add a random seed Random to obtain
// deterministic behaviours (see handout)
public class Utils {

  public static Random random = new Random();

  public static Scanner scanner = new Scanner(System.in);

  public static boolean isInteger(String s) {
    if (s == null) {
      return false;
    }
    // I try to parse the int, if it fails then I know is not an integer
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true; // no exception!
  }

  /**
   * @param min
   * @param max
   * @return a random number between min and max (inclusive)
   */
  public static int getRandomNumber(int min, int max) {
    return random.nextInt(max - min + 1) + min;
  }
}
