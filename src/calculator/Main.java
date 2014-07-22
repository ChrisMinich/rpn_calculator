package calculator;
public class Main {

    public static void main(String[] args) {
        InputBuffer stringBuffer = new InputBuffer();
        String entry;
        double d;

        System.out.println("Valid commands: +, -, *, /, c, s, q");

        do {
            entry = stringBuffer.getNextString();
            if (Calc.isNumeric(entry)) {
                d = Double.parseDouble(entry);
                Calc.calcStack.push(d);
            }
            else {
                if (entry.equals("+")) Calc.add();
                else if (entry.equals("-")) Calc.subtract();
                else if (entry.equals("*")) Calc.multiply();
                else if (entry.equals("/")) Calc.divide();
                else if (entry.equals("s")) Calc.showStack();
                else if (entry.equals("c")) Calc.clearStack();
            }
        } while (!entry.equals("q"));
    }
}