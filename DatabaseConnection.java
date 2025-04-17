package com.bloodbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Update these details with your MySQL credentials
    private static final String URL = "jdbc:mysql://localhost:3306/bloodbank";
    private static final String USER = "root";      // Your MySQL username
    private static final String PASSWORD = "";  // Your MySQL password

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        return conn;
    }
    
    // You can add a main method here to test the connection:
    public static void main(String[] args) {
        getConnection();
    }
}
