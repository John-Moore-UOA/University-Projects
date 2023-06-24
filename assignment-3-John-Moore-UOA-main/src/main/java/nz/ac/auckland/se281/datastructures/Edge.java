package nz.ac.auckland.se281.datastructures;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {
  private T source;
  private T destination;

  public Edge(T source, T destination) {
    this.source = source;
    this.destination = destination;
  }

  protected T getEdgeSource() {
    return source;
  }

  protected T getEdgeDestination() {
    return destination;
  }
}
