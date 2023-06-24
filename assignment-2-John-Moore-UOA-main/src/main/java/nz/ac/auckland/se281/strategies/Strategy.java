package nz.ac.auckland.se281.strategies;

import java.util.List;

public interface Strategy {
  List<Integer> generateAction(List<Integer> humanHistory);
}
