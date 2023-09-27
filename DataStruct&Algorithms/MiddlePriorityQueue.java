//Problem. You start with an empty collection of numbers. You are then given a sequence of m operations,
//where each operation is either add(x), which asks you to add the number x to the collection, or middle(),
//which asks you to output the (n + 1)/2-th largest number in your collection (the middle element), where n is
//the current number of elements in the collection. It is guaranteed that middle() will only be invoked when n
//is odd.
//Input. The first line contains integer m. Each of the following m lines has one of the two following formats:
//• 1 x (invoke add(x))
//• 0 (invoke middle())
//Output. For each input line which invokes middle(), output the middle element in the collection at that
//particular point in time.
//Constraints. There are two data sets, Middle_small (worth 5 marks) and Middle_large (worth 10 marks).
//In Middle_small we guarantee n ≤ 1000, and in Middle_large we guarantee n ≤ 100000. In both data sets,
//all the numbers in a collection are between 0 and 228.
//Example.
//Input:
//14
//1 1
//1 1
//1 3
//0
//1 5
//1 7
//0
//1 5
//1 1
//1 1
//1 1
//1 10
//1 1
//0
//Output:
//1
//3
//1
//Explanation of the example. We are given the following sequence of operations:
//1. add(1)
//2. add(1)
//3. add(3)
//4. middle() – At this point the collection contains 3 elements {1, 1, 3}, and the 2-nd largest is 1.
//5. add(5)
//6. add(7)
//7. middle() – At this point the collection contains 5 elements {1, 1, 3, 5, 7}, and the 3-rd largest is 3.
//8. add(5)
//9. add(1)
//10. add(1)
//11. add(1)
//12. add(10)
//13. add(1)
//14. middle() – At this point the collection contains 11 elements {1, 1, 3, 5, 7, 5, 1, 1, 1, 10, 1}, and the 6-th
//largest is 1

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class MiddlePriorityQueue {

  public static void main(String[] args) {
    FastScanner in = new FastScanner();
    PrintWriter out = new PrintWriter(System.out);
    int m = in.nextInt();

    // Create two PriorityQueues, one for the left half and one for the right half
    PriorityQueue<Integer> leftHalf = new PriorityQueue<>((a, b) -> Integer.compare(b, a)); // puts largest integers at
                                                                                            // the end
    PriorityQueue<Integer> rightHalf = new PriorityQueue<>();

    for (int i = 0; i < m; i++) {
      int input = in.nextInt();

      if (input == 0) {
        // Output middle
        if (leftHalf.size() + rightHalf.size() == 0) {
          out.println("Invalid");
        } else if (leftHalf.size() == rightHalf.size()) {
          out.println((leftHalf.peek() + rightHalf.peek()) / 2);
        } else {
          out.println(leftHalf.peek());
        }
      } else {
        // Add to PriorityQueues
        int number = in.nextInt();
        if (leftHalf.isEmpty() || number <= leftHalf.peek()) {
          leftHalf.add(number);
        } else {
          rightHalf.add(number);
        }

        // Balance the heaps
        if (leftHalf.size() > rightHalf.size() + 1) {
          rightHalf.add(leftHalf.poll());
        } else if (rightHalf.size() > leftHalf.size()) {
          leftHalf.add(rightHalf.poll());
        }
      }
    }

    out.close();
  }

  static class FastScanner {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer("");

    String next() {
      while (!st.hasMoreTokens()) {
        try {
          st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
        }
      }
      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }
  }
}
