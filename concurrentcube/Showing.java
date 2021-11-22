package concurrentcube;

public class Showing extends Operation {

    public Showing(Cube cube) {
        super(false,
              0,
              0,
              cube);
    }

    public Showing(Cube cube, OperationGroup group) {
        super(false, 
              0, // doesn't matter
              0, // doesn't matter
              cube,
              group);
    }

    @Override
    public void run() {
        String res = "";
        for (int i = 0; i < 6; i++) {
            res += cube.sides()[i].showSide();
            if (i != 5) res += "\n\n";
        }
        cube.setDisplay(res);
    }
}
