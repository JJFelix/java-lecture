package com.example.lesalonproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class RelationshipController {

    @FXML
    private TableView<Notebook> notebookTable;
    @FXML
    private TableColumn<Notebook, Integer> notebookIdColumn;
    @FXML
    private TableColumn<Notebook, String> notebookManufacturerColumn;
    @FXML
    private TableColumn<Notebook, String> notebookTypeColumn;
    @FXML
    private TableColumn<Notebook, Integer> notebookDisplayColumn;
    @FXML
    private TableColumn<Notebook, Integer> notebookMemoryColumn;
    @FXML
    private TableColumn<Notebook, Integer> notebookHarddiskColumn;
    @FXML
    private TableColumn<Notebook, String> notebookVideocontrollerColumn;
    @FXML
    private TableColumn<Notebook, Integer> notebookPriceColumn;
    @FXML
    private TableColumn<Notebook, Integer> notebookProcessoridColumn;
    @FXML
    private TableColumn<Notebook, Integer> notebookOpsystemidColumn;
    @FXML
    private TableColumn<Notebook, Integer> notebookPiecesColumn;

    @FXML
    private TableView<Processor> processorTable;
    @FXML
    private TableColumn<Processor, Integer> processorIdColumn;
    @FXML
    private TableColumn<Processor, String> processorManufacturerColumn;
    @FXML
    private TableColumn<Processor, String> processorTypeColumn;

    @FXML
    private TableView<Opsystem> opsystemTable;
    @FXML
    private TableColumn<Opsystem, Integer> osIdColumn;
    @FXML
    private TableColumn<Opsystem, String> osNameColumn;

    public void initialize() {
        // Setting up columns for Notebook Table
        notebookIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        notebookManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        notebookTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        notebookDisplayColumn.setCellValueFactory(new PropertyValueFactory<>("display"));
        notebookMemoryColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
        notebookHarddiskColumn.setCellValueFactory(new PropertyValueFactory<>("harddisk"));
        notebookVideocontrollerColumn.setCellValueFactory(new PropertyValueFactory<>("videocontroller"));
        notebookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        notebookProcessoridColumn.setCellValueFactory(new PropertyValueFactory<>("processorid"));
        notebookOpsystemidColumn.setCellValueFactory(new PropertyValueFactory<>("opsystemid"));
        notebookPiecesColumn.setCellValueFactory(new PropertyValueFactory<>("pieces"));

        // Setting up columns for Processor Table
        processorIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        processorManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        processorTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Setting up columns for Opsystem Table
        osIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        osNameColumn.setCellValueFactory(new PropertyValueFactory<>("osname"));

        // Loading data into the tables
        loadNotebookData();
        loadProcessorData();
        loadOpsystemData();
    }

    // Load Notebook data
    private void loadNotebookData() {
        try {
            ObservableList<Notebook> data = NotebookDAO.getNotebooks();
            notebookTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load Processor data
    private void loadProcessorData() {
        try {
            ObservableList<Processor> data = ProcessorDAO.getProcessors();
            processorTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load Opsystem data
    private void loadOpsystemData() {
        try {
            ObservableList<Opsystem> data = OpsystemDAO.getOpsystems();
            opsystemTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
