package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableItem{
    private SimpleStringProperty name;
    private SimpleDoubleProperty value;

    TableItem(String name, Double value){
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleDoubleProperty(value);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getValue() {
        return value.get();
    }

    public SimpleDoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }
}
