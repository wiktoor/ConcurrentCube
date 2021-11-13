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

    public String showSide() {
        String res = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                res += String.valueOf(values[i][j]);
                if (j != size - 1) res += " ";
            }
            if (i != size - 1) res += "\n";
        }
        return res;
    }
}
