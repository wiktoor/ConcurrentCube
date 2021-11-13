package concurrentcube;

import java.util.function.BiConsumer;

public class Cube {
    int size;
    BiConsumer<Integer, Integer> beforeRotation;
    BiConsumer<Integer, Integer> afterRotation;
    Runnable beforeShowing;
    Runnable afterShowing;

    Side[] sides;

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

    public String sequentialShow() {
        String res = "";
        for(int i = 0; i < 6; i++) {
            res += sides[i].showSide();
            if (i != 6) res += "\n";
        }
        return res;
    }
}