package me.aaronakhtar.invocord.threads;

import me.aaronakhtar.blockonomics_wrapper.Blockonomics;
import me.aaronakhtar.blockonomics_wrapper.BlockonomicsUtilities;
import me.aaronakhtar.blockonomics_wrapper.objects.BlockonomicsCallbackSettings;
import me.aaronakhtar.blockonomics_wrapper.objects.transaction.CallbackTransaction;
import me.aaronakhtar.blockonomics_wrapper.objects.transaction.TransactionStatus;
import me.aaronakhtar.invocord.GeneralUtilities;
import me.aaronakhtar.invocord.Invocord;
import me.aaronakhtar.invocord.objects.Invoice;

import java.util.ArrayList;
import java.util.List;

import static me.aaronakhtar.invocord.Invocord.blockonomics;

public class TransactionListener extends Thread {
    public static volatile boolean listen = false;
    private volatile boolean listening = false;

    public static final List<CallbackTransaction> transactions = new ArrayList<>();

    private int callbackPort;
    private BlockonomicsCallbackSettings blockonomicsCallbackSettings;

    public TransactionListener(int callbackPort, BlockonomicsCallbackSettings blockonomicsCallbackSettings) {
        this.callbackPort = callbackPort;
        this.blockonomicsCallbackSettings = blockonomicsCallbackSettings;
        listen = true;
    }

    public void awaitListening(){
        while(!listening);
    }

    private static Runnable handleTransaction(CallbackTransaction transaction){
        return new Runnable() {
            @Override
            public void run() {
                if (transaction.getStatus() != TransactionStatus.UNCONFIRMED) {
                    final List<Invoice> invoices = Invocord.invoices;
                    // figure out a better way for this, maybe mapping

                    Invoice paidInvoice = null;
                    for (Invoice invoice : invoices) {
                        if (!invoice.isPaid() && invoice.getPaymentAddress().equals(transaction.getAddress().getAddress())) {
                            final long neededSatoshis = BlockonomicsUtilities.bitcoinToSatoshi(invoice.getBitcoinAmount());
                            if (transaction.getAmount() >= neededSatoshis) {
                                transactions.add(transaction);  // only logging valid invoice transactions
                                // create a dump log file for paid invoices
                                invoice.setPaid(true);
                                paidInvoice = invoice;
                            }
                        }
                    }
                    Invocord.invoices.remove(paidInvoice);
                }
            }
        };
    }

    @Override
    public void run() {

        // if this thread stops, so does the callback, this is so that
        // blockonomics can backlog payments and send them once the server is back online
        // rather than missing payments.

        try{
            Blockonomics.startCallbackServer(new BlockonomicsCallbackSettings[]{blockonomicsCallbackSettings}, callbackPort);
            try(AutoCloseable autoCloseable = () -> Blockonomics.stopCallbackServer()){
                GeneralUtilities.log("Listening for new transactions...");
                listening = true;
                while(listen){
                    final CallbackTransaction transaction = blockonomics.listenForNewTransaction();
                    if (transaction != null){
                        new Thread(handleTransaction(transaction)).start(); // new thread to prevent blocking the listening function
                    }
                }
            }
        }catch (Exception e){
            GeneralUtilities.handleException(e);
        }finally {
            if (listening) listening = false;
        }

    }
}
