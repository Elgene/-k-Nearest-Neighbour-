package part2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DecisionTree {
    public static List<String> categoryNames=new ArrayList<>();
    public static List<Instance> hepatitisTraining = new ArrayList<>();
    public static List<Instance> hepatitisTest = new ArrayList<>();
    public static String bestCategory;
    private static List<Double> accuracyFoldTen = new ArrayList<>();

    /**
     * Load file for training data set
     * @param trainingFile hepatitis-training
     */

    public static void loadTraining(String trainingFile){
        InputStream fileTraining = DecisionTree.class.getClassLoader().getResourceAsStream(trainingFile);
		Scanner scanTraining = new Scanner(fileTraining);
		Scanner scanFirst = new Scanner(scanTraining.nextLine());
		while(scanFirst.hasNext()) { categoryNames.add(scanFirst.next()); }
		while(scanTraining.hasNext()) {
		    Scanner scanInput = new Scanner(scanTraining.nextLine());
		    List<Boolean> valueBool = new ArrayList<>();
		    String valueString = scanInput.next();
		    while(scanInput.hasNextBoolean()){ valueBool.add(scanInput.nextBoolean()); }
		    hepatitisTraining.add(new Instance(valueString, valueBool));
		}
		scanTraining.close();
    }

    /**
     * Load file for training data set
     * @param testFile hepatitis-test
     */
    public static void loadTest(String testFile){
        InputStream fileTest = DecisionTree.class.getClassLoader().getResourceAsStream(testFile);
		Scanner scanTest = new Scanner(fileTest);
		scanTest.nextLine();
		while(scanTest.hasNext()) {
		    Scanner inputTest = new Scanner(scanTest.nextLine());
		    List<Boolean> valueBool = new ArrayList<>();
		    String valueString = inputTest.next();
		    while(inputTest.hasNextBoolean()){ valueBool.add(inputTest.nextBoolean());}
		    hepatitisTest.add(new Instance(valueString, valueBool));
		}
		scanTest.close();
    }

    /**
     * K-fold 10 cross validation
     * @param trainingFile trainingFile
     * @param testFile testFile
     */
    private static void crossValidations(String trainingFile, String testFile) {
            InputStream fileTraining = DecisionTree.class.getClassLoader().getResourceAsStream(trainingFile);
            System.out.println("Loaded files from "+ trainingFile);
            Scanner scanTraining = new Scanner(fileTraining);
            categoryNames = new ArrayList<>();
            Scanner scanFirst = new Scanner(scanTraining.nextLine());
            while(scanFirst.hasNext()) { categoryNames.add(scanFirst.next()); }
            while(scanTraining.hasNext()) {
                Scanner inputTraining = new Scanner(scanTraining.nextLine());
                List<Boolean> valueBool = new ArrayList<>();
                String valueString = inputTraining.next();
                while(inputTraining.hasNextBoolean()){ valueBool.add(inputTraining.nextBoolean()); }
                hepatitisTraining.add(new Instance(valueString, valueBool));
            }
            scanTraining.close();

        Node tree = loadNodes(new ArrayList<>(hepatitisTraining), new ArrayList<>(categoryNames));

            InputStream fileTest = DecisionTree.class.getClassLoader().getResourceAsStream(trainingFile);
            System.out.println("Loaded files from  "+ testFile);
            Scanner scanTest = new Scanner(fileTest);
            scanTest.nextLine();
            while(scanTest.hasNext()) {
                Scanner inputValue = new Scanner(scanTest.nextLine());
                List<Boolean> valueBool = new ArrayList<>();
                String valueString = inputValue.next();
                while(inputValue.hasNextBoolean()){ valueBool.add(inputValue.nextBoolean()); }
                hepatitisTest.add(new Instance(valueString, valueBool));
            }
        scanTest.close();

        int countValue = 0;
        for (Instance instanceTest: hepatitisTest) {
            String value = recursiveDecision(tree, instanceTest);
            if(value.equals(instanceTest.getCategory())) {
                countValue++;
            }
        }
        accuracyFoldTen.add((double)countValue/(double) hepatitisTest.size());
    }

    /**
     * Load Nodes of all the neighbours
     * @param instances instances
     * @param categories categories
     * @return node
     */
    private static Node loadNodes(List<Instance> instances, List<String> categories) {

        double impurityValue = (calcImpurity(instances));
        if(instances.isEmpty()) {
            Node createInstances = new Node(calcTopCategory(hepatitisTraining), calcProbability(hepatitisTraining), null, null, null);
            return  createInstances;
        }
        else if(categories.isEmpty()) {
            Node newCategory = new Node(calcTopCategory(instances), calcProbability(instances), null, null, null);
            return newCategory;
        }
        else if(impurityValue == 0.0) {
            Node newImpurity = new Node(instances.get(0).getCategory(), 1, null, null, null);
            return newImpurity;
        }
        else {
            List<Instance> topTrueInstances = new ArrayList<>();
            List<Instance> topFalseInstances = new ArrayList<>();
             bestCategory = null;
            double minimumImpurity = Double.MAX_VALUE;
            for(int i = 1; i < categories.size(); i++) {
                String categoryValue = categories.get(i);
                List<Instance> trueInstances = new ArrayList<>();
                List<Instance> falseInstances = new ArrayList<>();
                int categoryNumber = categoryNames.indexOf(categoryValue);
                for(Instance value: instances) {
                    if(value.getValue(categoryNumber-1) == true) {
                        trueInstances.add(value);
                    }
                    else if(value.getValue(categoryNumber-1) == false) {
                        falseInstances.add(value);
                    }
                }
                double trueImpurity;
                if(!trueInstances.isEmpty()) {
                    trueImpurity = calcImpurity(trueInstances);
                }
                else {
                    trueImpurity = 0;
                }
                double falseImpurity = calcImpurity(falseInstances);
                if(!falseInstances.isEmpty()) {
                    falseImpurity = calcImpurity(falseInstances);
                }
                else {
                    falseImpurity = 0;
                }
                double averageWeightImpurity = trueImpurity * ((double) trueInstances.size() / (double) instances.size()) + falseImpurity * ((double) falseInstances.size() / (double) instances.size());
                if(averageWeightImpurity < minimumImpurity) {
                    bestCategory = categoryValue;
                    topTrueInstances = trueInstances;
                    topFalseInstances = falseInstances;
                    minimumImpurity = averageWeightImpurity;
                }
            }
            categories.remove(bestCategory);
            Node leftNode = loadNodes(topTrueInstances, new ArrayList<>(categories));
            Node rightNode = loadNodes(topFalseInstances, new ArrayList<>(categories));
            return new Node(null, Double.MAX_VALUE,leftNode,rightNode,bestCategory);
        }
    }

    /**
     * Calculate impurity
     * @param list of instances
     * @return any impurity
     */
    private static double calcImpurity(List<Instance> list) {
        double liveNum = 0.0,dieNum = 0.0;
        for (Instance value: list) {
            if(value.getCategory().equals("live")) {
                liveNum++;
            }
            if(value.getCategory().equals("die")) {
                dieNum++;
            }
        }
        double impurity= liveNum * dieNum / Math.pow((liveNum + dieNum), 2 );
        return impurity;
    }

    /**
     *Calculate the top of category
     * @param list list of instances
     * @return the status
     */
    private static String calcTopCategory(List<Instance> list) {
        double liveNum = 0.0,dieNum = 0.0;
        for (Instance value: list) {
            if(value.getCategory().equals("live")) {
                liveNum++;
            }
            if(value.getCategory().equals("die")) {
                dieNum++;
            }
        }
        if(liveNum > dieNum) return "live";
        else  return "die";
    }

    /**
     * Calculate the probability
     * @param list list of instances
     * @return probability value
     */
    private static double calcProbability(List<Instance> list) {
        double liveNum = 0.0,dieNum = 0.0,probability = 0.0;
        for (Instance value: list) {
            if(value.getCategory().equals("live")) {
                liveNum++;
            }
            if(value.getCategory().equals("die")) {
                dieNum++;
            }
        }
        if(liveNum > dieNum) {
            probability = liveNum / ( liveNum + dieNum );
        }
        else if(dieNum > liveNum) {
            probability = dieNum / ( dieNum + liveNum );
        }
        else {
            probability = 0.5;
        }
        return probability;
    }

    /**
     * Do recursive Decision Tree
     * @param tree Node
     * @param instance instance
     * @return node
     */
    private static String recursiveDecision(Node tree, Instance instance) {
        if(tree.getLeftNode() == null) { return tree.getClassValue(); }
        else {
            int valueCategory = categoryNames.indexOf(tree.getTopCategory());
            if(instance.getValue(valueCategory-1) == true) {
                Node leftNode = tree.getLeftNode();
                return recursiveDecision(leftNode, instance);
            }
            else {
                Node rightNode = tree.getRightNode();
                return recursiveDecision(rightNode, instance);
            }
        }
    }

    /**
     * Print the decision tree
     * @param root rootNode
     * @param indent create space
     */
    public static void printTree(Node root, String indent) {
        if(root.getLeftNode() == null) {
            if (root.getProb() == 0) {
                System.out.printf("%sUnknown%n", indent);
            }else {
                System.out.printf("%s Class = %s, prob = %.2f%n", indent, root.getClassValue(), root.getProb());
            }
        }
        else {
            System.out.printf("%s%s = True:%n", indent, root.getTopCategory());
            Node left = root.getLeftNode();
            printTree(left, indent+"\t");
            System.out.printf("%s%s = False:%n", indent, root.getTopCategory());
            Node right = root.getRightNode();
            printTree(right, indent+"\t");
        }
    }

    /**
     * main method to load all files
     * run decision tree
     * @param args
     */
    public static void main(String[] args) {
        new DecisionTree();
        System.out.println("------------------------------");
        System.out.println("Loaded hepatitis-training  ");
        System.out.println("Loaded hepatitis-test  ");
        System.out.println("------------------------------");
        loadTraining("part2/hepatitis-training");
        Node tree = loadNodes(new ArrayList<>(hepatitisTraining), new ArrayList<>(categoryNames));
        printTree(tree, "");
        loadTest("part2/hepatitis-test");

        int countValue = 0,index=0;
        for (Instance insTest: hepatitisTest) {
            String value = recursiveDecision(tree, insTest);
            if(value.equals(insTest.getCategory())) {
                countValue++;
            }
            System.out.println((index+1) + ")" +"  Result: " + value+ "   True value result: " + insTest.getCategory());
            System.out.println("---------------------");
            index++;
        }
        String dividerLine = "==========================================";
        System.out.println("Final result is "+countValue + " out of " + hepatitisTest.size()+ " (" + (double)countValue/(double) hepatitisTest.size()*100 + "% )" +"\n"+dividerLine);

        System.out.println("Baseline ( " + calcTopCategory(hepatitisTraining) + " ) is " + calcProbability(hepatitisTraining)*100 + "%" +"\n"+dividerLine);

        for(int i = 0; i < 10; i++) {
            crossValidations("part2/hepatitis-training-run-" + i, "part2/hepatitis-test-run-" + i +"\n"+ dividerLine);
        }
        double totalValue = 0.0;
        for(double accuracy: accuracyFoldTen) { totalValue += accuracy; }

        System.out.println("K-fold for 10 average value is " + String.format("%.2f",(totalValue/(double) accuracyFoldTen.size())*100) + "%");



    }

}
