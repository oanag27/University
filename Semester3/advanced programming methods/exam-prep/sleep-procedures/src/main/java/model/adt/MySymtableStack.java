package model.adt;

import model.exception.EmptyADTException;
import model.exception.MyException;
import model.value.Value;

public class MySymtableStack extends MyStack<MyIDictionary<String, Value>>{
    public MySymtableStack deepCopy() throws MyException, EmptyADTException {
        MySymtableStack stack = new MySymtableStack();
        MyStack<MyIDictionary<String, Value>> stackCopy = new MyStack<>();
        while (!this.isEmpty()) {
            stackCopy.push(this.pop());
        }
        while (!stackCopy.isEmpty()) {
            this.push(stackCopy.peek());
            stack.push(stackCopy.pop());

        }
        return stack;
    }
}
