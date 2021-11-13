package concurrentcube;

import java.util.Arrays;

public class Side {
    private int side;
    private int size;
    private int[][] values;

    public Side(int side, int size) {
        this.side = side;
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

    public String showSide() {
        String res = "";
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                res += String.valueOf(values[r][c]);
                if (c != size - 1) res += " ";
            }
            if (r != size - 1) res += "\n";
        }
        return res;
    }

    private void reverseRows() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size / 2; c++) {
                int tmp = values[r][c];
                values[r][c] = values[r][size - c - 1];
                values[r][size - c - 1] = tmp;
            }
        }
    }

    private void transpose() {
        for (int r = 1; r < size; r++) {
            for (int c = 0; c < r; c++) {
                int tmp = values[r][c];
                values[r][c] = values[c][r];
                values[c][r] = tmp;
            }
        }
    }

    public void rotateClockwise() {
        transpose();
        reverseRows();
    }

    public void rotateCounterclockwise() {
        reverseRows();
        transpose();
    }
}
