package com.example.lesalonproject;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProcessorReadController {
    @FXML
    private TableView<Processor> table;
    @FXML
    private TableColumn<Processor, Integer> idColumn;
    @FXML
    private TableColumn<Processor, String> manufacturerColumn;
    @FXML
    private TableColumn<Notebook, String> typeColumn;

    public void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        try{
            ObservableList<Processor> data = ProcessorDAO.getProcessors();
            table.setItems(data);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

// package com.example.lesalonproject;

// import javafx.collections.ObservableList;
// import javafx.fxml.FXML;
// import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableView;
// import javafx.scene.control.cell.PropertyValueFactory;

// public class ProcessorReadController {

//     @FXML
//     private TableView<Processor> table;
//     @FXML
//     private TableColumn<Processor, Integer> idColumn;
//     @FXML
//     private TableColumn<Processor, String> manufacturerColumn;
//     @FXML
//     private TableColumn<Processor, String> typeColumn;

//     public void initialize() {
//         // Set up the TableView columns with PropertyValueFactory to bind the properties of Processor class
//         idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//         manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
//         typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

//         try {
//             // Fetch processors from DAO and set the table's items
//             ObservableList<Processor> data = ProcessorDAO.getProcessors();
//             table.setItems(data);
//         } catch (Exception e) {
//             e.printStackTrace();
//             // Handle any exception that occurs while loading the processors (e.g., DB errors)
//         }
//     }
// }

// package com.example.lesalonproject;

// import javafx.collections.ObservableList;
// import javafx.fxml.FXML;
// import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableView;

// public class ProcessorReadController {

//     @FXML
//     private TableView<Processor> table;
//     @FXML
//     private TableColumn<Processor, Integer> idColumn;
//     @FXML
//     private TableColumn<Processor, String> manufacturerColumn;
//     @FXML
//     private TableColumn<Processor, String> typeColumn;

//     // Method to fetch processors and print them to the console
//     @FXML
//     private void printProcessorsToConsole() {
//         try {
//             // Fetch the list of processors from the database
//             ObservableList<Processor> data = ProcessorDAO.getProcessors();

//             // Print out the processor data to the console
//             if (data != null && !data.isEmpty()) {
//                 System.out.println("Processors Data:");
//                 for (Processor processor : data) {
//                     System.out.println("ID: " + processor.getId() +
//                                        ", Manufacturer: " + processor.getManufacturer() +
//                                        ", Type: " + processor.getType());
//                 }
//             } else {
//                 System.out.println("No processors available.");
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//             System.out.println("Error while fetching processor data.");
//         }
//     }
// }
