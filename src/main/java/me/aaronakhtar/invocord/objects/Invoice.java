package me.aaronakhtar.invocord.objects;

import me.aaronakhtar.blockonomics_wrapper.BlockonomicsUtilities;

public class Invoice {

    private String invoiceId;
    private String invoicedUser;
    private String invoiceCreator;
    private double usdAmount;
    private String paymentAddress;
    private boolean paid = false;

    public Invoice(String invoiceId, String invoicedUser, String invoiceCreator, double usdAmount, String paymentAddress) {
        this.invoiceId = invoiceId;
        this.invoicedUser = invoicedUser;
        this.invoiceCreator = invoiceCreator;
        this.usdAmount = usdAmount;
        this.paymentAddress = paymentAddress;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getInvoicedUser() {
        return invoicedUser;
    }

    public String getInvoiceCreator() {
        return invoiceCreator;
    }

    public double getUsdAmount() {
        return usdAmount;
    }

    public double getBitcoinAmount(){
        return BlockonomicsUtilities.fiatToBitcoin("usd", this.usdAmount);
    }

    public String getPaymentAddress() {
        return paymentAddress;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}

