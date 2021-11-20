package concurrentcube;

public class RotateBack extends Operation {
    public RotateBack(int layer, Cube cube) {
        super(true, 4, layer, cube);
    }

    @Override
    public void run() {
        rotateFront();
        rotateFront();
        rotateFront();
    }
}
