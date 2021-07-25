package part3;

import java.util.ArrayList;

public class Image {

    private String className;
    private ArrayList<Integer> featureInt;
    private boolean[][] pixelValues;
    private String perceptron;

    /**
     * Constructor for image class
     * @param classValue classValue
     * @param comment comment
     * @param row row
     * @param col column
     */
    public Image(String classValue, String comment, int row, int col) {
        this.className = comment.substring(1);
        this.featureInt = new ArrayList<Integer>();
        this.pixelValues = new boolean[row][col];
        this.perceptron = " ";
    }

    /**
     * Calculate features
     * @param features features
     */
    public void calcFeatures(Feature[] features) {
        int countFeature;
        for (int i = 0; i < features.length; ++i) {
            countFeature = 0;
            int[] featureRow = features[i].getRow();
            int[] featureCol = features[i].getCol();
            boolean[] featureSgn = features[i].getSgn();

            for (int j = 0; j < 4; ++j) {
                if (this.pixelValues[featureRow[j]][featureCol[j]] == featureSgn[j]) {
                    ++countFeature;
                }
            }
            if (countFeature >= 3) this.featureInt.add(1);
            else this.featureInt.add(0);
        }
        this.featureInt.add(0, 1);
    }

    /**
     * Gets class name
     * @return className
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * Gets row in length
     * @return length of row
     */

    public int getRowSize() {
        return this.pixelValues.length;
    }

    /**
     * Gets column in length
     * @return length of col
     */

    public int getColSize() {
        return this.pixelValues[0].length;
    }

    /**
     * Get integer of features
     * @return featureInt
     */
    public ArrayList<Integer> getFeatureInt() {
        return this.featureInt;
    }

    /**
     * Gets the class of perceptron
     * @return class
     */
    public boolean getPerceptronClass() {
        return this.perceptron.equalsIgnoreCase(this.getClassName());
    }

    /**
     * Sets the perceptron
     * @param perceptron perceptron
     */

    public void setPerceptron(String perceptron) {
        this.perceptron = perceptron;
    }

    /**
     * Checks for pixels for every rows and columns
     * @param row row
     * @param col col
     * @param pixel  pixel of true or false
     */
    public void addPixel(int row, int col, boolean pixel) {
        if (row > this.pixelValues.length) System.out.println("row doesn't exist");
        else if (col > this.pixelValues.length) System.out.println("column doesn't exist");
        else this.pixelValues[row][col] = pixel;
    }

    /**
     * Gets the pixels
     * @param row row
     * @param col column
     * @return  a boolean
     */
    public boolean getPixel(int row, int col) {
        if (row > this.pixelValues.length) System.out.println("No such row, returning false");
        else {
            if (col <= this.pixelValues.length) return this.pixelValues[row][col];
            System.out.println(" return false because column doesn't exist");
        }
        return false;
    }
}
