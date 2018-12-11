package application;

import java.io.FileInputStream;

import javafx.application.Application;
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
    static FoodData foodData = new FoodData();  //holds the food data for items entered
    static ArrayList<String> filterList;    //list to hold items to be filtered 
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

        //create new filter button and set the action
        filterBtn = new Button("application.Filter");
        filterBtn.setOnAction(event -> Filter.display());

        //create new add food button and set the action
        addBtn = new Button("Add Food");
        addBtn.setOnAction(event -> AddFood.display());

        //create new load food button and set the action
        loadBtn = new Button("Load Food");
        loadBtn.setOnAction(event -> LoadFood.display());

        //add the buttons to the grid
        topGrid.add(filterBtn, 0, 1);
        topGrid.add(addBtn, 1,1);
        topGrid.add(loadBtn, 2,1);

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
        
          
        Button analyzeMealBTN = new Button("Analyze Meal");
        analyzeMealBTN.setOnAction(event -> AnalyzeMeal.display());
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
