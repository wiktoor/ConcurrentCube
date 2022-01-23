package concurrentcube;

public class RotateBottom extends Operation {
    public RotateBottom(int layer, Cube cube) {
        super(true, 5, layer, cube);
    }

    public void run() {
        for (int i = 0; i < cube.size(); i++) {
            int tmp = cube.sides()[1].value(cube.size() - layer - 1, i);
            cube.sides()[1].setValue(
                cube.sides()[4].value(cube.size() - layer - 1, i), 
                cube.size() - layer - 1, 
                i);
            cube.sides()[4].setValue(
                cube.sides()[3].value(cube.size() - layer - 1, i), 
                cube.size() - layer - 1, 
                i);
            cube.sides()[3].setValue(
                cube.sides()[2].value(cube.size() - layer - 1, i), 
                cube.size() - layer - 1, 
                i);
            cube.sides()[2].setValue(
                tmp, 
                cube.size() - layer - 1, 
                i);
        }
        checkAndRotate();
    }
}
