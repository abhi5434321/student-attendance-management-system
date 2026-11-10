package controller;

import dao.*;
import model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

@WebServlet("/init-db")
public class DatabaseInitServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<html><head><title>Database Initialization</title></head><body>");
            out.println("<h2>Initializing Database...</h2>");
            
            // Create DAO instances to trigger table creation
            CourseDAO courseDAO = new CourseDAO();
            SubjectDAO subjectDAO = new SubjectDAO();
            TeacherDAO teacherDAO = new TeacherDAO();
            StudentDAO studentDAO = new StudentDAO();
            AttendanceDAO attendanceDAO = new AttendanceDAO();
            ScheduleDAO scheduleDAO = new ScheduleDAO();
            AdminDAO adminDAO = new AdminDAO();
            
            // Try to get all courses to trigger table creation
            courseDAO.getAllCourses();
            out.println("<p>✓ Course table verified/created</p>");
            
            // Try to get all subjects to trigger table creation
            subjectDAO.getAllSubjects();
            out.println("<p>✓ Subject table verified/created</p>");
            
            // Try to get all teachers to trigger table creation
            teacherDAO.getAllTeachers();
            out.println("<p>✓ Teacher table verified/created</p>");
            
            // Try to get all students to trigger table creation
            studentDAO.getAllStudents();
            out.println("<p>✓ Student table verified/created</p>");
            
            out.println("<h3>Database initialization completed successfully!</h3>");
            out.println("<p>You can now add courses through the admin panel and they will appear in the teacher registration dropdown.</p>");
            out.println("<a href=\"../admin/dashboard.jsp\">Go to Admin Dashboard</a> | ");
            out.println("<a href=\"../index.html\">Return to Home</a>");
            out.println("</body></html>");
            
        } catch (Exception e) {
            out.println("<h3>Error initializing database:</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<p>Please make sure MySQL server is running and credentials are correct.</p>");
            e.printStackTrace(out);
        }
    }
}