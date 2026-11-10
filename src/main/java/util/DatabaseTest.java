package util;

import model.DBConnection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {
    public static void main(String[] args) {
        System.out.println("=== Database Connection Test ===");
        
        try {
            // Test basic connection
            Connection conn = DBConnection.getConnection();
            System.out.println("✓ Database connection successful!");
            
            // Get database metadata
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println("Database Product Name: " + metaData.getDatabaseProductName());
            System.out.println("Database Product Version: " + metaData.getDatabaseProductVersion());
            System.out.println("Driver Name: " + metaData.getDriverName());
            System.out.println("Driver Version: " + metaData.getDriverVersion());
            
            // Test if required tables exist
            System.out.println("\n=== Checking Required Tables ===");
            String[] requiredTables = {"course_table", "subject_table", "student_table", "teacher_table", "attendance_table"};
            
            for (String tableName : requiredTables) {
                if (tableExists(conn, tableName)) {
                    System.out.println("✓ Table '" + tableName + "' exists");
                    
                    // Count rows in each table
                    int rowCount = getTableRowCount(conn, tableName);
                    System.out.println("  Rows: " + rowCount);
                } else {
                    System.out.println("✗ Table '" + tableName + "' NOT FOUND");
                }
            }
            
            // Test sample data
            System.out.println("\n=== Sample Data Check ===");
            testSampleData(conn);
            
            conn.close();
            System.out.println("\n✓ All connection tests passed!");
            
        } catch (SQLException e) {
            System.err.println("✗ Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("✗ Unexpected error occurred!");
            e.printStackTrace();
        }
    }
    
    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getTables(null, null, tableName, null);
        boolean exists = rs.next();
        rs.close();
        return exists;
    }
    
    private static int getTableRowCount(Connection conn, String tableName) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    private static void testSampleData(Connection conn) throws SQLException {
        // Check courses
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT course_id, course_name FROM course_table LIMIT 5")) {
            System.out.println("Sample Courses:");
            while (rs.next()) {
                System.out.println("  - " + rs.getInt("course_id") + ": " + rs.getString("course_name"));
            }
        }
        
        // Check subjects
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT subject_id, subject_name FROM subject_table LIMIT 5")) {
            System.out.println("Sample Subjects:");
            while (rs.next()) {
                System.out.println("  - " + rs.getInt("subject_id") + ": " + rs.getString("subject_name"));
            }
        }
        
        // Check students
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT student_id, name, roll_no FROM student_table LIMIT 5")) {
            System.out.println("Sample Students:");
            while (rs.next()) {
                System.out.println("  - " + rs.getInt("student_id") + ": " + rs.getString("name") + 
                                 " (Roll: " + rs.getString("roll_no") + ")");
            }
        }
    }
}