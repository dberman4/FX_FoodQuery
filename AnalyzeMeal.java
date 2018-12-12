package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Filename:   AnalyzeMeal.java
 * Project:    Food Query
 * Authors:    Amanda Sarsha, Tanner Bart, Xuefeng Xu, David Berman
 *
 * Show the food list user selected and the summery table of food nutrition
 */
public class AnalyzeMeal {

    static Stage window;



    /**
     * Display this window when usr click analyze meal button
     */
    public static void display(){
        window = new Stage(); // set main stage for this window
        window.setTitle("Analyze Meal");
        window.setMinWidth(300);

        FoodData mealList = new FoodData();
        // Use border pane format layout
        BorderPane borderPane = new BorderPane();

        // grid GridPane takes care of left part of borderPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // right GridPane takes care of right part of borderPane
        GridPane right = new GridPane();
        right.setPadding(new Insets(10, 10, 10, 10));
        right.setVgap(8);
        right.setHgap(10);

        // List all the food that usr selected
        ListView<String> foodList = new ListView<>();
        // Add to food list
        ObservableList<String> foodInMeal = FXCollections.observableArrayList("Coke", "Big Mac");
        for(FoodItem i : mealList.getAllFoodItems()){
            foodInMeal.add(i.getName());
        }
        foodList.setItems(foodInMeal);
        foodList.setPrefWidth(120);

        VBox foodListBox = new VBox();
        Label foodLabel = new Label("Current Food In the List:");
        foodLabel.setStyle("-fx-font-weight: bold");
        // Count the number of food that the usr select
        Label filteredCount = new Label("Number of Food Items: " + foodList.getItems().size());
        foodListBox.getChildren().addAll(foodLabel, filteredCount);
        grid.add(foodListBox,0,0);
        grid.add(foodList, 0,1);
        borderPane.setLeft(grid);

        // Summary table contains nutrition information
        TableView table = new TableView();
        final Label label = new Label("Meal Analysis");
        label.setFont(new Font("Arial", 18));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set a two columns table
        TableColumn firstCol = new TableColumn("Type");
        firstCol.setCellValueFactory(new PropertyValueFactory<TableItem, String>("name"));
        TableColumn lastCol = new TableColumn("Nutrition");
        lastCol.setCellValueFactory(new PropertyValueFactory<TableItem, Double>("value"));

        Double calories = 0.0;
        Double fat = 0.0;
        Double carbohydrate = 0.0;
        Double fiber = 0.0;
        Double protein = 0.0;

        for (FoodItem i : mealList.getAllFoodItems()){
            calories += i.getNutrientValue("calories");
        }
        for (FoodItem i : mealList.getAllFoodItems()){
            fat += i.getNutrientValue("fat");
        }
        for (FoodItem i : mealList.getAllFoodItems()){
            carbohydrate += i.getNutrientValue("carbohydrate");
        }
        for (FoodItem i : mealList.getAllFoodItems()){
            fiber += i.getNutrientValue("fiber");
        }
        for (FoodItem i : mealList.getAllFoodItems()){
            protein += i.getNutrientValue("protein");
        }

        // sample data to show
        ObservableList<TableItem> data = FXCollections.observableArrayList(
                new TableItem ("Calories", calories),
                new TableItem ("Fat", fat),
                new TableItem ("Fiber", fiber),
                new TableItem ("Carbohydrate", carbohydrate),
                new TableItem ("Protein", protein)
        );

        table.setItems(data);
        table.getColumns().addAll(firstCol, lastCol);

        // VBox put labels and tables together
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        // add vbox to right GridPane
        right.add(vbox,0,0);
        borderPane.setRight(right);

        // initial scene for this window
        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.show();

    }
}
