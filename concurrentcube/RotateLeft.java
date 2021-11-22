package concurrentcube;

public class RotateLeft extends Operation {
    public RotateLeft(int layer, Cube cube) {
        super(true, 1, layer, cube);
    }

    public RotateLeft(int layer, Cube cube, OperationGroup group) {
        super(true, 1, layer, cube, group);
    }

    @Override
    public void run() {
        beforeRotation();

        rotateLeft();
        if (layer == 0) {
            cube.sides()[1].rotateClockwise();
        }
        if (layer == cube.size() - 1) {
            cube.sides()[3].rotateCounterclockwise();
        }

        afterRotation();
    }
}
