package concurrentcube;

public class RotateTop extends Operation {
    public RotateTop(int layer, Cube cube) {
        super(true, 0, layer, cube);
    }

    @Override
    public void run() {
        rotateTop(); 
        if (layer == 0) {
            cube.sides()[0].rotateClockwise();
        }
        if (layer == cube.size() - 1) {
            cube.sides()[5].rotateCounterclockwise();
        }
    }
}
