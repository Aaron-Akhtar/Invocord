package me.aaronakhtar.invocord.configuration;

import me.aaronakhtar.blockonomics_wrapper.objects.transaction.CallbackTransaction;
import me.aaronakhtar.invocord.objects.Invoice;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.aaronakhtar.invocord.configuration.ConfigurationMain.configurationDirectory;

public class InvoiceLogsFile {

    public static final File invoiceLogsFile = new File(configurationDirectory.getAbsolutePath() + "/invoices.json");
    private static volatile boolean updatingLogs = false;

    public static File getInvoiceLogFile() {
        try {
            ConfigurationUtilities.checkConfigurationFile(invoiceLogsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceLogsFile;
    }

    public static synchronized void updateInvoiceLogs(List<Invoice> invoices) {
        updatingLogs = true;
        try (FileWriter fileWriter = new FileWriter(invoiceLogsFile)) {
            ConfigurationMain.gson.toJson(ConfigurationMain.gson.toJson(invoices), fileWriter);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updatingLogs = false;
    }

    public static List<Invoice> getInvoices() {
        try {
            while (updatingLogs) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Invoice> invoices = new ArrayList<>();
        Invoice[] invoiceArray = null;
        try (FileReader fileReader = new FileReader(invoiceLogsFile)) {
            invoiceArray = ConfigurationMain.gson.fromJson(fileReader, Invoice[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (invoiceArray != null || invoiceArray.length != 0)
            invoices = Arrays.asList(invoiceArray);
        return invoices;
    }




}
