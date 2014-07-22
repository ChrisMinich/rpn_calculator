package calculator; /**
 calculator.InputBuffer.java
 by Chris Minich
 cfminich@gmail.com

 This class will read a line of text, storing it in buffer.
 Once you instantiate it, you can obtain a string by calling yourObject.getNextString()

 If the buffer is empty, getNextString will automatically call prompt() in order to get
 more input from the user.
 */

import java.io.*;

class InputBuffer {
    private String buffer;
    private int position;
    private BufferedReader newLine = new BufferedReader(new InputStreamReader(System.in));

    // Construct empty buffer
    public InputBuffer() {
        position = -1;
        buffer = "";
    }

    // Prompt for input when buffer is empty
    private void prompt() {
        System.out.print("? ");

        try {
            buffer = newLine.readLine();
        }
        catch (IOException ioe) {
            System.out.println("IO error trying to read a string.");
            System.exit(1);
        }

        position = 0;
    }

    // Get a string from buffer
    public String getNextString() {
        if ( position < 0 )
            prompt();

        StringBuilder newString = new StringBuilder();
        char ch;

        while ( position < buffer.length() ) {
            ch = buffer.charAt(position++);
            if (ch != ' ')
                newString.append(ch);
            else if ( newString.length() > 0 )
                break;
        }

        if ( position >= buffer.length() )
            position = -1;

        return newString.toString();
    }
}