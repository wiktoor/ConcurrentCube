package concurrentcube;

public class Showing extends Operation {
    public Showing(Cube cube) {
        super(
            false, 
            0, // doesn't matter
            0, // doesn't matter
            cube);
    }

    /**
     * Checks if the display of the cube is set, and if not, then sets it
     * @Warning it should only be used when the display 
     * cannot be modified at the same time.
     * Therefore, only use it within the 'synchronized(this)' section
     */
    public void run() {
        if (!cube.isDisplaySet()) {
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                res.append(cube.sides()[i].showSide());
            }
            cube.setDisplay(res.toString());
        }
    }
}
