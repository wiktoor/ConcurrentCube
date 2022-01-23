package concurrentcube;

public class RotateRight extends Operation {
    public RotateRight(int layer, Cube cube) {
        super(true, 3, layer, cube);
    }

    public void run() {
        for (int i = 0; i < cube.size(); i++) {
            int tmp = cube.sides()[4].value(i, layer);
            cube.sides()[4].setValue(
                cube.sides()[0].value(cube.size() - i - 1, cube.size() - layer - 1), 
                i, 
                layer);
            cube.sides()[0].setValue(
                cube.sides()[2].value(cube.size() - i - 1, cube.size() - layer - 1), 
                cube.size() - i - 1, 
                cube.size() - layer - 1);
            cube.sides()[2].setValue(
                cube.sides()[5].value(cube.size() - i - 1, cube.size() - layer - 1), 
                cube.size() - i - 1, 
                cube.size() - layer - 1);
            cube.sides()[5].setValue(
                tmp, 
                cube.size() - i - 1, 
                cube.size() - layer - 1);
        }
        checkAndRotate();
    }
}
