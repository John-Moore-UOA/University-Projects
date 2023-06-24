package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {

  private Set<T> verticies; // set of verticies
  private Set<Edge<T>> edges; // set of edge source and destination

  /**
   * Contructor for seting up the graph data-structure.
   *
   * @param verticies set of verticies.
   * @param edges set of edges the graph contains.
   */
  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    // loaded graph details
    this.verticies = verticies;
    this.edges = edges;
  }

  /**
   * gets the maximum int value from a set.
   *
   * @param set set of type T.
   * @return integer value for the maximum value.
   */
  private int getMax(Set<T> set) {
    int max = 0;
    for (T t : set) {
      int compair = Integer.parseInt(t.toString());
      if (compair > max) {
        max = compair;
      }
    }
    return max;
  }

  /**
   * This function searchs through graph and picks out the roots.
   *
   * @return Set, the set of roots of the loaded graph.
   */
  public Set<T> getRoots() {

    // is a root if in-degree = 0 and out-degree > 0
    // or is equivalence sub-graph (then numeric ordering)

    Set<T> out = new TreeSet<>();
    List<Integer> list = new ArrayList<>();
    // is a root if in degree = 0
    // is a root if equivalence and is lowest numerical value in that

    // looking at edge a-->b
    if (!isEquivalence()) {
      for (Edge<T> edge : edges) {
        T source = edge.getEdgeSource();
        boolean inDegree = false;
        boolean outDegree = false;

        // looking for edge x-->a
        for (Edge<T> edge2 : edges) {
          T source2 = edge2.getEdgeSource();
          T dest2 = edge2.getEdgeDestination();

          // x-->a including when its self loop
          if (source == dest2) {
            inDegree = true;
            break;
          }
          if (source == source2) {
            // a --> x, including self loops
            outDegree = true;
          }
        }
        // only outgoing edges
        if (!inDegree & outDegree) {
          list.add(Integer.parseInt(source.toString())); // source, being the vertex
        }
      }

      // sort list
      Collections.sort(list);
      // add list items to out put set
      for (int t : list) {
        out.add((T) (Integer) t);
      }
      return out;
    }

    // now must look for strongly connected sections in equivalent graphs

    // making vertex adjacency matrix
    // loop through edges,

    // vertex adjacency martrix
    // loop through vertices
    //   look across row
    //     take first 1 in that row to be the root
    //       add to output set

    if (isEquivalence()) {
      List<List<Integer>> adjacencyMatrix = new ArrayList<List<Integer>>();
      List<Integer> lookAtA = new ArrayList<>();
      List<Integer> lookAtB = new ArrayList<>();

      for (int i = 0; i <= getMax(verticies); i++) {
        adjacencyMatrix.add(new ArrayList<Integer>());
        for (int j = 0; j <= getMax(verticies); j++) {
          adjacencyMatrix.get(i).add(0);
        }
      }

      // trying to get(11) when there are 7 verticies, therefore overflow
      for (Edge<T> edge : edges) {
        // loop through edges
        T source = edge.getEdgeSource();
        T dest = edge.getEdgeDestination();
        // on row {source}, set {dest} position to 1
        int a = Integer.parseInt(source.toString());
        int b = Integer.parseInt(dest.toString());
        adjacencyMatrix.get(a).set(b, 1);
        lookAtA.add(a);
        lookAtB.add(b);
      }

      for (int i = 0; i <= getMax(verticies); i++) {
        // loop down adjacencyMatrix
        for (int j = 0; j <= getMax(verticies); j++) {
          // looking across row

          if (lookAtA.contains(i)) {
            if (adjacencyMatrix.get(i).get(j) == 1) {
              // take the first 1 in the row, being the lowest numeric value 'root'
              out.add((T) (Integer) j);
              break;
            }
          }
        }
      }
    }
    return out;
  }

  /**
   * reflexive, each vertex must have a self loop.
   *
   * @return boolean true if this graph is reflexive, else false.
   */
  public boolean isReflexive() {
    // loop through edges, and look for n number reflexive
    // n being the number of vertices

    int numReflexive = 0;

    for (Edge<T> edge : edges) {
      T source = edge.getEdgeSource();
      T dest = edge.getEdgeDestination();
      if (dest == source) {
        numReflexive++;
      }
    }

    return numReflexive == verticies.size() ? true : false;
  }

  /**
   * for symmetry, for all edges from a-->b, there must exist an edge b-->a.
   *
   * @return boolean true if symmetric.
   */
  public boolean isSymmetric() {
    // loop through edges, if a-->b exists, then loop through looking for b-->a  else return false
    for (Edge<T> edge : edges) {
      T source = edge.getEdgeSource();
      T dest = edge.getEdgeDestination();
      boolean foundPair = false;
      for (Edge<T> edge2 : edges) {
        T source2 = edge2.getEdgeSource();
        T dest2 = edge2.getEdgeDestination();
        // edge 1 =  a-->b   look for edge 2 = b-->a
        if (source2 == dest & dest2 == source) {
          foundPair = true;
        }
      }
      if (!foundPair) {
        return false;
      }
    }

    return true;
  }

  /**
   * for transitivity, for all edges from a-->b and b-->c, there must exist an edge a-->c.
   *
   * @return boolean true for if this graph is transitive.
   */
  public boolean isTransitive() {
    // loop through, if a-->b and b-->c,  then a-->c

    for (Edge<T> edge : edges) {
      T source = edge.getEdgeSource(); // a
      T dest = edge.getEdgeDestination(); // b
      boolean foundTransative = false;

      // a-->b

      // Compair to a second edge
      for (Edge<T> edge2 : edges) {
        //
        T source2 = edge2.getEdgeSource(); // b
        T dest2 = edge2.getEdgeDestination(); // c

        // Looking at what b connects to
        if (source2 == dest) {

          // looking at all edges
          for (Edge<T> edge3 : edges) {
            T source3 = edge3.getEdgeSource(); // a
            T dest3 = edge3.getEdgeDestination(); // c

            // if source --> dest3
            if (source3 == source & dest3 == dest2) {
              foundTransative = true;
              break;
            }
          }
          if (!foundTransative) {
            return false;
          }
        }
      }
    }

    return true;
  }

  /**
   * for anti-symmetry, for all edges from a-->b, there shall not be an edge from b-->a this
   * implies. the relation, a R b, imples a is b.
   *
   * @return boolean true for if this graph is anti-symmetric.
   */
  public boolean isAntiSymmetric() {
    // loop through edges,  if a-->b, and NOT b-->a
    for (Edge<T> edge : edges) {
      T source = edge.getEdgeSource();
      T dest = edge.getEdgeDestination();
      boolean foundPair = false;
      for (Edge<T> edge2 : edges) {
        T source2 = edge2.getEdgeSource();
        T dest2 = edge2.getEdgeDestination();
        // edge 1 =  a-->b   look for edge 2 = b-->a
        if (source2 == dest & dest2 == source) {
          foundPair = true;
        }
      }
      if (foundPair) {
        return false;
      }
    }

    return true;
  }

  /**
   * for a graph to be equivalent, the graph must be relfexive, symmetric and transitive.
   *
   * @return boolean true if graph is an equivalence relation.
   */
  public boolean isEquivalence() {
    return isReflexive() & isSymmetric() & isTransitive() ? true : false;
  }

  /**
   * an equivalence class is a strongly connected segment of a graph.
   *
   * @param inputVertex the input vertex that we will find the equivalence class based around.
   * @return set of verticies that are part of the quivalence class.
   */
  public Set<T> getEquivalenceClass(T inputVertex) {
    // the full connected component from input vertex
    Set<T> out = new TreeSet<>();

    // Must be equivalence to have equivalence classes
    if (!isEquivalence()) {
      return out;
    }

    out.add(inputVertex);

    // return all vertices that have a path from a-->b etc

    // could add roots for completly isolated vertices

    // take input vertex, and lok at what it connects to

    // take what that connects to and add to set, loop through set looking at what connects to what.

    for (Edge<T> edge : edges) {
      T source = edge.getEdgeSource();
      T dest = edge.getEdgeDestination();

      // if we have sound source == input vertex
      if (source.equals(inputVertex)) {
        out.add(dest); // adds adjacent vertices in output set
      }
    }
    return out;
  }

  /**
   * An iterative Breath First Search, uses loops to loop through the graph in a breath first
   * manner. Breath First Search, loops through all adjacent vertices, then through thoose child's
   * adjacent.
   *
   * @return Ordered List of generics.
   */
  public List<T> iterativeBreadthFirstSearch() {
    List<T> visited = new LinkedList<>();
    Queue<T> queue = new Queue<>();
    Set<T> roots = getRoots();

    for (T root : roots) {
      // for each root

      // add next root to queue and visited
      if (!visited.contains(root)) {
        queue.enqueue(root);
        visited.add(root);
      }

      while (!queue.isEmpty()) {
        // while queue is not empty

        // get current vertex
        T vertex = queue.dequeue();

        // new LinkedList for each queue item
        List<Integer> sortingLinkedList;

        // external function to reduce code cloning
        sortingLinkedList = iterativeStep(visited, vertex);

        // sort sortingLinkedList
        Collections.sort(sortingLinkedList); // low high

        for (int item : sortingLinkedList) {
          // if not in visted, add to visted and queue
          if (!visited.contains(item)) {
            visited.add((T) (Integer) item);
            queue.enqueue((T) (Integer) item);
          }
        }
      }
    }

    return visited;
  }

  /**
   * A custom contains type function to properly work with Java Generics.
   *
   * @param list a generic list.
   * @param data a generic piece of information.
   * @return boolean true or false, based on if the data is contained within the list.
   */
  private boolean containsTest(List<T> list, T data) {

    for (T t : list) {
      if (t.toString().equals(data.toString())) {
        return true;
      }
    }
    return false;
  }

  /**
   * This is has shared use between iterative dept and breath searches.
   *
   * @param visited a list of previously visited nodes.
   * @param vertex the currect vertex being looked at.
   * @return list of type integer, the next, adjacent nodes.
   */
  private List<Integer> iterativeStep(List<T> visited, T vertex) {

    List<Integer> sortingLinkedList = new LinkedList<>();

    for (Edge<T> edge : edges) {
      // if edge source is vertex
      T source = edge.getEdgeSource();
      if (vertex.toString().equals(source.toString())) {
        // now take each destination and add to Linked list to be sorted
        T dest = edge.getEdgeDestination();
        if (!containsTest(visited, dest)) {
          sortingLinkedList.add(Integer.parseInt(dest.toString()));
        }
      }
    }
    return sortingLinkedList;
  }

  /**
   * Iterative Depth First Search, uses loops to traverse the graph.
   *
   * @return List in depth First search order.
   */
  public List<T> iterativeDepthFirstSearch() {
    List<T> visited = new LinkedList<>();
    Stack<T> stack = new Stack<>();
    Set<T> roots = getRoots();

    for (T root : roots) {
      // for each root

      // add next root to queue and visited
      if (!visited.contains(root)) {
        stack.push(root);
        visited.add(root);
      }

      while (!stack.isEmpty()) {
        // while queue is not empty
        // get current vertex
        T vertex = stack.pop();
        if (!visited.contains(vertex)) {
          visited.add(vertex);
        }

        // new LinkedList for each queue item
        List<Integer> sortingLinkedList;

        // get list from external function to reduce code cloning
        sortingLinkedList = iterativeStep(visited, vertex);

        // sort sortingLinkedList
        Collections.sort(sortingLinkedList, Collections.reverseOrder()); // high low

        for (int item : sortingLinkedList) {
          // if not in visted, add to visted and queue
          if (!visited.contains(item)) {
            // visited.add((T) (Integer) item);
            stack.push((T) (Integer) item);
          }
        }
      }
    }

    return visited;
  }

  /**
   * This is the recursive element of recursiveBreadthFirstSearch. This recursivly traverses the
   * graph in a Breath First Search.
   *
   * @param visited List of visted vertices.
   * @param queue Queue for O(1) dequeue and enqueue.
   * @return list of visited vertices, once all vertices have been traversed.
   */
  private List<T> recursiveBfs(List<T> visited, Queue<T> queue) {

    if (queue.isEmpty()) {
      return null;
    }
    // queue is not empty

    // dequeue queue
    T vertex = queue.dequeue();

    List<Integer> sortingLinkedList = new LinkedList<>();

    // add to visited
    if (!containsTest(visited, vertex)) {
      visited.add(vertex);
    }

    for (Edge<T> edge : edges) {
      // look for source == vertex

      T source = edge.getEdgeSource();
      if (vertex.toString().equals(source.toString())) {
        // look at destinations
        T dest = edge.getEdgeDestination();

        if (!containsTest(visited, dest)) {
          sortingLinkedList.add(
              Integer.parseInt(dest.toString())); // adding destination to sorting linked list
        }
      }
    }

    Collections.sort(sortingLinkedList); // low high

    for (int item : sortingLinkedList) {
      visited.add((T) (Integer) item);
      queue.enqueue((T) (Integer) item);
    }

    recursiveBfs(visited, queue);

    return visited;
  }

  /**
   * This function initiates recursiveBfs, passing it inital roots to search from.
   *
   * @return list of vertices in Breah First Search order.
   */
  public List<T> recursiveBreadthFirstSearch() {
    List<T> visited = new LinkedList<>(); // visited list
    Set<T> roots = getRoots(); // gets roots of graph
    Queue<T> queue = new Queue<>();

    for (T root : roots) {
      queue.enqueue(root); // load up root to test
      visited = recursiveBfs(visited, queue); // recieve visited vertices
    }

    return visited;
  }

  /**
   * This function initiates recursiveDfs, passing it itial roots to search from.
   *
   * @return list of vertices in Depth First Search order.
   */
  public List<T> recursiveDepthFirstSearch() {
    List<T> visited = new LinkedList<>(); // visited list
    Set<T> roots = getRoots(); // recieve roots as a set
    Stack<T> stack = new Stack<>();

    for (T root : roots) {
      stack.push(root); // add root to the stack
      visited = recursiveDfs(visited, stack); // call the depth first search
    }

    return visited;
  }

  /**
   * This is the recursive element of recursiveDepthFirstSearch. This recursivly traverses the graph
   * in a Depth First Search.
   *
   * @param visited list of visited vertices.
   * @param stack Stack for O(1) pop and push.
   * @return list of visited vertices, once all vertices have been traversed.
   */
  private List<T> recursiveDfs(List<T> visited, Stack<T> stack) {

    if (stack.isEmpty()) {
      return null;
    }
    // stack is not empty

    // pop stack
    T vertex = stack.pop();

    List<Integer> sortingLinkedList = new LinkedList<>();

    // add to visited
    if (!containsTest(visited, vertex)) {
      visited.add(vertex);
    }

    for (Edge<T> edge : edges) {
      // look for source == vertex

      T source = edge.getEdgeSource();
      if (vertex.toString().equals(source.toString())) {
        // look at destinations
        T dest = edge.getEdgeDestination();

        if (!containsTest(visited, dest)) {
          sortingLinkedList.add(
              Integer.parseInt(dest.toString())); // adding destination to sorting linked list
        }
      }
    }

    Collections.sort(sortingLinkedList, Collections.reverseOrder()); // high low

    for (int item : sortingLinkedList) {
      // if not in visted, add to visted and queue
      if (!visited.contains(item)) {
        stack.push((T) (Integer) item);
      }
    }

    recursiveDfs(visited, stack);

    return visited;
  }
}
