package concurrentcube;

public class RotateBack extends Operation {
    public RotateBack(int layer, Cube cube) {
        super(true, 4, layer, cube);
    }

    public void run() {
        for (int i = 0; i < cube.size(); i++) {
            int tmp = cube.sides()[0].value(layer, i);
            cube.sides()[0].setValue(
                cube.sides()[1].value(cube.size() - i - 1,layer), 
                layer, 
                i);
            cube.sides()[1].setValue(
                cube.sides()[5].value(cube.size() - layer - 1, cube.size() - i - 1), 
                cube.size() - i - 1, 
                layer);
            cube.sides()[5].setValue(
                cube.sides()[3].value(i, cube.size() - layer - 1), 
                cube.size() - layer - 1, 
                cube.size() - i - 1);
            cube.sides()[3].setValue(
                tmp, 
                i, 
                cube.size() - layer - 1);
        }
        checkAndRotate();
    }
}
