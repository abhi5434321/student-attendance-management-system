package controller;

import dao.CourseDAO;
import model.Course;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {
    private CourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        
        if (userType == null || !userType.equals("admin")) {
            response.sendRedirect("../index.html");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("view".equals(action) || action == null) {
            viewCourses(request, response);
        } else if ("add".equals(action)) {
            showAddForm(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else {
            viewCourses(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        
        if (userType == null || !userType.equals("admin")) {
            response.sendRedirect("../index.html");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            addCourse(request, response);
        } else if ("update".equals(action)) {
            updateCourse(request, response);
        } else if ("delete".equals(action)) {
            deleteCourse(request, response);
        } else {
            response.sendRedirect("?action=view");
        }
    }

    private void viewCourses(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/admin/viewCourses.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/addCourse.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            
            // Get the course directly from the database
            List<Course> allCourses = courseDAO.getAllCourses();
            Course courseToEdit = null;
            
            for (Course course : allCourses) {
                if (course.getCourseId() == courseId) {
                    courseToEdit = course;
                    break;
                }
            }
            
            if (courseToEdit != null) {
                request.setAttribute("course", courseToEdit);
                request.getRequestDispatcher("/admin/editCourse.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Course not found!");
                viewCourses(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course ID!");
            viewCourses(request, response);
        }
    }

    private void addCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String courseName = request.getParameter("courseName");
        
        Course course = new Course();
        course.setCourseName(courseName);
        
        boolean result = courseDAO.addCourse(course);
        
        if (result) {
            request.setAttribute("successMessage", "Course added successfully!");
        } else {
            request.setAttribute("errorMessage", "Failed to add course!");
        }
        
        viewCourses(request, response);
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            String courseName = request.getParameter("courseName");
            
            boolean result = courseDAO.updateCourse(courseId, courseName);
            
            if (result) {
                request.setAttribute("successMessage", "Course updated successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to update course!");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course ID!");
        }
        
        viewCourses(request, response);
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            boolean result = courseDAO.deleteCourse(courseId);
            
            if (result) {
                request.setAttribute("successMessage", "Course deleted successfully!");
            } else {
                // Check if it failed due to dependencies
                if (courseDAO.hasDependentRecords(courseId)) {
                    request.setAttribute("errorMessage", "Cannot delete course because it has associated teachers, subjects, students, or attendance records. Please remove all related records first.");
                } else {
                    request.setAttribute("errorMessage", "Failed to delete course!");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course ID!");
        }
        
        viewCourses(request, response);
    }
}