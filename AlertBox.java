package application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * Filename:   application.AlertBox.java
 * Project:    Food Query
 * Authors:    Amanda Sarsha, Tanner Bart, Xuefeng Xu, David Berman
 *
 * Show a pop out window to show given alert information
 */
public class AlertBox {

    /**
     * Show the alert box window and given information
     * @param title the title of the window
     * @param message alert messages
     */
    public static void display(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        // make sure the minimum window could show enough room for title and messages
        window.setMinWidth(250);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        Label label = new Label();
        label.setText(message);
        // add a button to close the window
        Button closeButton = new Button("Okay");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        // put elements at the center
        layout.setAlignment(Pos.CENTER);
        grid.add(layout, 0, 0);
        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
    }

}
