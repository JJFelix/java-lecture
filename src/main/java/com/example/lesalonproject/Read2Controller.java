package com.example.lesalonproject;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Read2Controller {
    @FXML
    private TextField manufacturerFilter;
    @FXML
    private ComboBox<String> processorFilter;
    @FXML
    private RadioButton harddiskFilter;
    @FXML
    private CheckBox opsystemFilterCheck;
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
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        manufacturerColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        displayColumn.setCellValueFactory(cellData -> cellData.getValue().displayProperty().asObject());
        memoryColumn.setCellValueFactory(cellData -> cellData.getValue().memoryProperty().asObject());
        harddiskColumn.setCellValueFactory(cellData -> cellData.getValue().harddiskProperty().asObject());
        videocontrollerColumn.setCellValueFactory(cellData -> cellData.getValue().videocontrollerProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        processoridColumn.setCellValueFactory(cellData -> cellData.getValue().processoridProperty().asObject());
        opsystemidColumn.setCellValueFactory(cellData -> cellData.getValue().opsystemidProperty().asObject());
        piecesColumn.setCellValueFactory(cellData -> cellData.getValue().piecesProperty().asObject());

//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
//        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
//        displayColumn.setCellValueFactory(new PropertyValueFactory<>("display"));
//        memoryColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
//        harddiskColumn.setCellValueFactory(new PropertyValueFactory<>("harddisk"));
//        videocontrollerColumn.setCellValueFactory(new PropertyValueFactory<>("videocontroller"));
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//        processoridColumn.setCellValueFactory(new PropertyValueFactory<>("processorid"));
//        opsystemidColumn.setCellValueFactory(new PropertyValueFactory<>("opsystemid"));
//        piecesColumn.setCellValueFactory(new PropertyValueFactory<>("pieces"));

        processorFilter.getItems().addAll("AMD", "Intel", "VIA"); //populate dropdown
        loadTableData();
    }

    @FXML
    private void applyFilter(){
        try{
            ObservableList<Notebook> filteredData = NotebookDAO.filterNotebooks(
                    manufacturerFilter.getText(),
                    processorFilter.getValue(),
                    opsystemFilterCheck.isSelected(),
                    harddiskFilter.isSelected()
            );
            table.setItems(filteredData);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadTableData(){
        try{
            ObservableList<Notebook> data = NotebookDAO.getNotebooks();
            table.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
