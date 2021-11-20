package concurrentcube;

public class RotateTop extends Operation {
    public RotateTop(int layer, Cube cube) {
        super(true, 0, layer, cube);
    }

    @Override
    public void run() {
       rotateTop(); 
    }
}
