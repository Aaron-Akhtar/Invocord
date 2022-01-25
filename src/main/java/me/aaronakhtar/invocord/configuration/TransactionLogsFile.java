package me.aaronakhtar.invocord.configuration;

import me.aaronakhtar.blockonomics_wrapper.objects.transaction.CallbackTransaction;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionLogsFile {

    private static final File transactionLogsFile = new File(ConfigurationMain.configurationDirectory.getAbsolutePath() + "/transactions.json");
    private static volatile boolean updatingLogs = false;

    public static File getTransactionConfigurationFile() {
        try {
            ConfigurationUtilities.checkConfigurationFile(transactionLogsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionLogsFile;
    }

    public static synchronized void updateTransactionLog(List<CallbackTransaction> transactions) {
        updatingLogs = true;

        try (FileWriter fileWriter = new FileWriter(transactionLogsFile)) {
            ConfigurationMain.gson.toJson(ConfigurationMain.gson.toJson(transactions), fileWriter);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        updatingLogs = false;
    }

    public static List<CallbackTransaction> getTransactions() {
        try {
            while (updatingLogs) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CallbackTransaction> transactions = new ArrayList<>();
        CallbackTransaction[] callbackTransactions = null;
        try (FileReader fileReader = new FileReader(transactionLogsFile)) {
            callbackTransactions = ConfigurationMain.gson.fromJson(fileReader, CallbackTransaction[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (callbackTransactions != null || callbackTransactions.length != 0)
            transactions = Arrays.asList(callbackTransactions);
        return transactions;
    }


}
