package com.example.lesalonproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class WriteController {
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

    @FXML
    private void addNotebook(){
        try{
            // validate numeric fields
            if(!isNumeric(displayField.getText()) || !isNumeric(memoryField.getText())
                || !isNumeric(harddiskField.getText()) || !isNumeric(priceField.getText())
                || !isNumeric(processoridField.getText()) || !isNumeric(opsystemidField.getText())
                || !isNumeric(piecesField.getText())){

                showError("Please enter valid numbers for all numerical fields");
                return;
            }

            //parse numeric values
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

            NotebookDAO.addNotebook(
                manufacturer, //manufacturerField.getText()
                type,
                display,
                memory,
                harddisk,
                videocontroller,
                price,
                processorid,
                opsystemid,
                pieces
            );

            clearFields();
//            System.out.println("Notebook added successfully");
            showSuccess("Notebook added successfully!");
        } catch (Exception e){
            e.printStackTrace();
//            System.out.println("Failed to add Notebook. Please check the inouts");
            showError("An error occurred while adding the notebook.");
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
