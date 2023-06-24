
package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public class CarPolicy extends PolicyBase {

  private boolean mechBreakdown;
  private String makeModel;
  private String licencePlate;

  public CarPolicy(int sumEnsured, String makeModel, String licencePlate, boolean mechBreakdown, int age, String name,
      UserDatabase user) {
    super(age, name, sumEnsured, user);
    this.mechBreakdown = mechBreakdown;
    this.makeModel = makeModel;
    this.licencePlate = licencePlate;

    setPremium(age, sumEnsured);
  }

  // calculate car premium
  public void setPremium(int age, int sumEnsured) {

    int carPremium = 0;
    if (age < 25) {
      carPremium = (int) ((double) sumEnsured * 0.15);
    } else if (age >= 25) {
      carPremium = (int) ((double) sumEnsured * 0.1);
    }

    // if mechanical breakdown warenty, hard add 80 to premium
    if (this.mechBreakdown) {
      carPremium += 80;
    }

    super.addSumPremium(carPremium);
  }

  public String getMakeModel() {
    return this.makeModel;
  }

}