package application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class Filter {
    static Stage window;
    public static void display(){
        window = new Stage();
        window.setTitle("Filter");
        window.setMinWidth(300);

        Label nameFilter = new Label("Enter Text to Filter Food Names: ");
        TextField nameField = new TextField();

        Label nutrientFilter = new Label("Enter Nutrient Information to add to Filter List: ");
        TextField nutrientField = new TextField();
        nutrientField.setText("<nutrient> <comparator> <value>");
        ListView<String> nutrientList = new ListView<String>();


        Button addNutrientBtn = new Button("Add To Nutrient List");
        addNutrientBtn.setOnAction(event -> {
            boolean shouldAdd = true;
            String inputText = nutrientField.getText();
            String[] inputArray = inputText.split(" ");
            if (inputArray.length != 3) {
                //display input error for format
                shouldAdd = false;
                nutrientField.setText("<nutrient> <comparator> <value>");
                FilterError.display(inputText + " is not a valid input format.");
            }
            else {
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
                    nutrientList.getItems().add(inputText);
                }
            }


        });



        Button btn = new Button("Submit");
        btn.setOnAction(event -> {
            window.close();
        });


        VBox displayBox = new VBox();
        displayBox.getChildren().addAll(nameFilter, nameField, nutrientFilter, nutrientField, 
            addNutrientBtn, nutrientList, btn);


        Scene scene = new Scene(displayBox);
        window.setScene(scene);
        window.show();

    }
}