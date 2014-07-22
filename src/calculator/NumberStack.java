/**
 NumberStack.java
 by Chris Minich
 cfminich@gmail.com
 */
package calculator;
// A number stack interface.
public interface NumberStack {

    // Put a double into the queue.
    void push(double n);

    // Get a double from the queue.
    double pop();
}