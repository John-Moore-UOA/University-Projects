package nz.ac.auckland.se281.bots;

import java.util.List;
import nz.ac.auckland.se281.strategies.RandomStrategy;

public class EasyBot implements Bot {

  private List<Integer> humanHistory;

  public EasyBot(List<Integer> history) {
    this.humanHistory = history;
  }

  @Override
  public List<Integer> botAction(int round) {

    // initalise new random strategy
    RandomStrategy randomStrategy = new RandomStrategy();

    return randomStrategy.generateAction(humanHistory);
  }
}
