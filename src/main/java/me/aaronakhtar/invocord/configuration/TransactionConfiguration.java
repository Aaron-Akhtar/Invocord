package me.aaronakhtar.invocord.configuration;

import me.aaronakhtar.blockonomics_wrapper.objects.transaction.CallbackTransaction;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionConfiguration {

    private static final File transactionConfigurationFile = new File(ConfigurationMain.configurationDirectory.getAbsolutePath() + "/transactions.json");

    public static File getTransactionConfigurationFile() {
        try {
            ConfigurationMain.checkConfigurationFile(transactionConfigurationFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return transactionConfigurationFile;
    }

    public static List<CallbackTransaction> getTransactions(){
        List<CallbackTransaction> transactions = new ArrayList<>();
        CallbackTransaction[] callbackTransactions = null;
        try(FileReader fileReader = new FileReader(transactionConfigurationFile)){
            callbackTransactions = ConfigurationMain.gson.fromJson(fileReader, CallbackTransaction[].class);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (callbackTransactions != null || callbackTransactions.length != 0) transactions = Arrays.asList(callbackTransactions);
        return transactions;
    }





}
