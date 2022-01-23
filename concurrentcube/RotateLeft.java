package concurrentcube;

public class RotateLeft extends Operation {
    public RotateLeft(int layer, Cube cube) {
        super(true, 1, layer, cube);
    }

    public void run() {
        for (int i = 0; i < cube.size(); i++) {
            int tmp = cube.sides()[0].value(i, layer);
            cube.sides()[0].setValue(
                cube.sides()[4].value(cube.size() - i - 1, cube.size() - layer - 1), 
                i, 
                layer);
            cube.sides()[4].setValue(
                cube.sides()[5].value(i, layer), 
                cube.size() - i - 1, 
                cube.size() - layer - 1);
            cube.sides()[5].setValue(
                cube.sides()[2].value(i, layer), 
                i, 
                layer);
            cube.sides()[2].setValue(
                tmp, 
                i, 
                layer);
        }
        checkAndRotate();
    }
}
