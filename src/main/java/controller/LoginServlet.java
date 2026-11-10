package controller;

import dao.AdminDAO;
import dao.TeacherDAO;
import model.Admin;
import model.Teacher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AdminDAO adminDAO = new AdminDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userType = request.getParameter("userType");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if ("admin".equals(userType)) {
            Admin admin = adminDAO.loginAdmin(email, password);
            if (admin != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userType", "admin");
                session.setAttribute("admin", admin);
                response.sendRedirect("admin/dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid admin credentials");
                request.getRequestDispatcher("admin/adminLogin.jsp").forward(request, response);
            }
        } else if ("teacher".equals(userType)) {
            Teacher teacher = teacherDAO.loginTeacher(email, password);
            if (teacher != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userType", "teacher");
                session.setAttribute("teacher", teacher);
                response.sendRedirect("teacher/dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid teacher credentials");
                request.getRequestDispatcher("teacher/teacherLogin.jsp").forward(request, response);
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("index.html").forward(request, response);
    }
}