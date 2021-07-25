package part3;

import java.util.Random;

public class Feature {
    private int[] row , col ;
    private boolean[] sgn;

    /**
     * Constructor for features
     * @param rowNumber row
     * @param colNumber col
     */
    public Feature(int rowNumber, int colNumber) {
        this.row = new int[4];
        this.col = new int[4];
        this.sgn = new boolean[4];
        this.getIntValue(this.row, rowNumber);
        this.getIntValue(this.col, colNumber);
    }

    /**
     * Get the int value based on image.data
     * @param integerArray integer values
     * @param nextValue nextValue
     */
    private void getIntValue(int[] integerArray, int nextValue) {
        Random randomNum = new Random();
        for(int i = 0; i < integerArray.length; ++i) { integerArray[i] = randomNum.nextInt(nextValue); }
    }

    /**
     * Gets the sgn
     * @return sgn
     */
    public boolean[] getSgn() {
        return this.sgn;
    }

    /**
     * Gets rows
     * @return row
     */
    public int[] getRow() {
        return this.row;
    }

    /**
     * Gets cols
     * @return col
     */
    public int[] getCol() {
        return this.col;
    }

}
