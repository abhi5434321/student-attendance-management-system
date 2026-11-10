package controller;

import dao.CourseDAO;
import dao.ScheduleDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.TeacherDAO;
import model.Course;
import model.Schedule;
import model.Student;
import model.Subject;
import model.Teacher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/schedule")
public class ScheduleServlet extends HttpServlet {
    private ScheduleDAO scheduleDAO = new ScheduleDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        
        if (userType == null || (!userType.equals("admin") && !userType.equals("teacher"))) {
            response.sendRedirect("../index.html");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("viewSchedule".equals(action)) {
            showScheduleDashboard(request, response);
        } else if ("viewByCourse".equals(action)) {
            viewScheduleByCourse(request, response);
        } else if ("viewBySubject".equals(action)) {
            viewScheduleBySubject(request, response);
        } else if ("viewByTeacher".equals(action)) {
            viewScheduleByTeacher(request, response);
        } else if ("viewStudentList".equals(action)) {
            showStudentListDashboard(request, response);
        } else if ("listByCourse".equals(action)) {
            viewStudentsByCourse(request, response);
        } else {
            showScheduleDashboard(request, response);
        }
    }

    private void showScheduleDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Schedule> schedules = scheduleDAO.getAllSchedules();
        List<Course> courses = courseDAO.getAllCourses();
        List<Subject> subjects = subjectDAO.getAllSubjects();
        List<Teacher> teachers = teacherDAO.getAllTeachers();
        
        request.setAttribute("schedules", schedules);
        request.setAttribute("courses", courses);
        request.setAttribute("subjects", subjects);
        request.setAttribute("teachers", teachers);
        request.getRequestDispatcher("/admin/scheduleDashboard.jsp").forward(request, response);
    }

    private void viewScheduleByCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String courseIdStr = request.getParameter("courseId");
            int courseId = Integer.parseInt(courseIdStr);
            
            List<Schedule> schedules = scheduleDAO.getSchedulesByCourse(courseId);
            Course course = courseDAO.getCourseById(courseId);
            
            request.setAttribute("schedules", schedules);
            request.setAttribute("course", course);
            request.getRequestDispatcher("/admin/viewScheduleByCourse.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course ID");
            showScheduleDashboard(request, response);
        }
    }

    private void viewScheduleBySubject(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String subjectIdStr = request.getParameter("subjectId");
            int subjectId = Integer.parseInt(subjectIdStr);
            
            List<Schedule> schedules = scheduleDAO.getSchedulesBySubject(subjectId);
            
            request.setAttribute("schedules", schedules);
            request.setAttribute("subjectId", subjectId);
            request.getRequestDispatcher("/admin/viewScheduleBySubject.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid subject ID");
            showScheduleDashboard(request, response);
        }
    }

    private void viewScheduleByTeacher(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String teacherIdStr = request.getParameter("teacherId");
            int teacherId = Integer.parseInt(teacherIdStr);
            
            List<Schedule> schedules = scheduleDAO.getSchedulesByTeacher(teacherId);
            
            request.setAttribute("schedules", schedules);
            request.setAttribute("teacherId", teacherId);
            request.getRequestDispatcher("/admin/viewScheduleByTeacher.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid teacher ID");
            showScheduleDashboard(request, response);
        }
    }

    private void showStudentListDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/admin/studentListDashboard.jsp").forward(request, response);
    }

    private void viewStudentsByCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String courseIdStr = request.getParameter("courseId");
            int courseId = Integer.parseInt(courseIdStr);
            
            List<Student> students = studentDAO.getStudentsByCourse(courseId);
            Course course = courseDAO.getCourseById(courseId);
            
            request.setAttribute("students", students);
            request.setAttribute("course", course);
            request.getRequestDispatcher("/admin/viewStudentsByCourse.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course ID");
            showStudentListDashboard(request, response);
        }
    }
}