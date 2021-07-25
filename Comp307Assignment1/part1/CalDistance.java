package part1;

import java.util.List;

/**
 *  Method for handling distance calculation
 *
 * @author Elgene Menon Leo Anthony 300492604
 **/

public class CalDistance {
    private List<Double> allData;
    private int classInt;
    private int predictValue = 0;
    private double distance;

    /**
     * Constructor for the CalDistance Class
     *
     * @param allData data
     * @param classInt num
     **/
    public CalDistance(List<Double> allData, int classInt) {
        this.allData = allData;
        this.classInt = classInt;
    }

    /**
     *  Calculate euclidean distance.
     *
     * @param obj obj
     * @param listRanges range
     * @return double distance
     **/
    public double calcEuclidean(CalDistance obj, List<Double> listRanges) {
        double distEuclidean;
        double count = 0.0;
        for (int i = 0; i < allData.size(); i++) {
            double euclidean  = ((this.allData.get(i) - obj.allData.get(i)) * (this.allData.get(i) - obj.allData.get(i)))/
                                 ((listRanges.get(i)) * (listRanges.get(i)));
            count += euclidean;
        }
        distEuclidean = Math.sqrt(count);
        this.distance = distEuclidean;
        return distEuclidean;
    }

    /**.
     * Get all data set
     * @return double getDataList
     **/
    public double getDataList(int i) {
        return allData.get(i);
    }

    /**
     *Gets the distance
     * @return double getDist
     **/
    public double getDistance() {
        return distance;
    }

    /**
     *  Gets the class value in integer
     *
     * @return double getClassNumber
     **/
    public int getClassInt() {
        return classInt;
    }

    /**
     * Gets the predictable value
     *
     * @return int labelGuess
     **/
    public int getPredictValue() { return predictValue; }

    /**
     * sets the predictable value
     *
     * @param predictValue labelGuess
     **/
    public void setPredictValue(int predictValue) { this.predictValue = predictValue; }
}
