package application;

import java.awt.List;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Filename:   application.Main.java
 * Project:    Food Query
 * Authors:    Amanda Sarsha, Tanner Bart, Xuefeng Xu, David Berman
 *
 * Use this class for starting the application
 */
public class Main extends Application {

    Stage window;   //stage to be displayed in the window
    Button filterBtn, addBtn, loadBtn;  //buttons created in the window
    static FoodData foodData = new FoodData();
    static FoodData newMeal = new FoodData();
    HashMap<String, FoodItem> nameToFood = new HashMap<>();
    static ArrayList<String> filterList;    //list to hold items to be filtered
    static ArrayList<String> mealArrayList;   //list to hold the meal food names
    /**
     * Starts the program
     *
     * @param primaryStage Stage to be given as the primary stage to
     *                              start the program with
     */
    @Override
    public void start(Stage primaryStage){
        try {
            //create the window
            window = primaryStage;
            window.setTitle("Food Logger");
            window.setMinWidth(900);
            window.setMinHeight(500);

            //set spacing around borders
            BorderPane borderpane = new BorderPane();
            GridPane topGrid = new GridPane();
            topGrid.setPadding(new Insets(20, 30, 20, 30));
            topGrid.setVgap(8);
            topGrid.setHgap(10);

            //create new grid for the left hand side of the program
            GridPane leftGrid = new GridPane();
            leftGrid.setPadding(new Insets(10, 10, 10, 10));
            leftGrid.setVgap(8);
            leftGrid.setHgap(10);

            //create list block with label for filtered food items
            final ListView<String> filteredList = new ListView<String>();

            filteredList.setPrefWidth(120);
            VBox filteredListBox = new VBox();
            Label filteredLabel = new Label("Filtered Food List");
            filteredLabel.setStyle("-fx-font-weight: bold");
            Label filteredCount = new Label("Number of Food Items: " + filteredList.getItems().size());
            filteredListBox.getChildren().addAll(filteredLabel, filteredCount);

            //add the filtered list items to the left grid
            leftGrid.add(filteredListBox,0,0);
            leftGrid.add(filteredList, 0,1);

            //create a new grid for the center and the right of the program
            GridPane rightGrid = new GridPane();
            GridPane middleGrid = new GridPane();
            middleGrid.setHgap(10);
            middleGrid.setVgap(20);

            rightGrid.setPadding(new Insets(10, 10, 10, 10));
            rightGrid.setVgap(8);
            rightGrid.setHgap(10);

            //create list block with label for meal items
            final ListView<String> mealList = new ListView<String>();
            filteredList.setPrefWidth(120);
            VBox mealListBox = new VBox();
            Label mealLabel = new Label("Meal Food List:");
            mealLabel.setStyle("-fx-font-weight: bold");
            Label mealCount = new Label("Number of Food Items: " + mealList.getItems().size());
            mealListBox.getChildren().addAll(mealLabel, mealCount);

            //add the meal list items to the right grid
            rightGrid.add(mealListBox,0,0);
            rightGrid.add(mealList,0,1);

            //create new filter button and set the action
            filterBtn = new Button("Filter");
            filterBtn.setOnAction(event -> {
                Filter.display();
                //TODO add functionality to compare the applied filters
                String nameSearch = filterList.get(0);
                filterList.remove(0);
                HashSet<FoodItem> nameFilterSet = new HashSet<FoodItem>();
                HashSet<FoodItem> nutrientFilterSet = new HashSet<FoodItem>();
                boolean filteredName = false;
                boolean filteredNutrient = false;
                if (!nameSearch.equals("")) {
                    java.util.List<FoodItem> nameFilterList = foodData.filterByName(nameSearch);
                    nameFilterSet.addAll(nameFilterList);
                    filteredName = true;
                }
                if (filterList.size() > 0) {
                    java.util.List<FoodItem> nutrientFilterList = foodData.filterByNutrients(filterList);
                    nutrientFilterSet.addAll(nutrientFilterList);
                    filteredNutrient = true;
                }
                if (filteredName && filteredNutrient) {
                    //display list with filtered for both 
                }
                else if (filteredName){
                    //display list with only filtered name

                }
                else if (filteredNutrient) {
                    //display list with only filtered nutrient

                }
                else {
                    //display list with no filter

                }

                //TODO display the filtered list in filteredList
            });

            //create new add food button and set the action
            addBtn = new Button("Add Food");
            addBtn.setOnAction(event -> {
                AddFood.display();
                for (FoodItem i : foodData.getAllFoodItems()){
                    nameToFood.put(i.getName().toLowerCase(), i);
                }
                ArrayList<String> addNames = new ArrayList<>();
                addNames.addAll(nameToFood.keySet());
                Collections.sort(addNames);
                ObservableList<String> items = FXCollections.observableArrayList (
                    addNames);
                filteredList.setItems(items);
                filteredCount.setText("Number of Food Items: " + filteredList.getItems().size());
            });

            //create new load food button and set the action
            loadBtn = new Button("Load Food");
            loadBtn.setOnAction(event -> {
                LoadFood.display();
                filteredList.getItems().clear();
                for (FoodItem i : foodData.getAllFoodItems()){
                    nameToFood.put(i.getName().toLowerCase(), i);
                }

                ArrayList<String> addNames = new ArrayList<>();
                addNames.addAll(nameToFood.keySet());
                Collections.sort(addNames);
                ObservableList<String> items = FXCollections.observableArrayList (
                    addNames);
                filteredList.getItems().addAll(items);
                filteredCount.setText("Number of Food Items: " + filteredList.getItems().size());
            });
            ArrayList<String> mealArrayList = new ArrayList<String>();

            //click on food in filtered list and add it to meal list, hides it then from filtered list

            filteredList.setOnMouseClicked(event -> {
                if (filteredList.getSelectionModel().getSelectedIndex() >= 0) {
                    newMeal.addFoodItem(nameToFood.get(filteredList.getSelectionModel().getSelectedItem().toLowerCase()));

                    mealArrayList.add(filteredList.getSelectionModel().getSelectedItem());
                    //hashset to use to compare meal items
                    HashSet<String> meal = new HashSet<String>();
                    meal.addAll(mealArrayList);
                    //hashset to use to compare list items
                    HashSet<String> list = new HashSet<String>();
                    //hashset to use to compare filter items
                    HashSet<String> filtered = new HashSet<String>();
                    //TODO get filtered items

                    //clear items in lists to update displayed items
                    filteredList.getItems().clear();
                    mealList.getItems().clear();

                    //get the food items in the list
                    ArrayList<FoodItem> addList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
                    ArrayList<String> addNames = new ArrayList<String>();
                    //get the names of the food items
                    for (int i = 0; i < addList.size(); i++) {
                        addNames.add(addList.get(i).getName().toLowerCase());
                    }
                    //add the names to the list to compare
                    list.addAll(addNames);
                    //get rid of the names in the meal
                    list.removeAll(meal);
                    //get rid of the non-filtered items
                    if (!filtered.isEmpty()) {
                        list.retainAll(filtered);
                    }
                    //add the compared list to the arraylist to be displayed
                    addNames.clear();
                    addNames.addAll(list);
                    //sort the arrays in alphabetical order
                    Collections.sort(addNames);
                    Collections.sort(mealArrayList);
                    //display the filtered list
                    for (int i = 0; i < addNames.size(); i++) {
                        filteredList.getItems().add(addNames.get(i).toLowerCase());
                    }
                    //display the meal list
                    for (int i = 0; i < mealArrayList.size(); i++) {
                        mealList.getItems().add(mealArrayList.get(i).toLowerCase());
                    }
                    //update the item counters for each list
                    mealCount.setText("Number of Food Items: " + mealList.getItems().size());
                    filteredCount.setText("Number of Food Items: " + filteredList.getItems().size());
                }
            });

            //click on food in meal list and remove it from the meal list, adds it to filtered list
            mealList.setOnMouseClicked(event -> {
                if (mealList.getSelectionModel().getSelectedIndex() >= 0) {

                    //remove the item from the meal list
                    mealArrayList.remove(mealList.getSelectionModel().getSelectedItem());
                    //hashset to use to compare the meal list
                    HashSet<String> meal = new HashSet<String>();
                    meal.addAll(mealArrayList);
                    //hashset to use to compare the food list
                    HashSet<String> list = new HashSet<String>();
                    //hashset used to compare the filtered items
                    HashSet<String> filtered = new HashSet<String>();
                    //TODO get filtered items

                    //clear lists to update the display
                    filteredList.getItems().clear();
                    mealList.getItems().clear();
                    //get all the food items
                    ArrayList<FoodItem> addList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
                    ArrayList<String> addNames = new ArrayList<String>();
                    //get names of food items
                    for (int i = 0; i < addList.size(); i++) {
                        addNames.add(addList.get(i).getName().toLowerCase());
                    }
                    //add all names to set to be compared
                    list.addAll(addNames);
                    //remove the meal items from the set
                    list.removeAll(meal);
                    //get rid of the non-filtered items
                    if (!filtered.isEmpty()) {
                        list.retainAll(filtered);
                    }
                    //add all the compared to the list to be displayed
                    addNames.clear();
                    addNames.addAll(list);
                    //sort in alphabetical order
                    Collections.sort(addNames);
                    Collections.sort(mealArrayList);
                    //display the filtered list
                    for (int i = 0; i < addNames.size(); i++) {
                        filteredList.getItems().add(addNames.get(i).toLowerCase());
                    }
                    //display the meal list
                    for (int i = 0; i < mealArrayList.size(); i++) {
                        mealList.getItems().add(mealArrayList.get(i).toLowerCase());
                    }
                    //update the item counters
                    mealCount.setText("Number of Food Items: " + mealList.getItems().size());
                    filteredCount.setText("Number of Food Items: " + filteredList.getItems().size());
                }
            });

            //add the buttons to the grid
            topGrid.add(filterBtn, 0, 1);
            topGrid.add(addBtn, 1,1);
            topGrid.add(loadBtn, 2,1);

            Button analyzeMealBTN = new Button("Analyze Meal");
            analyzeMealBTN.setOnAction(event -> {

                AnalyzeMeal.display();
            });
            DropShadow shadow = new DropShadow();

            //Adding the shadow when the mouse cursor is on
            analyzeMealBTN.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    analyzeMealBTN.setEffect(shadow);
                }
            });
            topGrid.add(analyzeMealBTN, 3, 1);

            //adding the image to the center of the program
            Image image = new Image(new FileInputStream("food_logger.png"), 450, 200, false, false);
            ImageView imageView = new ImageView(image);
            imageView.setImage(image);
            imageView.setX(50);
            middleGrid.add(imageView, 0, 4);

            //display the scene
            Scene scene = new Scene(borderpane, 900, 300);

            borderpane.setCenter(middleGrid);
            borderpane.setLeft(leftGrid);
            borderpane.setRight(rightGrid);
            borderpane.setTop(topGrid);
            window.setScene(scene);
            window.show();
        }

        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Launch the program
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
