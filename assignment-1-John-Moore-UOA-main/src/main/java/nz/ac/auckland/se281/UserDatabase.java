package nz.ac.auckland.se281;

import java.util.ArrayList;

public class UserDatabase {

  // initalize variables
  private String name;
  private int age;

  private ArrayList<PolicyBase> policyDataBase = new ArrayList<>();

  // constructor for class instance
  protected UserDatabase(String name, int age) {
    // alocate from variables being passed to the class instance
    this.name = name;
    this.age = age;
  }

  // getters for class instance
  protected String getName() {
    return this.name;
  }

  // getters for class instance
  protected int getAge() {
    return this.age;
  }

  public PolicyBase getReference(int index) {
    return policyDataBase.get(index);
  }

  public int getPolicyCount() {
    return policyDataBase.size();
  }

  protected void setPolicy(PolicyBase policy) {
    this.policyDataBase.add(policy);
  }

  protected PolicyBase getPolicyAtIndex(int index) {
    return this.policyDataBase.get(index);
  }

  // getting for total premium
  protected int getTotalPremium() {

    int totalPremium = 0;

    for (int i = 0; i < policyDataBase.size(); i++) {
      totalPremium += policyDataBase.get(i).getDiscount(policyDataBase.get(i).getPartPremium());
    }
    return totalPremium;
  }

  // check if already existing life policy
  public boolean containsLife() {
    for (int i = 0; i < policyDataBase.size(); i++) {
      if (policyDataBase.get(i).getClass().toString().equals("class nz.ac.auckland.se281.LifePolicy")) {
        return true;
      }
    }

    return false;
  }

}
