package nz.ac.auckland.se281;

/**
 * Templates of messages that can be printed to the CLI.
 *
 * <p>You should not modify this class.
 */
public enum MessageCli {
  COMMAND_NOT_FOUND(
      "Error! Command not found! (run 'help' for the list of available commands): \"%s\""),
  WRONG_ARGUMENT_COUNT(
      "Error! Incorrect number of arguments provided. Expected %s argument%s for the \"%s\""
          + " command"),

  OPENED_FILE_SUCCESSFULLY("Successfully opened graph from file %s"),
  FILE_NOT_OPEN("Error: no valid file open"),
  FILE_NOT_FOUND("Error: file '%s' not found in `testcases` folder"),
  INFO("File name: %s"),

  GRAPH_REFLEXIVE("The graph is %sreflexive"),
  GRAPH_SYMMETRIC("The graph is %ssymmetric"),
  GRAPH_ANTI_SYMMETRIC("The graph is %santisymmetric"),
  GRAPH_EQUIVALENCE("The graph is %san equivalence relation"),
  GRAPH_TRANSITIVE("The graph is %stransitive"),

  END("You closed the terminal. Goodbye.");

  private final String msg;

  /**
   * Create a new message from a message template.
   *
   * @param msg The message template.
   */
  private MessageCli(final String msg) {
    this.msg = msg;
  }

  /**
   * Fill the message template with the given arguments. Each "%s" in the template is replaced with
   * an argument in the same order they are provided.
   *
   * @param args The arguments to fill in the template.
   * @return The filled message.
   */
  public String getMessage(final String... args) {
    String tmpMessage = msg;

    for (final String arg : args) {
      tmpMessage = tmpMessage.replaceFirst("%s", arg);
    }

    return tmpMessage;
  }

  /**
   * Print the message to the CLI after filling the template using {@link #getMessage(String...)}.
   *
   * @param args The arguments to fill in the template.
   */
  public void printMessage(final String... args) {
    System.out.println(getMessage(args));
  }
}
