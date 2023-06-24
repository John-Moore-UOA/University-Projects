package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public class HomePolicy extends PolicyBase {

  private String address;
  private boolean isRental;

  public HomePolicy(int sumEnsured, String address, boolean isRental, int age, String name, UserDatabase user) {
    super(age, name, sumEnsured, user);
    this.isRental = isRental;
    this.address = address;

    getpremium(sumEnsured);
  }

  // calculate premium for home policy
  public void getpremium(int sumEnsured) {

    int homePremium = 0;

    if (isRental) {
      homePremium = (int) (sumEnsured * 2 / 100);
    } else {
      homePremium = (int) (sumEnsured * 1 / 100);
    }

    super.addSumPremium(homePremium);

  }

  public String getAddress() {
    return this.address;
  }

  public boolean getRental() {
    return this.isRental;
  }

}