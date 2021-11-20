package concurrentcube;

public class RotateRight extends Operation {
    public RotateRight(int layer, Cube cube) {
        super(true, 3, cube.size() - layer - 1, cube);
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
