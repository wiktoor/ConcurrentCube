package concurrentcube;

public class RotateBottom extends Operation {
    public RotateBottom(int layer, Cube cube) {
        super(true, 5, layer, cube);
    }

    @Override
    public void run() {
        rotateTop();
        rotateTop();
        rotateTop();
    }
}
