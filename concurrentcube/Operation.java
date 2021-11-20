package concurrentcube;

public abstract class Operation implements Runnable {
    protected boolean isRotation;
    protected int side;
    protected int layer;
    protected Cube cube;

    public Operation(boolean isRotation, 
                    int side,
                    int layer,
                    Cube cube) {
        this.isRotation = isRotation;
        this.side = side;
        this.layer = layer;
        this.cube = cube;
    }

    protected void rotateFront() {
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
    }

    protected void rotateLeft() {
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
    }

    public void rotateTop() {
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
    }
}
