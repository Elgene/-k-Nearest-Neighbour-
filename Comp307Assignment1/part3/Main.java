package part3;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Main {

    ArrayList<Image> images = new ArrayList<Image>();

    public Main(String file) throws FileNotFoundException {
        this.readFile(file);
        Feature[] features = this.doGenFeature(((Image)this.images.get(0)).getRowSize(), ((Image)this.images.get(0)).getColSize(), 250);
        Iterator<Image> imageIterator = this.images.iterator();

        while(imageIterator.hasNext()) {
            Image image = (Image)imageIterator.next();
            image.calcFeatures(features);
        }

        Perceptron newPerceptron = new Perceptron(this.images, 1000);
        newPerceptron.perceptronTraining();
    }

    /**
     *  Perform to read the files
     * @param fileName image.data
     * @throws FileNotFoundException
     */
    public void readFile(String fileName) throws FileNotFoundException {
        Pattern pattern = Pattern.compile("[01]");
        InputStream imageFile = Main.class.getClassLoader().getResourceAsStream(fileName);
        Scanner scan = new Scanner(imageFile);
        while(scan.hasNext()) {
            String classValue = scan.next();
            String commentValue = scan.next();
            int rows = scan.nextInt();
            int cols = scan.nextInt();
            Image image = new Image(classValue, commentValue, rows, cols);

            for(int i = 0; i < rows; ++i) {
                for(int j = 0; j < cols; ++j) {
                    image.addPixel(i, j, scan.findWithinHorizon(pattern, 0).equals("1"));
                }
            }
            this.images.add(image);
        }
        scan.close();
    }

    /**
     * Check for features
     * @param row row
     * @param col col
     * @param numFeatures number of features
     * @return a feature
     */
    public Feature[] doGenFeature(int row, int col, int numFeatures) {
        Feature[] featureValue = new Feature[numFeatures];

        for(int i = 0; i < featureValue.length; ++i) {
            Feature feature = new Feature(row, col);
            featureValue[i] = feature;
        }
        return featureValue;
    }

    /**
     * Print the image
     * @param images images
     */
    public void printImages(ArrayList<Image> images) {
        Iterator<Image> imageIterator = images.iterator();

        while(imageIterator.hasNext()) {
            Image image = (Image)imageIterator.next();

            for(int i = 0; i < image.getRowSize(); ++i) {
                for(int j = 0; j < image.getColSize(); ++j) {
                    if (image.getPixel(i, j)) {
                        System.out.print("X");
                    } else {
                        System.out.print("O");
                    }
                }
                System.out.println("");
            }
        }
    }

    /**
     * Load the file
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("------------------------------");
        System.out.println("Loaded image.data  ");
        System.out.println("------------------------------");
        new Main("part3/image.data");

    }

}
