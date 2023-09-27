//  Problem. You are given a table of size n × n, with cells labelled as follows: Cells in the first row are labelled
//  0 to n − 1 (left to right), cells in the second row n to 2n − 1, in the third row 2n to 3n − 1, and so on. Cells in
//  the last row are labeled n(n − 1) to n2 − 1. The figure below shows the labelling for n = 4.
//  0 1 2 3
//  4 5 6 7
//  8 9 10 11
//  12 13 14 15
//  You are also given a sequence of m pairs of adjacent cells. Pairs in this sequence are processed one after the
//  other. If the i-th pair is (ai, bi), then you should destroy the wall between ai and bi if, currently, there is no
//  way to get from ai to bi. Your goal is to output the table after processing all the pairs.
//  Input. The first line contains two integers, n and m. Each of the following m lines contains two integers, ai
//  and bi. It is guaranteed that 0 ≤ ai, bi < n2, and ai and bi are neighbouring cells in the table.
//  Output. Output the resulting table. For the format of the output, see the example.
//  Constraints. There are two data sets, Maze_small (worth 5 marks) and Maze_large (worth 15 marks).
//  In Maze_small we guarantee n ≤ 5, and in Maze_large we guarantee n ≤ 300. In both cases we have
//  m ≤ 2 · n(n − 1),
//  Example.
//  Input:
//  4 17
//  0 1
//  1 2
//  2 3
//  3 7
//  7 11
//  11 15
//  0 4
// 4 8
//8 12
//12 13
//13 14
//14 15
//1 5
//5 6
//6 10
//10 9
//9 8
//Output:
//+-+-+-+-+
//|       |
//+ + +-+ +
//| |   | |
//+ +-+ + +
//| |   | |
//+ +-+-+ +
//|     | |
//+-+-+-+-+



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Maze {

  public class UnionFind {
    //
    int n;
    int[] reps;
    char[][] walls;

    public UnionFind(int n) {
      this.n = n;

      reps = new int[n * n];
      walls = new char[2 * n + 1][2 * n + 1];
      setWalls();

      for (int i = 0; i < reps.length; i++) {
        reps[i] = -1;
      }
    }

    private void setWalls() {
      for (int i = 0; i < 2 * n + 1; i++) {
        for (int j = 0; j < 2 * n + 1; j++) {
          if (i % 2 == 0) {
            // horizontal row
            walls[i][j] = (j % 2 == 0) ? '+' : '-';
          } else {
            // vertical row
            walls[i][j] = (j % 2 == 0) ? '|' : ' ';
          }
        }
      }
    }

    public void printWalls() {
      //
      for (int i = 0; i < 2 * n + 1; i++) {
        for (int j = 0; j < 2 * n + 1; j++) {
          System.out.print(walls[i][j]);
        }
        System.out.println();
      }
    }

    public int findRep(int a) {
      if (reps[a] == -1) {
        return a;
      } else {
        // path compression
        reps[a] = findRep(reps[a]);
        return reps[a];
      }
    }

    public boolean isSameSection(int a, int b) {
      int repA = findRep(a);
      int repB = findRep(b);

      return repA == repB;
    }

    public void union(int a, int b) {
      if (isSameSection(a, b)) {
        return;
      }

      // add check height, and path compression
      // and if reps[a] already points elsewhere?

      removeWall(a, b);
      reps[findRep(a)] = findRep(b);

    }

    private void removeWall(int a, int b) {
      // find place in walls[][] where this relates to.
      // remove | or -

      // Calculate maze row and column indices for cell a
      int rowA = a / n;
      int colA = a % n;
      int mazeRowA = rowA * 2 + 1;
      int mazeColA = colA * 2 + 1;

      // Calculate maze row and column indices for cell b
      int rowB = b / n;
      int colB = b % n;
      int mazeRowB = rowB * 2 + 1;
      int mazeColB = colB * 2 + 1;

      // Determine if the wall is horizontal or vertical
      if (mazeRowA == mazeRowB) {
        // Horizontal wall
        int wallCol = Math.min(mazeColA, mazeColB) + 1;
        walls[mazeRowA][wallCol] = ' ';
      } else if (mazeColA == mazeColB) {
        // Vertical wall
        int wallRow = Math.min(mazeRowA, mazeRowB) + 1;
        walls[wallRow][mazeColA] = ' ';
      }
    }
  }

  public static void main(String args[]) {
    FastScanner scanner = new FastScanner();

    int n = scanner.nextInt(); // dimension
    int m = scanner.nextInt(); // number of pairs

    Maze maze = new Maze();
    Maze.UnionFind uf = maze.new UnionFind(n);

    int input_A, input_B;

    for (int i = 0; i < m; i++) {
      input_A = scanner.nextInt();
      input_B = scanner.nextInt();

      uf.union(input_A, input_B);

    }

    uf.printWalls();

  }

  static class FastScanner {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer("");

    String next() {
      while (!st.hasMoreTokens())
        try {
          st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
        }
      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }
  }
}
