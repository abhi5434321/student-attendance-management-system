package controller;

import dao.StudentDAO;
import dao.CourseDAO;
import model.Student;
import model.Course;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();
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
            viewStudents(request, response);
        } else if ("add".equals(action)) {
            showAddForm(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else {
            viewStudents(request, response);
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
            addStudent(request, response);
        } else if ("update".equals(action)) {
            updateStudent(request, response);
        } else if ("delete".equals(action)) {
            deleteStudent(request, response);
        } else {
            response.sendRedirect("?action=view");
        }
    }

    private void viewStudents(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Student> students = studentDAO.getAllStudents();
        request.setAttribute("students", students);
        request.getRequestDispatcher("/admin/viewStudents.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/admin/addStudent.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            
            // In a real implementation, we would fetch the specific student from the database
            // For now, we'll just forward to the edit page with the ID
            List<Student> allStudents = studentDAO.getAllStudents();
            Student studentToEdit = null;
            
            for (Student student : allStudents) {
                if (student.getStudentId() == studentId) {
                    studentToEdit = student;
                    break;
                }
            }
            
            if (studentToEdit != null) {
                List<Course> courses = courseDAO.getAllCourses();
                request.setAttribute("student", studentToEdit);
                request.setAttribute("courses", courses);
                request.getRequestDispatcher("/admin/editStudent.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Student not found!");
                viewStudents(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid student ID!");
            viewStudents(request, response);
        }
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String rollNo = request.getParameter("rollNo");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        try {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            int semester = Integer.parseInt(request.getParameter("semester"));
            
            Student student = new Student();
            student.setRollNo(rollNo);
            student.setName(name);
            student.setEmail(email);
            student.setPhone(phone);
            student.setGender(gender);
            student.setCourseId(courseId);
            student.setSemester(semester);
            
            boolean result = studentDAO.addStudent(student);
            
            if (result) {
                request.setAttribute("successMessage", "Student added successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to add student!");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course ID or semester!");
        }
        
        viewStudents(request, response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String rollNo = request.getParameter("rollNo");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            int semester = Integer.parseInt(request.getParameter("semester"));
            
            boolean result = studentDAO.updateStudent(studentId, rollNo, name, email, phone, gender, courseId, semester);
            
            if (result) {
                request.setAttribute("successMessage", "Student updated successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to update student!");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid student, course ID or semester!");
        }
        
        viewStudents(request, response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            boolean result = studentDAO.deleteStudent(studentId);
            
            if (result) {
                request.setAttribute("successMessage", "Student deleted successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to delete student!");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid student ID!");
        }
        
        viewStudents(request, response);
    }
}