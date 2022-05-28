package com.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TableModelOfLine extends AbstractTableModel {

    private ArrayList<InvoiceLine> lines;
    private String[] columns = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    public TableModelOfLine(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }

    public ArrayList<InvoiceLine> getLines() {
        return lines;
    }

    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine line = lines.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return line.getInvoice().getInvoiceNumber();
            case 1:
                return line.getItemName();
            case 2:
                return line.getItemPrice();
            case 3:
                return line.getCount();
            case 4:
                return line.getTotalOfLine();
            default:
                return "";
        }

    }

}
