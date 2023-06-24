package nz.ac.auckland.se281;

/** You cannot modify this class! */
public enum MessageCli {
  COMMAND_NOT_FOUND(
      "Error! Command not found! (run 'help' for the list of available commands): \"%s\""),
  WRONG_ARGUMENT_COUNT(
      "Error! Incorrect number of arguments provided. Expected %s argument%s for the \"%s\""
          + " command"),
  NO_COMMAND("Error! You did not give any command :)"),
  END("You closed the terminal. Goodbye."),
  INVALID_DIFFICULTY(
      "Error! Incorrect difficutly level. The possible difficulty leves are EASY,"
          + " MEDIUM, H̉ARD, and MASTER"),
  INVALID_NUMBER_OF_POINTS(
      "Error! Incorrect number of points. It should be a positive integer greater than zero"),
  ASK_INPUT("Give <fingers> <sum> and press enter"),
  INVALID_INPUT(
      "Error! Invalid input, you should give two integers numbers: <fingers> <sum>, <fingers>"
          + " should be between 1 and 5 (inclusive) and <sum> should be between 1 and 10"
          + " (inclusive), please try again"),
  GAME_NOT_STARTED("Error! Game not started yet. Please start a new game first"),
  WELCOME_PLAYER("Welcome %s!"),
  START_ROUND("Start Round #%s:"),
  PRINT_INFO_HAND("Player %s: fingers: %s, sum: %s"),
  PRINT_OUTCOME_ROUND("Result: %s"),
  PRINT_PLAYER_WINS("%s won %s rounds, %s more to win the game"),
  END_GAME("End game! %s won in %s rounds");
  private final String msg;

  private MessageCli(final String msg) {
    this.msg = msg;
  }

  public String getMessage(final String... args) {
    String tmpMessage = msg;

    for (final String arg : args) {
      tmpMessage = tmpMessage.replaceFirst("%s", arg);
    }

    return tmpMessage;
  }

  public void printMessage(final String... args) {
    print(getMessage(args));
  }

  public void printMessage() {
    print(msg);
  }

  private void print(String s) {
    System.out.println(s);
  }

  @Override
  public String toString() {
    return msg;
  }
}
