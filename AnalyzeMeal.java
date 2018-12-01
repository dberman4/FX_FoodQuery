package application;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import javax.xml.soap.Text;

public class AnalyzeMeal {

    static Stage window;
    public static void display(){
        window = new Stage();
        window.setTitle("Analyze Meal");
        window.setMinWidth(300);

        BorderPane borderPane = new BorderPane();


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        GridPane right = new GridPane();
        right.setPadding(new Insets(10, 10, 10, 10));
        right.setVgap(8);
        right.setHgap(10);

        final ListView<String> foodList = new ListView<String>();
        foodList.setPrefWidth(120);
        VBox foodListBox = new VBox();
        Label foodLabel = new Label("Current Food In the List:");
        foodLabel.setStyle("-fx-font-weight: bold");
        Label filteredCount = new Label("Number of Food Items: " + foodList.getItems().size());
        foodListBox.getChildren().addAll(foodLabel, filteredCount);
        grid.add(foodListBox,0,0);
        grid.add(foodList, 0,1);
        borderPane.setLeft(grid);

        TableView table = new TableView();
        final Label label = new Label("Meal Analysis");
        label.setFont(new Font("Arial", 18));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn firstCol = new TableColumn("Type");
        firstCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn lastCol = new TableColumn("Nutrition");
        lastCol.setCellValueFactory(new PropertyValueFactory<>("data"));

        firstCol.setCellFactory(new PropertyValueFactory<>("Calories"));
        table.getColumns().addAll(firstCol, lastCol);



        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        right.add(vbox,0,0);
        borderPane.setRight(right);

        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.show();

    }
}
