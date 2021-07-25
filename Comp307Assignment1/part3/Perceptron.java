package part3;

import java.util.ArrayList;
import java.util.Random;

public class Perceptron {
    private double[] weight;
    private int epochs;
    private ArrayList<Image> images;
    private double correct, accuracy;
    private double learningRate = 0.2d;

    /**
     * Contructor for perceptron class
     * @param images images
     * @param epochs epochs
     */
    public Perceptron(ArrayList<Image> images, int epochs) {
        this.epochs = epochs;
        this.images = images;
        this.weight = new double[((Image)images.get(0)).getFeatureInt().size()];
        this.addWeights(this.weight);
    }

    /**
     * Perform perceptron test
     */
    public void perceptronTraining() {
        System.out.println("Epoch | Accuracy " );
        for(int j = 0; j < this.epochs; ++j) {
            this.correct = 0.0d;

            for(int i = 0; i < this.images.size(); ++i) {
                double vectorSum = this.calcVectorSum((Image)this.images.get(i), this.weight);
                if (vectorSum >= 0.0d)  ((Image)this.images.get(i)).setPerceptron("X");
                else ((Image)this.images.get(i)).setPerceptron("O");

                if (!((Image)this.images.get(i)).getPerceptronClass()) {
                    if (vectorSum >= 0.0d) this.vectorSubtraction(this.weight, ((Image)this.images.get(i)).getFeatureInt());
                     else this.vectorAddition(this.weight, ((Image)this.images.get(i)).getFeatureInt());
                } else { ++this.correct; }
            }
            this.accuracy = this.correct / (double)this.images.size() * 100.0d;

            System.out.println(  j + "     | " + this.accuracy + "%");
            if (this.accuracy == 100.0d) { this.printWeights(this.weight);
                return;
            }
        }

    }

    /**
     * Print all the successful weights only
     * @param weights weights
     */
    private void printWeights(double[] weights) {
        System.out.println("Result for success weights only: ");
        int index =0;
        for(int i = 0; i < weights.length; ++i) {
            index++;
            if(index < 11 ){ System.out.printf("[ %.2f ]", weights[i]); }
            else {
                index=0;
                System.out.printf("\n[ %.2f ]", weights[i]);
            }
        }
        System.out.println("");
    }

    /**
     * To add the weights
     * @param arrayWeights array of weights
     */
    private void addWeights(double[] arrayWeights) {
        Random random = new Random();
        for(int i = 0; i < arrayWeights.length; ++i) { arrayWeights[i] = random.nextDouble(); }
    }

    /**
     * Calculate the addition of vectors
     * @param weights weights
     * @param features features in integer
     */
    private void vectorAddition(double[] weights, ArrayList<Integer> features) {
        for(int i = 0; i < features.size(); ++i) {
            double temp = weights[i];
            weights[i] = temp + (double)(Integer)features.get(i) * this.learningRate;
        }
    }

    /**
     * Calculate the subtraction of vectors
     * @param weights weights
     * @param features features in integer
     */
    private void vectorSubtraction(double[] weights, ArrayList<Integer> features) {
        for(int i = 0; i < features.size(); ++i) {
            double temp = weights[i];
            weights[i] = temp - (double)(Integer)features.get(i) * this.learningRate;
        }
    }

    /**
     * Calculate the vector to get the sum
     * @param image image
     * @param weights weights
     * @return sum of vector values
     */
    private double calcVectorSum(Image image, double[] weights) {
        double sum = 0.0d;
        ArrayList<Integer> featuresInt = image.getFeatureInt();
        for(int i = 0; i < weights.length; ++i) { sum += weights[i] * (double)(Integer)featuresInt.get(i); }
        return sum;
    }


}
