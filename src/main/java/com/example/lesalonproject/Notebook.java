package com.example.lesalonproject;

import javafx.beans.property.*;

public class Notebook {
    private final IntegerProperty id;
    private final StringProperty manufacturer;
    private final StringProperty type;
    private final IntegerProperty display;
    private final IntegerProperty memory;
    private final IntegerProperty harddisk;
    private final StringProperty videocontroller;
    private final IntegerProperty price;
    private final IntegerProperty processorid;
    private final IntegerProperty opsystemid;
    private final IntegerProperty pieces;

    public Notebook(int id, String manufacturer, String type, int display, int memory, int harddisk, String videocontroller, int price, int processorid, int opsystemid, int pieces) {
        this.id = new SimpleIntegerProperty(id);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.type = new SimpleStringProperty(type);
        this.display = new SimpleIntegerProperty(display);
        this.memory = new SimpleIntegerProperty(memory);
        this.harddisk = new SimpleIntegerProperty(harddisk);
        this.videocontroller = new SimpleStringProperty(videocontroller);
        this.price = new SimpleIntegerProperty(price);
        this.processorid = new SimpleIntegerProperty(processorid);
        this.opsystemid = new SimpleIntegerProperty(opsystemid);
        this.pieces = new SimpleIntegerProperty(pieces);
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

    public int getDisplay() {
        return display.get();
    }

    public IntegerProperty displayProperty() {
        return display;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public int getMemory() {
        return memory.get();
    }

    public IntegerProperty memoryProperty() {
        return memory;
    }

    public int getHarddisk() {
        return harddisk.get();
    }

    public IntegerProperty harddiskProperty() {
        return harddisk;
    }

    public String getVideocontroller() {
        return videocontroller.get();
    }

    public StringProperty videocontrollerProperty() {
        return videocontroller;
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public int getProcessorid() {
        return processorid.get();
    }

    public IntegerProperty processoridProperty() {
        return processorid;
    }

    public int getOpsystemid() {
        return opsystemid.get();
    }

    public IntegerProperty opsystemidProperty() {
        return opsystemid;
    }

    public int getPieces() {
        return pieces.get();
    }

    public IntegerProperty piecesProperty() {
        return pieces;
    }
}
