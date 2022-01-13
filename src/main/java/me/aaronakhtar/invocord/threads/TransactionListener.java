package me.aaronakhtar.invocord.threads;

import me.aaronakhtar.blockonomics_wrapper.Blockonomics;
import me.aaronakhtar.blockonomics_wrapper.objects.BlockonomicsCallbackSettings;
import me.aaronakhtar.blockonomics_wrapper.objects.transaction.CallbackTransaction;
import me.aaronakhtar.invocord.GeneralUtilities;

import static me.aaronakhtar.invocord.Invocord.blockonomics;

public class TransactionListener extends Thread {
    public static volatile boolean listen = false;

    private int callbackPort;
    private BlockonomicsCallbackSettings blockonomicsCallbackSettings;

    public TransactionListener(int callbackPort, BlockonomicsCallbackSettings blockonomicsCallbackSettings) {
        this.callbackPort = callbackPort;
        this.blockonomicsCallbackSettings = blockonomicsCallbackSettings;
        listen = true;
    }

    private static Runnable handleTransaction(CallbackTransaction transaction){
        return new Runnable() {
            @Override
            public void run() {
                //todo - handle transaction
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
                while(listen){
                    final CallbackTransaction transaction = blockonomics.listenForNewTransaction();
                    if (transaction != null){
                        new Thread(handleTransaction(transaction)).start(); // new thread to prevent blocking the listening function
                    }
                }
            }
        }catch (Exception e){
            GeneralUtilities.handleException(e);
        }

    }
}
