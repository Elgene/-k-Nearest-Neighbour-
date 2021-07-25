package part2;

import java.util.List;

public class Instance {

    private String category;
    private List<Boolean> valueBool;

    /**
     * Constructor for instances
     * @param category category
     * @param valueBool value
     */
    public Instance(String category, List<Boolean> valueBool){
        this.category = category;
        this.valueBool = valueBool;
    }

    /**
     * Get the values
     * @param index index of integer
     * @return a boolean value
     */

    public boolean getValue(int index){
        return valueBool.get(index);
    }

    /**
     * Gets the category
     * @return category
     */

    public String getCategory(){
        return category;
    }

    /**
     * toString method to print
     * @return a boolean true or false
     */
    public String toString(){
        StringBuilder result = new StringBuilder(category);
        result.append(" ");
        for (Boolean val : valueBool) result.append(val?"true  ":"false ");
        return result.toString();
    }

}