package nz.ac.auckland.se281.bots;

import java.util.List;
import nz.ac.auckland.se281.strategies.AverageStrategy;
import nz.ac.auckland.se281.strategies.RandomStrategy;

public class MediumBot implements Bot {

  private List<Integer> humanHistory;

  public MediumBot(List<Integer> history) {

    this.humanHistory = history;
  }

  @Override
  public List<Integer> botAction(int round) {

    RandomStrategy randomStrategy = new RandomStrategy();
    AverageStrategy averageStrategy = new AverageStrategy();

    if (round <= 3) {
      // first 3 rounds, use Random
      return randomStrategy.generateAction(humanHistory);
    } else {
      // else use average strategy
      return averageStrategy.generateAction(humanHistory);
    }
  }
}
