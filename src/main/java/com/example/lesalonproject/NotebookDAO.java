package com.example.lesalonproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotebookDAO{
    public static ObservableList<Notebook> getNotebooks() throws SQLException {
        ObservableList<Notebook> notebooks = FXCollections.observableArrayList();
        String query = "SELECT * FROM Notebook";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                notebooks.add(new Notebook(
                        rs.getInt("id"),
                        rs.getString("manufacturer"),
                        rs.getString("type"),
                        rs.getInt("display"),
                        rs.getInt("memory"),
                        rs.getInt("harddisk"),
                        rs.getString("videocontroller"),
                        rs.getInt("price"),
                        rs.getInt("processorid"),
                        rs.getInt("opsystemid"),
                        rs.getInt("pieces")
                ));
            }
        }

        return notebooks;
    }

    public static ObservableList<Notebook> filterNotebooks(String manufacturer, String processor, boolean includeOs, boolean harddisk) throws SQLException{
        ObservableList<Notebook> notebooks = FXCollections.observableArrayList();
        String query = "SELECT * FROM Notebook WHERE manufacturer LIKE ? AND processorid IN " +
                        "(SELECT id from Processor WHERE manufacturer = ?)";

        //if all filters were TEXT Fields
//        String query = "SELECT * FROM Notebook WHERE manufacturer LIKE ? AND type LIKE ? AND display >= ? AND memory >= ? " +
//                "AND harddisk >= ? AND videocontroller LIKE ? AND price >= ? AND processorid = ? AND opsystemid = ? AND pieces >= ?";

        if (includeOs & harddisk){
            query += " AND opsystemid IS NOT NULL AND harddisk IS NOT NULL";
        }

        try( Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, "%" + manufacturer + "%");
            stmt.setString(2, processor);
//            stmt.setString(3, String.valueOf(includeOs));
//            stmt.setString(4, String.valueOf(harddisk));

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    notebooks.add(new Notebook(
                            rs.getInt("id"),
                            rs.getString("manufacturer"),
                            rs.getString("type"),
                            rs.getInt("display"),
                            rs.getInt("memory"),
                            rs.getInt("harddisk"),
                            rs.getString("videocontroller"),
                            rs.getInt("price"),
                            rs.getInt("processorid"),
                            rs.getInt("opsystemid"),
                            rs.getInt("pieces")
                    ));
                }
            }
        }

        return notebooks;
    }

    public static void addNotebook(String manufacturer, String type, int display, int memory, int harddisk, String videocontroller, int price, int processorid, int opsystemid, int pieces) throws  SQLException{
        String query = "INSERT INTO Notebook (manufacturer, type, display, memory, harddisk, videocontroller, price, processorid, opsystemid, pieces) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn =DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, manufacturer);
            stmt.setString(2, type);
            stmt.setInt(3, display);
            stmt.setInt(4, memory);
            stmt.setInt(5, harddisk);
            stmt.setString(6, videocontroller);
            stmt.setInt(7, price);
            stmt.setInt(8, processorid);
            stmt.setInt(9, opsystemid);
            stmt.setInt(10, pieces);
            stmt.executeUpdate();
        }
    }

    public static void updateNotebook(int id, String manufacturer, String type, int display, int memory, int harddisk, String videocontroller, int price, int processorid, int opsystemid, int pieces) throws SQLException{
        String query = """
            UPDATE Notebook SET\s
                manufacturer = ?, type = ?, display = ?,
                memory = ?, harddisk = ?, videocontroller = ?,
                price = ?, processorid = ?, opsystemid = ?, pieces = ? WHERE id = ?""";
        try(Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, manufacturer);
            stmt.setString(2, type);
            stmt.setInt(3, display);
            stmt.setInt(4, memory);
            stmt.setInt(5, harddisk);
            stmt.setString(6, videocontroller);
            stmt.setInt(7, price);
            stmt.setInt(8, processorid);
            stmt.setInt(9, opsystemid);
            stmt.setInt(10, pieces);
            stmt.setInt(11, id);

            stmt.executeUpdate();
        }
    }

    public static void deleteNotebook(int id) throws SQLException{
        String query = "DELETE FROM Notebook WHERE id = ?";

        try(Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static ObservableList<Integer> getAllNotebookIds() throws SQLException{
        ObservableList<Integer> ids = FXCollections.observableArrayList();
        String query = "SELECT id FROM Notebook";

        try(Connection conn = DatabaseHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                ids.add(rs.getInt("id"));
            }
        }
        return ids;
    }

    public static Notebook getNotebookById(int id) throws SQLException{
        Notebook notebook = null;
        String query = "SELECT * from Notebook WHERE id = ?";

        try(Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);;
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    notebook =  new Notebook(
                            rs.getInt("id"),
                            rs.getString("manufacturer"),
                            rs.getString("type"),
                            rs.getInt("display"),
                            rs.getInt("memory"),
                            rs.getInt("harddisk"),
                            rs.getString("videocontroller"),
                            rs.getInt("price"),
                            rs.getInt("processorid"),
                            rs.getInt("opsystemid"),
                            rs.getInt("pieces")
                    );
                }
            }
        }

        return  notebook;
    }

}
