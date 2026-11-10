package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DBConnection;

@WebServlet("/testDb")
public class TestDatabaseServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>Database Test Results</h2>");
        
        try {
            Connection conn = DBConnection.getConnection();
            out.println("<p style='color:green'>✓ Database connection successful!</p>");
            
            // Test courses
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM course_table");
            if (rs.next()) {
                int count = rs.getInt("count");
                out.println("<p>Courses count: " + count + "</p>");
                
                if (count == 0) {
                    out.println("<p style='color:red'>⚠ No courses found in database!</p>");
                } else {
                    rs = stmt.executeQuery("SELECT * FROM course_table ORDER BY course_name");
                    out.println("<h3>Courses:</h3><ul>");
                    while (rs.next()) {
                        out.println("<li>ID: " + rs.getInt("course_id") + 
                                  ", Name: " + rs.getString("course_name") + "</li>");
                    }
                    out.println("</ul>");
                }
            }
            
            // Test subjects
            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM subject_table");
            if (rs.next()) {
                int count = rs.getInt("count");
                out.println("<p>Subjects count: " + count + "</p>");
                
                if (count == 0) {
                    out.println("<p style='color:red'>⚠ No subjects found in database!</p>");
                } else {
                    rs = stmt.executeQuery("SELECT s.*, c.course_name FROM subject_table s LEFT JOIN course_table c ON s.course_id = c.course_id ORDER BY c.course_name, s.subject_name");
                    out.println("<h3>Subjects:</h3><ul>");
                    while (rs.next()) {
                        out.println("<li>ID: " + rs.getInt("subject_id") + 
                                  ", Name: " + rs.getString("subject_name") + 
                                  ", Course: " + rs.getString("course_name") + "</li>");
                    }
                    out.println("</ul>");
                }
            }
            
            conn.close();
            
        } catch (Exception e) {
            out.println("<p style='color:red'>✗ Database error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
        
        out.println("<br><a href='/AttendanceSystem/teacher/markAttendance.jsp'>Go to Attendance Page</a>");
        out.println("</body></html>");
    }
}