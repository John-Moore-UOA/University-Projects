package nz.ac.auckland.se281.bots;

import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;

public class BotFactory {

  // should this be static, it makes sense, but it 'might' bug tests, but its just a factory

  public static Bot formBot(Difficulty difficulty, List<Integer> history) {

    switch (difficulty) {
      case EASY:
        return new EasyBot(history); // return instance reference of easy bot

      case MEDIUM:
        return new MediumBot(history);

      case HARD:
        return new HardBot(history);

      case MASTER:
        return new MasterBot(history);

      default:
        return new EasyBot(history);
    }
  }
}
