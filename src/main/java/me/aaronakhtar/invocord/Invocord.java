package me.aaronakhtar.invocord;

import com.google.gson.Gson;
import me.aaronakhtar.blockonomics_wrapper.Blockonomics;
import me.aaronakhtar.blockonomics_wrapper.objects.BlockonomicsCallbackSettings;
import me.aaronakhtar.blockonomics_wrapper.objects.transaction.CallbackTransaction;
import me.aaronakhtar.invocord.configuration.ConfigurationMain;
import me.aaronakhtar.invocord.configuration.ConfigurationUtilities;
import me.aaronakhtar.invocord.configuration.InvoiceLogsFile;
import me.aaronakhtar.invocord.configuration.TransactionLogsFile;
import me.aaronakhtar.invocord.objects.Invoice;
import me.aaronakhtar.invocord.threads.TransactionListener;

import java.util.ArrayList;
import java.util.List;

public class Invocord {

    public static final String name = "Invocord";
    public static Blockonomics blockonomics = null;
    public static ConfigurationMain configuration = null;
    public static BlockonomicsCallbackSettings blockonomicsCallbackSettings = null;

    //todo preload transactions and invoices
    public static final List<CallbackTransaction> transactions = new ArrayList<>();
    public static final List<Invoice> invoices = new ArrayList<>();

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

            // create files.
            TransactionLogsFile.getTransactionsLogFile();
            InvoiceLogsFile.getInvoiceLogFile();


            ConfigurationMain.gson = new Gson();    // disable pretty print after config creation

            configuration = ConfigurationMain.getMainConfiguration();
            blockonomics = new Blockonomics(configuration.getBlockonomicsApiKey());
            blockonomicsCallbackSettings = new BlockonomicsCallbackSettings(blockonomics, "/listener", configuration.getSecretKey());
            new TransactionListener(configuration.getPort(), blockonomicsCallbackSettings);









        }catch (Exception e){
            GeneralUtilities.handleException(e);
        }
    }

}
