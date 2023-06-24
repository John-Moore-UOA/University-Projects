package nz.ac.auckland.se281;

/** You cannot modify this class! */
public class Main {

  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD,
    MASTER;

    public static boolean isValidEnum(String s) {
      // return true if it is a valid enum (to check for valid input)
      for (Difficulty d : values()) {
        if (d.name().equals(s)) {
          return true;
        }
      }
      return false;
    }
  }

  public enum Command {
    NEW_GAME(
        2,
        "start a new game <DIFFICULTY_LEVEL> <NUMBER_POINTS_TO_WIN>\n"
            + "\t \t \t \t \t \tDIFFICULTY_LEVEL:  EASY \n"
            + "\t \t \t \t \t \t \t \t   MEDIUM \n"
            + "\t \t \t \t \t \t \t \t   HARD \n"
            + "\t \t \t \t \t \t \t \t   MASTER ",
        "What is your name?"),
    PLAY(0, "Play a round"),
    SHOW_STATS(0, "Show the current statistics"),
    HELP(0, "Print usage"),
    EXIT(0, "Exit the application");

    private final int numArgs;
    private final String message;
    private final String[] optionPrompts;

    private Command(final int numArgs, final String message) {
      this(numArgs, message, new String[] {});
    }

    private Command(final int numArgs, final String message, final String... optionPrompts) {
      this.numArgs = numArgs;
      this.message = message;
      this.optionPrompts = optionPrompts;
    }

    public boolean hasArguments() {
      return numArgs > 0;
    }

    public int getNumArgs() {
      return numArgs;
    }

    public boolean hasOptions() {
      return optionPrompts.length > 0;
    }

    public int getNumOptions() {
      return optionPrompts.length;
    }

    public String getOptionPrompt(final int index) {
      return optionPrompts[index];
    }
  }

  private static final String COMMAND_PREFIX = "morra> ";

  public static void main(final String[] args) {
    new Main(new Morra()).start();
  }

  public static String help() {
    final StringBuilder sb = new StringBuilder();

    for (final Command command : Command.values()) {
      sb.append(command).append("\t");

      // Add extra padding to align the argument counts
      // if the command name is less than the tab width.
      if (command.toString().length() < 8) {
        sb.append("\t");
      }

      if (command.numArgs > 0) {
        sb.append("[").append(command.numArgs).append(" arguments]");
      } else {
        sb.append("[no args]");
      }

      sb.append("\t").append(command.message).append(System.lineSeparator());
    }

    return sb.toString();
  }

  private static void printBanner() {
    // https://patorjk.com/software/taag/
    // https://www.freeformatter.com/java-dotnet-escape.html#before-output

    System.out.println(
        "\r\n"
            + " \r\n"
            + " .----------------.  .----------------.  .----------------.  .----------------. "
            + " .----------------. \r\n"
            + "| .--------------. || .--------------. || .--------------. || .--------------. ||"
            + " .--------------. |\r\n"
            + "| | ____    ____ | || |     ____     | || |  _______     | || |  _______     | || | "
            + "     __      | |\r\n"
            + "| ||_   \\  /   _|| || |   .'    `.   | || | |_   __ \\    | || | |_   __ \\    | ||"
            + " |     /  \\     | |\r\n"
            + "| |  |   \\/   |  | || |  /  .--.  \\  | || |   | |__) |   | || |   | |__) |   | ||"
            + " |    / /\\ \\    | |\r\n"
            + "| |  | |\\  /| |  | || |  | |    | |  | || |   |  __ /    | || |   |  __ /    | || |"
            + "   / ____ \\   | |\r\n"
            + "| | _| |_\\/_| |_ | || |  \\  `--'  /  | || |  _| |  \\ \\_  | || |  _| |  \\ \\_  |"
            + " || | _/ /    \\ \\_ | |\r\n"
            + "| ||_____||_____|| || |   `.____.'   | || | |____| |___| | || | |____| |___| | ||"
            + " ||____|  |____|| |\r\n"
            + "| |              | || |              | || |              | || |              | || | "
            + "             | |\r\n"
            + "| '--------------' || '--------------' || '--------------' || '--------------' ||"
            + " '--------------' |\r\n"
            + " '----------------'  '----------------'  '----------------'  '----------------' "
            + " '----------------' \r\n\r\n"
            + "                                            \r\n");
  }

  private final Morra game;

  public Main(final Morra game) {
    this.game = game;
  }

  public void start() {
    printBanner();
    System.out.println(help());

    String command;
    // Prompt and process commands until the exit command.
    do {
      System.out.print(COMMAND_PREFIX);
      command = Utils.scanner.nextLine().trim();
    } while (processCommand(command));
  }

  private boolean processCommand(String input) {
    if (input.isEmpty()) {
      MessageCli.NO_COMMAND.printMessage();
      return true;
    }

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

    if (!checkArgs(command, args)) {
      MessageCli.WRONG_ARGUMENT_COUNT.printMessage(
          String.valueOf(command.getNumArgs()), command.getNumArgs() > 1 ? "s" : "", commandStr);
      return true;
    }

    switch (command) {
      case NEW_GAME:
        String difficulty = args[1].toUpperCase();
        if (!Difficulty.isValidEnum(difficulty)) {
          MessageCli.INVALID_DIFFICULTY.printMessage();
          break;
        }
        // the number of points must be a positive integer
        if (!Utils.isInteger(args[2]) || Integer.parseInt(args[2]) <= 0) {
          MessageCli.INVALID_NUMBER_OF_POINTS.printMessage();
          break;
        }
        // only if the input is fine, start a new game
        game.newGame(
            Difficulty.valueOf(difficulty), Integer.parseInt(args[2]), processOptions(command));
        break;
      case PLAY:
        game.play();
        break;
      case SHOW_STATS:
        game.showStats();
        break;
      case EXIT:
        MessageCli.END.printMessage();
        // Signal that the program should exit.
        return false;
      case HELP:
        System.out.println(help());
        break;
      default:
        break;
    }

    // Signal that another command is expected.
    return true;
  }

  private String[] processOptions(final Command command) {
    final String[] options = new String[command.getNumOptions()];

    for (int i = 0; i < command.getNumOptions(); i++) {
      System.out.print("\t" + command.getOptionPrompt(i) + ": ");
      options[i] = Utils.scanner.nextLine().trim();
    }

    return options;
  }

  private boolean checkArgs(final Command command, final String[] args) {
    return command.getNumArgs() == args.length - 1;
  }
}
