package application;

import java.io.*;
import java.util.*;

/**
 * Filename:   application.FoodData.java
 * Project:    Food Query
 * Authors:    Amanda Sarsha, Tanner Bart, Xuefeng Xu, David Berman
 *
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    
    /**
     * Public constructor
     *
     * Create a list to store all the FoodItems and a HashMap to store all the
     * nutrient information
     */
    public FoodData() {
        foodItemList = new ArrayList<>(); //Store FoodItems
        indexes = new HashMap<>(); // Store Nutrient info
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
        List<FoodItem> newList = new ArrayList<FoodItem>(); // new list store newly loaded data
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line; // each line BufferedReader reads
            Scanner scanner; // scan elements in each line
            int index = 0; // keep track which column is in scanner right now
            String id;
            String name = "";

            while ((line = reader.readLine()) != null) {
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                id = scanner.next();

                if (scanner.hasNext()) name = scanner.next();
                FoodItem food = new FoodItem(id, name);

                if (id.equals("")) continue;

                while (scanner.hasNext()) {
                    String item = scanner.next();
                    if (index == 1){
                        Double data = Double.parseDouble(item);
                        food.addNutrient("calories", data);
                        indexes.get("calories").insert(data, food);

                    }

                    else if (index == 3){
                        Double data = Double.parseDouble(item);
                        food.addNutrient("fat", data);
                        indexes.get("fat").insert(data, food);
                    }

                    else if (index == 5){
                        Double data = Double.parseDouble(item);
                        food.addNutrient("carbohydrate", data);
                        indexes.get("carbohydrate").insert(data, food);
                    }

                    else if (index == 7){
                        Double data = Double.parseDouble(item);
                        food.addNutrient("fiber", data);
                        indexes.get("fiber").insert(data, food);
                    }

                    else if (index == 9){
                        Double data = Double.parseDouble(item);
                        food.addNutrient("protein", data);
                        indexes.get("protein").insert(data, food);
                    }

                    index++;
                }
                index = 0;
                newList.add(food);

            }
            foodItemList = newList; // replace the origin list with newly loaded data
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
        // If the filter condition is null, return an empty list;
        if(substring == null) return new ArrayList<FoodItem>();
        List<FoodItem> res = new ArrayList<>(); // store filtered FoodItem
        for (FoodItem i : foodItemList){
            if(i.getName().toLowerCase().contains(substring)){
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
        List<FoodItem> res = new ArrayList<>(); // store filtered FoodItem
        String n[] = rules.get(0).split("\\s+");
        String nutri = n[0]; // nutrient name
        String compara = n[1]; // comparator
        Double value = Double.parseDouble(n[2]); // filter value
        if(indexes.containsKey(nutri)){
            res = indexes.get(nutri).rangeSearch(value, compara);
        }

        // if the user enter more than one rule, iteratively go through every rules
        for (String i : rules){
            String s[] = i.split("\\s+");
            String nutrient = s[0];
            String comparator = s[1];
            Double values = Double.parseDouble(s[2]);
            if(indexes.containsKey(nutrient)){
                List<FoodItem> filter = indexes.get(nutrient).rangeSearch(values, comparator);
                // retain FoodItem satisfying every rules
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
        // add a single foodItem into the list
        foodItemList.add(foodItem);
        // add every nutrient information into indexes HashMap
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

        // mapping food name to foodItem
        HashMap<String, FoodItem> nameFood = new HashMap<>();
        for (FoodItem i : foodItemList){
            nameFood.put(i.getName(), i);
        }
        // Store food name to a list
        List<String> nameList = new ArrayList<>();
        nameList.addAll(nameFood.keySet());
        // sorted the food name list
        Collections.sort(nameList);
        // put every sorted foodItem into a list
        List<FoodItem> sortedFoodItem = new ArrayList<>();
        for (String i : nameList){
            sortedFoodItem.add(nameFood.get(i));
        }
        // Define Delimiter
        final String COMMA_DELIMITER = ",";
        final String NEW_LINE_SEPARATOR = "\n";

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filename);
            for (FoodItem i : sortedFoodItem){
                // Save id and name
                fileWriter.append(String.valueOf(i.getID()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(i.getName()));
                fileWriter.append(COMMA_DELIMITER);
                // Save calories,fat,carbohydrate,fiber,protein
                fileWriter.append("calories");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(i.getNutrientValue("calories")));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("fat");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(i.getNutrientValue("fat")));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("carbohydrate");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(i.getNutrientValue("carbohydrate")));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("fiber");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(i.getNutrientValue("fiber")));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("protein");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(i.getNutrientValue("protein")));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // flush everything in the buffer to the file
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
