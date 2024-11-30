package com.example.lesalonproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    public static final String URL = "jdbc:sqlite:data2.db";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL);
    }
}
