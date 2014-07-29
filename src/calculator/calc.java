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

public class calc extends JPanel implements ActionListener {
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    public double d;

    public calc() {
        super(new GridBagLayout());

        textField = new JTextField(20);
        textField.addActionListener(this);

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }

    public void actionPerformed(ActionEvent evt) {
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
            else {
                if (op.equals("+")) add();
                else if (op.equals("-")) subtract();
                else if (op.equals("*")) multiply();
                else if (op.equals("/")) divide();
                else if (op.equals("s")) displayStack();
                else if (op.equals("c")) Operations.clearStack();
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

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("RPN Calc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new calc());

        //Display the window.
        frame.setSize(200,250);
        //frame.pack();
        frame.setVisible(true);
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
        Operations.divide();
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

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}