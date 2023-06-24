package nz.ac.auckland.se281;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import nz.ac.auckland.se281.datastructures.Edge;
import nz.ac.auckland.se281.datastructures.Graph;

/**
 * The calculator that reports properties of graphs to the user.
 *
 * <p>You should not modify this class.
 */
public class GraphCalculator {
  private static final Path TESTCASES = Path.of("testcases");

  private final Path file;

  private final Collection<GraphNode> verticies;

  private final Collection<GraphEdge> edges;

  private final Graph<String> graph;

  /**
   * Creates a new graph calculator.
   *
   * @param filename The name of the test case to read.
   * @throws FileNotFoundException If the file does not exist.
   */
  public GraphCalculator(final String filename) throws FileNotFoundException {
    file = TESTCASES.resolve(filename);

    final GraphParser parser = new GraphParser(new FileInputStream(file.toFile()));

    this.verticies = parser.getNodes().values();
    this.edges = parser.getEdges().values();

    final Set<String> verticies = new HashSet<>();

    for (final GraphNode node : this.verticies) {
      verticies.add(node.getId());
    }

    final Set<Edge<String>> edges = new HashSet<>();

    for (final GraphEdge edge : this.edges) {
      edges.add(new Edge<>(edge.getNode1().getId(), edge.getNode2().getId()));
    }

    graph = new Graph<>(verticies, edges);
  }

  /** Prints the filename of the test case that is loaded. */
  public void printInfo() {
    MessageCli.INFO.printMessage(TESTCASES.relativize(file).toString());
  }

  /** Prints the set of verticies and edges in the graph. */
  public void printVerticiesEdges() {
    final StringBuilder sb = new StringBuilder("Verticies:").append(System.lineSeparator());

    // Vertically print each vertex.
    for (final GraphNode node : verticies) {
      sb.append("\t").append(node.getId()).append(System.lineSeparator());
    }

    sb.append(System.lineSeparator()).append("Edges:").append(System.lineSeparator());

    // Vertically print each edge between verticies.
    for (final GraphEdge edge : edges) {
      sb.append("\t")
          .append(edge.getNode1().getId())
          .append(" -> ")
          .append(edge.getNode2().getId())
          .append(System.lineSeparator());
    }

    System.out.print(sb.toString());
  }

  /** Prints the set of verticies in the graph that are root verticies. */
  public void printRoots() {
    System.out.println(graph.getRoots());
  }

  /** Prints if the set of verticies in the graph exhibit the reflexivity property. */
  public void printReflexitivity() {
    MessageCli.GRAPH_REFLEXIVE.printMessage(prefixNot(graph.isReflexive()));
  }

  /** Prints if the set of edges in the graph exhibit the symmetric property. */
  public void printSymmetry() {
    MessageCli.GRAPH_SYMMETRIC.printMessage(prefixNot(graph.isSymmetric()));
  }

  /** Prints if the set of edges in the graph exhibit transitivity. */
  public void printTransitivity() {
    MessageCli.GRAPH_TRANSITIVE.printMessage(prefixNot(graph.isTransitive()));
  }

  /** Prints if the set of edges in the graph exhibit anti-symmetry. */
  public void printAntiSymmetry() {
    MessageCli.GRAPH_ANTI_SYMMETRIC.printMessage(prefixNot(graph.isAntiSymmetric()));
  }

  /** Prints if the entire graph is considered an equivalence relation. */
  public void printEquivalence() {
    MessageCli.GRAPH_EQUIVALENCE.printMessage(prefixNot(graph.isEquivalence()));
  }

  /**
   * Prints the verticies in the equivalence class of the given vertex.
   *
   * @param vertex The vertex to compute the equivalence class of.
   */
  public void printEquivalenceClass(final String vertex) {
    final Object[] equivalenceClass = graph.getEquivalenceClass(vertex).toArray();
    Arrays.sort(equivalenceClass);
    System.out.println(Arrays.toString(equivalenceClass));
  }

  /** Prints the search order of an iterative breadth first search. */
  public void printIterativeBreadthFirstSearch() {
    System.out.println(Arrays.toString(graph.iterativeBreadthFirstSearch().toArray()));
  }

  /** Prints the search order of an iterative depth first search. */
  public void printDepthFirstSearch() {
    System.out.println(Arrays.toString(graph.iterativeDepthFirstSearch().toArray()));
  }

  /** Prints the search order of a recursive breadth first search. */
  public void printRecursiveBreadthFirstSearch() {
    System.out.println(Arrays.toString(graph.recursiveBreadthFirstSearch().toArray()));
  }

  /** Prints the search order of a recursive depth first search. */
  public void printRecursiveDepthFirstSearch() {
    System.out.println(Arrays.toString(graph.recursiveDepthFirstSearch().toArray()));
  }

  /**
   * Prefixes the given string with "NOT " if the property is not observed.
   *
   * @param isPropertyTrue If the property is observed.
   * @return The prefix.
   */
  private String prefixNot(final boolean isPropertyTrue) {
    return isPropertyTrue ? "" : "NOT ";
  }
}
