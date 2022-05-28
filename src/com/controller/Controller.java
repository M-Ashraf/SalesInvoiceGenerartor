package com.controller;

import com.model.InvoiceHeader;
import com.model.InvoiceLine;
import com.model.TableModelOfInvoice;
import com.model.TableModelOfLine;
import com.view.DialogOfInvoice;
import com.view.DialogOfLine;
import com.view.FrameOfInvoice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller implements ActionListener, ListSelectionListener {

    private FrameOfInvoice frame;
    private DialogOfInvoice invoiceDialog;
    private DialogOfLine lineDialog;

    public Controller(FrameOfInvoice frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "CreateInvoiceForOk":
                createInvoiceOk();
                break;
            case "CreateInvoiceForCancel":
                createInvoiceForCancel();
                break;
            case "CreateLineforOK":
                createLineforOk();
                break;
            case "CreateLineforCancel":
                createLineforCancel();
                break;
        }
    }

    // Implementation of all buttons 
    private void createNewInvoice() {
        invoiceDialog = new DialogOfInvoice(frame);
        invoiceDialog.setVisible(true);

    }

    private void deleteInvoice() {
        int selectedRow = frame.getTableOfInvoice().getSelectedRow();
        if (selectedRow > -1) {
            frame.getInvoices().remove(selectedRow);
            frame.getTableModelOfInvoice().fireTableDataChanged();
        }
    }

    private void loadFile() {
        //To read InvoiceHeader File
        JFileChooser uploadFile = new JFileChooser();
        try {
            int result = uploadFile.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File invoiceHeaderFile = uploadFile.getSelectedFile();
                Path invoiceHeaderPath = Paths.get(invoiceHeaderFile.getAbsolutePath());
                List<String> invoiceHeaderLines = Files.readAllLines(invoiceHeaderPath);
                ArrayList<InvoiceHeader> invoices = new ArrayList<>(); // Creation of array of invoices
                for (String invoiceHeaderLine : invoiceHeaderLines) {
                    String invoiceHeaderElements[] = invoiceHeaderLine.split(",");
                    int invoiceNumber = Integer.parseInt(invoiceHeaderElements[0]);
                    String invoiceDate = invoiceHeaderElements[1];
                    String customerName = invoiceHeaderElements[2];

                    InvoiceHeader invoice = new InvoiceHeader(invoiceNumber, invoiceDate, customerName);
                    invoices.add(invoice);
                }
                //To read InvoiceLine File
                result = uploadFile.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File invoiceLineFile = uploadFile.getSelectedFile();
                    Path invoiceLinePath = Paths.get(invoiceLineFile.getAbsolutePath());
                    List<String> invoiceLineLines = Files.readAllLines(invoiceLinePath);
                    for (String invoiceLineLine : invoiceLineLines) {
                        String invoiceLineElements[] = invoiceLineLine.split(",");
                        int invoiceNumber = Integer.parseInt(invoiceLineElements[0]);
                        String itemName = invoiceLineElements[1];
                        double itemPrice = Double.parseDouble(invoiceLineElements[2]);
                        int count = Integer.parseInt(invoiceLineElements[3]);
                        InvoiceHeader invoiceNum = null;
                        for (InvoiceHeader invoice : invoices) {
                            if (invoice.getInvoiceNumber() == invoiceNumber) {
                                invoiceNum = invoice;
                                break;
                            }
                        }
                        InvoiceLine line = new InvoiceLine(itemName, itemPrice, count, invoiceNum);
                        invoiceNum.getInvoiceLines().add(line);
                    }
                }
                frame.setInvoices(invoices);
                TableModelOfInvoice tableModelOfInvoice = new TableModelOfInvoice(invoices);
                frame.setTableModelOfInvoice(tableModelOfInvoice);
                frame.getTableModelOfInvoice().fireTableDataChanged();
                frame.getTableOfInvoice().setModel(tableModelOfInvoice);
            }
        } catch (IOException uploadExecption) {
            uploadExecption.printStackTrace();
        }

    } // End of loadFile Method

    private void saveFile() {
        ArrayList<InvoiceHeader> invoices = frame.getInvoices();
        String headerCSV = "";
        String lineCSV = "";
        for (InvoiceHeader invoice : invoices) {
            String invoiceCSV = invoice.getCSV();
            headerCSV += invoiceCSV;
            headerCSV += "\n";

            for (InvoiceLine line : invoice.getInvoiceLines()) {
                String lineOfCSV = line.getCSV();
                lineCSV += lineOfCSV;
                lineCSV += "\n";
            }

        }
        // Implementation of saving header file
        try {
            JFileChooser uploadFile = new JFileChooser();
            int result = uploadFile.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File fileOfheader = uploadFile.getSelectedFile();
                FileWriter headerFileWriter = new FileWriter(fileOfheader);
                headerFileWriter.write(headerCSV);
                headerFileWriter.flush();
                headerFileWriter.close();
                result = uploadFile.showSaveDialog(frame);
                // Implementation of saving header file
                if (result == JFileChooser.APPROVE_OPTION) {
                    File fileOfLine = uploadFile.getSelectedFile();
                    FileWriter lineFileWriter = new FileWriter(fileOfLine);
                    lineFileWriter.write(lineCSV);
                    lineFileWriter.flush();
                    lineFileWriter.close();
                }
            }
        } catch (Exception ex) {

        }

    }

    private void createNewItem() {
        lineDialog = new DialogOfLine(frame);
        lineDialog.setVisible(true);
    }

    private void deleteItem() {
        int selectInvoice = frame.getTableOfInvoice().getSelectedRow();
        int selectedRow = frame.getTableOfLine().getSelectedRow();
        if (selectInvoice > -1 && selectedRow > -1) {
            InvoiceHeader invoice = frame.getInvoices().get(selectInvoice);
            invoice.getInvoiceLines().remove(selectedRow);
            TableModelOfLine tableModelOfLines = new TableModelOfLine(invoice.getInvoiceLines());
            frame.getTableOfLine().setModel(tableModelOfLines);
            tableModelOfLines.fireTableDataChanged();
            frame.getTableModelOfInvoice().fireTableDataChanged();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getTableOfInvoice().getSelectedRow();
        if (selectedIndex != -1) {
            InvoiceHeader selectedInvoice = frame.getInvoices().get(selectedIndex);
            frame.getInvoiceNumberLabel().setText("" + selectedInvoice.getInvoiceNumber());
            frame.getInvoiceDateField().setText(selectedInvoice.getInvoiceDate());
            frame.getCustomerNameField().setText(selectedInvoice.getCustomerName());
            frame.getInvoiceTotalLabel().setText("" + selectedInvoice.getToltalOfInvoice());
            TableModelOfLine tableModelOfLine = new TableModelOfLine(selectedInvoice.getInvoiceLines());
            frame.getTableOfLine().setModel(tableModelOfLine);
            tableModelOfLine.fireTableDataChanged();
        }

    }

    private void createInvoiceOk() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateField = invoiceDialog.getInvoiceDate().getText();
        String customeField = invoiceDialog.getCustName().getText();
        int number = frame.getNextInvoiceNumber();
        try {
            dateFormat.parse(dateField);
            InvoiceHeader invoice = new InvoiceHeader(number, dateField, customeField);
            frame.getInvoices().add(invoice);
            frame.getTableModelOfInvoice().fireTableDataChanged();
            invoiceDialog.setVisible(false);
            invoiceDialog.dispose();
            invoiceDialog = null;

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void createInvoiceForCancel() {

        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createLineforOk() {

        String itemName = lineDialog.getItemNameField().getText();
        String countString = lineDialog.getItemCountField().getText();
        String priceString = lineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countString);
        double price = Double.parseDouble(priceString);
        int selectinvoice = frame.getTableOfInvoice().getSelectedRow();
        if (selectinvoice > -1) {
            InvoiceHeader invoice = frame.getInvoices().get(selectinvoice);
            InvoiceLine line = new InvoiceLine(itemName, price, count, invoice);
            invoice.getInvoiceLines().add(line);
            TableModelOfLine tableModelOfLine = (TableModelOfLine) frame.getTableOfLine().getModel();
            // tableModelOfLine.getLines().add(line);
            tableModelOfLine.fireTableDataChanged();
            frame.getTableModelOfInvoice().fireTableDataChanged();

        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void createLineforCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;

    }
}
