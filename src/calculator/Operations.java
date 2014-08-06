package calculator;

class Operations {
    public static RegisterStack calcStack = new RegisterStack(8, "primary");
    private static double x, y;

    private static boolean popTwo() {
        if ( calcStack.getCount() < 2 ) {
            System.out.println("Insufficient operand count");
            if ( calcStack.getCount() == 1 )
                System.out.println(calcStack.getValueAtIndex(0));
            return false;
        }
        else {
            x = calcStack.pop();
            y = calcStack.pop();
            return true;
        }
    }

    private static void pushResult(double result) {
        calcStack.push(result);
    }

    public static boolean add() {
        if ( popTwo() ) {
            double result = y + x;
            pushResult(result);
            return true;
        }
        return false;
    }

    public static boolean subtract() {
        if ( calcStack.getCount() < 1 ) {
            calcStack.printEmptyMsg();
            return false;
        }
        else if ( calcStack.getCount() == 1) {
            x = calcStack.pop();
            y = 0;
        }
        else {
            popTwo();
        }

        double result = y - x;
        pushResult(result);
        return true;
    }

    public static boolean multiply() {
        if ( popTwo() ) {
            double result = y * x;
            pushResult(result);
            return true;
        }
        return false;
    }

    public static boolean divide() {
        if ( !popTwo() ) return false;
        if ( x == 0 ) {
            System.out.println("Cannot divide by 0.");
            calcStack.push(y);
            calcStack.push(x);
        }
        else {
            double result = y / x;
            pushResult(result);
        }
        return true;
    }

    // get result from top of stack ( x register )
    public static double getRegisterX() {
        int size = calcStack.getCount();
        if ( size > 0 )
            return calcStack.getValueAtIndex(calcStack.getCount()-1);
        else
            return 0;
    }

    // print out the stack
    public static void showStack() {
        for (int i=0; i<calcStack.getCount(); i++)
            System.out.println(calcStack.getValueAtIndex(i));
    }

    // clear the stack
    public static void clearStack() {
        calcStack.clearStack();
    }

    // see if a string is numeric
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // see if a string is hexadecimal
    public static boolean isHex(String str) {
        try {
            int i = Integer.parseInt(str, 16);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // show x in hexadecimal
    public static String asHex() {
        int i = (int) calcStack.getX();
        return Integer.toHexString(i);
    }

}