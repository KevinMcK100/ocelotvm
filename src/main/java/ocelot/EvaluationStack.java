package ocelot;

import java.util.Stack;

/**
 *
 * @author ben
 */
public class EvaluationStack extends Stack<JVMValue> {

    void iconst(int i) {
        push(new JVMValue(JVMType.I, i));
    }
    
    void dconst(int i) {
        push(new JVMValue(JVMType.D, i));
    }

    void iadd() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int add = (int) ev1.value + (int) ev2.value;
        push(new JVMValue(JVMType.I, add));
    }
    
    void dadd() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int add = (int) ev1.value + (int) ev2.value;
        push(new JVMValue(JVMType.D, add));
    }

    void idiv() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int div = (int) ev1.value / (int) ev2.value;
        push(new JVMValue(JVMType.I, div));
    }

    void imul() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int mul = (int) ev1.value * (int) ev2.value;
        push(new JVMValue(JVMType.I, mul));
    }

    void isub() {
        JVMValue ev1 = pop();
        JVMValue ev2 = pop();
        // For a runtime checking interpreter - type checks would go here...
        int sub = (int) ev1.value - (int) ev2.value;
        push(new JVMValue(JVMType.I, sub));
    }

}
