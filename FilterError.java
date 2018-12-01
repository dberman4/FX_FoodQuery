package application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class FilterError {
    static Stage window;
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
