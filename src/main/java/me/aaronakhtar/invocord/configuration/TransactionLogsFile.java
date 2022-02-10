package me.aaronakhtar.invocord.configuration;

import me.aaronakhtar.blockonomics_wrapper.objects.transaction.CallbackTransaction;
import me.aaronakhtar.invocord.GeneralUtilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionLogsFile {

    private static final File transactionLogsFile = new File(ConfigurationMain.configurationDirectory.getAbsolutePath() + "/transactions.json");
    private static volatile boolean updatingLogs = false;

    public static File getTransactionsLogFile() {
        try {
            ConfigurationUtilities.checkConfigurationFile(transactionLogsFile);
        } catch (Exception e) {
            GeneralUtilities.handleException(e);
        }
        return transactionLogsFile;
    }

    public static synchronized void updateTransactionLog(List<CallbackTransaction> transactions) {
        updatingLogs = true;
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(transactionLogsFile.toURI()), StandardOpenOption.TRUNCATE_EXISTING)) {
            ConfigurationMain.gson.toJson(ConfigurationMain.gson.toJson(transactions), writer);
            writer.flush();
        } catch (Exception e) {
            GeneralUtilities.handleException(e);
        }

        updatingLogs = false;
    }

    public static List<CallbackTransaction> getTransactions() {
        try {
            while (updatingLogs) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            GeneralUtilities.handleException(e);
        }

        List<CallbackTransaction> transactions = new ArrayList<>();
        CallbackTransaction[] callbackTransactions = null;
        try (FileReader fileReader = new FileReader(transactionLogsFile)) {
            callbackTransactions = ConfigurationMain.gson.fromJson(fileReader, CallbackTransaction[].class);
        } catch (Exception e) {
            GeneralUtilities.handleException(e);
        }
        if (callbackTransactions != null && callbackTransactions.length != 0)
            transactions = Arrays.asList(callbackTransactions);
        return transactions;
    }


}
