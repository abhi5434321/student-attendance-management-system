package controller;

import dao.StudentDAO;
import dao.CourseDAO;
import dao.SubjectDAO;
import dao.TeacherDAO;
import model.Student;
import model.Course;
import model.Subject;
import model.Teacher;
import model.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/markAttendance")
public class AttendanceServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        
        if (userType == null || (!userType.equals("teacher") && !userType.equals("admin"))) {
            response.sendRedirect("../index.html");
            return;
        }
        
        // Get the logged-in teacher's information
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        List<Course> courses = new ArrayList<>();
        List<Subject> subjects = new ArrayList<>();
        
        if (teacher != null) {
            // Filter courses and subjects based on the teacher's assigned course
            Course assignedCourse = courseDAO.getCourseById(teacher.getCourseId());
            if (assignedCourse != null) {
                courses.add(assignedCourse);
            }
            
            // Get subjects for the teacher's assigned course
            subjects = subjectDAO.getSubjectsByCourse(teacher.getCourseId());
        } else {
            // Fallback to all courses and subjects if teacher info not available
            // This ensures dropdowns have data even if not properly logged in
            courses = courseDAO.getAllCourses();
            subjects = subjectDAO.getAllSubjects();
        }
        
        // Debug logging
        System.out.println("Courses loaded: " + courses.size());
        System.out.println("Subjects loaded: " + subjects.size());
        for (Course course : courses) {
            System.out.println("  Course: " + course.getCourseId() + " - " + course.getCourseName());
        }
        for (Subject subject : subjects) {
            System.out.println("  Subject: " + subject.getSubjectId() + " - " + subject.getSubjectName());
        }
        
        request.setAttribute("courses", courses);
        request.setAttribute("subjects", subjects);
        
        request.getRequestDispatcher("/teacher/markAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("loadStudents".equals(action)) {
            loadStudents(request, response);
        } else if ("saveAttendance".equals(action)) {
            saveAttendance(request, response);
        } else {
            // Default behavior - try to load students
            loadStudents(request, response);
        }
    }
    
    private void loadStudents(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String courseIdStr = request.getParameter("courseId");
        String subjectIdStr = request.getParameter("subjectId");
        String dateStr = request.getParameter("date");

        System.out.println("Loading students for courseId: " + courseIdStr + ", subjectId: " + subjectIdStr);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            int subjectId = Integer.parseInt(subjectIdStr);
            
            // Get the logged-in teacher's information
            HttpSession session = request.getSession();
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            
            // Security check: Ensure the teacher can only access their assigned course
            String userType = (String) session.getAttribute("userType");
            if (teacher != null && !"admin".equals(userType) && teacher.getCourseId() != courseId) {
                request.setAttribute("errorMessage", "You are not authorized to access this course.");
                
                // Repopulate dropdowns with only authorized courses and subjects
                List<Course> courses = new ArrayList<>();
                List<Subject> subjects = new ArrayList<>();
                
                Course assignedCourse = courseDAO.getCourseById(teacher.getCourseId());
                if (assignedCourse != null) {
                    courses.add(assignedCourse);
                }
                subjects = subjectDAO.getSubjectsByCourse(teacher.getCourseId());
                
                request.setAttribute("courses", courses);
                request.setAttribute("subjects", subjects);
                request.getRequestDispatcher("/teacher/markAttendance.jsp").forward(request, response);
                return;
            }
            
            // Get students for the course
            List<Student> students = studentDAO.getStudentsByCourse(courseId);
            
            // Debug logging
            System.out.println("Students found: " + students.size());
            for (Student student : students) {
                System.out.println("  Student: " + student.getStudentId() + " - " + student.getName());
            }
            
            // Repopulate dropdowns with all courses and subjects (for demo purposes)
            List<Course> courses = courseDAO.getAllCourses();
            List<Subject> subjects = subjectDAO.getAllSubjects();
            
            // Debug logging
            System.out.println("Courses reloaded: " + courses.size());
            System.out.println("Subjects reloaded: " + subjects.size());
            
            // Store in request scope to be used in JSP
            request.setAttribute("students", students);
            request.setAttribute("courseId", courseId);
            request.setAttribute("subjectId", subjectId);
            request.setAttribute("date", dateStr);
            request.setAttribute("courses", courses);
            request.setAttribute("subjects", subjects);
            
            request.getRequestDispatcher("/teacher/markAttendance.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.out.println("Number format exception: " + e.getMessage());
            
            // Get the logged-in teacher's information
            HttpSession session = request.getSession();
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            
            // Repopulate dropdowns with all courses and subjects (for demo purposes)
            List<Course> courses = courseDAO.getAllCourses();
            List<Subject> subjects = subjectDAO.getAllSubjects();
            
            request.setAttribute("courses", courses);
            request.setAttribute("subjects", subjects);
            request.setAttribute("errorMessage", "Invalid course or subject ID: " + e.getMessage());
            request.getRequestDispatcher("/teacher/markAttendance.jsp").forward(request, response);
        }
    }

    private void saveAttendance(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String dateStr = request.getParameter("date");
        String courseIdStr = request.getParameter("courseId");
        String subjectIdStr = request.getParameter("subjectId");

        // Get the logged-in teacher's information
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (dateStr == null || courseIdStr == null || subjectIdStr == null) {
            // Repopulate dropdowns with all courses and subjects (for demo purposes)
            List<Course> courses = courseDAO.getAllCourses();
            List<Subject> subjects = subjectDAO.getAllSubjects();
            
            request.setAttribute("courses", courses);
            request.setAttribute("subjects", subjects);
            request.setAttribute("errorMessage", "Missing required parameters");
            request.getRequestDispatcher("/teacher/markAttendance.jsp").forward(request, response);
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdStr);
            int subjectId = Integer.parseInt(subjectIdStr);

            // Security check: Ensure the teacher can only access their assigned course
            String userType = (String) session.getAttribute("userType");
            if (teacher != null && !"admin".equals(userType) && teacher.getCourseId() != courseId) {
                request.setAttribute("errorMessage", "You are not authorized to access this course.");
                
                // Repopulate dropdowns with all courses and subjects (for demo purposes)
                List<Course> courses = courseDAO.getAllCourses();
                List<Subject> subjects = subjectDAO.getAllSubjects();
                
                request.setAttribute("courses", courses);
                request.setAttribute("subjects", subjects);
                request.getRequestDispatcher("/teacher/markAttendance.jsp").forward(request, response);
                return;
            }

            // Parse the date
            java.sql.Date date = java.sql.Date.valueOf(dateStr);
            
            // Get all parameters and find attendance entries
            Map<String, String[]> parameterMap = request.getParameterMap();
            Set<String> attendanceParams = new HashSet<>();
            
            for (String paramName : parameterMap.keySet()) {
                if (paramName.startsWith("attendance_")) {
                    attendanceParams.add(paramName);
                }
            }
            
            // Prepare SQL for inserting attendance records
            String insertSQL = "INSERT INTO attendance_table (student_id, subject_id, course_id, date, status) VALUES (?, ?, ?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE status = VALUES(status)";
            
            int recordsSaved = 0;
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
                
                for (String param : attendanceParams) {
                    String studentIdStr = param.substring("attendance_".length());
                    
                    try {
                        int studentId = Integer.parseInt(studentIdStr);
                        String status = request.getParameter(param);
                        
                        if (status != null && (status.equals("Present") || status.equals("Absent"))) {
                            stmt.setInt(1, studentId);
                            stmt.setInt(2, subjectId);
                            stmt.setInt(3, courseId);
                            stmt.setDate(4, date);
                            stmt.setString(5, status);
                            
                            stmt.addBatch();
                            recordsSaved++;
                        }
                    } catch (NumberFormatException e) {
                        // Skip invalid student IDs
                        continue;
                    }
                }
                
                stmt.executeBatch();
            }
            
            // Repopulate dropdowns with all courses and subjects (for demo purposes)
            List<Course> courses = courseDAO.getAllCourses();
            List<Subject> subjects = subjectDAO.getAllSubjects();
            
            request.setAttribute("courses", courses);
            request.setAttribute("subjects", subjects);
            
            // Set success message
            request.setAttribute("successMessage", recordsSaved + " attendance records saved successfully!");
            request.getRequestDispatcher("/teacher/markAttendance.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Repopulate dropdowns on error with all courses and subjects (for demo purposes)
            List<Course> courses = courseDAO.getAllCourses();
            List<Subject> subjects = subjectDAO.getAllSubjects();
            
            request.setAttribute("courses", courses);
            request.setAttribute("subjects", subjects);
            request.setAttribute("errorMessage", "Error saving attendance: " + e.getMessage());
            request.getRequestDispatcher("/teacher/markAttendance.jsp").forward(request, response);
        }
    }
}