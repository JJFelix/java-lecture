package com.example.lesalonproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsystemDAO {

    // Retrieve all operations systems from the database
    public static ObservableList<Opsystem> getOpsystems() throws SQLException {
        ObservableList<Opsystem> opsystems = FXCollections.observableArrayList();
        String query = "SELECT * FROM Opsystem";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                opsystems.add(new Opsystem(
                        rs.getInt("id"),
                        rs.getString("osname")
                ));
            }
        }

        return opsystems;
    }

    // Add a new operation system to the database
    public static void addOpsystem(String osname) throws SQLException {
        String query = "INSERT INTO Opsystem (osname) VALUES (?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, osname);
            stmt.executeUpdate();
        }
    }

    // Retrieve all Opsystem IDs from the database
    public static ObservableList<Integer> getAllOpsystemIds() throws SQLException {
        ObservableList<Integer> ids = FXCollections.observableArrayList();
        String query = "SELECT id FROM Opsystem";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        }
        return ids;
    }

    // Retrieve a specific Opsystem by its ID
    public static Opsystem getOpsystemById(int id) throws SQLException {
        Opsystem opsystem = null;
        String query = "SELECT * FROM Opsystem WHERE id = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    opsystem = new Opsystem(
                            rs.getInt("id"),
                            rs.getString("osname")
                    );
                }
            }
        }
        return opsystem;
    }

    // Update an existing Opsystem in the database
    public static void updateOpsystem(int id, String osname) throws SQLException {
        String query = "UPDATE Opsystem SET osname = ? WHERE id = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, osname);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    // Delete an Opsystem by its ID
    public static void deleteOpsystem(int id) throws SQLException {
        String query = "DELETE FROM Opsystem WHERE id = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
