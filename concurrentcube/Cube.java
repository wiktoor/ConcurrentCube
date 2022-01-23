package concurrentcube;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Cube {
    private final int size;
    private final BiConsumer<Integer, Integer> beforeRotation;
    private final BiConsumer<Integer, Integer> afterRotation;
    private final Runnable beforeShowing;
    private final Runnable afterShowing;
    private Side[] sides;
    private String display;
    private boolean isDisplaySet;
    private List<Operation> currentlyWorking;
    

    public Cube(int size,
                BiConsumer<Integer, Integer> beforeRotation,
                BiConsumer<Integer, Integer> afterRotation,  
                Runnable beforeShowing,
                Runnable afterShowing) {
        this.size = size;
        this.beforeRotation = beforeRotation;
        this.afterRotation = afterRotation;
        this.beforeShowing = beforeShowing;
        this.afterShowing = afterShowing;
        this.sides = new Side[6];
        for (int i = 0; i < 6; i++) sides[i] = new Side(i, size);
        this.currentlyWorking = new ArrayList<Operation>();
        this.isDisplaySet = false;
    }
    
    // getters
    public Side[] sides() { return sides; }
    public int size() { return size; }

    /**
     * Returns the current display of the cube
     * @Warning it should only be used when the display
     * cannot be modified at the same time.
     * Therefore, only use it within the 'synchronized(this)' section
     * @return current display of the cube
     */
    public String display() { return display; }

    /**
     * Checks if the display of the cube is already set
     * @Warning it should only be used when the display
     * cannot be modified at the same time.
     * Therefore, only use it within the 'synchronized(this)' section
     * @return is the display of the cube already set?
     */
    public boolean isDisplaySet() { return isDisplaySet; }

    // setters
    /**
     * Sets the display of the cube
     * @Warning it should only be used when the display 
     * cannot be modified at the same time.
     * Therefore, only use it within the 'synchronized(this)' section
     * @param display new display of the cube
     */
    public void setDisplay(String display) {
        if (!isDisplaySet) {
            isDisplaySet = true;
            this.display = display;
        }
    }

    /**
     * Returns the opposite side
     * @param side number corresponding to a side
     * @return number corresponding to the side opposite to side
     */
    public static int oppositeSide(int side) {
        switch (side) {
            case 0: return 5;
            case 1: return 3;
            case 2: return 4;
            case 3: return 1;
            case 4: return 2;
            default: return 0;
        }
    }

    public void rotate(int side, int layer) throws InterruptedException {
        if (layer < 0 || layer >= size || side < 0 || side > 5) return;
        Operation op = createRotation(side, layer); 
        entryProtocol(op);

        beforeRotation.accept(side, layer);
        op.run();
        afterRotation.accept(side, layer);

        endProtocol(op);
    }

    public String show() throws InterruptedException {
        Operation op = createShow();
        String res = "";
        entryProtocol(op);
        
        beforeShowing.run();
        // these operations are synchronized to avoid situation
        // when numerous processes are computing show() value
        // which is a costly operation
        synchronized (this) {
            op.run();
            res = display();
        }
        afterShowing.run();

        endProtocol(op);
        return res;
    }

    /**
     * Checks if operation can join currently working group.
     * @Warning it should only be used when currentlyWorking 
     * cannot be modified at the same time.
     * Therefore, only use it within the 'synchronized(this)' section
     * @param op some operation
     * @return can op join currently working group?
     */
    private boolean canJoin(Operation op) {
        for (Operation operation : currentlyWorking) {
            if (!operation.canWorkConcurrently(op)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns number of currently working processes
     * @Warning it should only be used when currentlyWorking 
     * cannot be modified at the same time.
     * Therefore, only use it within the 'synchronized(this)' section
     * @return number of currently working processes
     */
    private int numOfWorking() {
        return currentlyWorking.size();
    }

    /**
     * Protocol when operation wants to join computation
     * @param op operation that wants to join computation
     * @throws InterruptedException
     */
    private synchronized void entryProtocol(Operation op) throws InterruptedException {
        // read 'Warning' from canJoin documentation
        while(!canJoin(op)) {
            try { wait(); }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new InterruptedException();
            }
        }
        currentlyWorking.add(op);
    }

    /**
     * Protocol when an operation wants to exit computation
     * @param op operation that wants to exit computation
     */
    private synchronized void endProtocol(Operation op) {
        currentlyWorking.remove(op);
        // read 'Warning' from numOfWorking documentation
        if (numOfWorking() == 0) {
            if (op.isRotation) isDisplaySet = false;
            notifyAll();
        }
    }

    /**
     * Creates a rotation with respect to side and layer
     * @param side number corresponding to side
     * @param layer some layer
     * @return rotation wrt side and layer
     */
    private Operation createRotation(int side, int layer) {
        switch (side) {
            case 0: return new RotateTop(layer, this);
            case 1: return new RotateLeft(layer, this);
            case 2: return new RotateFront(layer, this);
            case 3: return new RotateRight(layer, this);
            case 4: return new RotateBack(layer, this);
            default: return new RotateBottom(layer, this);
        }
    }

    /**
     * Creates showing
     * @return showing
     */
    private Operation createShow() {
        return new Showing(this);
    }
}