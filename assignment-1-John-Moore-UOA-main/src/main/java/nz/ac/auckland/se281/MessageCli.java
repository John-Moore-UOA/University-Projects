package nz.ac.auckland.se281;

/** You cannot modify this class! */
public enum MessageCli {
  COMMAND_NOT_FOUND(
      "Error! Command not found! (run 'help' for the list of available commands): \"%s\""),
  WRONG_ARGUMENT_COUNT(
      "Error! Incorrect number of arguments provided. Expected %s argument%s for the \"%s\""
          + " command"),

  PRINT_DB_POLICY_COUNT("Database has %s profile%s%s"),
  PRINT_DB_PROFILE_HEADER_MINIMAL(" %s: %s, %s"),
  PRINT_DB_PROFILE_HEADER_SHORT(" %s%s: %s, %s"),
  PRINT_DB_PROFILE_HEADER_MEDIUM(" %s%s: %s, %s, %s polic%s"),
  PRINT_DB_PROFILE_HEADER_LONG(" %s%s: %s, %s, %s polic%s for a total of $%s"),

  PRINT_DB_CAR_POLICY("\tCar Policy (%s, Sum Insured: $%s, Premium: $%s -> $%s)"),
  PRINT_DB_HOME_POLICY("\tHome Policy (%s, Sum Insured: $%s, Premium: $%s -> $%s)"),
  PRINT_DB_LIFE_POLICY("\tLife Policy (Sum Insured: $%s, Premium: $%s -> $%s)"),

  INVALID_USERNAME_TOO_SHORT(
      "'%s' is an invalid username, it should be at least 3 characters long. No profile was"
          + " created."),
  INVALID_USERNAME_NOT_UNIQUE("Usernames must be unique. No profile was created for '%s'."),
  INVALID_AGE(
      "'%s' is an invalid age, please provide a positive whole number only. No profile was created"
          + " for %s."),

  CANNOT_CREATE_WHILE_LOADED("Cannot create a new profile. First unload the profile for %s."),

  PROFILE_CREATED("New profile created for %s with age %s."),
  PROFILE_DELETED("Profile deleted for %s."),
  PROFILE_LOADED("Profile loaded for %s."),
  PROFILE_UNLOADED("Profile unloaded for %s."),

  NO_PROFILE_FOUND_TO_LOAD("No profile found for %s. Profile not loaded."),
  NO_PROFILE_FOUND_TO_DELETE("No profile found for %s. No profile was deleted."),
  NO_PROFILE_FOUND_TO_CREATE_POLICY("Need to load a profile in order to create a policy."),

  NO_PROFILE_LOADED("No profile is currently loaded."),
  CANNOT_DELETE_PROFILE_WHILE_LOADED(
      "Cannot delete profile for %s while loaded. No profile was deleted."),

  NEW_POLICY_CREATED("New %s policy created for %s."),
  ALREADY_HAS_LIFE_POLICY("%s already has a life policy. No new policy was created."),
  OVER_AGE_LIMIT_LIFE_POLICY("%s is over the age limit. No policy was created."),

  DISCOUNT_TWO("Two policies are in place, client will get 10% discount"),
  DISCOUNT_MULTIPLE("Multiple policies are in place, client will get 20% discount"),

  END("You closed the terminal. Goodbye.");

  private final String msg;

  private MessageCli(final String msg) {
    this.msg = msg;
  }

  public String getMessage(final String... args) {
    String tmpMessage = msg;

    for (final String arg : args) {
      tmpMessage = tmpMessage.replaceFirst("%s", arg);
    }

    return tmpMessage;
  }

  public void printMessage(final String... args) {
    System.out.println(getMessage(args));
  }

  @Override
  public String toString() {
    return msg;
  }
}
