package me.aaronakhtar.invocord.threads;

import me.aaronakhtar.invocord.GeneralUtilities;
import me.aaronakhtar.invocord.Invocord;
import me.aaronakhtar.invocord.configuration.InvoiceLogsFile;
import me.aaronakhtar.invocord.configuration.TransactionLogsFile;

public class UpdaterThread extends Thread {

    public static volatile boolean running = true;

    @Override
    public void run() {

        while(running){
            try {
                TransactionLogsFile.updateTransactionLog(Invocord.transactions);
                InvoiceLogsFile.updateInvoiceLogs(Invocord.invoices);
                GeneralUtilities.log("Updated Log Files...");
                Thread.sleep(60000);  // update the log files every 1 min
            }catch (Exception e){
                GeneralUtilities.handleException(e);
            }
        }

        // also maybe make a shutdown hook  to backup everything
    }
}
