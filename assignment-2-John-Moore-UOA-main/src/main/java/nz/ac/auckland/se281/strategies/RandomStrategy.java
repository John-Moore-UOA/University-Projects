package nz.ac.auckland.se281.strategies;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Utils;

public class RandomStrategy implements Strategy {
  // psuedo random, based of getRandomNumber(int min, int max) in Utils.java

  private List<Integer> action;
  private int fingers;
  private int sum;

  @Override
  public List<Integer> generateAction(List<Integer> humanHistory) {
    // random
    action = new ArrayList<>();

    // generate random finger number
    fingers = Utils.getRandomNumber(1, 5);
    action.add(fingers);

    // generate random guess
    sum = Utils.getRandomNumber(fingers + 1, fingers + 5);
    action.add(sum);

    return action;
  }
}
