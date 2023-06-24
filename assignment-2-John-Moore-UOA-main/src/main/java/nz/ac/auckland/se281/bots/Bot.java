package nz.ac.auckland.se281.bots;

import java.util.List;

public interface Bot {
  // this will contain bot related stuff
  //
  // Name
  // Difficulty
  // Move Set

  List<Integer> botAction(int round); // should be overrided everywhere
}
