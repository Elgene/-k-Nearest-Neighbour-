package part1;

import java.io.InputStream;
import java.util.*;




public class KNN {
    private static int kValue=1;
    public static double rangeValue = 0.0;
    public static List<Double> ranges = new ArrayList<>();
    public static List<CalDistance> wineTraining = new ArrayList<>();
    public static List<CalDistance> wineTest = new ArrayList<>();

    /**
     * Loads files for training and test data sets
     * @param trainingFile wine-training data set
     * @param testFile wine-test data set
     */
    @SuppressWarnings("resource")
	public static void loadFiles(String trainingFile,String testFile){
        InputStream fileTraining = KNN.class.getClassLoader().getResourceAsStream(trainingFile);
        
		InputStream fileTest = KNN.class.getClassLoader().getResourceAsStream(testFile);
		
		Scanner scanTraining = new Scanner(fileTraining);
		Scanner scanTest = new Scanner(fileTest);
		scanTraining.nextLine();
		scanTest.nextLine();
		while(scanTraining.hasNext()) {
		    String readLine = scanTraining.nextLine();
		    Scanner line = new Scanner(readLine);
		    List<Double> doubleValues = new ArrayList<>();
		    while(!(line.hasNextInt())){ doubleValues.add(line.nextDouble()); }
		    int intValue = line.nextInt();
		    wineTraining.add(new CalDistance(doubleValues, intValue));
		    rangeValue = doubleValues.size();
		}
		while(scanTest.hasNext()) {
		    String readLine = scanTest.nextLine();
		    Scanner line = new Scanner(readLine);
		    List<Double> doubleValues = new ArrayList<>();
		    while (!(line.hasNextInt())) {
		        doubleValues.add(line.nextDouble());
		    }
		    int intValue = line.nextInt();
		    wineTest.add(new CalDistance(doubleValues, intValue));
		}
		scanTraining.close();
		scanTest.close();
    }

    /**
     * Calulate the accuracy of test data set
     * @return
     */
    public static double calculateAccuracy() {
        int count = 0;
        for(CalDistance test: wineTest) {
            if(test.getPredictValue() == test.getClassInt()) { //prediction value
                count++;
            }
        }
        double accuracy=count/(double) wineTest.size();
        return accuracy;
    }

    /**
     * Checks for KNN classification
     */
    @SuppressWarnings("unused")
	public static void performClassifier() {
        while (kValue < 4) {
            int index=0;
        for (CalDistance testData : wineTest) {
            for (CalDistance trainData : wineTraining) {
                double calculate = trainData.calcEuclidean(testData, ranges);
            }
            wineTraining.sort(Comparator.comparingDouble(CalDistance::getDistance));
                int originalValue = testData.getClassInt();
                int predictValue = highestPick();
               testData.setPredictValue(predictValue);
                 String status = "";
               if(predictValue==originalValue) status="Match";
               else status="Mismatch";

                System.out.println((index+1)+")"+" k =" + kValue+ "  Original: "+ originalValue +" Predict: " + predictValue + "  " + status);
            index++;
            }
            double finalAccuracy = calculateAccuracy();
            String dividerLine = "=============================";
            System.out.println("Accuracy is " + String.format("%.2f", finalAccuracy * 100) + "% for k =" + kValue +"\n"+ dividerLine);
            kValue += 2;
        }
    }
    /**
     * Calculate the ranges
     *
     **/
    public static void calculateRanges() {
        for (int i = 0; i < rangeValue; i++) {
            double minNumber = Double.MAX_VALUE, maxNumber = Double.MIN_VALUE;
            for (CalDistance trainingValue : wineTraining) {
                if (trainingValue.getDataList(i) < minNumber) { minNumber = trainingValue.getDataList(i); }
                else if (trainingValue.getDataList(i) > maxNumber) { maxNumber = trainingValue.getDataList(i); }
            }
            ranges.add(minNumber-maxNumber);
        }
    }

    /**
     * Checks for the best value to pick
     *
     * @return int labelVotes
     **/
    public static int highestPick() {
        Map<Integer, Integer> picked = new HashMap<>();
        for(int i = 0; i < kValue; i++) {
            int value = wineTraining.get(i).getClassInt();
            if (picked.containsKey(value)) { picked.put(value, picked.get(value) + 1);
            } else { picked.put(value, 1);
            }
        }
        int numberPicked = 0, actualValue = 0;

        for (int pick : picked.keySet()) {
            if (picked.get(pick) > numberPicked) {
                numberPicked = picked.get(pick);
                actualValue = pick;
            }
        }
        return actualValue;
    }
    /**
     * loads files and do KNN classifiers
     * @param args args
     **/
    public static void main(String[] args){
        new KNN();
        System.out.println("------------------------------");
        System.out.println("Loaded wine-training  ");
        System.out.println("Loaded wine-test  ");
        System.out.println("------------------------------");
        loadFiles("part1/wine-training","part1/wine-test");
        calculateRanges();
        System.out.println("\n K-Nearest Neighbour result:");
        performClassifier();
    }

}