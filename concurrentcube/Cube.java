package concurrentcube;

import java.util.function.BiConsumer;

public class Cube {
    private int size;
    private BiConsumer<Integer, Integer> beforeRotation;
    private BiConsumer<Integer, Integer> afterRotation;
    private Runnable beforeShowing;
    private Runnable afterShowing;

    private Side[] sides;
    private String display;

    // getters
    public Side[] sides() { return sides; }
    public int size() { return size; }

    // setters
    public void setDisplay(String display) {
        this.display = display;
    }

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
    }

    public static int oppositeSide(int side) {
        switch (side) {
            case 0: return 5;
            case 1: return 3;
            case 2: return 4;
            case 3: return 1;
            case 4: return 2;
            case 5: return 0;
            default: {
                assert(false);
                return 0;
            }
        }
    }

    // funkcje do testowania

    public String sequentialShow() {
        String res = "";
        for(int i = 0; i < 6; i++) {
            res += sides[i].showSide();
            if (i != 5) res += "\n";
        }
        return res;
    }

    public String showTest() {
        Showing show = new Showing(this);
        Thread t = new Thread(show);
        t.run();
        try {
            t.join();
        }
        catch (InterruptedException e) {

        }
        return display;
    }

    public void rotateTopTest() {
        Thread t = new Thread(new RotateTop(0, this));
        t.run();
        try { t.join(); }
        catch (InterruptedException e) { }
    }

    public void rotateLeftTest() {
        Thread t = new Thread(new RotateLeft(0, this));
        t.run();
        try { t.join(); }
        catch (InterruptedException e) { }
    }

    public void rotateFrontTest() {
        Thread t = new Thread(new RotateFront(0, this));
        t.run();
        try { t.join(); }
        catch (InterruptedException e) { }
    }

    public void rotateBottomTest() {
        Thread t = new Thread(new RotateBottom(0, this));
        t.run();
        try { t.join(); }
        catch (InterruptedException e) { }
    }
}