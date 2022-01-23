package concurrentcube;

import java.util.Arrays;

public class Side {
    private int size;
    private int[][] values;

    public Side(int side, int size) {
        this.size = size;
        this.values = new int[size][size];
        for (int[] row : values) {
            Arrays.fill(row, side);
        }
    }

    // getters
    public int value(int row, int column) {
        return values[row][column];
    }

    // setters
    public void setValue(int value, int row, int column) {
        values[row][column] = value;
    }

    /**
     * Returns display of the side as a String
     * @return display of the side
     */
    public StringBuilder showSide() {
        StringBuilder res = new StringBuilder();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                res.append(values[r][c]);
            }
        }
        return res;
    }

    /**
     * Rotates side clockwise
     */
    public void rotateClockwise() {
        transpose();
        reverseRows();
    }

    /**
     * Rotates side counterclockwise
     */
    public void rotateCounterclockwise() {
        reverseRows();
        transpose();
    }

    /**
     * Reverses rows in side, used to rotate it
     */
    private void reverseRows() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size / 2; c++) {
                int tmp = values[r][c];
                values[r][c] = values[r][size - c - 1];
                values[r][size - c - 1] = tmp;
            }
        }
    }

    /**
     * Transposes the side, used to rotate it
     */
    private void transpose() {
        for (int r = 1; r < size; r++) {
            for (int c = 0; c < r; c++) {
                int tmp = values[r][c];
                values[r][c] = values[c][r];
                values[c][r] = tmp;
            }
        }
    }
}
