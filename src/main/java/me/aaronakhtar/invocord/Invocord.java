package me.aaronakhtar.invocord;

import me.aaronakhtar.blockonomics_wrapper.Blockonomics;
import me.aaronakhtar.blockonomics_wrapper.objects.BlockonomicsCallbackSettings;
import me.aaronakhtar.blockonomics_wrapper.objects.transaction.TransactionStatus;
import me.aaronakhtar.invocord.configuration.ConfigurationMain;
import me.aaronakhtar.invocord.configuration.ConfigurationUtilities;
import me.aaronakhtar.invocord.threads.TransactionListener;

public class Invocord {

    //  todos in order of priority;
    //  todo - JSON ARRAY for invoices, upon starting bot, it will fetch all
    //         invoices/pre-load them, and it will concurrently update the invoice file
    //         when new invoices are created to prevent loss of data due to any fatal issues.

    public static final String name = "Invocord";
    public static Blockonomics blockonomics = null;
    public static ConfigurationMain configuration = null;
    public static BlockonomicsCallbackSettings blockonomicsCallbackSettings = null;

    public static void main(String[] args) {
        // Actual DISCORD API integration will likely occur after most of the main stuff has been completed.
        try {
            if (ConfigurationUtilities.checkConfigurationFile(ConfigurationMain.mainConfigurationFile)){
                if (ConfigurationMain.setupMainConfiguration()) {
                    System.out.println("Configuration File was created (\"" + ConfigurationMain.mainConfigurationFile.getAbsolutePath() + "\")...");
                }else{
                    System.out.println("Fatal Error Occurred.... (creating config file)");
                }
                return;
            }

            configuration = ConfigurationMain.getMainConfiguration();
            blockonomics = new Blockonomics(configuration.getBlockonomicsApiKey());
            blockonomicsCallbackSettings = new BlockonomicsCallbackSettings(blockonomics, "/listener", configuration.getSecretKey());
            new TransactionListener(configuration.getPort(), blockonomicsCallbackSettings);








        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
