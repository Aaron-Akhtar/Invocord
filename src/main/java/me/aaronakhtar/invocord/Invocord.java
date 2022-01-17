package me.aaronakhtar.invocord;

import me.aaronakhtar.blockonomics_wrapper.Blockonomics;
import me.aaronakhtar.invocord.configuration.ConfigurationMain;

public class Invocord {

    // todos in order of priority;
    //  todo - create json configuration system.
    //  todo - JSON ARRAY for invoices, upon starting bot, it will fetch all
    //         invoices/pre-load them, and it will concurrently update the invoice file
    //         when new invoices are created to prevent loss of data due to any fatal issues.

    public static final String name = "Invocord";
    public static Blockonomics blockonomics = null;     // todo - init after reading configuration which should contain the blockonomics api key.

    public static void main(String[] args) {
        // Actual DISCORD API integration will likely occur after most of the main stuff has been completed.
        try {
            if (ConfigurationMain.checkConfigurationFile(ConfigurationMain.mainConfigurationFile)){
                if (ConfigurationMain.setupMainConfiguration()) {
                    System.out.println("Configuration File was created (\"" + ConfigurationMain.mainConfigurationFile.getAbsolutePath() + "\")...");
                }else{
                    System.out.println("Fatal Error Occurred....");
                }
                return;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
