package com.example.lesalonproject;

import javafx.beans.property.*;

public class Processor {
    private final IntegerProperty id;
    private final StringProperty manufacturer;
    private final StringProperty type;

    public Processor(int id, String manufacturer, String type){
        this.id = new SimpleIntegerProperty(id);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.type = new SimpleStringProperty(type);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public StringProperty manufacturerProperty() {
        return manufacturer;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }
}
