package concurrentcube;

public abstract class Operation {
    protected final boolean isRotation;
    protected final int side;
    protected final int layer;
    protected final Cube cube;

    public Operation(boolean isRotation,
                     int side,
                     int layer,
                     Cube cube) {
        this.isRotation = isRotation;
        this.side = side;
        this.layer = layer;
        this.cube = cube;
    }

    /**
     * Checks if this operation can work concurrently with another operation
     * @param op another operation
     * @return can this and op work concurrently?
     */
    public boolean canWorkConcurrently(Operation op) {
        if (isRotation != op.isRotation) return false;
        if (!isRotation && !op.isRotation) return true;
        if (side == op.side && layer != op.layer) return true;
        if (side == Cube.oppositeSide(op.side) 
            && layer != cube.size() - layer - 1) return true;
        return false;
    }

    /**
     * Rotates this side and the opposite side if necessary
     */
    protected void checkAndRotate() {
        if (layer == 0) {
            cube.sides()[side].rotateClockwise();
        }
        if (layer == cube.size() - 1) {
            cube.sides()[Cube.oppositeSide(side)].rotateCounterclockwise();
        }
    }

    public abstract void run();
}
