import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.server.LDClient;

import java.io.IOException;

public class Hello {

  // Set SDK_KEY to your LaunchDarkly SDK key.
  static final String SDK_KEY = "sdk-fb192242-63d8-4ad0-94e9-7d9ea4723b32";

  // Set FEATURE_FLAG_KEY to the feature flag key you want to evaluate.
  static final String FEATURE_FLAG_KEY = "java-sdk-boolean";

  private static void showMessage(String s) {
    System.out.println("*** " + s);
    System.out.println();
  }

  public static void main(String... args) throws IOException {
    if (SDK_KEY.equals("")) {
      showMessage("Please edit Hello.java to set SDK_KEY to your LaunchDarkly SDK key first");
      System.exit(1);
    }

    LDClient client = new LDClient(SDK_KEY);

    if (client.isInitialized()) {
      showMessage("SDK successfully initialized!");
    } else {
      showMessage("SDK failed to initialize");
      System.exit(1);
    }
    
    // Set up the user properties. This user should appear on your LaunchDarkly users dashboard
    // soon after you run the demo.
    LDUser user = new LDUser.Builder("USER KEY")
                            .name("Brad Bunce")
			    .firstName("Brad")
			    .lastName("Bunce")
			    .email("brad@launchdarkly.com")
			    .custom("Cell", "919-802-8222")
			    .custom("Company", "LaunchDarkly")
			    .custom("Group", "Solutions Engineering")
			    .custom("Country", "US")
			    .custom("State", "NC")
			    .custom("City", "Oak Island")
                            .build();

    boolean flagValue = client.boolVariation(FEATURE_FLAG_KEY, user, false);

    showMessage("Feature flag '" + FEATURE_FLAG_KEY + "' is " + flagValue + " for this user");

    // Here we ensure that the SDK shuts down cleanly and has a chance to deliver analytics
    // events to LaunchDarkly before the program exits. If analytics events are not delivered,
    // the user properties and flag usage statistics will not appear on your dashboard. In a
    // normal long-running application, the SDK would continue running and events would be
    // delivered automatically in the background.
    client.close();
  }
}
