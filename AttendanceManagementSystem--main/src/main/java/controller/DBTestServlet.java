package controller;

import model.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/dbtest")
public class DBTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Database Connection Test</title></head><body>");
        out.println("<h2>Database Connection Test</h2>");
        
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                out.println("<h3 style='color: green;'>Connection successful!</h3>");
                out.println("<p>Connected to database: " + conn.getCatalog() + "</p>");
                out.println("<p>Database URL: " + conn.getMetaData().getURL() + "</p>");
                out.println("<p>Database Product: " + conn.getMetaData().getDatabaseProductName() + 
                           " " + conn.getMetaData().getDatabaseProductVersion() + "</p>");
                conn.close();
            } else {
                out.println("<h3 style='color: red;'>Connection failed!</h3>");
            }
        } catch (SQLException e) {
            out.println("<h3 style='color: red;'>Connection failed with error:</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
        
        String contextPath = request.getContextPath();
            out.println("<br><a href='" + contextPath + "/index.html'>Back to Home</a>");
        out.println("</body></html>");
    }
}