package concurrentcube;

public class RotateFront extends Operation {
    public RotateFront(int layer, Cube cube) {
        super(true, 2, layer, cube);
    }

    @Override
    public void run() {
        rotateFront();
    }
}
