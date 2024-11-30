package com.example.lesalonproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcessorDAO{

    public static ObservableList<Processor> getProcessors() throws SQLException {
        ObservableList<Processor> processors = FXCollections.observableArrayList();
        String query = "SELECT * FROM Processor";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                processors.add(new Processor(
                        rs.getInt("id"),
                        rs.getString("manufacturer"),
                        rs.getString("type")
                ));
            }
        }

        return processors;
    }

    public static void addProcessor(String manufacturer, String type) throws SQLException {
        String query = "INSERT INTO Processor (manufacturer, type) VALUES (?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, manufacturer);
            stmt.setString(2, type);

            stmt.executeUpdate();
            
        }
    }


    public static ObservableList<Integer> getAllProcessorsIds() throws SQLException{
        ObservableList<Integer> ids = FXCollections.observableArrayList();
        String query = "SELECT id FROM Processor";

        try(Connection conn = DatabaseHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                ids.add(rs.getInt("id"));
            }
        }
        return ids;
    }

    public static Processor getProcessorById(int id) throws SQLException{
        Processor processor = null;
        String query = "SELECT * from Processor WHERE id = ?";

        try(Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);;
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    processor =  new Processor(
                            rs.getInt("id"),
                            rs.getString("manufacturer"),
                            rs.getString("type")
                            
                    );
                }
            }
        }

        return  processor;
    }

    public static void updateProcessor(int id, String manufacturer, String type) throws SQLException{
        String query = """
            UPDATE Processor SET\s
                manufacturer = ?, type = ? WHERE id = ?""";
        try(Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, manufacturer);
            stmt.setString(2, type);
            

            stmt.executeUpdate();
        }
    }

    public static void deleteProcessor(int id) throws SQLException{
        String query = "DELETE FROM Processor WHERE id = ?";

        try(Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
