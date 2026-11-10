package controller;

import dao.AttendanceDAO;
import dao.CourseDAO;
import dao.SubjectDAO;
import model.Attendance;
import model.Course;
import model.Subject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/attendanceViews")
public class AttendanceViewServlet extends HttpServlet {
    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();

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
        
        if ("dayWise".equals(action)) {
            showDayWiseForm(request, response);
        } else if ("subjectWise".equals(action)) {
            showSubjectWiseForm(request, response);
        } else if ("viewDayWise".equals(action)) {
            viewDayWiseAttendance(request, response);
        } else if ("viewSubjectWise".equals(action)) {
            viewSubjectWiseAttendance(request, response);
        } else {
            showViewDashboard(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        
        if (userType == null || (!userType.equals("admin") && !userType.equals("teacher"))) {
            response.sendRedirect("../index.html");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("viewDayWise".equals(action)) {
            viewDayWiseAttendance(request, response);
        } else if ("viewSubjectWise".equals(action)) {
            viewSubjectWiseAttendance(request, response);
        } else {
            response.sendRedirect("?action=dayWise");
        }
    }

    private void showViewDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/attendanceViewDashboard.jsp").forward(request, response);
    }

    private void showDayWiseForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        List<Subject> subjects = subjectDAO.getAllSubjects();
        request.setAttribute("courses", courses);
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/admin/dayWiseForm.jsp").forward(request, response);
    }

    private void showSubjectWiseForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        List<Subject> subjects = subjectDAO.getAllSubjects();
        request.setAttribute("courses", courses);
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/admin/subjectWiseForm.jsp").forward(request, response);
    }

    private void viewDayWiseAttendance(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String dateStr = request.getParameter("date");
            String courseIdStr = request.getParameter("courseId");
            String subjectIdStr = request.getParameter("subjectId");
            
            Date date = Date.valueOf(dateStr);
            int courseId = courseIdStr != null && !courseIdStr.isEmpty() ? Integer.parseInt(courseIdStr) : 0;
            int subjectId = subjectIdStr != null && !subjectIdStr.isEmpty() ? Integer.parseInt(subjectIdStr) : 0;
            
            List<Attendance> attendances = attendanceDAO.getDailyAttendance(date);
            
            // Filter by course or subject if specified
            if (courseId > 0) {
                attendances.removeIf(attendance -> attendance.getCourseId() != courseId);
            }
            if (subjectId > 0) {
                attendances.removeIf(attendance -> attendance.getSubjectId() != subjectId);
            }
            
            request.setAttribute("attendances", attendances);
            request.setAttribute("viewDate", date);
            request.setAttribute("courseId", courseId);
            request.setAttribute("subjectId", subjectId);
            
            request.getRequestDispatcher("/admin/viewDayWise.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid date format or parameters: " + e.getMessage());
            showDayWiseForm(request, response);
        }
    }

    private void viewSubjectWiseAttendance(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String subjectIdStr = request.getParameter("subjectId");
            String dateFromStr = request.getParameter("dateFrom");
            String dateToStr = request.getParameter("dateTo");
            
            int subjectId = Integer.parseInt(subjectIdStr);
            
            List<Attendance> attendances;
            if (dateFromStr != null && !dateFromStr.isEmpty() && dateToStr != null && !dateToStr.isEmpty()) {
                // For date range filtering, we'll get all records and filter in memory
                attendances = attendanceDAO.getAttendanceBySubject(subjectId);
                
                Date dateFrom = Date.valueOf(dateFromStr);
                Date dateTo = Date.valueOf(dateToStr);
                
                // Filter by date range
                attendances.removeIf(attendance -> 
                    attendance.getDate().before(dateFrom) || attendance.getDate().after(dateTo));
            } else {
                attendances = attendanceDAO.getAttendanceBySubject(subjectId);
            }
            
            Subject subject = subjectDAO.getSubjectById(subjectId);
            
            request.setAttribute("attendances", attendances);
            request.setAttribute("subject", subject);
            request.setAttribute("dateFrom", dateFromStr);
            request.setAttribute("dateTo", dateToStr);
            
            request.getRequestDispatcher("/admin/viewSubjectWise.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid parameters: " + e.getMessage());
            showSubjectWiseForm(request, response);
        }
    }
}