package concurrentcube;

public class RotateBack extends Operation {
    public RotateBack(int layer, Cube cube) {
        super(true, 4, cube.size() - layer - 1, cube);
    }

    @Override
    public void run() {
        rotateFront();
        rotateFront();
        rotateFront();

        if (layer == 0) {
            cube.sides()[2].rotateCounterclockwise();
        }
        if (layer == cube.size() - 1) {
            cube.sides()[4].rotateClockwise();
        }
    }
}
