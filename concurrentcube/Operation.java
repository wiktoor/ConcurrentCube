package concurrentcube;

public abstract class Operation implements Runnable {
    protected final boolean isRotation;
    protected final int side;
    protected final int layer;
    protected final Cube cube;
    protected OperationGroup group;

    public Operation(boolean isRotation,
                     int side,
                     int layer,
                     Cube cube) {
        this.isRotation = isRotation;
        this.side = side;
        this.layer = layer;
        this.cube = cube;
        this.group = null;
    }

    public Operation(boolean isRotation, 
                    int side,
                    int layer,
                    Cube cube,
                    OperationGroup group) {
        this.isRotation = isRotation;
        this.side = side;
        this.layer = layer;
        this.cube = cube;
        this.group = group;
    }

    // getters
    public boolean isRotation() {
        return isRotation;
    }

    public int side() {
        return side;
    }

    public int layer() {
        return layer;
    }

    public void beforeRotation() {
        // we assume that we got mutex
        group.noteStart();
        int actualLayer = 0;
        if (side == 0 || side == 1 || side == 2) {
            actualLayer = layer;
        }
        else actualLayer = cube.size() - layer - 1;

        cube.beforeRotation().accept(actualLayer, side);

        Thread t = new Thread(this);
        t.start();

        cube.mutex().release();
    }

    public void afterRotation() {
        cube.mutex().acquireUninterruptibly();
        int actualLayer = 0;
        if (side == 0 || side == 1 || side == 2) {
            actualLayer = layer;
        }
        else actualLayer = cube.size() - layer - 1;

        cube.afterRotation().accept(actualLayer, side);

        group.noteEnd();
        cube.mutex().release();
    }

    public void beforeShowing() {
        // we assume that we got mutex
        group.noteStart();
        Thread t = new Thread(cube.beforeShowing());
        t.start();
        try { t.join(); }
        catch (InterruptedException e) { /* TODO: co tu powinno być? */ }
        cube.mutex().release();
    } 

    public void afterShowing() {
        cube.mutex().acquireUninterruptibly();
        Thread t = new Thread(cube.afterShowing());
        t.start();
        try { t.join(); }
        catch (InterruptedException e) { /* TODO: co tu powinno być? */ }
        cube.mutex().release();
    }

    public boolean canWorkConcurrently(Operation op) {
        if (!isRotation || !op.isRotation()) return false;
        if (layer != op.layer() && (side == op.side() || side == Cube.oppositeSide(op.side()))) return true;
        return false;
    }

    protected void rotateFront() {
        for (int i = 0; i < cube.size(); i++) {
            int tmp = cube.sides()[0].value(cube.size() - layer - 1, i);
            cube.sides()[0].setValue(
                cube.sides()[1].value(cube.size() - i - 1, cube.size() - layer - 1), 
                cube.size() - layer - 1, 
                i);
            cube.sides()[1].setValue(
                cube.sides()[5].value(layer, cube.size() - i - 1), 
                cube.size() - i - 1, 
                cube.size() - layer - 1);
            cube.sides()[5].setValue(
                cube.sides()[3].value(i, layer), 
                layer, 
                cube.size() - i - 1);
            cube.sides()[3].setValue(
                tmp, 
                i, 
                layer);
        }
    }

    protected void rotateLeft() {
        for (int i = 0; i < cube.size(); i++) {
            int tmp = cube.sides()[0].value(i, layer);
            cube.sides()[0].setValue(
                cube.sides()[4].value(cube.size() - i - 1, cube.size() - layer - 1), 
                i, 
                layer);
            cube.sides()[4].setValue(
                cube.sides()[5].value(i, layer), 
                cube.size() - i - 1, 
                cube.size() - layer - 1);
            cube.sides()[5].setValue(
                cube.sides()[2].value(i, layer), 
                i, 
                layer);
            cube.sides()[2].setValue(
                tmp, 
                i, 
                layer);
        }
    }

    public void rotateTop() {
        for (int i = 0; i < cube.size(); i++) {
            int tmp = cube.sides()[2].value(layer, i);
            cube.sides()[2].setValue(
                cube.sides()[3].value(layer, i), 
                layer, 
                i);
            cube.sides()[3].setValue(
                cube.sides()[4].value(layer, i), 
                layer, 
                i);
            cube.sides()[4].setValue(
                cube.sides()[1].value(layer, i), 
                layer, 
                i);
            cube.sides()[1].setValue(
                tmp, 
                layer, 
                i);
        }
    }
}
