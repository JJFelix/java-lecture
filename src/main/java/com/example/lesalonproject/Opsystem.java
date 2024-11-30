package com.example.lesalonproject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Opsystem {
    private final IntegerProperty id;
    private final StringProperty osname;

    public Opsystem(int id, String osname){
        this.id = new SimpleIntegerProperty(id);
        this.osname = new SimpleStringProperty(osname);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getOsname() {
        return osname.get();
    }

    public StringProperty osnameProperty() {
        return osname;
    }
}
