package concurrentcube;

public class RotateFront extends Operation {
    public RotateFront(int layer, Cube cube) {
        super(true, 2, layer, cube);
    }

    public RotateFront(int layer, Cube cube, OperationGroup group) {
        super(true, 2, layer, cube, group);
    }

    @Override
    public void run() {
        rotateFront();
        if (layer == 0) {
            cube.sides()[2].rotateClockwise();
        }
        if (layer == cube.size() - 1) {
            cube.sides()[4].rotateCounterclockwise();
        }
    }
}
