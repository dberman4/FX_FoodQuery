package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import javafx.scene.control.*;

import javax.xml.soap.Text;
/**
 * Filename:   application.AddFood.java
 * Project:    Food Query
 * Authors:    Amanda Sarsha, Tanner Bart, Xuefeng Xu, David Berman
 * 
 * Use this class for added a user added food to the program
 */
public class AddFood {
    static Stage window;    //stage to be displayed
    /**
     * Displays the add food window to allow the user to add their own food
     */
    public static void display(){
        window = new Stage();   //creates the stage
        window.setTitle("Add A Food Item");
        window.setMinWidth(300);


        //creates labels for each field
        Label id = new Label("id:");
        Label foodName = new Label("Food Name:");
        Label calories = new Label("Calories:");
        Label fat = new Label("Fat:");
        Label carbohydrate = new Label("Carbohydrate:");
        Label fiber = new Label("Fiber:");
        Label protein = new Label("protein");

        //creates textfield for each field
        TextField idInput = new TextField();
        TextField foodNameInput = new TextField();
        TextField caloriesInput = new TextField();
        TextField fatInput = new TextField();
        TextField carbohydrateInput = new TextField();
        TextField fiberInput = new TextField();
        TextField proteinInput = new TextField();
        // calories,fat,carbohydrate,fiber,protein
        //creates submit button to allow the user to submit
        Button btn = new Button("Submit");
        btn.setOnAction(event -> {
            boolean shouldAdd = true;
            String name = foodNameInput.getText();
            String idAdd = idInput.getText();
            if (idAdd.equals("")) {
                Error.display("ID field cannot be empty!");
                shouldAdd = false;
            }
            else if (name.equals("")) {
                Error.display("Name field cannot be empty!");
                shouldAdd = false;
            }
            else {
                FoodItem food = new FoodItem(idInput.getText(), name);
                //check to see if the calories was entered correctly
                if (shouldAdd) {
                    try {
                        double caloriesAdd = Double.parseDouble(caloriesInput.getText());
                        food.addNutrient("calories", caloriesAdd);
                    }
                    catch (NumberFormatException e) {
                        Error.display("Enter valid calories!");
                        shouldAdd = false;
                    }
                }
                //check to see if the fat was entered correctly
                if (shouldAdd) {
                    try {
                        double fatAdd = Double.parseDouble(fatInput.getText());
                        food.addNutrient("fat", fatAdd);                    }
                    catch (NumberFormatException e) {
                        Error.display("Enter valid fat!");
                        shouldAdd = false;
                    }
                }
                //check to see if the carbohydrate was entered correctly
                if (shouldAdd) {
                    try {
                        double carbAdd = Double.parseDouble(carbohydrateInput.getText());
                        food.addNutrient("carbohydrate", carbAdd);
                    }
                    catch (NumberFormatException e) {
                        Error.display("Enter valid carbohydrates!");
                        shouldAdd = false;
                    }
                }
                //check to see if fiber was entered correctly
                if (shouldAdd) {
                    try {
                        double fiberAdd = Double.parseDouble(fiberInput.getText());
                        food.addNutrient("fiber", fiberAdd);
                    }
                    catch (NumberFormatException e) {
                        Error.display("Enter valid fiber!");
                        shouldAdd = false;
                    }
                }
                //check to see if protein was entered correctly
                if (shouldAdd) {
                    try {
                        double proteinAdd = Double.parseDouble(proteinInput.getText());
                        food.addNutrient("protein", proteinAdd);
                    }
                    catch (NumberFormatException e) {
                        Error.display("Enter valid protein!");
                        shouldAdd = false;
                    }
                }
                //add the new food to foodData
                if (shouldAdd) {
                    Main.foodData.addFoodItem(food);
                    AlertBox.display("Add Food", "Successfully add Food Item!");
                    window.close();
                }
            }
        });

        Label prompt = new Label("Type to add a new food:");

        //creates the grid to be displayed
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        //adds all items to the grid
        grid.add(prompt, 0,0);
        grid.add(id, 0, 1);
        grid.add(idInput,1,1);
        grid.add(foodName, 0,2);
        grid.add(foodNameInput,1,2);
        grid.add(calories, 0,3);
        grid.add(caloriesInput,1,3);
        grid.add(fat,0,4);
        grid.add(fatInput, 1,4);
        grid.add(carbohydrate, 0,5);
        grid.add(carbohydrateInput,1,5);
        grid.add(fiber,0,6);
        grid.add(fiberInput,1,6);
        grid.add(protein,0,7);
        grid.add(proteinInput,1,7);
        grid.add(btn,1,8);

        //displays the window
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.show();

    }
}
