package me.aaronakhtar.invocord.objects;

public class Invoice {

    private String invoiceId;
    private String invoicedUser;
    private String invoiceCreator;
    private double usdAmount;

    public Invoice(String invoiceId, String invoicedUser, String invoiceCreator, double usdAmount) {
        this.invoiceId = invoiceId;
        this.invoicedUser = invoicedUser;
        this.invoiceCreator = invoiceCreator;
        this.usdAmount = usdAmount;
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
}
