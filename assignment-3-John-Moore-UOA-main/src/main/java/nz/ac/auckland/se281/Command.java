package nz.ac.auckland.se281;

/**
 * The commands that the user can enter into the CLI.
 *
 * <p>You should not modify this class.
 */
public enum Command {
  OPEN_FILE(1, "Open the file <FILENAME>"),
  INFO(0, "Print information about the current loaded file"),

  LIST_VERTICIES_EDGES(0, "List the set of vertices and edges"),
  LIST_ROOT_VERTICIES(0, "List the set of root vertices"),

  CHECK_REFLEXIVITY(0, "Check reflexivity of the graph"),
  CHECK_SYMMETRY(0, "Check symmetry of the graph"),
  CHECK_TRANSITIVITY(0, "Check transitivity of the graph"),
  CHECK_ANTISYMMETRY(0, "Check anti-symmetry of the graph"),
  CHECK_EQUIVALENCE(0, "Check equivalence of the graph"),
  COMPUTE_EQUIVALENCE(1, "Compute equivalence class for a vertex in loaded file <VERTEX>"),

  GRAPH_SEARCH_IBFS(
      0, "Perform a breadth-first search on the current loaded file, and display the BFS order"),
  GRAPH_SEARCH_IDFS(
      0, "Perform a depth-first search on the current loaded file, and display the DFS order"),

  GRAPH_SEARCH_RBFS(
      0,
      "Perform a recursive breadth-first search on the current loaded file, and display the BFS"
          + " order"),
  GRAPH_SEARCH_RDFS(
      0,
      "Perform a recursive depth-first search on the current loaded file, and display the DFS"
          + " order"),

  HELP(0, "Print usage"),
  EXIT(0, "Exit the application");

  private static final int TAB_WIDTH = 8;

  private final int numArgs;

  private final String message;

  /**
   * Create a new command.
   *
   * @param numArgs The number of arguments the command takes.
   * @param message The help message for the command.
   */
  private Command(final int numArgs, final String message) {
    this.numArgs = numArgs;
    this.message = message;
  }

  /**
   * Get the number of arguments the command requires from the user.
   *
   * @return The number of arguments.
   */
  public int getNumArgs() {
    return numArgs;
  }

  /**
   * Get the help message and required arguments for the command.
   *
   * @return The help message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Calculate the number of tabs needed to align the help messages.
   *
   * @return The number of tabs to pad.
   */
  public int calculatePaddingSize() {
    int longest = 0;

    for (final Command command : Command.values()) {
      longest = Math.max(longest, command.toString().length());
    }

    return (longest / TAB_WIDTH) - (toString().length() / TAB_WIDTH);
  }
}
