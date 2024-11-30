package com.example.lesalonproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ChangeController {
    @FXML
    private ComboBox<Integer> idBox;
    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField displayField;
    @FXML
    private TextField memoryField;
    @FXML
    private TextField harddiskField;
    @FXML
    private TextField videocontrollerField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField processoridField;
    @FXML
    private TextField opsystemidField;
    @FXML
    private TextField piecesField;

    public void initialize() throws SQLException {
        // Populate ComboBox with available Notebook IDs
        try{
            idBox.getItems().addAll(NotebookDAO.getAllNotebookIds());
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error");
        }

    }

    @FXML
    private void loadNotebookData(){
        try{
            int selectedId = idBox.getValue();
            Notebook notebook = NotebookDAO.getNotebookById(selectedId);

            //prefill fields with current data
            manufacturerField.setText(notebook.getManufacturer());
            typeField.setText(notebook.getType());
            displayField.setText(String.valueOf(notebook.getDisplay()));
            memoryField.setText(String.valueOf(notebook.getMemory()));
            harddiskField.setText(String.valueOf(notebook.getHarddisk()));
            videocontrollerField.setText(notebook.getVideocontroller());
            priceField.setText(String.valueOf(notebook.getPrice()));
            processoridField.setText(String.valueOf(notebook.getProcessorid()));
            opsystemidField.setText(String.valueOf(notebook.getOpsystemid()));
            piecesField.setText(String.valueOf(notebook.getPieces()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateNotebook(){
        try{
            int id = idBox.getValue();
            String manufacturer = manufacturerField.getText();
            String type = typeField.getText();
            int display = Integer.parseInt(displayField.getText());
            int memory = Integer.parseInt(memoryField.getText());
            int harddisk = Integer.parseInt(harddiskField.getText());
            String videocontroller = videocontrollerField.getText();
            int price = Integer.parseInt(priceField.getText());
            int processorid = Integer.parseInt(processoridField.getText());
            int opsystemid = Integer.parseInt(opsystemidField.getText());
            int pieces = Integer.parseInt(piecesField.getText());

            //Update notebook record
            NotebookDAO.updateNotebook(
                    id, manufacturer, type, display,
                    memory, harddisk, videocontroller,
                    price, processorid, opsystemid, pieces);
            clearFields();
//            System.out.println("Notebook added successfully");
            showSuccess("Notebook added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("Error updating notebookk");
            showError("Error updating notebook");
        }

    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Operation Successful");
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void clearFields(){
        manufacturerField.clear();
        typeField.clear();
        displayField.clear();
        memoryField.clear();
        harddiskField.clear();
        videocontrollerField.clear();
        priceField.clear();
        processoridField.clear();
        opsystemidField.clear();
        piecesField.clear();
    }
}
