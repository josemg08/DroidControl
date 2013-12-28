/*
Develop by Jose Gonzalez & Tomas Najun
2013 - Argentina
*/

package com.josetomas.server.gui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddingShortCutDialog extends JDialog {

    public static final Dimension BUTTON_PREFERRED_SIZE = new Dimension(60, 60);
    public static final String SHORT_CUT = "Short cut: ";
    private JButton okButton, cancelButton, finishButton;
    private JLabel labelDialog, labelShortCut;
    private GroupLayout layout;

    String dialogText = "Enter a short-Cut or the first part of it";
    String dialogTitle = "Custom short-Cuts";



    public AddingShortCutDialog() {
        super();
        setModal(true);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");
        finishButton = new JButton("Finish");
        labelDialog = new JLabel(dialogText);
        labelShortCut = new JLabel("a");
        labelShortCut.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {         //Here i get the keycode and show it on the label
                Character character = e.getKeyChar();
                labelShortCut.setText(SHORT_CUT + String.valueOf(e.getKeyCode()));
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        labelShortCut.setFocusable(true);

        layout = new GroupLayout(getContentPane()); //todo
        getContentPane().setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateContainerGaps(true);



        setTitle(dialogTitle);

        configLayout();

        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void configLayout() {
        GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();

        GroupLayout.ParallelGroup column = layout.createParallelGroup();
        column.addComponent(labelDialog);
        column.addComponent(labelShortCut);

        GroupLayout.SequentialGroup columnRowBottom = layout.createSequentialGroup();
        columnRowBottom.addComponent(okButton);
        columnRowBottom.addComponent(cancelButton);
        columnRowBottom.addComponent(finishButton);
        column.addGroup(columnRowBottom);
        leftToRight.addGroup(column);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();

        GroupLayout.ParallelGroup rowBottom = layout.createParallelGroup();
        topToBottom.addComponent(labelDialog);
        topToBottom.addComponent(labelShortCut);
        rowBottom.addComponent(okButton);
        rowBottom.addComponent(cancelButton);
        rowBottom.addComponent(finishButton);
        topToBottom.addGroup(rowBottom);

        layout.linkSize(SwingConstants.HORIZONTAL, okButton, cancelButton, finishButton);
        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);
    }



    public static void main(String[] args) {
        AddingShortCutDialog a = new AddingShortCutDialog();
        a.setVisible(true);
    }

}
