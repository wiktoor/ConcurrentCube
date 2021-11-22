package concurrentcube;

public class RotateBottom extends Operation {
    public RotateBottom(int layer, Cube cube) {
        super(true, 5, cube.size() - layer - 1, cube);
    }

    public RotateBottom(int layer, Cube cube, OperationGroup group) {
        super(true, 5, cube.size() - layer - 1, cube, group);
    }

    @Override
    public void run() {
        rotateTop();
        rotateTop();
        rotateTop();

        if (layer == 0) {
            cube.sides()[0].rotateCounterclockwise();
        }
        if (layer == cube.size() - 1) {
            cube.sides()[5].rotateClockwise();
        }
    }
}
