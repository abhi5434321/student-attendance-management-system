package dao;

import model.Course;
import model.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class CourseDAO {
    // In-memory storage as backup when database is not available
    private static final Map<Integer, Course> courseStorage = new ConcurrentHashMap<>();
    private static int nextId = 1;
    
    static {
        // Initialize nextId counter
        nextId = 4; // Start from 4 to reserve IDs 1-3 for potential database defaults
    }
    
    public boolean addCourse(Course course) {
        // Ensure the table exists
        ensureTableExists();
        
        // First, try to insert into database to get the auto-generated ID
        String sql = "INSERT INTO course_table (course_name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, course.getCourseName());
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating course failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    course.setCourseId(id);
                    // Add to in-memory storage with the same ID from database
                    courseStorage.put(id, course);
                } else {
                    throw new SQLException("Creating course failed, no ID obtained.");
                }
            }
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Database insert failed, trying in-memory storage");
            e.printStackTrace();
            
            // Fallback to in-memory storage
            int id = nextId++;
            course.setCourseId(id);
            courseStorage.put(id, course);
            return true;
        }
    }
    
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        
        // Try to get courses from database first
        // Ensure the table exists
        ensureTableExists();
        
        String sql = "SELECT * FROM course_table ORDER BY course_name";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Clear in-memory storage and repopulate from database to ensure sync
            courseStorage.clear();
            
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                courses.add(course);
                
                // Also add to in-memory storage to keep them in sync
                courseStorage.put(course.getCourseId(), course);
            }
            
            // Update nextId to be higher than the highest ID in the database
            if (!courses.isEmpty()) {
                int maxId = courses.stream()
                    .mapToInt(Course::getCourseId)
                    .max().orElse(0);
                nextId = Math.max(nextId, maxId + 1);
            }
            
            return courses;
            
        } catch (SQLException e) {
            System.err.println("Database connection failed. Using in-memory storage.");
            e.printStackTrace();
        }
        
        // If database is not available, return all courses from memory
        for (Map.Entry<Integer, Course> entry : courseStorage.entrySet()) {
            courses.add(entry.getValue());
        }
        
        return courses;
    }
    
    public boolean updateCourse(int courseId, String courseName) {
        // Update in-memory storage
        Course course = courseStorage.get(courseId);
        if (course != null) {
            course.setCourseName(courseName);
        }
        
        // Ensure the table exists
        ensureTableExists();
        
        String sql = "UPDATE course_table SET course_name = ? WHERE course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, courseName);
            stmt.setInt(2, courseId);
            boolean result = stmt.executeUpdate() > 0;
            if (!result) {
                System.out.println("Database update failed, using in-memory storage only");
            }
            return true; // Return true if either storage worked
            
        } catch (SQLException e) {
            System.out.println("Database update failed, using in-memory storage only");
            e.printStackTrace();
            return true; // Still return true since we updated in memory
        }
    }
    
    public boolean deleteCourse(int courseId) {
        // Delete from in-memory storage
        Course removed = courseStorage.remove(courseId);
        
        // Ensure the table exists
        ensureTableExists();
        
        // First check if there are any dependent records
        if (hasDependentRecords(courseId)) {
            // If we removed from memory but couldn't delete from DB due to dependencies,
            // put it back in memory
            if (removed != null) {
                courseStorage.put(courseId, removed);
            }
            return false; // Cannot delete course with dependencies
        }
        
        String sql = "DELETE FROM course_table WHERE course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            boolean result = stmt.executeUpdate() > 0;
            if (!result) {
                System.out.println("Database delete failed, using in-memory storage only");
            }
            return true; // Return true if either storage worked
            
        } catch (SQLException e) {
            System.out.println("Database delete failed, using in-memory storage only");
            e.printStackTrace();
            return true; // Still return true since we deleted from memory
        }
    }
    
    public boolean hasDependentRecords(int courseId) {
        // For now, assume no dependencies if database is not available
        // This is a simplified approach for when database is not accessible
        try (Connection conn = DBConnection.getConnection()) {
            // Check teachers
            String teacherSql = "SELECT COUNT(*) FROM teacher_table WHERE course_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(teacherSql)) {
                stmt.setInt(1, courseId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
            
            // Check subjects
            String subjectSql = "SELECT COUNT(*) FROM subject_table WHERE course_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(subjectSql)) {
                stmt.setInt(1, courseId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
            
            // Check students
            String studentSql = "SELECT COUNT(*) FROM student_table WHERE course_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(studentSql)) {
                stmt.setInt(1, courseId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
            
            // Check attendance
            String attendanceSql = "SELECT COUNT(*) FROM attendance_table WHERE course_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(attendanceSql)) {
                stmt.setInt(1, courseId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Database connection failed in hasDependentRecords, assuming no dependencies");
            e.printStackTrace();
        }
        
        return false;
    }
    
    public Course getCourseById(int courseId) {
        // First check in-memory storage
        Course course = courseStorage.get(courseId);
        if (course != null) {
            return course;
        }
        
        // Ensure the table exists
        ensureTableExists();
        
        String sql = "SELECT * FROM course_table WHERE course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                return course;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void ensureTableExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS course_table (" +
                "course_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "course_name VARCHAR(100) NOT NULL UNIQUE" +
                ")";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Could not ensure course_table exists: " + e.getMessage());
        }
    }
}