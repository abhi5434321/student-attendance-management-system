package model;

import java.util.Date;

public class Attendance {
    private int attendanceId;
    private int studentId;
    private int subjectId;
    private int courseId;
    private Date date;
    private String status; // "Present" or "Absent"
    
    // Additional fields for display purposes
    private String studentName;
    private String subjectName;
    private String courseName;
    private String rollNo;
    
    // Constructors
    public Attendance() {}
    
    public Attendance(int studentId, int subjectId, int courseId, Date date, String status) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.courseId = courseId;
        this.date = date;
        this.status = status;
    }
    
    public Attendance(int attendanceId, int studentId, int subjectId, int courseId, Date date, String status) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.courseId = courseId;
        this.date = date;
        this.status = status;
    }
    
    // Getters and Setters
    public int getAttendanceId() {
        return attendanceId;
    }
    
    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public int getSubjectId() {
        return subjectId;
    }
    
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Display fields getters and setters
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getSubjectName() {
        return subjectName;
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getRollNo() {
        return rollNo;
    }
    
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
}