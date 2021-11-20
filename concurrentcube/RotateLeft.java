package concurrentcube;

public class RotateLeft extends Operation {
    public RotateLeft(int layer, Cube cube) {
        super(true, 1, layer, cube);
    }

    @Override
    public void run() {
        rotateLeft();
    }
}
