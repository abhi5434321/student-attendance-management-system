package controller;

import dao.AdminDAO;
import dao.CourseDAO;
import dao.TeacherDAO;
import model.Admin;
import model.Course;
import model.Teacher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private AdminDAO adminDAO = new AdminDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private CourseDAO courseDAO = new CourseDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userType = request.getParameter("userType");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validate passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match!");
            if ("admin".equals(userType)) {
                request.getRequestDispatcher("admin/adminRegister.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("teacher/teacherRegister.jsp").forward(request, response);
            }
            return;
        }
        
        // Validate password length
        if (password.length() < 6) {
            request.setAttribute("errorMessage", "Password must be at least 6 characters long!");
            if ("admin".equals(userType)) {
                request.getRequestDispatcher("admin/adminRegister.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("teacher/teacherRegister.jsp").forward(request, response);
            }
            return;
        }
        
        if ("admin".equals(userType)) {
            // Check if admin already exists
            if (adminDAO.isAdminExists(email)) {
                request.setAttribute("errorMessage", "An admin with this email already exists!");
                request.getRequestDispatcher("admin/adminRegister.jsp").forward(request, response);
                return;
            }
            
            // Create new admin
            Admin admin = new Admin(name, email, password);
            if (adminDAO.registerAdmin(admin)) {
                request.setAttribute("successMessage", "Admin registered successfully! You can now login.");
                request.getRequestDispatcher("admin/adminLogin.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("admin/adminRegister.jsp").forward(request, response);
            }
            
        } else if ("teacher".equals(userType)) {
            String courseIdStr = request.getParameter("courseId");
            int courseId = 0;
            try {
                courseId = Integer.parseInt(courseIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Please select a valid course!");
                request.getRequestDispatcher("teacher/teacherRegister.jsp").forward(request, response);
                return;
            }
            
            // Check if teacher already exists
            if (teacherDAO.isTeacherExists(email)) {
                request.setAttribute("errorMessage", "A teacher with this email already exists!");
                request.getRequestDispatcher("teacher/teacherRegister.jsp").forward(request, response);
                return;
            }
            
            // Create new teacher
            Teacher teacher = new Teacher(name, email, password, courseId);
            if (teacherDAO.registerTeacher(teacher)) {
                request.setAttribute("successMessage", "Teacher registered successfully! You can now login.");
                request.getRequestDispatcher("teacher/teacherLogin.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("teacher/teacherRegister.jsp").forward(request, response);
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String page = request.getParameter("page");
        
        if (page != null) {
            if ("teacherRegister".equals(page)) {
                // Fetch courses for teacher registration
                List<Course> courses = courseDAO.getAllCourses();
                System.out.println("DEBUG: Loading courses for teacher registration");
                System.out.println("DEBUG: Found " + courses.size() + " courses");
                
                // Add default courses if none exist
                if (courses.isEmpty()) {
                    System.out.println("DEBUG: No courses found, adding default courses");
                    Course cs = new Course();
                    cs.setCourseName("Computer Science");
                    courseDAO.addCourse(cs);
                    
                    Course electronics = new Course();
                    electronics.setCourseName("Electronics");
                    courseDAO.addCourse(electronics);
                    
                    Course mechanical = new Course();
                    mechanical.setCourseName("Mechanical Engineering");
                    courseDAO.addCourse(mechanical);
                    
                    // Reload courses
                    courses = courseDAO.getAllCourses();
                    System.out.println("DEBUG: Added default courses, now have " + courses.size() + " courses");
                }
                
                for (Course course : courses) {
                    System.out.println("DEBUG: Course ID: " + course.getCourseId() + ", Name: " + course.getCourseName());
                }
                
                request.setAttribute("courses", courses);
                request.getRequestDispatcher("teacher/teacherRegister.jsp").forward(request, response);
            } else if ("adminRegister".equals(page)) {
                request.getRequestDispatcher("admin/adminRegister.jsp").forward(request, response);
            } else {
                response.sendRedirect("index.html");
            }
        } else {
            response.sendRedirect("index.html");
        }
    }
}