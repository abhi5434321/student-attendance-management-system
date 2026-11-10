package model;

public class Student {
    private int studentId;
    private String rollNo;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private int courseId;
    private int semester;
    private String courseName; // For display purposes
    
    // Constructors
    public Student() {}
    
    public Student(String rollNo, String name, String email, String phone, String gender, int courseId, int semester) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.courseId = courseId;
        this.semester = semester;
    }
    
    public Student(int studentId, String rollNo, String name, String email, String phone, String gender, int courseId, int semester) {
        this.studentId = studentId;
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.courseId = courseId;
        this.semester = semester;
    }
    
    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public String getRollNo() {
        return rollNo;
    }
    
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
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
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public int getSemester() {
        return semester;
    }
    
    public void setSemester(int semester) {
        this.semester = semester;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
}