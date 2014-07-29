/**
 RegisterStack.java
 by Chris Minich
 cfminich@gmail.com

 This is a stack of doubles. If you insert more items than the stack can hold,
 the bottom item is deleted.

 The primary stack of the calculator uses this class. It is also intended to
 be used for memory registers.

 The string "name", is designed to identify a memory register that the user
 has put into use during the run. Example: <sto 5> to use memory location 5.
 This way, each storage register will be dynamically created when the user
 stores a value in a previously unused location.
 */

package calculator;

class RegisterStack implements NumberStack {
    private double stack[];
    private int index;
    private String name;

    // Construct an empty stack given its size.
    public RegisterStack(int size) {
        stack = new double[size];
        index = -1;
        name = "anonStack";
    }

    // Construct an empty stack given its size and its name.
    public RegisterStack(int size, String n) {
        stack = new double[size];
        index = -1;
        name = n;
    }

    // Push a number on the stack
    public void push(double x) {
        if (index+1 == stack.length) {
            for (int i=0; i<stack.length-1; i++) {
                stack[i] = stack[i+1];
                index = i;
            }
        }
        stack[++index] = x;
    }

    // Pop a number from the stack
    public double pop() {
        if(index < 0) {
            printEmptyMsg();
            return 0;
        }
        return stack[index--];
    }

    // getCount() - number of items in the queue
    public int getCount() {
        return index+1;
    }

    // getName() - return the name of the stack
    public String getName() {
        return name;
    }

    // getValueAtIndex() - return the array element at given index
    public double getValueAtIndex(int index) {
        return stack[index];
    }

    // clearStack()
    public void clearStack() {
        index = -1;
    }

    public void printEmptyMsg() {
        System.out.println("Stack is empty");
    }
}