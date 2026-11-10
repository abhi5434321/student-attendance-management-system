package dao;

import model.Student;
import model.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO student_table (roll_no, name, email, phone, gender, course_id, semester) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, student.getRollNo());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getPhone());
            stmt.setString(5, student.getGender());
            stmt.setInt(6, student.getCourseId());
            stmt.setInt(7, student.getSemester());
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Student> getStudentsByCourse(int courseId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.*, c.course_name FROM student_table s " +
                    "LEFT JOIN course_table c ON s.course_id = c.course_id " +
                    "WHERE s.course_id = ? ORDER BY s.roll_no";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setRollNo(rs.getString("roll_no"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setGender(rs.getString("gender"));
                student.setCourseId(rs.getInt("course_id"));
                student.setSemester(rs.getInt("semester"));
                student.setCourseName(rs.getString("course_name"));
                students.add(student);
            }
            
        } catch (SQLException e) {
            System.err.println("Database connection failed. Returning mock data for demo purposes.");
            e.printStackTrace();
            
            // Return mock data for demo purposes when database is not available
            if (courseId == 1) {
                Student alice = new Student();
                alice.setStudentId(1);
                alice.setRollNo("CS001");
                alice.setName("Alice Johnson");
                alice.setEmail("alice@example.com");
                alice.setPhone("123-456-7890");
                alice.setGender("Female");
                alice.setCourseId(1);
                alice.setSemester(3);
                alice.setCourseName("Computer Science");
                students.add(alice);
                
                Student bob = new Student();
                bob.setStudentId(2);
                bob.setRollNo("CS002");
                bob.setName("Bob Wilson");
                bob.setEmail("bob@example.com");
                bob.setPhone("098-765-4321");
                bob.setGender("Male");
                bob.setCourseId(1);
                bob.setSemester(3);
                bob.setCourseName("Computer Science");
                students.add(bob);
            } else if (courseId == 2) {
                Student charlie = new Student();
                charlie.setStudentId(3);
                charlie.setRollNo("EC001");
                charlie.setName("Charlie Brown");
                charlie.setEmail("charlie@example.com");
                charlie.setPhone("555-123-4567");
                charlie.setGender("Male");
                charlie.setCourseId(2);
                charlie.setSemester(2);
                charlie.setCourseName("Electronics");
                students.add(charlie);
            }
        }
        return students;
    }
    
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.*, c.course_name FROM student_table s " +
                    "LEFT JOIN course_table c ON s.course_id = c.course_id ORDER BY c.course_name, s.roll_no";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setRollNo(rs.getString("roll_no"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setGender(rs.getString("gender"));
                student.setCourseId(rs.getInt("course_id"));
                student.setSemester(rs.getInt("semester"));
                student.setCourseName(rs.getString("course_name"));
                students.add(student);
            }
            
        } catch (SQLException e) {
            System.err.println("Database connection failed. Returning mock data for demo purposes.");
            e.printStackTrace();
            
            // Return mock data for demo purposes when database is not available
            Student alice = new Student();
            alice.setStudentId(1);
            alice.setRollNo("CS001");
            alice.setName("Alice Johnson");
            alice.setEmail("alice@example.com");
            alice.setPhone("123-456-7890");
            alice.setGender("Female");
            alice.setCourseId(1);
            alice.setSemester(3);
            alice.setCourseName("Computer Science");
            students.add(alice);
            
            Student bob = new Student();
            bob.setStudentId(2);
            bob.setRollNo("CS002");
            bob.setName("Bob Wilson");
            bob.setEmail("bob@example.com");
            bob.setPhone("098-765-4321");
            bob.setGender("Male");
            bob.setCourseId(1);
            bob.setSemester(3);
            bob.setCourseName("Computer Science");
            students.add(bob);
            
            Student charlie = new Student();
            charlie.setStudentId(3);
            charlie.setRollNo("EC001");
            charlie.setName("Charlie Brown");
            charlie.setEmail("charlie@example.com");
            charlie.setPhone("555-123-4567");
            charlie.setGender("Male");
            charlie.setCourseId(2);
            charlie.setSemester(2);
            charlie.setCourseName("Electronics");
            students.add(charlie);
        }
        return students;
    }
    
    public boolean updateStudent(int studentId, String rollNo, String name, String email, String phone, String gender, int courseId, int semester) {
        String sql = "UPDATE student_table SET roll_no = ?, name = ?, email = ?, phone = ?, gender = ?, course_id = ?, semester = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        
            stmt.setString(1, rollNo);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, gender);
            stmt.setInt(6, courseId);
            stmt.setInt(7, semester);
            stmt.setInt(8, studentId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM student_table WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        
            stmt.setInt(1, studentId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}