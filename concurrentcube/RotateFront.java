package concurrentcube;

public class RotateFront extends Operation {
    public RotateFront(int layer, Cube cube) {
        super(true, 2, layer, cube);
    }

    @Override
    public void run() {
        for (int i = 0; i < cube.size(); i++) {
            int tmp = cube.sides()[0].value(cube.size() - layer - 1, i);
            cube.sides()[0].setValue(
                cube.sides()[1].value(cube.size() - i - 1, cube.size() - layer - 1), 
                cube.size() - layer - 1, 
                i);
            cube.sides()[1].setValue(
                cube.sides()[5].value(layer, cube.size() - i - 1), 
                cube.size() - i - 1, 
                cube.size() - layer - 1);
            cube.sides()[5].setValue(
                cube.sides()[3].value(i, layer), 
                layer, 
                cube.size() - i - 1);
            cube.sides()[3].setValue(
                tmp, 
                i, 
                layer);
        }
        if (layer == 0) {
            cube.sides()[2].rotateClockwise();
        }
        if (layer == cube.size() - 1) {
            cube.sides()[4].rotateCounterclockwise();
        }
    }
}
