package nz.ac.auckland.se281.bots;

import java.util.List;
import nz.ac.auckland.se281.strategies.RandomStrategy;
import nz.ac.auckland.se281.strategies.TopStrategy;

public class HardBot implements Bot {

  private List<Integer> humanHistory;

  public HardBot(List<Integer> history) {

    this.humanHistory = history;
  }

  @Override
  public List<Integer> botAction(int round) {

    // random for first 3 rounds
    // then use top strategy

    RandomStrategy randomStrategy = new RandomStrategy();
    TopStrategy topStrategy = new TopStrategy();

    if (round <= 3) {
      return randomStrategy.generateAction(humanHistory);
    } else {

      return topStrategy.generateAction(humanHistory);
    }
  }
}
