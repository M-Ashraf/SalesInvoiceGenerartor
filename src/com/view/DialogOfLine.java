package com.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DialogOfLine extends JDialog {

    private JTextField itemName;
    private JTextField itemCount;
    private JTextField itemPrice;
    private JLabel itemNameLabel;
    private JLabel itemCountLabel;
    private JLabel itemPriceLabel;
    private JButton okButton;
    private JButton cancelButton;

    public DialogOfLine(FrameOfInvoice frame) {
        itemName = new JTextField(20);
        itemNameLabel = new JLabel("Item Name");

        itemCount = new JTextField(20);
        itemCountLabel = new JLabel("Item Count");

        itemPrice = new JTextField(20);
        itemPriceLabel = new JLabel("Item Price");

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        okButton.setActionCommand("CreateLineforOK");
        cancelButton.setActionCommand("CreateLineforCancel");

        okButton.addActionListener(frame.getController());
        cancelButton.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));

        add(itemNameLabel);
        add(itemName);
        add(itemCountLabel);
        add(itemCount);
        add(itemPriceLabel);
        add(itemPrice);
        add(okButton);
        add(cancelButton);

        pack();
    }

    public JTextField getItemNameField() {
        return itemName;
    }

    public JTextField getItemCountField() {
        return itemCount;
    }

    public JTextField getItemPriceField() {
        return itemPrice;
    }
}
