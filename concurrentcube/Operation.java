package concurrentcube;

public abstract class Operation implements Runnable {
    protected boolean isRotation;
    protected int side;
    protected int layer;
    protected Cube cube;

    public Operation(boolean isRotation, 
                    int side,
                    int layer,
                    Cube cube) {
        this.isRotation = isRotation;
        this.side = side;
        this.layer = layer;
        this.cube = cube;
    }
}
