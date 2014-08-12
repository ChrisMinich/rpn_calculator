/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package calculator;

/* TextDemo.java requires no other files. */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class calc extends JFrame {
    private JTextField textField;
    private JScrollPane  scrollPane;
    private JTextArea  textArea;
    private final static String newline = "\n";
    private double d;
    private final static String helpString;
    private final static String infoString;

    static {
        String h1 = "\nValid commands:\n\n+   -   *   /\n\nhex: hexadecimal value\ns: show stack\nclear: empty the stack\n";
        String h2 = "\ninfo: additional information\n\n";
        String h3 = "\nStack holds 8 numbers\n";
        String h4 = "\nUse postfix notation.\nEx:\n15  5  /\n3.0\n";
        String h5 = "\nNon commands are echoed to\nthis text area.\n\n";
        helpString = h1 + h2;
        infoString = h3 + h4 + h5;
    }

    public calc() {
        textField = new JTextField();
        scrollPane = new JScrollPane();
        textArea = new JTextArea();
        initComponents();
        textField.requestFocus();
        textField.selectAll();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple Calculator");

        textField.setText("");
        // textField.setMargin(new Insets(2,5,2,5));
        textField.setPreferredSize(new Dimension(86, 27));

        textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jActionPerformed(evt);
            }
        });

        textArea.setColumns(20);
        textArea.setRows(5);
        // textArea.setMargin(new Insets(2,5,2,5));
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                        .addComponent(textField, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
        textField.setText("Enter commands here" + newline);
        textField.selectAll();
    }

    public void jActionPerformed(ActionEvent evt) {
        String op;
        String text = textField.getText();
        InputBuffer lineStr = new InputBuffer(text);
        op = lineStr.getNextString();
        while ( !op.equals("") ) {
            textArea.append(op + newline);
            if (Operations.isNumeric(op)) {
                d = Double.parseDouble(op);
                Operations.calcStack.push(d);
            }
            else if (Operations.isHex(op)) {
                Integer i = Integer.parseInt(op, 16);
                Operations.calcStack.push(i);
                textArea.append(i.toString() + newline);
            }
            else {
                if (op.equals("help")) {
                    textArea.append(help("help"));
                    op = lineStr.getNextString();
                    continue;
                }
                if (op.equals("info")) {
                    textArea.append(help("info"));
                    op = lineStr.getNextString();
                    continue;
                }
                else if (op.equals("clear"))
                    Operations.clearStack();
                else if (op.equals("+")) add();
                else if (op.equals("-")) subtract();
                else if (op.equals("*")) multiply();
                else if (op.equals("/")) divide();
                else if (op.equals("s")) displayStack();
                else if (op.equals("hex")) {
                    textArea.append(Operations.asHex() + newline);
                    op = lineStr.getNextString();
                    continue;
                }
                else { // echo input only
                    op = lineStr.getNextString();
                    continue;
                }
                // calculation was performed, get result from stack
                d = Operations.getRegisterX();
                textArea.append(d + newline);
            }
            op = lineStr.getNextString();
        }

        textField.setText("");

        // textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public String help(String str) {
        if (str.equals("help")) return helpString;
        else return infoString;
    }

    public void add() {
        Operations.add();
    }

    public void subtract() {
        Operations.subtract();
    }

    public void multiply() {
        Operations.multiply();
    }

    public void divide() {
        if (!Operations.divide())
            textArea.append("Cannot divide by 0" + newline);
    }

    // print out the stack
    public void displayStack() {
        double d;
        textArea.append("[" + newline);
        for (int i=0; i < Operations.calcStack.getCount(); i++) {
            d = Operations.calcStack.getValueAtIndex(i);
            textArea.append(d + newline);
        }
        textArea.append("]" + newline);
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(calc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(calc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(calc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(calc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new calc().setVisible(true);
            }
        });
    }
}