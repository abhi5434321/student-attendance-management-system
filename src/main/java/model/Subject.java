package model;

public class Subject {
    private int subjectId;
    private String subjectName;
    private int courseId;
    private String courseName; // For display purposes
    
    // Constructors
    public Subject() {}
    
    public Subject(String subjectName, int courseId) {
        this.subjectName = subjectName;
        this.courseId = courseId;
    }
    
    public Subject(int subjectId, String subjectName, int courseId) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.courseId = courseId;
    }
    
    // Getters and Setters
    public int getSubjectId() {
        return subjectId;
    }
    
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    
    public String getSubjectName() {
        return subjectName;
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
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