package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/attendance_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";  // Change this to your MySQL username
    private static final String PASSWORD = "Abhi1234";      // Change this to your MySQL password
    
    static {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            System.err.println("Please ensure MySQL is installed and the JDBC driver is in the classpath.");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please ensure MySQL server is running and the database 'attendance_system' exists.");
            System.err.println("Run the database.sql script to create the database and tables.");
            
            // Attempt to create the database if it doesn't exist
            try {
                String dbCreateUrl = "jdbc:mysql://localhost:3306?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
                Connection conn = DriverManager.getConnection(dbCreateUrl, USERNAME, PASSWORD);
                java.sql.Statement stmt = conn.createStatement();
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS attendance_system");
                stmt.close();
                conn.close();
                
                // Now try connecting to the created database
                return DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException ex) {
                System.err.println("Failed to create database: " + ex.getMessage());
                throw e; // Re-throw the original exception
            }
        }
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}