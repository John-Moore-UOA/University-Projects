package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.bots.Bot;
import nz.ac.auckland.se281.bots.BotFactory;

public class Morra {

  private int round;
  private String userName;
  private Bot jarvis;

  private int totalRounds;
  private int playerScore;
  private int botScore;

  private List<Integer> humanSumHistory;

  private boolean activeGame;

  public Morra() {
    activeGame = false;
  }

  public void newGame(Difficulty difficulty, int pointsToWin, String[] options) {

    this.activeGame = true;
    // Ensure that everything is cleared

    this.userName = options[0];
    this.round = 1;
    this.totalRounds = pointsToWin;
    humanSumHistory = new ArrayList<>();

    playerScore = 0;
    botScore = 0;

    try {
      humanSumHistory.clear();
    } catch (NullPointerException npe) {
      // don't do anything, becuase i don't care ?
    }

    // "welcome Username"
    MessageCli.WELCOME_PLAYER.printMessage(userName);

    // call bot factory to form new bot
    jarvis = BotFactory.formBot(difficulty, this.humanSumHistory);
  }

  public void play() {

    if (!activeGame) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }

    // check if game is active

    // Player Variables
    List<Integer> playerAction;

    // Bot Variables
    List<Integer> botAction;

    // show current round
    MessageCli.START_ROUND.printMessage(this.round + "");

    playerAction = recieveUserName();

    // return -> botAction = [botFingers,botSum]
    botAction = jarvis.botAction(this.round);
    String botFingersStr = botAction.get(0) + "";
    String botSumStr = botAction.get(1) + "";

    String playerFingersStr = playerAction.get(0) + "";
    String playerSumStr = playerAction.get(1) + "";

    MessageCli.PRINT_INFO_HAND.printMessage(userName, playerFingersStr, playerSumStr);
    MessageCli.PRINT_INFO_HAND.printMessage("Jarvis", botFingersStr, botSumStr);
    roundOutcome(playerAction, botAction);

    humanSumHistory.add(playerAction.get(0));

    this.round++;
  }

  private List<Integer> recieveUserName() {

    // initalise variables
    int playerFingers;
    int playerSum;
    List<Integer> playerActions = new ArrayList<>();

    boolean invalid = true;
    while (invalid) {

      // ask for input
      MessageCli.ASK_INPUT.printMessage();

      // scan line in
      String inputStr = Utils.scanner.nextLine();

      // split by spaces
      String[] delimitedStr = inputStr.split(" ");

      if (delimitedStr.length != 2) {
        // Too many inputs
        MessageCli.INVALID_INPUT.printMessage();

      } else {

        String fingerStr = delimitedStr[0];
        String sumStr = delimitedStr[1];

        // try to parse integer, if NFE return -1
        playerFingers = tryPaseInt(fingerStr);
        playerSum = tryPaseInt(sumStr);

        // between Fingers: [1,5] and sum: [1,10] (inclusive)
        if (playerFingers < 1
            || playerFingers > 5 //
            || playerSum < 1
            || playerSum > 10) {
          // out of acceptable range
          MessageCli.INVALID_INPUT.printMessage();
        } else {
          // valid number of inputs
          // valid integer inputs
          playerActions.add(playerFingers);
          playerActions.add(playerSum);
          invalid = false;
        }
      }
    }

    return playerActions;
  }

  private int playerWins(List<Integer> playerAction, List<Integer> botAction) {
    // 0 draw
    // 1 player wins
    // 2 bot wins
    int totalSum = playerAction.get(0) + botAction.get(0);
    int result = 0;

    if (playerAction.get(1) == totalSum) {
      result = 1;
    }

    if (botAction.get(1) == totalSum) {
      result = 2;
    }

    if (playerAction.get(1) == botAction.get(1)) {
      result = 0;
    }

    return result;
  }

  private void roundOutcome(List<Integer> playerAction, List<Integer> botAction) {

    // who wins, 0 draw, 1 human, 2 bot
    int result = playerWins(playerAction, botAction);
    int pointsToWin;

    if (result == 0) {
      // DRAW
      MessageCli.PRINT_OUTCOME_ROUND.printMessage("DRAW");
    } else {

      if (result == 1) {
        // HUMAN_WINS
        playerScore++;

        pointsToWin = this.totalRounds - playerScore;
        MessageCli.PRINT_OUTCOME_ROUND.printMessage("HUMAN_WINS");

        if (pointsToWin == 0) {
          gameWin(userName); // end game
        }
        return;

      } else {
        // AI_WINS
        botScore++;

        pointsToWin = this.totalRounds - botScore;
        MessageCli.PRINT_OUTCOME_ROUND.printMessage("AI_WINS");

        if (pointsToWin == 0) {
          gameWin("Jarvis"); // end game
        }
        return;
      }
    }
  }

  public int tryPaseInt(String str) {

    int num;

    try {
      num = Integer.parseInt(str);
    } catch (NumberFormatException e) {

      // if could not parse integer, return -1 as an invalid result input
      return -1;
    }

    return num;
  }

  public void gameWin(String name) {
    // win message
    // reset
    MessageCli.END_GAME.printMessage(name, round + "");

    activeGame = false;
  }

  public void showStats() {

    // if there is game being played
    if (!activeGame) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }

    // calculate required points to win the game
    int playerPointsToWin = totalRounds - playerScore;
    int botPointsToWin = totalRounds - botScore;

    // display current players score, and required points count to win the current game
    MessageCli.PRINT_PLAYER_WINS.printMessage(userName, playerScore + "", playerPointsToWin + "");

    MessageCli.PRINT_PLAYER_WINS.printMessage("Jarvis", botScore + "", botPointsToWin + "");
  }
}
