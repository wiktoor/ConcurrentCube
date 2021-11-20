package concurrentcube;

public class RotateRight extends Operation {
    public RotateRight(int layer, Cube cube) {
        super(true, 3, layer, cube);
    }

    @Override
    public void run() {
        rotateLeft();
        rotateLeft();
        rotateLeft();
    }
}
