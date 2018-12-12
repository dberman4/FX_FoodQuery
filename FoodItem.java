package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Filename:   application.FoodItem.java
 * Project:    Food Query
 * Authors:    Amanda Sarsha, Tanner Bart, Xuefeng Xu, David Berman
 * 
 * This class builds a single food item 
 */
public class FoodItem {
    // The name of the food item.
    private String name;

    // The id of the food item.
    private String id;

    // Map of nutrients and value.
    private HashMap<String, Double> nutrients;
    
    /**
     * Constructor
     * @param name name of the food item
     * @param id unique id of the food item 
     */
    public FoodItem(String id, String name) {
        this.id = id;
        this.name = name;
        // map nutrient name to data
        nutrients = new HashMap<>();
    }
    
    /**
     * Gets the name of the food item
     * 
     * @return name of the food item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unique id of the food item
     * 
     * @return id of the food item
     */
    public String getID() { return id; }
    
    /**
     * Gets the nutrients of the food item
     * 
     * @return nutrients of the food item
     */
    public HashMap<String, Double> getNutrients() {
        return nutrients;
    }

    /**
     * Adds a nutrient and its value to this food. 
     * If nutrient already exists, updates its value.
     */
    public void addNutrient(String name, double value) {
        // check if nutrient name is empty
        if (name == null) return;
        if (nutrients.containsKey(name)) {
            nutrients.replace(name, value);
            return;
        }
        nutrients.put(name, value);
    }

    /**
     * Returns the value of the given nutrient for this food item. 
     * If not present, then returns 0.
     */
    public double getNutrientValue(String name) {
        // check if the search key is null
        if (name == null) return 0.0;
        // check if the key is in the map
        if (!nutrients.containsKey(name)){
            return 0.0;
        }
        return nutrients.get(name);
    }
    
}
