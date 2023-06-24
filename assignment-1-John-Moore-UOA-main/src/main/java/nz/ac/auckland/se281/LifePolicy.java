package nz.ac.auckland.se281;

public class LifePolicy extends PolicyBase {

  public LifePolicy(int sumEnsured, int age, String loadedProfile, UserDatabase user) {
    super(age, loadedProfile, sumEnsured, user);
    getpremium(age, sumEnsured);
  }

  // calculate premium for life policy
  public void getpremium(int age, int sumEnsured) {
    int lifePremium = 0;
    lifePremium = (int) (sumEnsured * ((1 + ((double) age / 100)) / 100));

    super.addSumPremium(lifePremium);

  }

}
