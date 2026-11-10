package model;

public class Teacher {
    private int teacherId;
    private String name;
    private String email;
    private String password;
    private int courseId;
    private String courseName; // For display purposes
    
    // Constructors
    public Teacher() {}
    
    public Teacher(String name, String email, String password, int courseId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.courseId = courseId;
    }
    
    public Teacher(int teacherId, String name, String email, String password, int courseId) {
        this.teacherId = teacherId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.courseId = courseId;
    }
    
    // Getters and Setters
    public int getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
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