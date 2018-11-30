package application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class LoadFood {
    static Stage window;

    public static void display(){
        window = new Stage();
        window.setTitle("Load");
        window.setMinWidth(300);

        Label loadPath = new Label("Load Path:");
        TextField pathInput = new TextField();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);


        Button btn = new Button("Submit");
        btn.setOnAction(event -> {
            window.close();
        });
        grid.add(loadPath, 0,0);
        grid.add(pathInput,1,0);

        grid.add(btn, 1,2);
        Scene scene = new Scene(grid);
        window.setScene(scene);




        window.show();

    }
}
