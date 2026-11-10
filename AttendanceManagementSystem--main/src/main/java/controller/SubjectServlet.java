package controller;

import dao.SubjectDAO;
import dao.CourseDAO;
import model.Subject;
import model.Course;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/subjects")
public class SubjectServlet extends HttpServlet {
    private SubjectDAO subjectDAO = new SubjectDAO();
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
            viewSubjects(request, response);
        } else if ("add".equals(action)) {
            showAddForm(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else {
            viewSubjects(request, response);
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
            addSubject(request, response);
        } else if ("update".equals(action)) {
            updateSubject(request, response);
        } else if ("delete".equals(action)) {
            deleteSubject(request, response);
        } else {
            response.sendRedirect("?action=view");
        }
    }

    private void viewSubjects(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Subject> subjects = subjectDAO.getAllSubjects();
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/admin/viewSubjects.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/admin/addSubject.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            
            // Get the subject to edit
            List<Subject> allSubjects = subjectDAO.getAllSubjects();
            Subject subjectToEdit = null;
            
            for (Subject subject : allSubjects) {
                if (subject.getSubjectId() == subjectId) {
                    subjectToEdit = subject;
                    break;
                }
            }
            
            if (subjectToEdit != null) {
                List<Course> courses = courseDAO.getAllCourses();
                request.setAttribute("subject", subjectToEdit);
                request.setAttribute("courses", courses);
                request.getRequestDispatcher("/admin/editSubject.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Subject not found!");
                viewSubjects(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid subject ID!");
            viewSubjects(request, response);
        }
    }

    private void addSubject(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String subjectName = request.getParameter("subjectName");
        try {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            
            Subject subject = new Subject();
            subject.setSubjectName(subjectName);
            subject.setCourseId(courseId);
            
            boolean result = subjectDAO.addSubject(subject);
            
            if (result) {
                request.setAttribute("successMessage", "Subject added successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to add subject!");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course ID!");
        }
        
        viewSubjects(request, response);
    }

    private void updateSubject(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            String subjectName = request.getParameter("subjectName");
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            
            boolean result = subjectDAO.updateSubject(subjectId, subjectName, courseId);
            
            if (result) {
                request.setAttribute("successMessage", "Subject updated successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to update subject!");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid subject or course ID!");
        }
        
        viewSubjects(request, response);
    }

    private void deleteSubject(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            boolean result = subjectDAO.deleteSubject(subjectId);
            
            if (result) {
                request.setAttribute("successMessage", "Subject deleted successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to delete subject!");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid subject ID!");
        }
        
        viewSubjects(request, response);
    }
}