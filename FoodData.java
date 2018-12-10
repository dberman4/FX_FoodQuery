package application;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    
    /**
     * Public constructor
     */
    public FoodData() {
        foodItemList = new ArrayList<>();
        indexes = new HashMap<>();
        indexes.put("calories", new BPTree<Double, FoodItem>(3));
        indexes.put("fat", new BPTree<Double, FoodItem>(3));
        indexes.put("carbohydrate", new BPTree<Double, FoodItem>(3));
        indexes.put("fiber", new BPTree<Double, FoodItem>(3));
        indexes.put("protein", new BPTree<Double, FoodItem>(3));
    }


    /**
     * Loads the data in the .csv file
     *
     * file format:
     * <id1>,<name>,<nutrient1>,<value1>,<nutrient2>,<value2>,...
     * <id2>,<name>,<nutrient1>,<value1>,<nutrient2>,<value2>,...
     *
     * Example:
     * 556540ff5d613c9d5f5935a9,Stewarts_PremiumDarkChocolatewithMintCookieCrunch,calories,280,fat,18,carbohydrate,34,fiber,3,protein,3
     *
     * Note:
     *     1. All the rows are in valid format.
     *  2. All IDs are unique.
     *  3. Names can be duplicate.
     *  4. All columns are strictly alphanumeric (a-zA-Z0-9_).
     *  5. All food items will strictly contain 5 nutrients in the given order:
     *     calories,fat,carbohydrate,fiber,protein
     *  6. Nutrients are CASE-INSENSITIVE.
     *
     * @param filePath path of the food item data file
     *        (e.g. folder1/subfolder1/.../foodItems.csv)
     */
    @Override
    public void loadFoodItems(String filePath) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            Scanner scanner;
            int index = 0;
            String id;
            String name = "";

            while ((line = reader.readLine()) != null) {
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                if (scanner.hasNext()) {
                    id = scanner.next();
                } else {
                    continue;
                }
                if (scanner.hasNext()) name = scanner.next();
                FoodItem food = new FoodItem(id, name);
                while (scanner.hasNext()) {
                    Double data = scanner.nextDouble();
                    if (index == 1){
                        food.addNutrient("calories", data);
                        indexes.get("calories").insert(data, food);
                    }

                    else if (index == 3){
                        food.addNutrient("fat", data);
                        indexes.get("fat").insert(data, food);
                    }

                    else if (index == 5){
                        food.addNutrient("carbohydrate", data);
                        indexes.get("carbohydrate").insert(data, food);
                    }

                    else if (index == 7){
                        food.addNutrient("fiber", data);
                        indexes.get("fiber").insert(data, food);
                    }

                    else if (index == 9){
                        food.addNutrient("protein", data);
                        indexes.get("protein").insert(data, food);
                    }

                    index++;
                }
                index = 0;
                foodItemList.add(food);

            }
            //close reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all the food items that have name containing the substring.
     *
     * Example:
     *     All application.FoodItem
     *         51c38f5d97c3e6d3d972f08a,Similac_FormulaSoyforDiarrheaReadytoFeed,calories,100,fat,0,carbohydrate,0,fiber,0,protein,3
     *         556540ff5d613c9d5f5935a9,Stewarts_PremiumDarkChocolatewithMintCookieCrunch,calories,280,fat,18,carbohydrate,34,fiber,3,protein,3
     *     Substring: soy
     *     Filtered application.FoodItem
     *         51c38f5d97c3e6d3d972f08a,Similac_FormulaSoyforDiarrheaReadytoFeed,calories,100,fat,0,carbohydrate,0,fiber,0,protein,3
     *
     * Note:
     *     1. Matching should be CASE-INSENSITIVE.
     *     2. The whole substring should be present in the name of application.FoodItem object.
     *     3. substring will be strictly alphanumeric (a-zA-Z0-9_)
     *
     * @param substring substring to be searched
     * @return list of filtered food items; if no food item matched, return empty list
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        if(substring == null) return new ArrayList<FoodItem>();
        List<FoodItem> res = new ArrayList<>();
        for (FoodItem i : foodItemList){
            if(i.getName().contains(substring)){
                res.add(i);
            }
        }
        return res;
    }

    /**
     * Gets all the food items that fulfill ALL the provided rules
     *
     * Format of a rule:
     *     "<nutrient> <comparator> <value>"
     *
     * Definition of a rule:
     *     A rule is a string which has three parts separated by a space:
     *         1. <nutrient>: Name of one of the 5 nutrients [CASE-INSENSITIVE]
     *         2. <comparator>: One of the following comparison operators: <=, >=, ==
     *         3. <value>: a double value
     *
     * Note:
     *     1. Multiple rules can contain the same nutrient.
     *         E.g. ["calories >= 50.0", "calories <= 200.0", "fiber == 2.5"]
     *     2. A FoodItemADT object MUST satisfy ALL the provided rules i
     *        to be returned in the filtered list.
     *
     * @param rules list of rules
     * @return list of filtered food items; if no food item matched, return empty list
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
        List<FoodItem> res = new ArrayList<>();
        String n[] = rules.get(0).split("\\s+");
        String nutri = n[0];
        String compara = n[1];
        Double value = Double.parseDouble(n[2]);
        if(indexes.containsKey(nutri)){
            res = indexes.get(nutri).rangeSearch(value, compara);
        }

        for (String i : rules){
            String s[] = i.split("\\s+");
            String nutrient = s[0];
            String comparator = s[1];
            Double values = Double.parseDouble(s[2]);
            if(indexes.containsKey(nutrient)){
                List<FoodItem> filter = indexes.get(nutrient).rangeSearch(values, comparator);
                res.retainAll(filter);
            }
        }

        return res;
    }

    /**
     * Adds a food item to the loaded data.
     * @param foodItem the food item instance to be added
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        foodItemList.add(foodItem);
        indexes.get("calories").insert(foodItem.getNutrientValue("calories"), foodItem);
        indexes.get("fat").insert(foodItem.getNutrientValue("fat"), foodItem);
        indexes.get("carbohydrate").insert(foodItem.getNutrientValue("carbohydrate"), foodItem);
        indexes.get("fiber").insert(foodItem.getNutrientValue("fiber"), foodItem);
        indexes.get("protein").insert(foodItem.getNutrientValue("protein"), foodItem);
    }

    /**
     * Gets the list of all food items.
     * @return list of application.FoodItem
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }

    /**
     * Save the list of food items in ascending order by name
     * calories,fat,carbohydrate,fiber,protein
     *
     * @param filename name of the file where the data needs to be saved
     */
    @Override
    public void saveFoodItems(String filename){

    }
}
