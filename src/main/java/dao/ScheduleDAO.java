package dao;

import model.Schedule;
import model.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT sc.schedule_id, sc.course_id, sc.subject_id, sc.teacher_id, sc.day_of_week, " +
                    "sc.start_time, sc.end_time, sc.classroom, c.course_name, s.subject_name, t.name as teacher_name " +
                    "FROM schedule_table sc " +
                    "JOIN course_table c ON sc.course_id = c.course_id " +
                    "JOIN subject_table s ON sc.subject_id = s.subject_id " +
                    "JOIN teacher_table t ON sc.teacher_id = t.teacher_id " +
                    "ORDER BY c.course_name, sc.day_of_week, sc.start_time";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setCourseId(rs.getInt("course_id"));
                schedule.setSubjectId(rs.getInt("subject_id"));
                schedule.setTeacherId(rs.getInt("teacher_id"));
                schedule.setDayOfWeek(rs.getString("day_of_week"));
                schedule.setStartTime(rs.getTime("start_time"));
                schedule.setEndTime(rs.getTime("end_time"));
                schedule.setClassroom(rs.getString("classroom"));
                schedule.setCourseName(rs.getString("course_name"));
                schedule.setSubjectName(rs.getString("subject_name"));
                schedule.setTeacherName(rs.getString("teacher_name"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public List<Schedule> getSchedulesByCourse(int courseId) {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT sc.schedule_id, sc.course_id, sc.subject_id, sc.teacher_id, sc.day_of_week, " +
                    "sc.start_time, sc.end_time, sc.classroom, c.course_name, s.subject_name, t.name as teacher_name " +
                    "FROM schedule_table sc " +
                    "JOIN course_table c ON sc.course_id = c.course_id " +
                    "JOIN subject_table s ON sc.subject_id = s.subject_id " +
                    "JOIN teacher_table t ON sc.teacher_id = t.teacher_id " +
                    "WHERE sc.course_id = ? " +
                    "ORDER BY sc.day_of_week, sc.start_time";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setCourseId(rs.getInt("course_id"));
                schedule.setSubjectId(rs.getInt("subject_id"));
                schedule.setTeacherId(rs.getInt("teacher_id"));
                schedule.setDayOfWeek(rs.getString("day_of_week"));
                schedule.setStartTime(rs.getTime("start_time"));
                schedule.setEndTime(rs.getTime("end_time"));
                schedule.setClassroom(rs.getString("classroom"));
                schedule.setCourseName(rs.getString("course_name"));
                schedule.setSubjectName(rs.getString("subject_name"));
                schedule.setTeacherName(rs.getString("teacher_name"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public List<Schedule> getSchedulesBySubject(int subjectId) {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT sc.schedule_id, sc.course_id, sc.subject_id, sc.teacher_id, sc.day_of_week, " +
                    "sc.start_time, sc.end_time, sc.classroom, c.course_name, s.subject_name, t.name as teacher_name " +
                    "FROM schedule_table sc " +
                    "JOIN course_table c ON sc.course_id = c.course_id " +
                    "JOIN subject_table s ON sc.subject_id = s.subject_id " +
                    "JOIN teacher_table t ON sc.teacher_id = t.teacher_id " +
                    "WHERE sc.subject_id = ? " +
                    "ORDER BY c.course_name, sc.day_of_week, sc.start_time";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, subjectId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setCourseId(rs.getInt("course_id"));
                schedule.setSubjectId(rs.getInt("subject_id"));
                schedule.setTeacherId(rs.getInt("teacher_id"));
                schedule.setDayOfWeek(rs.getString("day_of_week"));
                schedule.setStartTime(rs.getTime("start_time"));
                schedule.setEndTime(rs.getTime("end_time"));
                schedule.setClassroom(rs.getString("classroom"));
                schedule.setCourseName(rs.getString("course_name"));
                schedule.setSubjectName(rs.getString("subject_name"));
                schedule.setTeacherName(rs.getString("teacher_name"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public List<Schedule> getSchedulesByTeacher(int teacherId) {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT sc.schedule_id, sc.course_id, sc.subject_id, sc.teacher_id, sc.day_of_week, " +
                    "sc.start_time, sc.end_time, sc.classroom, c.course_name, s.subject_name, t.name as teacher_name " +
                    "FROM schedule_table sc " +
                    "JOIN course_table c ON sc.course_id = c.course_id " +
                    "JOIN subject_table s ON sc.subject_id = s.subject_id " +
                    "JOIN teacher_table t ON sc.teacher_id = t.teacher_id " +
                    "WHERE sc.teacher_id = ? " +
                    "ORDER BY c.course_name, sc.day_of_week, sc.start_time";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setCourseId(rs.getInt("course_id"));
                schedule.setSubjectId(rs.getInt("subject_id"));
                schedule.setTeacherId(rs.getInt("teacher_id"));
                schedule.setDayOfWeek(rs.getString("day_of_week"));
                schedule.setStartTime(rs.getTime("start_time"));
                schedule.setEndTime(rs.getTime("end_time"));
                schedule.setClassroom(rs.getString("classroom"));
                schedule.setCourseName(rs.getString("course_name"));
                schedule.setSubjectName(rs.getString("subject_name"));
                schedule.setTeacherName(rs.getString("teacher_name"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }
}