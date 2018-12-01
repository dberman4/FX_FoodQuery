package application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
/**
 * Filename:   FilterError.java
 * Project:    Food Query
 * Authors:    Amanda Sarsha, Tanner Bart, Xuefeng Xu, David Berman
 * 
 * Use this class for displaying the given error with filtering
 */
public class FilterError {
    static Stage window;    //stage to be displayed
    /**
     * Display the error message with filter
     * 
     * @param message   String of which error occured
     */
    public static void display(String message){
        window = new Stage();
        window.setTitle("Error");
        window.setMinWidth(300);

        Label errorText = new Label(message);

        Scene scene = new Scene(errorText);
        window.setScene(scene);
        window.show();

    }
}
