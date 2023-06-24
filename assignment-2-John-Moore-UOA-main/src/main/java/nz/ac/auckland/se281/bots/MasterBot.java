package nz.ac.auckland.se281.bots;

import java.util.List;
import nz.ac.auckland.se281.strategies.AverageStrategy;
import nz.ac.auckland.se281.strategies.RandomStrategy;
import nz.ac.auckland.se281.strategies.TopStrategy;

public class MasterBot implements Bot {

  private List<Integer> humanHistory;

  public MasterBot(List<Integer> history) {
    this.humanHistory = history;
  }

  @Override
  public List<Integer> botAction(int round) {

    // random for first 3 rounds
    // then alternate
    //  Average --> Top --> Average --> ...

    RandomStrategy randomStrategy = new RandomStrategy();
    TopStrategy topStrategy = new TopStrategy();
    AverageStrategy averageStrategy = new AverageStrategy();

    if (round <= 3) {
      return randomStrategy.generateAction(humanHistory);
    } else {

      if (round % 2 == 0) {
        // average Strategy on odd rounds past round 3
        return averageStrategy.generateAction(humanHistory);

      } else {
        // top strategy on even rounds past round 3
        return topStrategy.generateAction(humanHistory);
      }
    }
  }
}
