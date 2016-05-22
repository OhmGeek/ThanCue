package ThanCue;
import java.util.EmptyStackException;


/**
 * Created by ryan on 22/05/16.
 */
public class CircularStack<T>  {

    private int N;
    private T[] stackArray;
    private int topPtr;
    private int btmPtr;

    public CircularStack(int size) {
        N = 0;
        stackArray = (T[]) new Object[size];
        this.topPtr = -1;
        this.btmPtr = 0;
    }

    public void push(T item) {
        N += 1;
        //if full, then remove the value at the bottom
        if(topPtr + btmPtr >= stackArray.length - 1) {
            btmPtr++; //we just write over the previous value.
        }

        //add the next value.
        topPtr = (topPtr + 1) % stackArray.length;
        stackArray[topPtr] = item;
    }

    public T pop() {

        //empty stack causes errors
        if(empty())
            throw new EmptyStackException();
        N -= 1;
        //find value to pop
        T valueToPop = stackArray[topPtr];


        //decrement and wrap around (if necessary) the top ptr.
        topPtr = (topPtr - 1) % stackArray.length;

        return valueToPop;
    }

    public boolean empty() {
        return N==0;
    }

    public T peek() {
        return stackArray[topPtr];
    }
}
