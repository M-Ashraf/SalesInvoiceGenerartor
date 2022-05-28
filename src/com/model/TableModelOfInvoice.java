package com.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TableModelOfInvoice extends AbstractTableModel {

    private ArrayList<InvoiceHeader> invoices;
    private String[] coulmns = {"No.", "Date", "Customer", "Total"};

    public TableModelOfInvoice(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }

    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return coulmns.length;
    }

    @Override
    public String getColumnName(int coulmn) {
        return coulmns[coulmn];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader invoice = invoices.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return invoice.getInvoiceNumber();
            case 1:
                return invoice.getInvoiceDate();
            case 2:
                return invoice.getCustomerName();
            case 3:
                return invoice.getToltalOfInvoice();
            default:
                return "";
        }

    }

}
