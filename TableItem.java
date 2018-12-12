package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Filename:   application.TableItem.java
 * Project:    Food Query
 * Authors:    Amanda Sarsha, Tanner Bart, Xuefeng Xu, David Berman
 *
 * This class constructs each item stored in the GUI table view
 */
public class TableItem{
    // column names
    private SimpleStringProperty name;
    private SimpleDoubleProperty value;

    /**
     * Constructor
     *
     * @param name nutrient name
     * @param value nutrient value
     */
    TableItem(String name, Double value){
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleDoubleProperty(value);
    }

    /**
     * Getters and Setters
     * @return name of the nutrient
     */
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
