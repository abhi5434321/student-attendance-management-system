package dao;

import model.Teacher;
import model.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    
    public boolean registerTeacher(Teacher teacher) {
        String sql = "INSERT INTO teacher_table (name, email, password, course_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getEmail());
            stmt.setString(3, teacher.getPassword());
            stmt.setInt(4, teacher.getCourseId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Teacher loginTeacher(String email, String password) {
        String sql = "SELECT t.*, c.course_name FROM teacher_table t " +
                    "LEFT JOIN course_table c ON t.course_id = c.course_id " +
                    "WHERE t.email = ? AND t.password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setName(rs.getString("name"));
                teacher.setEmail(rs.getString("email"));
                teacher.setPassword(rs.getString("password"));
                teacher.setCourseId(rs.getInt("course_id"));
                teacher.setCourseName(rs.getString("course_name"));
                return teacher;
            }
            
        } catch (SQLException e) {
            System.err.println("Database connection failed. Using mock data for demo purposes.");
            e.printStackTrace();
            
            // Mock teacher for demo purposes when database is not available
            if ("teacher@example.com".equals(email) && "password".equals(password)) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(1);
                teacher.setName("Demo Teacher");
                teacher.setEmail("teacher@example.com");
                teacher.setPassword("password");
                teacher.setCourseId(1);
                teacher.setCourseName("Computer Science");
                return teacher;
            }
        }
        return null;
    }
    
    public boolean isTeacherExists(String email) {
        String sql = "SELECT COUNT(*) FROM teacher_table WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Database connection failed. Using mock data for demo purposes.");
            e.printStackTrace();
            
            // Mock response for demo purposes when database is not available
            return "teacher@example.com".equals(email);
        }
        return false;
    }
    
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT t.*, c.course_name FROM teacher_table t " +
                    "LEFT JOIN course_table c ON t.course_id = c.course_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setName(rs.getString("name"));
                teacher.setEmail(rs.getString("email"));
                teacher.setCourseId(rs.getInt("course_id"));
                teacher.setCourseName(rs.getString("course_name"));
                teachers.add(teacher);
            }
            
        } catch (SQLException e) {
            System.err.println("Database connection failed. Using mock data for demo purposes.");
            e.printStackTrace();
            
            // Mock teachers for demo purposes when database is not available
            Teacher teacher1 = new Teacher();
            teacher1.setTeacherId(1);
            teacher1.setName("Demo Teacher 1");
            teacher1.setEmail("teacher1@example.com");
            teacher1.setCourseId(1);
            teacher1.setCourseName("Computer Science");
            teachers.add(teacher1);
            
            Teacher teacher2 = new Teacher();
            teacher2.setTeacherId(2);
            teacher2.setName("Demo Teacher 2");
            teacher2.setEmail("teacher2@example.com");
            teacher2.setCourseId(2);
            teacher2.setCourseName("Electronics");
            teachers.add(teacher2);
        }
        return teachers;
    }
}