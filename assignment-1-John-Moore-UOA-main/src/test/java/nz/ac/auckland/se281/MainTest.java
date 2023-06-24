package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.Main.Command.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    // MainTest.Task1.class,
    // MainTest.Task2.class, // Uncomment this line when to start Task 2
    // MainTest.Task3.class, // Uncomment this line when to start Task 3
    MainTest.YourTests.class, // Uncomment this line to run your own tests
})
public class MainTest {
  public static class Task1 extends CliTest {
    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_01_empty_database() throws Exception {
      runCommands(PRINT_DB);
      assertContains("Database has 0 profiles.");
    }

    @Test
    public void T1_02_add_one_client() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "21", PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("New profile created for Jordan with age 21.");
      assertDoesNotContain("Database has 0 profiles", true);
    }

    @Test
    public void T1_03_add_one_client_with_info() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "21", PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("New profile created for Jordan with age 21.");
      assertContains("1: Jordan, 21");
      assertDoesNotContain("Database has 0 profiles", true);
    }

    @Test
    public void T1_04_ignore_short_name() throws Exception {
      runCommands(CREATE_PROFILE, "Jo", "21", PRINT_DB);
      assertContains("Database has 0 profiles.");
      assertContains(
          "'Jo' is an invalid username, it should be at least 3 characters long. No profile was"
              + " created.");
      assertDoesNotContain("Database has 1 profiles", true);
      assertDoesNotContain("New profile created", true);
      assertDoesNotContain("21");
    }

    @Test
    public void T1_05_add_two_clients() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "21", CREATE_PROFILE, "Tom", "25", PRINT_DB);
      assertContains("Database has 2 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertDoesNotContain("Database has 0 profiles", true);
      assertDoesNotContain("Database has 1 profile", true);
    }

    @Test
    public void T1_06_username_to_titlecase() throws Exception {
      runCommands(CREATE_PROFILE, "jorDan", "21", CREATE_PROFILE, "TOM", "25", PRINT_DB);
      assertContains("Database has 2 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertDoesNotContain("jorDan");
      assertDoesNotContain("TOM");
    }
  }

  public static class Task2 extends CliTest {
    public Task2() {
      super(Main.class);
    }

    @Test
    public void T2_01_load_profile_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Tom"));

      assertContains("Profile loaded for Tom.");
      assertDoesNotContain("No profile found for Tom. Profile not loaded.", true);
    }

    @Test
    public void T2_02_load_profile_not_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Alex"));

      assertContains("No profile found for Alex. Profile not loaded.");
      assertDoesNotContain("Profile loaded for Alex.", true);
    }

    @Test
    public void T2_03_load_profile_found_display() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Tom", PRINT_DB));

      assertContains("Profile loaded for Tom.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("*** 2: Tom, 25");
      assertContains("3: Jenny, 23");
    }

    @Test
    public void T2_04_load_profile_switch_profiles() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "tom", LOAD_PROFILE, "jenny", PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("Profile loaded for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("*** 3: Jenny, 23");
      assertDoesNotContain("*** 1: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
    }

    @Test
    public void T2_05_unload_profile() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Jenny", UNLOAD_PROFILE, PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("Profile unloaded for Jenny.");

      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");

      assertDoesNotContain("*** 1: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
      assertDoesNotContain("*** 3: Jenny, 23", true);
    }

    @Test
    public void T2_06_unload_invalid_profile() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "jen", UNLOAD_PROFILE, PRINT_DB));

      assertContains("No profile is currently loaded.");

      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");

      assertDoesNotContain("*** 1: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
      assertDoesNotContain("*** 3: Jenny, 23", true);
    }

    @Test
    public void T2_07_delete_profile_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, DELETE_PROFILE, "jordan", PRINT_DB));

      assertContains("Profile deleted for Jordan.");
      assertContains("Database has 2 profiles:");
      assertContains("1: Tom, 25");
      assertContains("2: Jenny, 23");
      assertDoesNotContain("Jordan, 21", true);
    }

    @Test
    public void T2_08_delete_profile_while_loaded() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Jenny", DELETE_PROFILE, "jenny", PRINT_DB));

      assertContains("Profile loaded for Jenny.");

      assertContains("Cannot delete profile for Jenny while loaded. No profile was deleted.");
      assertDoesNotContain("Profile deleted for Jenny", true);

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");
    }
  }

  public static class Task3 extends CliTest {
    public Task3() {
      super(Main.class);
    }

    @Test
    public void T3_01_cannot_add_policy_without_loaded_profile() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, POLICY_HOME, options("1000000", "20 Symonds Street", "yes")));

      assertContains("Need to load a profile in order to create a policy.");
      assertDoesNotContain("New home policy created", true);
    }

    @Test
    public void T3_02_add_home_policy_loaded_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("*** 3: Jenny, 23, 1 policy");

      assertContains("New home policy created for Jenny.");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $20000)");
    }

    @Test
    public void T3_03_add_car_policy_loaded_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Tom",
              POLICY_CAR,
              options("55000", "Subaru Impreza", "SUB123", "yes"),
              PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("New car policy created for Tom.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("*** 2: Tom, 25, 1 policy");
      assertContains("3: Jenny, 23, 0 policies");

      assertContains("Car Policy (Subaru Impreza, Sum Insured: $55000, Premium: $5580 -> $5580)");
    }

    @Test
    public void T3_04_two_different_policies_home_life_one_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              POLICY_LIFE,
              options("1000000"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("New home policy created for Jenny.");
      assertContains("New life policy created for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("*** 3: Jenny, 23, 2 policies");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12300 -> $11070)");
    }

    @Test
    public void T3_05_three_policies_one_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              POLICY_HOME,
              options("1000000", "20 Queen Street", "no"),
              POLICY_LIFE,
              options("1000000"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("New home policy created for Jenny.");
      assertContains("New life policy created for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("*** 3: Jenny, 23, 3 policies");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $16000)");
      assertContains(
          "Home Policy (20 Queen Street, Sum Insured: $1000000, Premium: $10000 -> $8000)");
      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12300 -> $9840)");
    }

    @Test
    public void T3_06_life_policy_over_age_limit() throws Exception {
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          101,
          LOAD_PROFILE,
          "Jenny",
          POLICY_LIFE,
          options("100000"),
          UNLOAD_PROFILE,
          PRINT_DB);

      assertContains("Profile loaded for Jenny.");
      assertContains("Jenny is over the age limit. No policy was created.");

      assertContains("Database has 1 profile:");
      assertContains("1: Jenny, 101, 0 policies");

      assertDoesNotContain("New life policy created for Jenny.", true);
      assertDoesNotContain("Life Policy (Sum Insured", true);
    }

    @Test
    public void T3_07_two_policies_one_profile_ignore_zero_policy_total_costs() throws Exception {
      runCommands(
          unpack( //
              CREATE_SOME_CLIENTS, //
              LOAD_PROFILE,
              "Tom", //
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"), //
              POLICY_CAR,
              options("55000", "Subaru Impreza", "SUB123", "no"), //
              UNLOAD_PROFILE, //
              LOAD_PROFILE,
              "Jenny", //
              POLICY_CAR,
              options("55000", "Subaru Impreza", "SUB123", "no"), //
              UNLOAD_PROFILE, //
              PRINT_DB));

      assertContains("2: Tom, 25, 2 policies for a total of $22950");
      assertContains("3: Jenny, 23, 1 policy for a total of $8250");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains("Car Policy (Subaru Impreza, Sum Insured: $55000, Premium: $5500 -> $4950)");
      assertContains("Car Policy (Subaru Impreza, Sum Insured: $55000, Premium: $8250 -> $8250)");
    }
  }

  public static class YourTests extends CliTest {
    public YourTests() {
      super(Main.class);
    }

    @Test
    public void JoshT_TY2_01_delete_profile_while_other_profile_is_loaded() throws Exception {
      // Deletes a profile while another profile is loaded
      // Checks to see if correct profile is still loaded

      runCommands(
          CREATE_PROFILE,
          "SamA",
          38,
          CREATE_PROFILE,
          "SuperintelligentAI",
          1000000,
          CREATE_PROFILE,
          "Bob",
          20,
          CREATE_PROFILE,
          "Elon",
          51,
          CREATE_PROFILE,
          "Eliezer",
          43,
          LOAD_PROFILE,
          "Elon",
          DELETE_PROFILE,
          "Bob",
          PRINT_DB);

      assertContains("Profile loaded for Elon.");
      assertContains("*** 3: Elon, 51, 0 policies for a total of $0");
      assertDoesNotContain("*** 4: Eliezer, 43, 0 policies for a total of $0");
      assertDoesNotContain(" 3: Bob, 20, 0 policies for a total of $0");
    }

    @Test
    public void JoshT_TY2_02_delete_several_profiles_while_profile_is_loaded() throws Exception {

      runCommands(
          CREATE_PROFILE,
          "Never",
          20,
          CREATE_PROFILE,
          "Gonna",
          20,
          CREATE_PROFILE,
          "Give",
          20,
          CREATE_PROFILE,
          "You",
          20,
          CREATE_PROFILE,
          "Up.",
          20,
          LOAD_PROFILE,
          "You",
          DELETE_PROFILE,
          "NEVER",
          DELETE_PROFILE,
          "gonna",
          DELETE_PROFILE,
          "give",
          DELETE_PROFILE,
          "you",
          DELETE_PROFILE,
          "up.",
          PRINT_DB);

      assertContains("Profile loaded for You.");
      assertContains("Cannot delete profile for You while loaded. No profile was deleted.");
      assertDoesNotContain("Profile deleted for You");
      assertContains("Profile deleted for Never");
      assertContains("Profile deleted for Gonna");
      assertContains("Profile deleted for Give");
      assertContains("Profile deleted for Up");
    }

    @Test
    public void TY_01_Final_Task_print_db() throws Exception {
      // empty database
      runCommands(PRINT_DB);
      assertContains("Database has 0 profiles.");
    }

    @Test
    public void TY_02_Final_Task_print_db() throws Exception {
      // 1 profile
      runCommands(CREATE_PROFILE, "Jenny", 24, PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("1: Jenny, 24, 0 policies for a total of $0");
    }

    @Test
    public void TY_03_Final_Task_print_db() throws Exception {
      // 2 profiles or more
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          24,
          CREATE_PROFILE,
          "Tom",
          25,
          CREATE_PROFILE,
          "Jane",
          74,
          PRINT_DB);
      assertContains("Database has 3 profiles:");
      assertContains("1: Jenny, 24, 0 policies for a total of $0");
      assertContains("2: Tom, 25, 0 policies for a total of $0");
      assertContains("3: Jane, 74, 0 policies for a total of $0");
    }

    @Test
    public void TY_04_Final_Task_Create_Profiles() throws Exception {
      runCommands(CREATE_PROFILE, "Jenny", 24);
      assertContains("New profile created for Jenny with age 24.");
    }

    @Test
    public void TY_05_Final_Task_Check_UserName() throws Exception {
      // Must be unique across
      runCommands(CREATE_PROFILE, "Jenny", 24, CREATE_PROFILE, "Jenny", 34);
      assertContains("Usernames must be unique. No profile was created for 'Jenny'.");
    }

    @Test
    public void TY_06_Final_Task_Check_UserName() throws Exception {
      // Must be at least 3 characters long
      runCommands(CREATE_PROFILE, "Ja", 24);
      assertContains(
          "'Ja' is an invalid username, it should be at least 3 characters long. No profile was"
              + " created.");
      assertDoesNotContain("New profile created for Ja with age 24.", true);
    }

    @Test
    public void TY_07_Final_Task_Check_UserName() throws Exception {
      // always process it in title case
      runCommands(CREATE_PROFILE, "kaTE", 24, PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("1: Kate, 24, 0 policies for a total of $0");
      assertDoesNotContain("kaTE");
    }

    @Test
    public void TY_08_Final_Task_Check_Age() throws Exception {
      // can not be negative
      runCommands(CREATE_PROFILE, "JaNe", -23);
      assertContains(
          "'-23' is an invalid age, please provide a positive whole number only. No profile was"
              + " created for Jane.");
    }

    @Test
    public void TY_09_Final_Task_LoadingProfiles() throws Exception {
      runCommands(CREATE_PROFILE, "Jenny", 24, LOAD_PROFILE, "Jenny");
      assertContains("Profile loaded for Jenny");
    }

    @Test
    public void TY_10_Final_Task_LoadingProfiles() throws Exception {
      // can not find profile
      runCommands(CREATE_PROFILE, "Jenny", 24, LOAD_PROFILE, "Jane");
      assertContains("No profile found for Jane. Profile not loaded.");
    }

    @Test
    public void TY_11_Final_Task_LoadingProfiles() throws Exception {
      // Check that the profile is loaded
      runCommands(
          CREATE_PROFILE, "Jenny", 24, CREATE_PROFILE, "Jane", 43, LOAD_PROFILE, "Jane", PRINT_DB);
      assertContains("Database has 2 profiles:");
      assertContains("1: Jenny, 24, 0 policies for a total of $0");
      assertContains("*** 2: Jane, 43, 0 policies for a total of $0");
    }

    @Test
    public void TY_12_Final_Task_LoadingProfiles() throws Exception {
      // load another profile, but fail to load
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          24,
          CREATE_PROFILE,
          "Jane",
          43,
          LOAD_PROFILE,
          "Jane",
          LOAD_PROFILE,
          "Tom",
          PRINT_DB);
      assertContains("No profile found for Tom. Profile not loaded.");
      assertContains("Database has 2 profiles:");
      assertContains("1: Jenny, 24, 0 policies for a total of $0");
      assertContains("*** 2: Jane, 43, 0 policies for a total of $0");
    }

    @Test
    public void TY_13_Final_Task_LoadingProfiles() throws Exception {
      // load another profile
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          24,
          CREATE_PROFILE,
          "Jane",
          43,
          LOAD_PROFILE,
          "Jane",
          PRINT_DB,
          LOAD_PROFILE,
          "Jenny",
          PRINT_DB);
      assertContains("Database has 2 profiles:");
      assertContains("1: Jenny, 24, 0 policies for a total of $0");
      assertContains("*** 2: Jane, 43, 0 policies for a total of $0");
      assertContains("Database has 2 profiles:");
      assertContains("*** 1: Jenny, 24, 0 policies for a total of $0");
      assertContains("2: Jane, 43, 0 policies for a total of $0");
    }

    @Test
    public void TY_14_Final_Task_LoadingProfiles() throws Exception {
      // create a profile, when loading a profile
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          24,
          CREATE_PROFILE,
          "Jane",
          43,
          LOAD_PROFILE,
          "Jane",
          CREATE_PROFILE,
          "Tom",
          25);
      assertContains("Cannot create a new profile. First unload the profile for Jane.");
    }

    @Test
    public void TY_15_Final_Task_UnloadingProfiles() throws Exception {
      // unload a profile
      runCommands(CREATE_PROFILE, "Jenny", 24, LOAD_PROFILE, "Jenny", UNLOAD_PROFILE);
      assertContains("Profile unloaded for Jenny.");
    }

    @Test
    public void TY_16_Final_Task_UnloadingProfiles() throws Exception {
      // there is no currently-loaded profile, unload fails
      runCommands(CREATE_PROFILE, "Jenny", 24, UNLOAD_PROFILE);
      assertContains("No profile is currently loaded.");
    }

    @Test
    public void TY_17_Final_Task_DELETE_PROFILE() throws Exception {
      // delete a profile, and check database
      runCommands(CREATE_PROFILE, "Jenny", 24, DELETE_PROFILE, "Jenny", PRINT_DB);
      assertContains("Profile deleted for Jenny.");
      assertContains("Database has 0 profiles.");
    }

    @Test
    public void TY_18_Final_Task_DELETE_PROFILE() throws Exception {
      // delete a profile, but the profile is not found
      runCommands(CREATE_PROFILE, "Jenny", 24, DELETE_PROFILE, "Jane");
      assertContains("No profile found for Jane. No profile was deleted.");
    }

    @Test
    public void TY_19_Final_Task_DELETE_PROFILE() throws Exception {
      // delete a profile, but the profile is loaded
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          24,
          CREATE_PROFILE,
          "Jane",
          43,
          LOAD_PROFILE,
          "Jenny",
          DELETE_PROFILE,
          "Jenny",
          DELETE_PROFILE,
          "Jane",
          PRINT_DB);
      assertContains("Cannot delete profile for Jenny while loaded. No profile was deleted.");
      assertContains("Database has 1 profile:");
      assertContains("*** 1: Jenny, 24, 0 policies for a total of $0");
      assertDoesNotContain("2: Jane, 43, 0 policies for a total of $0");
    }

    @Test
    public void TY_20_Final_Task_CreatePolicy() throws Exception {
      // create a policy, but no loaded profile
      runCommands(
          CREATE_PROFILE, "Jenny", 24, POLICY_HOME, options("1000000", "20 Symonds Street", "yes"));
      assertContains("Need to load a profile in order to create a policy.");
      assertDoesNotContain("New home policy created", true);
    }

    @Test
    public void TY_21_Final_Task_CreatePolicy() throws Exception {
      // create policies
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          24,
          CREATE_PROFILE,
          "Jane",
          43,
          CREATE_PROFILE,
          "Tom",
          12,
          LOAD_PROFILE,
          "Jenny",
          POLICY_HOME,
          options("1000000", "20 Symonds Street", "yes"),
          POLICY_CAR,
          options("100000", "Toyota", "SUB435", "yes"),
          UNLOAD_PROFILE,
          LOAD_PROFILE,
          "Jane",
          POLICY_CAR,
          options("200000", "Toyota", "SUB435", "no"),
          POLICY_HOME,
          options("1000000", "20 Symonds Street", "no"),
          UNLOAD_PROFILE,
          LOAD_PROFILE,
          "Tom",
          POLICY_LIFE,
          options("1000"),
          PRINT_DB);
      assertContains("New home policy created for Jenny.");
      assertContains("New car policy created for Jenny.");
      assertContains("New home policy created for Jane.");
      assertContains("New car policy created for Jane.");
      assertContains("New life policy created for Tom.");
      assertContains("Database has 3 profiles:");
      assertContains("1: Jenny, 24, 2 policies for a total of $31572");
      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains("Car Policy (Toyota, Sum Insured: $100000, Premium: $15080 -> $13572)");
      assertContains("2: Jane, 43, 2 policies for a total of $27000");
      assertContains("Car Policy (Toyota, Sum Insured: $200000, Premium: $20000 -> $18000)");
      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $10000 -> $9000)");
      assertContains("*** 3: Tom, 12, 1 policy for a total of $11");
      assertContains("Life Policy (Sum Insured: $1000, Premium: $11 -> $11)");
    }

    @Test
    public void TY_22_Final_Task_CreatePolicy() throws Exception {
      // check home policy is rented out
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          24,
          CREATE_PROFILE,
          "Jane",
          43,
          CREATE_PROFILE,
          "Tom",
          12,
          LOAD_PROFILE,
          "Jenny",
          POLICY_HOME,
          options("1000000", "20 Symonds Street", "Yeah"),
          UNLOAD_PROFILE,
          LOAD_PROFILE,
          "Jane",
          POLICY_HOME,
          options("1000000", "20 Symonds Street", "N"),
          UNLOAD_PROFILE,
          LOAD_PROFILE,
          "Tom",
          POLICY_HOME,
          options("1000000", "20 Symonds Street", "y"),
          PRINT_DB);
      assertContains("New home policy created for Jenny.");
      assertContains("New home policy created for Jane.");
      assertContains("New home policy created for Tom.");
      assertContains("Database has 3 profiles:");
      assertContains("1: Jenny, 24, 1 policy for a total of $20000");
      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $20000)");
      assertContains("2: Jane, 43, 1 policy for a total of $10000");
      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $10000 -> $10000)");
      assertContains("*** 3: Tom, 12, 1 policy for a total of $20000");
      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $20000)");
    }

    @Test
    public void TY_23_Final_Task_CreatePolicy() throws Exception {
      // check car policy
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          24,
          CREATE_PROFILE,
          "Jane",
          43,
          CREATE_PROFILE,
          "Tom",
          12,
          LOAD_PROFILE,
          "Jenny",
          POLICY_CAR,
          options("100000", "Toyota", "SUB435", "Y"),
          LOAD_PROFILE,
          "Jane",
          POLICY_CAR,
          options("200000", "Toyota", "SUB435", "Yeap"),
          UNLOAD_PROFILE,
          LOAD_PROFILE,
          "Tom",
          POLICY_CAR,
          options("400000", "Toyota", "SUB435", "Nop"),
          LOAD_PROFILE,
          PRINT_DB);
      assertContains("New car policy created for Jenny.");
      assertContains("New car policy created for Jane.");
      assertContains("New car policy created for Tom.");
      assertContains("Database has 3 profiles:");
      assertContains("1: Jenny, 24, 1 policy for a total of $15080");
      assertContains("Car Policy (Toyota, Sum Insured: $100000, Premium: $15080 -> $15080)");
      assertContains("2: Jane, 43, 1 policy for a total of $20080");
      assertContains("Car Policy (Toyota, Sum Insured: $200000, Premium: $20080 -> $20080)");
      assertContains("3: Tom, 12, 1 policy for a total of $60000");
      assertContains("Car Policy (Toyota, Sum Insured: $400000, Premium: $60000 -> $60000)");
    }

    @Test
    public void TY_24_Final_Task_CreatePolicy() throws Exception {
      // create a life policy, but over the age limit
      runCommands(
          CREATE_PROFILE, "Jenny", 120, LOAD_PROFILE, "Jenny", POLICY_LIFE, options("1000000"));
      assertContains("Jenny is over the age limit. No policy was created.");
      assertDoesNotContain("New life policy created for Jenny.");
    }

    @Test
    public void TY_25_Final_Task_CreatePolicy() throws Exception {
      // there can only be at most one life policy per client profile.
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          23,
          LOAD_PROFILE,
          "Jenny",
          POLICY_LIFE,
          options("1000000"),
          POLICY_LIFE,
          options("3000000"));
      assertContains("Jenny already has a life policy. No new policy was created.");
    }

    @Test
    public void TY_26_Final_Task_CreatePolicy() throws Exception {
      // If the client has three or more policies
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          23,
          LOAD_PROFILE,
          "Jenny",
          POLICY_LIFE,
          options("1000000"),
          POLICY_CAR,
          options("100000", "Toyota", "SUB435", "Yes"),
          POLICY_HOME,
          options("1000000", "20 Symonds Street", "N"),
          POLICY_CAR,
          options("100000", "Subaru Impreza", "SUB625", "No"),
          UNLOAD_PROFILE,
          PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("1: Jenny, 23, 4 policies for a total of $41904");
      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12300 -> $9840)");
      assertContains("Car Policy (Toyota, Sum Insured: $100000, Premium: $15080 -> $12064)");
      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $10000 -> $8000)");
      assertContains(
          "Car Policy (Subaru Impreza, Sum Insured: $100000, Premium: $15000 -> $12000)");
    }

    @Test
    public void Home_Policy_Tests_One_Policy() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "John",
          21,
          LOAD_PROFILE,
          "John",
          POLICY_HOME,
          options("1000000", "123 Seaseme Street", "yes"),
          PRINT_DB);

      assertContains("*** 1: John, 21, 1 policy for a total of $20000");
      assertContains(
          "Home Policy (123 Seaseme Street, Sum Insured: $1000000, Premium: $20000 -> $20000)");
    }

    @Test
    public void Home_Policy_Tests_Two_Policies() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "John",
          21,
          LOAD_PROFILE,
          "John",
          POLICY_HOME,
          options("1000000", "123 Seaseme Street", "yes"),
          POLICY_HOME,
          options("1000000", "18 This Way", "no"),
          PRINT_DB);
      assertContains("*** 1: John, 21, 2 policies for a total of $27000");
      assertContains(
          "Home Policy (123 Seaseme Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains("Home Policy (18 This Way, Sum Insured: $1000000, Premium: $10000 -> $9000)");
    }

    @Test
    public void Home_Policy_Tests_Three_Policies() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "John",
          21,
          LOAD_PROFILE,
          "John",
          POLICY_HOME,
          options("1000000", "123 Seaseme Street", "yes"),
          POLICY_HOME,
          options("1000000", "18 This Way", "no"),
          POLICY_HOME,
          options("1000000", "20 No Name Road", "yes"),
          UNLOAD_PROFILE,
          PRINT_DB);

      assertContains("1: John, 21, 3 policies for a total of $40000");
      assertContains(
          "Home Policy (123 Seaseme Street, Sum Insured: $1000000, Premium: $20000 -> $16000)");
      assertContains("Home Policy (18 This Way, Sum Insured: $1000000, Premium: $10000 -> $8000)");
      assertContains(
          "Home Policy (20 No Name Road, Sum Insured: $1000000, Premium: $20000 -> $16000)");
    }

    @Test
    public void Home_Policy_Tests_One_Policy_Type2() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Joe",
          21,
          LOAD_PROFILE,
          "Joe",
          POLICY_HOME,
          options("1000000", "20 Symon Street", "yes"),
          PRINT_DB);
      assertContains("*** 1: Joe, 21, 1 policy for a total of $20000");
      assertContains(
          "Home Policy (20 Symon Street, Sum Insured: $1000000, Premium: $20000 -> $20000)");
    }

    @Test
    public void Home_Policy_Tests_Two_Policies_Type2() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Joe",
          21,
          LOAD_PROFILE,
          "Joe",
          POLICY_HOME,
          options("10", "20 Symon Street", "yes"),
          POLICY_HOME,
          options("1000000", "671 Lincoln Ave", "no"),
          PRINT_DB);
      assertContains("*** 1: Joe, 21, 2 policies for a total of $9000");
      assertContains("Home Policy (20 Symon Street, Sum Insured: $10, Premium: $0 -> $0)");
      assertContains(
          "Home Policy (671 Lincoln Ave, Sum Insured: $1000000, Premium: $10000 -> $9000)");
    }

    @Test
    public void Home_Policy_Tests_Three_Policies_Type2() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Joe",
          21,
          LOAD_PROFILE,
          "Joe",
          POLICY_HOME,
          options("1000000", "20 Symon Street", "yes"),
          POLICY_HOME,
          options("1000000", "671 Lincoln Ave", "no"),
          POLICY_HOME,
          options("10000", "20 Garden Street", "no"),
          PRINT_DB);

      assertContains("*** 1: Joe, 21, 3 policies for a total of $24080");
      assertContains(
          "Home Policy (20 Symon Street, Sum Insured: $1000000, Premium: $20000 -> $16000)");
      assertContains(
          "Home Policy (671 Lincoln Ave, Sum Insured: $1000000, Premium: $10000 -> $8000)");
      assertContains("Home Policy (20 Garden Street, Sum Insured: $10000, Premium: $100 -> $80)");
    }

    @Test
    public void Car_Policy_Tests_One_Policy() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Eric",
          255,
          LOAD_PROFILE,
          "Eric",
          POLICY_CAR,
          options("1000000", "Subaru Imperza", "ABC123", "yes"),
          PRINT_DB);

      assertContains("*** 1: Eric, 255, 1 policy for a total of $100080");
      assertContains(
          "Car Policy (Subaru Imperza, Sum Insured: $1000000, Premium: $100080 -> $100080)");
    }

    @Test
    public void Car_Policy_Tests_Two_Policies() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Eric",
          255,
          LOAD_PROFILE,
          "Eric",
          POLICY_CAR,
          options("100000", "Subaru Imperza", "ABC123", "yes"),
          POLICY_CAR,
          options("1000000", "DeLorean", "BACK2F", "yes"),
          PRINT_DB);
      assertContains("*** 1: Eric, 255, 2 policies for a total of $99144");
      assertContains("Car Policy (Subaru Imperza, Sum Insured: $100000, Premium: $10080 -> $9072)");
      assertContains("Car Policy (DeLorean, Sum Insured: $1000000, Premium: $100080 -> $90072)");
    }

    @Test
    public void Car_Policy_Tests_Three_Policies() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Eric",
          255,
          LOAD_PROFILE,
          "Eric",
          POLICY_CAR,
          options("1000000", "Subaru Imperza", "ABC123", "yes"),
          POLICY_CAR,
          options("100", "DeLorean", "BACK2F", "yes"),
          POLICY_CAR,
          options("1000000", "Dodge Charger", "2FAST", "yes"),
          PRINT_DB);

      assertContains("*** 1: Eric, 255, 3 policies for a total of $160200");
      assertContains(
          "Car Policy (Subaru Imperza, Sum Insured: $1000000, Premium: $100080 -> $80064)");
      assertContains("Car Policy (DeLorean, Sum Insured: $100, Premium: $90 -> $72)");
      assertContains(
          "Car Policy (Dodge Charger, Sum Insured: $1000000, Premium: $100080 -> $80064)");
    }

    @Test
    public void Car_Policy_Tests_One_Policy_Type2() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Dominic",
          25,
          LOAD_PROFILE,
          "Dominic",
          POLICY_CAR,
          options("1000000", "Honda NSX", "NSX923", "no"),
          PRINT_DB);
      assertContains("*** 1: Dominic, 25, 1 policy for a total of $100000");
      assertContains("Car Policy (Honda NSX, Sum Insured: $1000000, Premium: $100000 -> $100000)");
    }

    @Test
    public void Car_Policy_Tests_Two_Policies_Type2() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Dominic",
          25,
          LOAD_PROFILE,
          "Dominic",
          POLICY_CAR,
          options("1000", "Honda NSX", "NSX923", "no"),
          POLICY_CAR,
          options("1000000", "Cheverlot Camaro", "BUMBLE", "yes"),
          PRINT_DB);
      assertContains("*** 1: Dominic, 25, 2 policies for a total of $90162");
      assertContains("Car Policy (Honda NSX, Sum Insured: $1000, Premium: $100 -> $90)");
      assertContains(
          "Car Policy (Cheverlot Camaro, Sum Insured: $1000000, Premium: $100080 -> $90072)");
    }

    @Test
    public void Car_Policy_Tests_Three_Policies_Type2() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Dominic",
          25,
          LOAD_PROFILE,
          "Dominic",
          POLICY_CAR,
          options("1000000", "Honda NSX", "NSX923", "no"),
          POLICY_CAR,
          options("1000000", "Cheverlot Camaro", "BUMBLE", "yes"),
          POLICY_CAR,
          options("1000", "Nissan Fairlady Z", "DJK321", "no"),
          PRINT_DB);

      assertContains("*** 1: Dominic, 25, 3 policies for a total of $160144");
      assertContains("Car Policy (Honda NSX, Sum Insured: $1000000, Premium: $100000 -> $80000)");
      assertContains(
          "Car Policy (Cheverlot Camaro, Sum Insured: $1000000, Premium: $100080 -> $80064)");
      assertContains("Car Policy (Nissan Fairlady Z, Sum Insured: $1000, Premium: $100 -> $80)");
    }

    @Test
    public void Car_Policy_Tests_One_Policy_Under25() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Elon",
          16,
          LOAD_PROFILE,
          "Elon",
          POLICY_CAR,
          options("1000000", "Tesla Model 3", "NOGAS", "yes"),
          PRINT_DB);
      assertContains("*** 1: Elon, 16, 1 policy for a total of $150080");
      assertContains(
          "Car Policy (Tesla Model 3, Sum Insured: $1000000, Premium: $150080 -> $150080)");
    }

    @Test
    public void Car_Policy_Tests_Two_Policies_Under25() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Elon",
          16,
          LOAD_PROFILE,
          "Elon",
          POLICY_CAR,
          options("1000", "Tesla Model 3", "NOGAS", "yes"),
          POLICY_CAR,
          options("1000000", "Model T Ford", "OLDMAN", "yes"),
          PRINT_DB);
      assertContains("*** 1: Elon, 16, 2 policies for a total of $135279");
      assertContains("Car Policy (Tesla Model 3, Sum Insured: $1000, Premium: $230 -> $207)");
      assertContains(
          "Car Policy (Model T Ford, Sum Insured: $1000000, Premium: $150080 -> $135072)");
    }

    @Test
    public void Car_Policy_Tests_Three_Policies_Under25() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Elon",
          16,
          LOAD_PROFILE,
          "Elon",
          POLICY_CAR,
          options("1000000", "Tesla Model 3", "NOGAS", "yes"),
          POLICY_CAR,
          options("1000000", "Model T Ford", "OLDMAN", "yes"),
          POLICY_CAR,
          options("1000000", "Volkswagon Beetle", "HERBIE", "yes"),
          PRINT_DB);

      assertContains("*** 1: Elon, 16, 3 policies for a total of $360192");
      assertContains(
          "Car Policy (Tesla Model 3, Sum Insured: $1000000, Premium: $150080 -> $120064)");
      assertContains(
          "Car Policy (Model T Ford, Sum Insured: $1000000, Premium: $150080 -> $120064)");
      assertContains(
          "Car Policy (Volkswagon Beetle, Sum Insured: $1000000, Premium: $150080 -> $120064)");
    }

    @Test
    public void Car_Policy_Tests_One_Policy_Over25() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Brian",
          30,
          LOAD_PROFILE,
          "Brian",
          POLICY_CAR,
          options("1000000", "Nissan Skyline", "CONNER", "no"),
          PRINT_DB);
      assertContains("*** 1: Brian, 30, 1 policy for a total of $100000");
      assertContains(
          "Car Policy (Nissan Skyline, Sum Insured: $1000000, Premium: $100000 -> $100000)");
    }

    @Test
    public void Car_Policy_Tests_Two_Policies_Over25() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Brian",
          30,
          LOAD_PROFILE,
          "Brian",
          POLICY_CAR,
          options("1000000", "Nissan Skyline", "CONNER", "no"),
          POLICY_CAR,
          options("1000", "Optimus Prime", "ATOBOT", "yes"),
          PRINT_DB);
      assertContains("*** 1: Brian, 30, 2 policies for a total of $90162");
      assertContains(
          "Car Policy (Nissan Skyline, Sum Insured: $1000000, Premium: $100000 -> $90000)");
      assertContains("Car Policy (Optimus Prime, Sum Insured: $1000, Premium: $180 -> $162)");
    }

    @Test
    public void Car_Policy_Tests_Three_Policies_Over25() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Brian",
          30,
          LOAD_PROFILE,
          "Brian",
          POLICY_CAR,
          options("1000000", "Nissan Skyline", "CONNER", "no"),
          POLICY_CAR,
          options("1000000", "Optimus Prime", "ATOBOT", "yes"),
          POLICY_CAR,
          options("1000000", "Toyota Tecoma", "NIVISN", "no"),
          PRINT_DB);

      assertContains("*** 1: Brian, 30, 3 policies for a total of $240064");
      assertContains(
          "Car Policy (Nissan Skyline, Sum Insured: $1000000, Premium: $100000 -> $80000)");
      assertContains(
          "Car Policy (Optimus Prime, Sum Insured: $1000000, Premium: $100080 -> $80064)");
      assertContains(
          "Car Policy (Toyota Tecoma, Sum Insured: $1000000, Premium: $100000 -> $80000)");
    }

    @Test
    public void Life_Policy_Too_Old() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(
          CREATE_PROFILE,
          "Thanos",
          1000,
          LOAD_PROFILE,
          "Thanos",
          POLICY_LIFE,
          options("1000"),
          POLICY_CAR,
          options("1000000", "Thanos Copter", "THANOS", "no"),
          POLICY_HOME,
          options("1000000", "Titan", "yes"),
          PRINT_DB);
      assertContains("*** 1: Thanos, 1000, 2 policies for a total of $108000");
      assertContains(
          "Car Policy (Thanos Copter, Sum Insured: $1000000, Premium: $100000 -> $90000)");
      assertContains("Home Policy (Titan, Sum Insured: $1000000, Premium: $20000 -> $18000)");
    }

    @Test
    public void Full_Test_1() throws Exception {
      runCommands(
          CREATE_PROFILE,
          "John",
          21,
          LOAD_PROFILE,
          "John",
          POLICY_HOME,
          options("1000000", "123 Seaseme Street", "yes"),
          POLICY_HOME,
          options("1000", "18 This Way", "no"),
          UNLOAD_PROFILE,
          CREATE_PROFILE,
          "Joe",
          21,
          LOAD_PROFILE,
          "Joe",
          POLICY_HOME,
          options("1000000", "20 Symon Street", "yes"),
          POLICY_HOME,
          options("1000000", "20 Garden Street", "no"),
          UNLOAD_PROFILE,
          CREATE_PROFILE,
          "Eric",
          255,
          LOAD_PROFILE,
          "Eric",
          POLICY_CAR,
          options("1000", "Subaru Imperza", "ABC123", "yes"),
          POLICY_CAR,
          options("1000", "DeLorean", "BACK2F", "no"),
          POLICY_CAR,
          options("1000000", "Dodge Charger", "2FAST", "yes"),
          UNLOAD_PROFILE,
          CREATE_PROFILE,
          "Dominic",
          25,
          LOAD_PROFILE,
          "Dominic",
          POLICY_CAR,
          options("1000000", "Honda NSX", "NSX923", "no"),
          POLICY_CAR,
          options("1000000", "Cheverlot Camaro", "BUMBLE", "yes"),
          UNLOAD_PROFILE,
          CREATE_PROFILE,
          "Elon",
          16,
          LOAD_PROFILE,
          "Elon",
          POLICY_CAR,
          options("1000000", "Tesla Model 3", "NOGAS", "yes"),
          POLICY_CAR,
          options("1000000", "Model T Ford", "OLDMAN", "no"),
          POLICY_CAR,
          options("1000000", "Volkswagon Beetle", "HERBIE", "yes"),
          UNLOAD_PROFILE,
          CREATE_PROFILE,
          "Brian",
          30,
          LOAD_PROFILE,
          "Brian",
          POLICY_CAR,
          options("1000000", "Nissan Skyline", "CONNER", "no"),
          POLICY_CAR,
          options("1000000", "Optimus Prime", "ATOBOT", "yes"),
          UNLOAD_PROFILE,
          CREATE_PROFILE,
          "Elizabeth",
          25,
          LOAD_PROFILE,
          "Elizabeth",
          POLICY_CAR,
          options("1000000", "Aston Martin DB9", "FAST8", "no"),
          UNLOAD_PROFILE,
          CREATE_PROFILE,
          "LeBron",
          14,
          LOAD_PROFILE,
          "LeBron",
          POLICY_CAR,
          options("1000000", "Subaru Impreza WRX", "BABY", "no"),
          POLICY_CAR,
          options("1000000", "Enzo Ferrari", "FERARI", "no"),
          UNLOAD_PROFILE,
          CREATE_PROFILE,
          "Thanos",
          1000,
          LOAD_PROFILE,
          "Thanos",
          POLICY_LIFE,
          options("1000"),
          POLICY_CAR,
          options("1000", "Thanos Copter", "THANOS", "no"),
          POLICY_HOME,
          options("1000", "Titan", "yes"),
          UNLOAD_PROFILE,
          PRINT_DB);

      assertContains("1: John, 21, 2 policies for a total of $18009");
      assertContains(
          "Home Policy (123 Seaseme Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains("Home Policy (18 This Way, Sum Insured: $1000, Premium: $10 -> $9)");
      assertContains("2: Joe, 21, 2 policies for a total of $27000");
      assertContains(
          "Home Policy (20 Symon Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains(
          "Home Policy (20 Garden Street, Sum Insured: $1000000, Premium: $10000 -> $9000)");
      assertContains("3: Eric, 255, 3 policies for a total of $80288");
      assertContains("Car Policy (Subaru Imperza, Sum Insured: $1000, Premium: $180 -> $144)");
      assertContains("Car Policy (DeLorean, Sum Insured: $1000, Premium: $100 -> $80)");
      assertContains(
          "Car Policy (Dodge Charger, Sum Insured: $1000000, Premium: $100080 -> $80064)");
      assertContains("4: Dominic, 25, 2 policies for a total of $180072");
      assertContains("Car Policy (Honda NSX, Sum Insured: $1000000, Premium: $100000 -> $90000)");
      assertContains(
          "Car Policy (Cheverlot Camaro, Sum Insured: $1000000, Premium: $100080 -> $90072)");
      assertContains("5: Elon, 16, 3 policies for a total of $360128");
      assertContains(
          "Car Policy (Tesla Model 3, Sum Insured: $1000000, Premium: $150080 -> $120064)");
      assertContains(
          "Car Policy (Model T Ford, Sum Insured: $1000000, Premium: $150000 -> $120000)");
      assertContains(
          "Car Policy (Volkswagon Beetle, Sum Insured: $1000000, Premium: $150080 -> $120064)");
      assertContains("6: Brian, 30, 2 policies for a total of $180072");
      assertContains(
          "Car Policy (Nissan Skyline, Sum Insured: $1000000, Premium: $100000 -> $90000)");
      assertContains(
          "Car Policy (Optimus Prime, Sum Insured: $1000000, Premium: $100080 -> $90072)");
      assertContains("7: Elizabeth, 25, 1 policy for a total of $100000");
      assertContains(
          "Car Policy (Aston Martin DB9, Sum Insured: $1000000, Premium: $100000 -> $100000)");
      assertContains("8: Lebron, 14, 2 policies for a total of $270000");
      assertContains(
          "Car Policy (Subaru Impreza WRX, Sum Insured: $1000000, Premium: $150000 -> $135000)");
      assertContains(
          "Car Policy (Enzo Ferrari, Sum Insured: $1000000, Premium: $150000 -> $135000)");
      assertContains("9: Thanos, 1000, 2 policies for a total of $108");
      assertContains("Car Policy (Thanos Copter, Sum Insured: $1000, Premium: $100 -> $90)");
      assertContains("Home Policy (Titan, Sum Insured: $1000, Premium: $20 -> $18)");
    }

    @Test
    public void T2_M01_load_invalid_profile_while_already_loaded() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "jorDAN", LOAD_PROFILE, "toBY", PRINT_DB));

      assertContains("Profile loaded for Jordan.");
      assertContains("No profile found for Toby. Profile not loaded.");
      assertContains("Database has 3 profiles:");
      assertContains("*** 1: Jordan, 21");

      assertDoesNotContain("Profile loaded for Toby.", true);
    }

    @Test
    public void T2_M02_create_profile_when_profile_loaded() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "TOM", CREATE_PROFILE, "tOBy", "28", PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("Cannot create a new profile. First unload the profile for Tom.");
      assertContains("Database has 3 profiles:");

      assertDoesNotContain("Database has 4 profiles:", true);
      assertDoesNotContain("4: Tom, 28", true);
    }

    @Test
    public void T2_M03_create_profile_after_profile_loaded() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "toM",
              UNLOAD_PROFILE,
              CREATE_PROFILE,
              "ToBy",
              "28",
              PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("Profile unloaded for Tom.");
      assertContains("New profile created for Toby with age 28.");
      assertContains("Database has 4 profiles:");
      assertContains("4: Toby, 28");

      assertDoesNotContain("Cannot create a new profile. First unload the profile for Tom.", true);
      assertDoesNotContain("Database has 3 profiles:", true);
    }

    @Test
    public void T2_M04_delete_profile_not_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, DELETE_PROFILE, "samUEl", PRINT_DB));

      assertContains("Database has 3 profiles:");
      assertContains("No profile found for Samuel. No profile was deleted.");

      assertDoesNotContain("Profile deleted for Samuel.", true);
    }

    @Test
    public void T3_M01_create_car_policy_age_test() throws Exception {
      runCommands(
          unpack( //
              CREATE_SOME_CLIENTS, //
              LOAD_PROFILE,
              "ToM", //
              POLICY_CAR,
              options("35000", "Nissan Leaf", "GR333N", "no"), //
              LOAD_PROFILE,
              "jorDAN", //
              POLICY_CAR,
              options("1000", "Toyota RAV-4", "S4V3RS", "yes"), //
              PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("Profile loaded for Jordan.");

      assertContains("Car Policy (Nissan Leaf, Sum Insured: $35000, Premium: $3500 -> $3500)");
      assertContains("Car Policy (Toyota RAV-4, Sum Insured: $1000, Premium: $230 -> $230)");

      assertContains(" *** 1: Jordan, 21, 1 policy for a total of $230");
      assertContains("2: Tom, 25, 1 policy for a total of $3500");
    }

    @Test
    public void T3_M02_maximum_age_boundary_life_policy() throws Exception {
      runCommands( //
          CREATE_PROFILE,
          "cAMEROn",
          "100", //
          LOAD_PROFILE,
          "CAMERon", //
          POLICY_LIFE,
          options("950000"), //
          PRINT_DB);

      assertContains("New profile created for Cameron with age 100.");
      assertContains("Profile loaded for Cameron.");

      assertContains("Life Policy (Sum Insured: $950000, Premium: $19000 -> $19000)");
    }

    @Test
    public void T3_M03_only_one_life_policy_per_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS, //
              LOAD_PROFILE,
              "JEnnY", //
              POLICY_LIFE,
              options("15000000"), //
              LOAD_PROFILE,
              "JorDAN", //
              POLICY_LIFE,
              options("5000000"), //
              POLICY_HOME,
              options("7500000", "80 Queens Road", "yes"), //
              LOAD_PROFILE,
              "JEnNY", //
              POLICY_LIFE,
              options("10000000"), //
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("Profile loaded for Jordan.");

      assertContains("New life policy created for Jenny.");
      assertContains("New life policy created for Jordan.");
      assertContains("New home policy created for Jordan.");

      assertContains("Life Policy (Sum Insured: $15000000, Premium: $184500 -> $184500)");
      assertContains("Life Policy (Sum Insured: $5000000, Premium: $60500 -> $54450)");
      assertContains(
          "Home Policy (80 Queens Road, Sum Insured: $7500000, Premium: $150000 -> $135000)");

      assertContains("Jenny already has a life policy. No new policy was created.");

      assertDoesNotContain(
          "Life Policy (Sum Insured: $10000000, Premium: $123000 -> $123000)", true);
      assertDoesNotContain(
          "Life Policy (Sum Insured: $10000000, Premium: $123000 -> $110700)", true);
      assertDoesNotContain(
          "Life Policy (Sum Insured: $15000000, Premium: $184500 -> $166050)", true);
      assertDoesNotContain("3: Jenny, 23, 2 policies", true);
    }

  }

  private static final Object[] CREATE_SOME_CLIENTS = new Object[] {
      CREATE_PROFILE, "Jordan", "21", //
      CREATE_PROFILE, "Tom", "25", //
      CREATE_PROFILE, "Jenny", "23",
  };

  private static Object[] unpack(Object[] commands, Object... more) {
    final List<Object> all = new ArrayList<Object>();
    all.addAll(List.of(commands));
    all.addAll(List.of(more));
    return all.toArray(new Object[all.size()]);
  }

  private static String[] options(String... options) {
    final List<String> all = new ArrayList<String>();
    all.addAll(List.of(options));
    return all.toArray(new String[all.size()]);
  }
}
