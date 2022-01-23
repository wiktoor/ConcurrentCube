package concurrentcube;

public class RotateTop extends Operation {
    public RotateTop(int layer, Cube cube) {
        super(true, 0, layer, cube);
    }

    public void run() {
        for (int i = 0; i < cube.size(); i++) {
            int tmp = cube.sides()[2].value(layer, i);
            cube.sides()[2].setValue(
                cube.sides()[3].value(layer, i), 
                layer, 
                i);
            cube.sides()[3].setValue(
                cube.sides()[4].value(layer, i), 
                layer, 
                i);
            cube.sides()[4].setValue(
                cube.sides()[1].value(layer, i), 
                layer, 
                i);
            cube.sides()[1].setValue(
                tmp, 
                layer, 
                i);
        }
        checkAndRotate();
    }
}
