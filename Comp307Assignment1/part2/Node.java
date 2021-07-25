package part2;

public class Node {
    private double prob = 0.0;
    private String classValue;
    private String topCategory;
    private Node leftNode;
    private Node rightNode;

    /**
     * Constructor of node
     * @param classValue classValue
     * @param prob probability
     * @param leftNode leftNode
     * @param rightNode rightNode
     * @param topCategory topCategory
     */
    public Node(String classValue, double prob, Node leftNode, Node rightNode, String topCategory) {
        this.classValue = classValue;
        this.prob = prob;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.topCategory = topCategory;
    }

    /**
     * Gets the class value
     * @return classValue
     */
    public String getClassValue() {
        return classValue;
    }

    /**
     * Get the probability
     * @return probability
     */
    public double getProb() {
        return prob;
    }

    /**
     * Gets the leftNode
     * @return leftNode
     */
    public Node getLeftNode() {
        return leftNode;
    }

    /**
     * Gets the rightNode
     * @return rightNode
     */
    public Node getRightNode() {
        return rightNode;
    }

    /**
     * Get the top category
     * @return topCategory
     */
    public String getTopCategory() {
        return topCategory;
    }

}
