package calculator;

/**
 calculator.InputBuffer.java
 by Chris Minich
 cfminich@gmail.com

 This class will read a line of text and put it in buffer.
 Obtain each one-word substring by calling getNextString().
 */

class InputBuffer {
    private String buffer;
    private int position;

    public InputBuffer(String str) {
        position = 0;
        buffer = str;
    }

    public String getNextString() {
        if ( position < 0 )
            return "";

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