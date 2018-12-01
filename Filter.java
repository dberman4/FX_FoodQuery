package application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
/**
 * Filename:   Filter.java
 * Project:    Food Query
 * Authors:    Amanda Sarsha, Tanner Bart, Xuefeng Xu, David Berman
 * 
 * Use this class for filter window to add a query to the food list
 */
public class Filter {
    static Stage window;    //window to be displayed
    /**
     * Displays the window
     */
    public static void display(){
        //set defaults for the window
        window = new Stage();
        window.setTitle("Filter");
        window.setMinWidth(300);
        
        //create a gridplane to set the items in to be displayed
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        
        //create label and textfield for the name query
        Label nameFilter = new Label("Enter Text to Filter Food Names: ");
        TextField nameField = new TextField();

        //create items for the nutrient query
        Label nutrientFilter = new Label("Enter Nutrient Information to add to Filter List: ");
        TextField nutrientField = new TextField();
        nutrientField.setText("<nutrient> <comparator> <value>");
        ListView<String> nutrientList = new ListView<String>();

        //create button to add nutrient query to the nutrient query list
        Button addNutrientBtn = new Button("Add To Nutrient List");
        addNutrientBtn.setOnAction(event -> {
            boolean shouldAdd = true;   //keep track of if it should be added
            String inputText = nutrientField.getText(); //get input text
            String[] inputArray = inputText.split(" "); //create array split by " "
            if (inputArray.length != 3) {
                //display input error for format
                shouldAdd = false;
                nutrientField.setText("<nutrient> <comparator> <value>");
                FilterError.display(inputText + " is not a valid input format.");
            }
            else {
                //check to see that a correct nutrient is entered
                switch (inputArray[0].toLowerCase()) {
                    case "calories":
                    case "carbs":
                    case "fat":
                    case "protein":
                    case "fiber":
                        break;
                    default:
                        //display input error for nutrient
                        shouldAdd = false;
                        nutrientField.setText("<nutrient> <comparator> <value>");
                        FilterError.display(inputArray[0].toString() + " is not a valid nutrient.");
                        break;
                }
                if (shouldAdd) {
                    //check to see if a valid comparator is entered
                    switch (inputArray[1]) {
                        case "<=":
                        case ">=":
                        case "==":
                            break;
                        default:
                            //display input error for comparator
                            shouldAdd = false;
                            nutrientField.setText("<nutrient> <comparator> <value>");
                            FilterError.display(inputArray[1].toString() + " is not a valid comparator.");
                            break;
                    }
                }
                if (shouldAdd) {
                    try
                    {
                        //check to see if a valid double was entered
                        Double.parseDouble(inputArray[2]);
                    }
                    catch(NumberFormatException e)
                    {
                        //display input error for value
                        shouldAdd = false;
                        nutrientField.setText("<nutrient> <comparator> <value>");
                        FilterError.display(inputArray[2].toString() + " is not a valid value.");
                    }
                }
                if (shouldAdd) {
                    //add nutrient query to the list
                    nutrientList.getItems().add(inputText);
                }
            }


        });


        //button to submit the entered filters
        Button btn = new Button("Submit");
        btn.setOnAction(event -> {
            window.close();
        });
        
        //display the window
        grid.add(nameFilter,0,0);
        grid.add(nameField, 0,1);
        grid.add(nutrientFilter,0,2);
        grid.add(nutrientField,0,3);
        grid.add(addNutrientBtn, 0,4);
        grid.add(nutrientList,0,5);
        grid.add(btn,0,6);
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.show();

    }
}
