package nz.ac.auckland.se281;

import java.util.ArrayList;
import javax.security.auth.PrivateCredentialPermission;
import org.eclipse.jgit.transport.CredentialItem.Username;

import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {

  // ArrayList of type UserDatabase class instances
  private ArrayList<UserDatabase> dataBaseArray = new ArrayList<>();
  private String loadedProfile = null;
  private boolean loadedProfileState = false;

  public InsuranceSystem() {
  }

  public void printDatabase() {

    int numProfiles = dataBaseArray.size();

    // check grammer for number of profiles
    if (numProfiles == 1) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage("1", "", ":");
    } else if (numProfiles == 0) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage("0", "s", ".");
    } else {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(String.format("%d", numProfiles), "s", ":");
    }

    // List Profiles
    for (int i = 0; i < numProfiles; i++) {

      String tempName = dataBaseArray.get(i).getName();
      String tempAge = Integer.toString(dataBaseArray.get(i).getAge());

      int tempPolicyCount = dataBaseArray.get(i).getPolicyCount();
      String tempPolciyCountStr = tempPolicyCount + "";

      // change to total discounted premium
      int tempTotalPremium = dataBaseArray.get(i).getTotalPremium();

      // grammer on displaying policies
      if (tempName.equals(loadedProfile)) {

        if (tempPolicyCount == 1) {

          MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage("*** ", Integer.toString(i + 1), tempName, tempAge,
              tempPolciyCountStr, "y", tempTotalPremium + "");
        } else {
          MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage("*** ", Integer.toString(i + 1), tempName, tempAge,
              tempPolciyCountStr, "ies", tempTotalPremium + "");
        }

      } else {

        if (tempPolicyCount == 1) {

          MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage("", Integer.toString(i + 1), tempName, tempAge,
              tempPolciyCountStr, "y", tempTotalPremium + "");
        } else {

          MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage("", Integer.toString(i + 1), tempName, tempAge,
              tempPolciyCountStr, "ies", tempTotalPremium + "");
        }

      }

      // List all polcies in order
      for (int j = 0; j < dataBaseArray.get(i).getPolicyCount(); j++) {

        PolicyBase policy = dataBaseArray.get(i).getReference(j);

        String tempSumEnsured = policy.getSumEnsured() + "";

        int tempPriorPremium = policy.getSumPremium();
        String tempPriorPremiumStr = tempPriorPremium + "";

        String tempDiscountPremium = policy.getDiscount(tempPriorPremium) + "";

        String policyClass = policy.getClass().toString();

        if (policyClass.equals("class nz.ac.auckland.se281.LifePolicy")) {
          // if policy is Life Policy

          MessageCli.PRINT_DB_LIFE_POLICY.printMessage(tempSumEnsured, tempPriorPremiumStr, tempDiscountPremium);

        } else if (policyClass.equals("class nz.ac.auckland.se281.CarPolicy")) {
          // if Car policy

          CarPolicy tempCarPolicy = (CarPolicy) policy;
          MessageCli.PRINT_DB_CAR_POLICY.printMessage(tempCarPolicy.getMakeModel(), tempSumEnsured, tempPriorPremiumStr,
              tempDiscountPremium);
        } else {
          // else if Home Policy

          HomePolicy tempHomePolicy = (HomePolicy) policy;
          MessageCli.PRINT_DB_HOME_POLICY.printMessage(tempHomePolicy.getAddress(), tempSumEnsured, tempPriorPremiumStr,
              tempDiscountPremium);
        }

      }

    }
  }

  // return cammelCaseString method
  private String getCammelString(String inputName) {
    return ("" + inputName.charAt(0)).toUpperCase() + inputName.substring(1).toLowerCase();
  }

  public void createNewProfile(String inputName, String age) {

    // don't allow creation if profile is loaded
    if (loadedProfileState) {
      MessageCli.CANNOT_CREATE_WHILE_LOADED.printMessage(loadedProfile);
      return;
    }

    // change username to appropriate casing
    boolean isUnique = true;
    String UserName = getCammelString(inputName);

    // Check if username is unique
    for (int i = 0; i < dataBaseArray.size(); i++) {
      if (dataBaseArray.get(i).getName().equals(UserName)) {
        isUnique = false;
      }
    }

    // Is unique username?
    if (isUnique) {

      // Is username longer or equal to 3 characters?
      if (UserName.length() >= 3) {

        // Check Age Requirements?
        int userAge = Integer.parseInt(age);

        // Is Age positive integer?
        if (userAge >= 0) {

          // Add user reference to array list
          UserDatabase user = new UserDatabase(UserName, userAge);
          dataBaseArray.add(user);

          // Print successful profile created message
          MessageCli.PROFILE_CREATED.printMessage(UserName, Integer.toString(userAge));

        } else {
          // Invalid Age: Age is not positive integer
          MessageCli.INVALID_AGE.printMessage(age, UserName);
        }
      } else {
        // Invalid username: Username to short
        MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(UserName);
      }
    } else {
      // Invalid username: username not unique
      MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(UserName);
    }
  }

  // method to return index of string in array
  private int getIndexOfUserName(String name) {

    for (int i = 0; i < dataBaseArray.size(); i++) {
      if (dataBaseArray.get(i).getName().equals(name)) {
        return i;
      }
    }

    // can't find index
    return -1;
  }

  public void loadProfile(String inputName) {

    // user might type the username in any case, but the system will always process
    // it in title case.
    String userName = getCammelString(inputName);
    boolean couldNotFindUser = true;

    for (int i = 0; i < dataBaseArray.size(); i++) {
      if (dataBaseArray.get(i).getName().equals(userName)) {
        couldNotFindUser = false;
        break;
      }
    }

    if (couldNotFindUser) {
      MessageCli.NO_PROFILE_FOUND_TO_LOAD.printMessage(userName);
    } else {
      MessageCli.PROFILE_LOADED.printMessage(userName);
      loadedProfile = userName;
      loadedProfileState = true;
    }

  }

  public void unloadProfile() {

    // if loaded true
    // set false
    // wipe loadedprofile string
    if (loadedProfileState) {
      MessageCli.PROFILE_UNLOADED.printMessage(loadedProfile);
      loadedProfile = null;
      loadedProfileState = false;
    } else {
      MessageCli.NO_PROFILE_LOADED.printMessage();
    }

  }

  public void deleteProfile(String inputName) {

    // transform to cammelCase
    String userName = getCammelString(inputName);
    int index = 0;
    boolean foundProfile = false;

    // if loaded
    if (userName.equals(loadedProfile)) {
      MessageCli.CANNOT_DELETE_PROFILE_WHILE_LOADED.printMessage(loadedProfile);
      return;
    }

    // should be replaced with the find index method
    for (int i = 0; i < dataBaseArray.size(); i++) {
      if (dataBaseArray.get(i).getName().equals(userName)) {
        index = i;
        foundProfile = true;
        break;
      }
    }

    // Error, can't find profile
    if (!foundProfile) {
      MessageCli.NO_PROFILE_FOUND_TO_DELETE.printMessage(userName);
      return;
    }

    // delete profile
    dataBaseArray.remove(index);
    MessageCli.PROFILE_DELETED.printMessage(userName);

  }

  public void createPolicy(PolicyType type, String[] options) {

    // get loaded profile age
    // find index of String loadedProfile in dataBaseArray

    boolean policyCreated = false;
    PolicyBase policybase = null;

    if (!loadedProfileState) {
      MessageCli.NO_PROFILE_FOUND_TO_CREATE_POLICY.printMessage();
      return;
    }

    int index = 0;
    int age = 0;

    for (int i = 0; i < dataBaseArray.size(); i++) {
      if (dataBaseArray.get(i).getName().equals(loadedProfile)) {
        index = i;
        age = dataBaseArray.get(i).getAge();
        break;
      }
    }

    if (type == PolicyType.CAR) {
      // Type Car policy

      int sumEnsured = Integer.parseInt(options[0]);
      String makeAndModel = options[1].toString();
      String licensePlate = options[2];
      boolean mechanicalWarranty = false;

      String tempStrCar = options[3].toString();
      String tempCharCar = tempStrCar.substring(0, 1).toLowerCase();

      if (tempCharCar.equals("y")) {
        mechanicalWarranty = true;
      } else if (tempCharCar.equals("n")) {
        mechanicalWarranty = false;
      }

      // make new instance of Car Policy
      CarPolicy carPolicy = new CarPolicy(sumEnsured, makeAndModel, licensePlate, mechanicalWarranty, age,
          this.loadedProfile, dataBaseArray.get(getIndexOfUserName(loadedProfile)));
      policyCreated = true;
      policybase = (PolicyBase) carPolicy;

    } else if (type == PolicyType.HOME) {

      int sumEnsured = Integer.parseInt(options[0]);
      String address = options[1].toString();
      boolean isRental = false;

      // convert address String to first letter lowercase
      String tempStr = options[2].toString();
      String tempChar = tempStr.substring(0, 1).toLowerCase();

      if (tempChar.equals("y")) {
        isRental = true;
      } else if (tempChar.equals("n")) {
        isRental = false;
      }

      // Contruct new instance of a home policy
      HomePolicy homePolicy = new HomePolicy(sumEnsured, address, isRental, age, this.loadedProfile,
          dataBaseArray.get(getIndexOfUserName(loadedProfile)));
      policyCreated = true;
      policybase = (PolicyBase) homePolicy;

    } else if (type == PolicyType.LIFE) {

      int sumEnsured = Integer.parseInt(options[0]);

      // if age less or equal to 100
      if (age <= 100) {

        // check for already existing life policy for user
        if (dataBaseArray.get(getIndexOfUserName(loadedProfile)).containsLife()) {

          MessageCli.ALREADY_HAS_LIFE_POLICY.printMessage(loadedProfile);

        } else {
          // make new instance of life policy
          LifePolicy lifePolicy = new LifePolicy(sumEnsured, age, this.loadedProfile,
              dataBaseArray.get(getIndexOfUserName(loadedProfile)));
          policyCreated = true;
          policybase = (PolicyBase) lifePolicy;
        }

      } else {

        MessageCli.OVER_AGE_LIMIT_LIFE_POLICY.printMessage(loadedProfile);
      }

    }

    if (policyCreated == true) {

      String policyType = type.toString().toLowerCase();
      MessageCli.NEW_POLICY_CREATED.printMessage(policyType, loadedProfile);

      dataBaseArray.get(getIndexOfUserName(loadedProfile)).setPolicy(policybase);

      dataBaseArray.get(getIndexOfUserName(loadedProfile));
    }

  }
}