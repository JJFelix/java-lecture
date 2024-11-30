package com.example.lesalonproject;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OpsystemRead {

    @FXML
    private TableView<Opsystem> table;
    @FXML
    private TableColumn<Opsystem, Integer> idColumn;
    @FXML
    private TableColumn<Opsystem, String> osnameColumn;

    // Initialize method to set up the table view and load data
    public void initialize() {
        // Setting up the columns to match the properties of Opsystem
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        osnameColumn.setCellValueFactory(new PropertyValueFactory<>("osname"));

        // Fetch the data and set it to the table
        try {
            ObservableList<Opsystem> data = OpsystemDAO.getOpsystems();
            table.setItems(data); // Display the Opsystem list in the TableView
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

