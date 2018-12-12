package application;

import java.io.File;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
/**
 * Filename:   application.LoadFood.java
 * Project:    Food Query
 * Authors:    Amanda Sarsha, Tanner Bart, Xuefeng Xu, David Berman
 * 
 * Use this class for loading a given file to the program
 */
public class LoadFood {
    static Stage window;

    /**
     * Displays the window to allow the user to load a food file
     */
    public static void display(){
        window = new Stage();   //stage to be displayed
        window.setTitle("Load");
        window.setMinWidth(300);
        
        window.initModality(Modality.APPLICATION_MODAL);


        Label loadPath = new Label("Load Path:");
        TextField pathInput = new TextField();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Submit button to allow for the user to submit the given file
        Button btn = new Button("Submit");
        btn.setOnAction(event -> {
            String path = pathInput.getText();
            Main.path = path;
            File tryOpen = new File(path);
            if (tryOpen.exists() && tryOpen.isFile() && tryOpen.canRead()) {
                Main.foodData.loadFoodItems(pathInput.getText());
                AlertBox.display("LoadFood", "The food list has been loaded successfully!");
                window.close();
            }
            else {
                AlertBox.display("Error", "\"" +path + "\" does not exist");
            }
        });
        grid.add(loadPath, 0,0);
        grid.add(pathInput,1,0);

        grid.add(btn, 1,2);
        Scene scene = new Scene(grid);
        window.setScene(scene);

        //show the window
        window.showAndWait();

    }
}
