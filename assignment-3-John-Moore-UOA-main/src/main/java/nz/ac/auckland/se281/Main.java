package nz.ac.auckland.se281;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The entry point of the graph calculator command line interface.
 *
 * <p>You should not modify this class.
 */
public class Main {
  private static final String COMMAND_PREFIX = "graph-calculator> ";

  public static void main(final String[] args) {
    new Main(new Scanner(System.in)).start();
  }

  /**
   * The help menu of commands for the graph caluclator command line interface.
   *
   * @return The help menu.
   */
  public static String help() {
    final StringBuilder sb = new StringBuilder();

    for (final Command command : Command.values()) {
      sb.append(command).append("\t");

      // Add extra padding to vertically align the argument counts.
      sb.append("\t".repeat(command.calculatePaddingSize()));

      if (command.getNumArgs() > 0) {
        sb.append("[").append(command.getNumArgs()).append(" arguments]");
      } else {
        sb.append("[no args]");
      }

      // Add extra padding to vertically align the help messages.
      sb.append("\t").append(command.getMessage()).append(System.lineSeparator());
    }

    return sb.toString();
  }

  private final Scanner scanner;

  private GraphCalculator calculator;

  /**
   * Create a new command line interface.
   *
   * @param scanner The scanner to read user input from.
   */
  public Main(final Scanner scanner) {
    this.scanner = scanner;
  }

  /** Process commands from the user for the graph calculator command line interface. */
  public void start() {
    System.out.println(help());

    String command;

    // Prompt and process commands until the exit command.
    do {
      System.out.print(COMMAND_PREFIX);
      command = scanner.nextLine().trim();
    } while (processCommand(command));
  }

  /**
   * Execute a command entered by the user and determine if more commands are expected.
   *
   * @param input The command entered by the user.
   * @return If the program should continue to wait for another command.
   */
  private boolean processCommand(String input) {
    // Remove whitespace at the beginning and end of the input.
    input = input.trim();

    final String[] args = input.split(" ");

    // Allow any case, and dashes to be used instead of underscores.
    final String commandStr = args[0].toUpperCase().replaceAll("-", "_");

    final Command command;

    try {
      // Command names correspond to the enum names.
      command = Command.valueOf(commandStr);
    } catch (final Exception e) {
      MessageCli.COMMAND_NOT_FOUND.printMessage(commandStr);
      return true;
    }

    // Check that the correct number of arguments were provided.
    if (!checkArgs(command, args)) {
      final String numCommandArgs = String.valueOf(command.getNumArgs());
      final String plural = command.getNumArgs() > 1 ? "s" : "";
      MessageCli.WRONG_ARGUMENT_COUNT.printMessage(numCommandArgs, plural, commandStr);
      return true;
    }

    // A file is only open if there is a calculator instance.
    // The user cannot do anything other than open a file, exit,
    // or get help if there is no file.
    if (calculator == null
        && command != Command.OPEN_FILE
        && command != Command.EXIT
        && command != Command.HELP) {
      MessageCli.FILE_NOT_OPEN.printMessage();
      return true;
    }

    switch (command) {
      case OPEN_FILE:
        final String filename = args[1];

        try {
          calculator = new GraphCalculator(filename);
          MessageCli.OPENED_FILE_SUCCESSFULLY.printMessage(filename);
        } catch (FileNotFoundException e) {
          MessageCli.FILE_NOT_FOUND.printMessage(filename);
        }
        break;
      case INFO:
        calculator.printInfo();
        break;
      case LIST_VERTICIES_EDGES:
        calculator.printVerticiesEdges();
        break;
      case LIST_ROOT_VERTICIES:
        calculator.printRoots();
        break;
      case CHECK_REFLEXIVITY:
        calculator.printReflexitivity();
        break;
      case CHECK_SYMMETRY:
        calculator.printSymmetry();
        break;
      case CHECK_TRANSITIVITY:
        calculator.printTransitivity();
        break;
      case CHECK_ANTISYMMETRY:
        calculator.printAntiSymmetry();
        break;
      case CHECK_EQUIVALENCE:
        calculator.printEquivalence();
        break;
      case COMPUTE_EQUIVALENCE:
        calculator.printEquivalenceClass(args[1]);
        break;
      case GRAPH_SEARCH_IBFS:
        calculator.printIterativeBreadthFirstSearch();
        break;
      case GRAPH_SEARCH_IDFS:
        calculator.printDepthFirstSearch();
        break;
      case GRAPH_SEARCH_RBFS:
        calculator.printRecursiveBreadthFirstSearch();
        break;
      case GRAPH_SEARCH_RDFS:
        calculator.printRecursiveDepthFirstSearch();
        break;
      case EXIT:
        MessageCli.END.printMessage();

        // Signal that the program should exit.
        return false;
      case HELP:
        System.out.println(help());
        break;
    }

    // Signal that another command is expected.
    return true;
  }

  /**
   * Check that the correct number of arguments were provided for the command.
   *
   * @param command The command to check.
   * @param args The arguments provided by the user.
   * @return If the correct number of arguments were provided.
   */
  private boolean checkArgs(final Command command, final String[] args) {
    return command.getNumArgs() == args.length - 1;
  }
}
