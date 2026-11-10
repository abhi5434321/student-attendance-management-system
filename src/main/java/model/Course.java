package model;

public class Course {
    private int courseId;
    private String courseName;
    
    // Constructors
    public Course() {}
    
    public Course(String courseName) {
        this.courseName = courseName;
    }
    
    public Course(int courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }
    
    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}