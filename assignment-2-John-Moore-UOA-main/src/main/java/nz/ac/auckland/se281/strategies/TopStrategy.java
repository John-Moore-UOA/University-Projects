package nz.ac.auckland.se281.strategies;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Utils;

public class TopStrategy implements Strategy {

  // Top strategy returns the Mode of the human history

  private List<Integer> action;

  @Override
  public List<Integer> generateAction(List<Integer> humanHistory) {

    int fingers;
    action = new ArrayList<>();
    int mode;

    // get the number of fingers randomly between 1 and 5, inclusive
    fingers = Utils.getRandomNumber(1, 5);
    action.add(fingers);

    // calculate the guess value
    mode = getMode(humanHistory) + fingers;
    action.add(mode);

    return action;
  }

  private int getMode(List<Integer> inputArray) {

    int[] count = new int[5];
    int max;
    int indexOfMax = 0;
    List<Integer> additionalIndices = new ArrayList<>();
    double mode;

    // count each number instance
    for (int i = 0; i < inputArray.size(); i++) {
      // count number of times each finger count was used

      switch (inputArray.get(i)) {
        case (1):
          count[0]++;
          break;
        case (2):
          count[1]++;
          break;
        case (3):
          count[2]++;
          break;
        case (4):
          count[3]++;
          break;
        case (5):
          count[4]++;
          break;
      }
    }

    // find max of count array

    max = count[0];
    for (int i = 0; i < count.length; i++) {
      if (max < count[i]) {
        // set new maximum
        max = count[i];
        indexOfMax = i;

        // clear reference to additional maximum
        additionalIndices.clear();

      } else if (max == count[i]) {
        // add reference to additional maximum
        additionalIndices.add(i + 1);
      }
    }

    // average between max and additional indices
    mode = indexOfMax + 1;

    if (!additionalIndices.isEmpty()) {
      for (int i = 0; i < additionalIndices.size(); i++) {
        mode += additionalIndices.get(i);
      }

      mode /= additionalIndices.size() + 1;
    }

    return (int) Math.round(mode);
  }
}
