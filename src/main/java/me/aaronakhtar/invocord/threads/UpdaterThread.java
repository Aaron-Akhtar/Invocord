package me.aaronakhtar.invocord.threads;

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
                Thread.sleep(60000);  // update the log files every 1 min
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // also maybe make a shutdown hook  to backup everything
    }
}
