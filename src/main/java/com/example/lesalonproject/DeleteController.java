package com.example.lesalonproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;

public class DeleteController {
    @FXML
    private ComboBox<Integer> idBox;

    public void initialize() throws SQLException {
//        idBox.getItems().addAll(NotebookDAO.getAllNotebookIds());
        try{
            idBox.getItems().addAll(NotebookDAO.getAllNotebookIds());
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error");
        }
    }

    @FXML
    private void deleteNotebook(){
        try{
            int id = idBox.getValue();
            NotebookDAO.deleteNotebook(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
