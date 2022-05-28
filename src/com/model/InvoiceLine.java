package com.model;

public class InvoiceLine {

    private String itemName;
    private double itemPrice;
    private int count;
    private InvoiceHeader invoice;

    public InvoiceLine() {

    }

    public InvoiceLine(String itemName, double itemPrice, int count, InvoiceHeader invoice) {

        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.invoice = invoice;
    }

    public double getTotalOfLine() {
        return itemPrice * count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" + "invoiceNumber=" + invoice.getInvoiceNumber() + ", itemName=" + itemName + ", itemPrice=" + itemPrice + ", count=" + count + '}';
    }

    public String getCSV() {

        return invoice.getInvoiceNumber() + "," + itemName + "," + itemPrice + "," + count;
    }

}
