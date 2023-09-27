//Problem. Given two strings, pattern and text, count the number of occurrences of pattern in text.
//Input. The first line contains string pattern, the second string text.

// Applying Rabin-Karp’s algorithm

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Pattern {

  public final static int d = 256;

  static int search(String pat, String txt, int q) {
    int M = pat.length();
    int N = txt.length();
    int i, j;
    int p = 0; // hash value for pattern
    int t = 0; // hash value for txt
    int h = 1;

    int count = 0;

    // for the length of the pattern given, smallest h to ensure every possible hash
    for (i = 0; i < M - 1; i++)
      h = (h * d) % q;

    for (i = 0; i < M; i++) {
      p = (d * p + pat.charAt(i)) % q; // compile hash of each char
      t = (d * t + txt.charAt(i)) % q;
    }

    // for each possible pattern over text
    for (i = 0; i <= N - M; i++) {

      // does the pattern hash == text hash
      if (p == t) {
        /* Check for characters one by one */
        for (j = 0; j < M; j++) {
          // if the hash is wrong even in 1 bit, skip straight away
          if (txt.charAt(i + j) != pat.charAt(j))
            break;
        }

        // if j made it to the end of the pattern hash, then pattern is found
        if (j == M) {
          count++;
        }
      }

      // then for every pattern
      if (i < N - M) {

        // text hash =
        t = (d * (t - txt.charAt(i) * h) + txt.charAt(i + M)) % q;

        // We might get negative value of t, converting it
        // to positive
        if (t < 0)
          t = (t + q);
      }
    }
    return count;
  }

  public static void main(String args[]) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String pattern = br.readLine();
    String text = br.readLine();

    // String pattern = "ab";
    // String text = "abababababbabbaabbababbaaba";

    System.out.println(search(pattern, text, 101));

  }
}
