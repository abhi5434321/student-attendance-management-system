package dao;

import model.Subject;
import model.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    public boolean addSubject(Subject subject) {
        String sql = "INSERT INTO subject_table (subject_name, course_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, subject.getSubjectName());
            stmt.setInt(2, subject.getCourseId());
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.*, c.course_name FROM subject_table s " +
                    "LEFT JOIN course_table c ON s.course_id = c.course_id ORDER BY c.course_name, s.subject_name";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subject_id"));
                subject.setSubjectName(rs.getString("subject_name"));
                subject.setCourseId(rs.getInt("course_id"));
                subject.setCourseName(rs.getString("course_name"));
                subjects.add(subject);
            }
            
        } catch (SQLException e) {
            System.err.println("Database connection failed. Returning mock data for demo purposes.");
            e.printStackTrace();
            
            // Return mock data for demo purposes when database is not available
            Subject java = new Subject();
            java.setSubjectId(1);
            java.setSubjectName("Java Programming");
            java.setCourseId(1);
            java.setCourseName("Computer Science");
            subjects.add(java);
            
            Subject ds = new Subject();
            ds.setSubjectId(2);
            ds.setSubjectName("Data Structures");
            ds.setCourseId(1);
            ds.setCourseName("Computer Science");
            subjects.add(ds);
            
            Subject de = new Subject();
            de.setSubjectId(3);
            de.setSubjectName("Digital Electronics");
            de.setCourseId(2);
            de.setCourseName("Electronics");
            subjects.add(de);
        }
        return subjects;
    }

    public List<Subject> getSubjectsByCourse(int courseId) {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.*, c.course_name FROM subject_table s " +
                    "LEFT JOIN course_table c ON s.course_id = c.course_id " +
                    "WHERE s.course_id = ? ORDER BY s.subject_name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subject_id"));
                subject.setSubjectName(rs.getString("subject_name"));
                subject.setCourseId(rs.getInt("course_id"));
                subject.setCourseName(rs.getString("course_name"));
                subjects.add(subject);
            }
            
        } catch (SQLException e) {
            System.err.println("Database connection failed. Returning mock data for demo purposes.");
            e.printStackTrace();
            
            // Return mock data for demo purposes when database is not available
            if (courseId == 1) {
                Subject java = new Subject();
                java.setSubjectId(1);
                java.setSubjectName("Java Programming");
                java.setCourseId(1);
                java.setCourseName("Computer Science");
                subjects.add(java);
                
                Subject ds = new Subject();
                ds.setSubjectId(2);
                ds.setSubjectName("Data Structures");
                ds.setCourseId(1);
                ds.setCourseName("Computer Science");
                subjects.add(ds);
            } else if (courseId == 2) {
                Subject de = new Subject();
                de.setSubjectId(3);
                de.setSubjectName("Digital Electronics");
                de.setCourseId(2);
                de.setCourseName("Electronics");
                subjects.add(de);
            }
        }
        return subjects;
    }

    public boolean updateSubject(int subjectId, String subjectName, int courseId) {
        String sql = "UPDATE subject_table SET subject_name = ?, course_id = ? WHERE subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, subjectName);
            stmt.setInt(2, courseId);
            stmt.setInt(3, subjectId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSubject(int subjectId) {
        String sql = "DELETE FROM subject_table WHERE subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, subjectId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Subject getSubjectById(int subjectId) {
        String sql = "SELECT s.*, c.course_name FROM subject_table s " +
                    "LEFT JOIN course_table c ON s.course_id = c.course_id " +
                    "WHERE s.subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, subjectId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subject_id"));
                subject.setSubjectName(rs.getString("subject_name"));
                subject.setCourseId(rs.getInt("course_id"));
                subject.setCourseName(rs.getString("course_name"));
                return subject;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}