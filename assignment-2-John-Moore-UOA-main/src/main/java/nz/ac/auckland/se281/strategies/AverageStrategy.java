package nz.ac.auckland.se281.strategies;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Utils;

public class AverageStrategy implements Strategy {

  private List<Integer> action;

  @Override
  public List<Integer> generateAction(List<Integer> humanHistory) {

    // loop through history
    // totalSum from human sum
    // average totalSum over totatRounds
    // Random Finger 1-5 using random
    // sum returned is Fingers+averageSum

    // get average sum
    double sum = 0;
    int average;
    int fingers;
    int guess;
    action = new ArrayList<>();

    for (int i = 0; i < humanHistory.size(); i++) {
      sum += humanHistory.get(i);
    }

    average = (int) Math.round(sum / humanHistory.size());

    // get finger selection [1-5]
    fingers = Utils.getRandomNumber(1, 5);
    action.add(fingers);

    // get bot's guess
    guess = average + fingers;
    action.add(guess);

    return action;
  }
}
