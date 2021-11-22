package concurrentcube;

public class RotateRight extends Operation {
    public RotateRight(int layer, Cube cube) {
        super(true, 3, cube.size() - layer - 1, cube);
    }

    public RotateRight(int layer, Cube cube, OperationGroup group) {
        super(true, 3, cube.size() - layer - 1, cube, group);
    }

    @Override
    public void run() {
        rotateLeft();
        rotateLeft();
        rotateLeft();

        if (layer == 0) {
            cube.sides()[1].rotateCounterclockwise();
        }
        if (layer == cube.size() - 1) {
            cube.sides()[3].rotateClockwise();
        }
    }
}
