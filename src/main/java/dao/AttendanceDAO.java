package dao;

import model.Attendance;
import model.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public boolean markAttendance(Attendance attendance) {
        String sql = "INSERT INTO attendance_table (student_id, subject_id, course_id, date, status) VALUES (?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE status = VALUES(status)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, attendance.getStudentId());
            stmt.setInt(2, attendance.getSubjectId());
            stmt.setInt(3, attendance.getCourseId());
            stmt.setDate(4, new java.sql.Date(attendance.getDate().getTime()));
            stmt.setString(5, attendance.getStatus());
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Attendance> getDailyAttendance(java.sql.Date date) {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT a.*, s.name AS student_name, s.roll_no, sub.subject_name, c.course_name " +
                    "FROM attendance_table a " +
                    "JOIN student_table s ON a.student_id = s.student_id " +
                    "JOIN subject_table sub ON a.subject_id = sub.subject_id " +
                    "JOIN course_table c ON a.course_id = c.course_id " +
                    "WHERE a.date = ? " +
                    "ORDER BY c.course_name, sub.subject_name, s.roll_no";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setStudentId(rs.getInt("student_id"));
                attendance.setSubjectId(rs.getInt("subject_id"));
                attendance.setCourseId(rs.getInt("course_id"));
                attendance.setDate(rs.getDate("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setStudentName(rs.getString("student_name"));
                attendance.setRollNo(rs.getString("roll_no"));
                attendance.setSubjectName(rs.getString("subject_name"));
                attendance.setCourseName(rs.getString("course_name"));
                attendances.add(attendance);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public List<Attendance> getMonthlyAttendance(int month, int year) {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT a.*, s.name AS student_name, s.roll_no, sub.subject_name, c.course_name " +
                    "FROM attendance_table a " +
                    "JOIN student_table s ON a.student_id = s.student_id " +
                    "JOIN subject_table sub ON a.subject_id = sub.subject_id " +
                    "JOIN course_table c ON a.course_id = c.course_id " +
                    "WHERE MONTH(a.date) = ? AND YEAR(a.date) = ? " +
                    "ORDER BY a.date DESC, c.course_name, sub.subject_name, s.roll_no";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setStudentId(rs.getInt("student_id"));
                attendance.setSubjectId(rs.getInt("subject_id"));
                attendance.setCourseId(rs.getInt("course_id"));
                attendance.setDate(rs.getDate("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setStudentName(rs.getString("student_name"));
                attendance.setRollNo(rs.getString("roll_no"));
                attendance.setSubjectName(rs.getString("subject_name"));
                attendance.setCourseName(rs.getString("course_name"));
                attendances.add(attendance);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public List<Attendance> getAttendanceByStudent(int studentId) {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT a.*, s.name AS student_name, s.roll_no, sub.subject_name, c.course_name " +
                    "FROM attendance_table a " +
                    "JOIN student_table s ON a.student_id = s.student_id " +
                    "JOIN subject_table sub ON a.subject_id = sub.subject_id " +
                    "JOIN course_table c ON a.course_id = c.course_id " +
                    "WHERE a.student_id = ? " +
                    "ORDER BY a.date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setStudentId(rs.getInt("student_id"));
                attendance.setSubjectId(rs.getInt("subject_id"));
                attendance.setCourseId(rs.getInt("course_id"));
                attendance.setDate(rs.getDate("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setStudentName(rs.getString("student_name"));
                attendance.setRollNo(rs.getString("roll_no"));
                attendance.setSubjectName(rs.getString("subject_name"));
                attendance.setCourseName(rs.getString("course_name"));
                attendances.add(attendance);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public List<Attendance> getAttendanceBySubject(int subjectId) {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT a.*, s.name AS student_name, s.roll_no, sub.subject_name, c.course_name " +
                    "FROM attendance_table a " +
                    "JOIN student_table s ON a.student_id = s.student_id " +
                    "JOIN subject_table sub ON a.subject_id = sub.subject_id " +
                    "JOIN course_table c ON a.course_id = c.course_id " +
                    "WHERE a.subject_id = ? " +
                    "ORDER BY a.date DESC, s.roll_no";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, subjectId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setStudentId(rs.getInt("student_id"));
                attendance.setSubjectId(rs.getInt("subject_id"));
                attendance.setCourseId(rs.getInt("course_id"));
                attendance.setDate(rs.getDate("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setStudentName(rs.getString("student_name"));
                attendance.setRollNo(rs.getString("roll_no"));
                attendance.setSubjectName(rs.getString("subject_name"));
                attendance.setCourseName(rs.getString("course_name"));
                attendances.add(attendance);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public List<Attendance> getAttendanceByCourse(int courseId) {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT a.*, s.name AS student_name, s.roll_no, sub.subject_name, c.course_name " +
                    "FROM attendance_table a " +
                    "JOIN student_table s ON a.student_id = s.student_id " +
                    "JOIN subject_table sub ON a.subject_id = sub.subject_id " +
                    "JOIN course_table c ON a.course_id = c.course_id " +
                    "WHERE a.course_id = ? " +
                    "ORDER BY a.date DESC, sub.subject_name, s.roll_no";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setStudentId(rs.getInt("student_id"));
                attendance.setSubjectId(rs.getInt("subject_id"));
                attendance.setCourseId(rs.getInt("course_id"));
                attendance.setDate(rs.getDate("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setStudentName(rs.getString("student_name"));
                attendance.setRollNo(rs.getString("roll_no"));
                attendance.setSubjectName(rs.getString("subject_name"));
                attendance.setCourseName(rs.getString("course_name"));
                attendances.add(attendance);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }
}