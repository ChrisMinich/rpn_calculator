package calculator;

class Calc {
    public static RegisterStack calcStack = new RegisterStack(8, "primary");
    public static double x, y;

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
        System.out.println(result);
    }

    public static void add() {
        if ( popTwo() ) {
            double result = y + x;
            pushResult(result);
        }
    }

    public static void subtract() {
        if ( calcStack.getCount() < 1 ) {
            calcStack.printEmptyMsg();
            return;
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
    }

    public static void multiply() {
        if ( popTwo() ) {
            double result = y * x;
            pushResult(result);
        }
    }

    public static void divide() {
        if ( !popTwo() ) return;
        if ( x == 0 ) {
            System.out.println("Cannot divide by 0.");
            calcStack.push(y);
            calcStack.push(x);
        }
        else {
            double result = y / x;
            pushResult(result);
        }
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
}