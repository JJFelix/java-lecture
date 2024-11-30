package com.example.lesalonproject;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReadController {
    @FXML
    private TableView<Notebook> table;
    @FXML
    private TableColumn<Notebook, Integer> idColumn;
    @FXML
    private TableColumn<Notebook, String> manufacturerColumn;
    @FXML
    private TableColumn<Notebook, String> typeColumn;
    @FXML
    private TableColumn<Notebook, Integer> displayColumn;
    @FXML
    private TableColumn<Notebook, Integer> memoryColumn;
    @FXML
    private TableColumn<Notebook, Integer> harddiskColumn;
    @FXML
    private TableColumn<Notebook, String> videocontrollerColumn;
    @FXML
    private TableColumn<Notebook, Integer> priceColumn;
    @FXML
    private TableColumn<Notebook, Integer> processoridColumn;
    @FXML
    private TableColumn<Notebook, Integer> opsystemidColumn;
    @FXML
    private TableColumn<Notebook, Integer> piecesColumn;

    public void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        displayColumn.setCellValueFactory(new PropertyValueFactory<>("display"));
        memoryColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
        harddiskColumn.setCellValueFactory(new PropertyValueFactory<>("harddisk"));
        videocontrollerColumn.setCellValueFactory(new PropertyValueFactory<>("videocontroller"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        processoridColumn.setCellValueFactory(new PropertyValueFactory<>("processorid"));
        opsystemidColumn.setCellValueFactory(new PropertyValueFactory<>("opsystemid"));
        piecesColumn.setCellValueFactory(new PropertyValueFactory<>("pieces"));

        try{
            ObservableList<Notebook> data = NotebookDAO.getNotebooks();
            table.setItems(data);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
