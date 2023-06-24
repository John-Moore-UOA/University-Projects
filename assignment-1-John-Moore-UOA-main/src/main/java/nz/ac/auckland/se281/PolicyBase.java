package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public abstract class PolicyBase {

  private int sumPremium = 0;
  private int premiumPer = 0;
  private int sumEnsured = 0;

  protected static int age;
  protected static String name;
  protected UserDatabase user = null;

  public PolicyBase(int age, String name, int sumEnsured, UserDatabase user) {
    this.age = age;
    this.name = name;
    this.sumEnsured = sumEnsured;
    this.user = user;
  }

  protected int getSumEnsured() {
    return this.sumEnsured;
  }

  protected int getPartPremium() {
    return this.premiumPer;
  }

  protected int getSumPremium() {
    return this.sumPremium;
  }

  protected void addSumPremium(int value) {
    this.premiumPer = value;
    this.sumPremium += value;
  }

  // discound, 1 for 100%, 2 for 90%, 3+ for 80%
  protected int getDiscount(int priorPremium) {
    if (user.getPolicyCount() < 2) {
      return (int) ((double) priorPremium * 1);
    } else if (user.getPolicyCount() == 2) {
      return (int) ((double) priorPremium * 0.9);
    } else {
      return (int) ((double) priorPremium * 0.8);
    }
  }

}
