package com.model;

import java.util.ArrayList;
import javafx.scene.shape.Line;

public class InvoiceHeader {

    private int invoiceNumber;
    private String invoiceDate;
    private String customerName;
    private ArrayList<InvoiceLine> invoiceLines;

    public double getToltalOfInvoice() {
        double total = 0.0;
        for (InvoiceLine line : getInvoiceLines()) {
            total += line.getTotalOfLine();
        }
        return total;
    }

    public ArrayList<InvoiceLine> getInvoiceLines() {
        if (invoiceLines == null) {
            invoiceLines = new ArrayList<>();
        }
        return invoiceLines;
    }

    public InvoiceHeader() {

    }

    public InvoiceHeader(int invoiceNumber, String inoiveDate, String customerName) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = inoiveDate;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String inoiveDate) {
        this.invoiceDate = inoiveDate;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate + ", customerName=" + customerName + '}';
    }

    public String getCSV() {
        return invoiceNumber + "," + invoiceDate + "," + customerName;
    }
}
