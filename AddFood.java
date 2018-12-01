package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import javafx.scene.control.*;

import javax.xml.soap.Text;

public class AddFood {
    static Stage window;

    public static void display(){
        window = new Stage();
        window.setTitle("Add A Food Item");
        window.setMinWidth(300);

        Label id = new Label("id:");
        Label foodName = new Label("Food Name:");
        Label calories = new Label("Calories:");
        Label fat = new Label("Fat:");
        Label carbohydrate = new Label("Carbohydrate:");
        Label fiber = new Label("Fiber:");
        Label protein = new Label("protein");

        TextField idInput = new TextField();
        TextField foodNameInput = new TextField();
        TextField caloriesInput = new TextField();
        TextField fatInput = new TextField();
        TextField carbohydrateInput = new TextField();
        TextField fiberInput = new TextField();
        TextField proteinInput = new TextField();

        Button btn = new Button("Submit");
        btn.setOnAction(event -> {
            window.close();
        });

        Label prompt = new Label("Type to add a new food:");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(prompt, 0,0);
        grid.add(id, 0, 1);
        grid.add(idInput,1,1);
        grid.add(foodName, 0,2);
        grid.add(foodNameInput,1,2);
        grid.add(calories, 0,3);
        grid.add(caloriesInput,1,3);
        grid.add(fat,0,4);
        grid.add(fatInput, 1,4);
        grid.add(carbohydrate, 0,5);
        grid.add(carbohydrateInput,1,5);
        grid.add(fiber,0,6);
        grid.add(fiberInput,1,6);
        grid.add(protein,0,7);
        grid.add(proteinInput,1,7);
        grid.add(btn,1,8);


        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.show();

    }
}
