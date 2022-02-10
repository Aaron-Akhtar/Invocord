package me.aaronakhtar.invocord.objects;

import me.aaronakhtar.blockonomics_wrapper.BlockonomicsUtilities;
import me.aaronakhtar.invocord.GeneralUtilities;
import me.aaronakhtar.invocord.Invocord;

import java.util.Date;

public class Invoice {

    private String invoiceId;
    private String invoicedUser;
    private String invoiceCreator;
    private double usdAmount;
    private String paymentAddress;
    private boolean paid = false;
    private String creationDate;

    public Invoice(String invoiceId, String invoicedUser, String invoiceCreator, double usdAmount, String paymentAddress) {
        this.invoiceId = invoiceId;
        this.invoicedUser = invoicedUser;
        this.invoiceCreator = invoiceCreator;
        this.usdAmount = usdAmount;
        this.paymentAddress = paymentAddress;
        creationDate = GeneralUtilities.sdf.format(new Date());
        Invocord.invoices.add(this);
    }

    public String getCreationDate() {
        return creationDate;
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
        GeneralUtilities.log("Invoice ("+invoiceId+") Paid Status Updated: " + paid); // temp
        this.paid = paid;
    }
}

