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
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;

@WebServlet("/reports")
public class AttendanceReportServlet extends HttpServlet {
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
        
        if ("daily".equals(action)) {
            showDailyReportForm(request, response);
        } else if ("monthly".equals(action)) {
            showMonthlyReportForm(request, response);
        } else if ("student".equals(action)) {
            showStudentReportForm(request, response);
        } else if ("pdf".equals(action)) {
            exportPdf(request, response);
        } else if ("viewDaily".equals(action)) {
            viewDailyReport(request, response);
        } else if ("viewMonthly".equals(action)) {
            viewMonthlyReport(request, response);
        } else if ("viewStudent".equals(action)) {
            viewStudentReport(request, response);
        } else {
            showReportDashboard(request, response);
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
        
        if ("viewDaily".equals(action)) {
            viewDailyReport(request, response);
        } else if ("viewMonthly".equals(action)) {
            viewMonthlyReport(request, response);
        } else if ("viewStudent".equals(action)) {
            viewStudentReport(request, response);
        } else {
            response.sendRedirect("?action=daily");
        }
    }

    private void showReportDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/reportDashboard.jsp").forward(request, response);
    }

    private void showDailyReportForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        List<Subject> subjects = subjectDAO.getAllSubjects();
        request.setAttribute("courses", courses);
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/admin/dailyReport.jsp").forward(request, response);
    }

    private void showMonthlyReportForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        List<Subject> subjects = subjectDAO.getAllSubjects();
        request.setAttribute("courses", courses);
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/admin/monthlyReport.jsp").forward(request, response);
    }

    private void viewDailyReport(HttpServletRequest request, HttpServletResponse response) 
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
            request.setAttribute("reportDate", date);
            request.setAttribute("courseId", courseId);
            request.setAttribute("subjectId", subjectId);
            
            request.getRequestDispatcher("/admin/viewDailyReport.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid date format or parameters: " + e.getMessage());
            showDailyReportForm(request, response);
        }
    }

    private void viewMonthlyReport(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String monthStr = request.getParameter("month");
            String yearStr = request.getParameter("year");
            String courseIdStr = request.getParameter("courseId");
            String subjectIdStr = request.getParameter("subjectId");
            
            int month = Integer.parseInt(monthStr);
            int year = Integer.parseInt(yearStr);
            int courseId = courseIdStr != null && !courseIdStr.isEmpty() ? Integer.parseInt(courseIdStr) : 0;
            int subjectId = subjectIdStr != null && !subjectIdStr.isEmpty() ? Integer.parseInt(subjectIdStr) : 0;
            
            List<Attendance> attendances = attendanceDAO.getMonthlyAttendance(month, year);
            
            // Filter by course or subject if specified
            if (courseId > 0) {
                attendances.removeIf(attendance -> attendance.getCourseId() != courseId);
            }
            if (subjectId > 0) {
                attendances.removeIf(attendance -> attendance.getSubjectId() != subjectId);
            }
            
            request.setAttribute("attendances", attendances);
            request.setAttribute("month", month);
            request.setAttribute("year", year);
            request.setAttribute("courseId", courseId);
            request.setAttribute("subjectId", subjectId);
            
            request.getRequestDispatcher("/admin/viewMonthlyReport.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid parameters: " + e.getMessage());
            showMonthlyReportForm(request, response);
        }
    }

    private void exportPdf(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("reportType");
        String dateStr = request.getParameter("date");
        String monthStr = request.getParameter("month");
        String yearStr = request.getParameter("year");
        String courseIdStr = request.getParameter("courseId");
        String subjectIdStr = request.getParameter("subjectId");
        
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=attendance_report.pdf");
            
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            
            document.add(new Paragraph("Attendance Report")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20));
            document.add(new Paragraph(" ")); // Empty line
            
            if ("daily".equals(action) && dateStr != null) {
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
                
                document.add(new Paragraph("Daily Report for: " + date.toString()));
                
                if (!attendances.isEmpty()) {
                    Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 2, 1, 2, 1, 1})).setWidth(UnitValue.createPercentValue(100));
                    table.addHeaderCell(new Cell().add(new Paragraph("S.No.")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Roll No")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Student Name")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Course")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Subject")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Status")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Date")));
                    
                    for (int i = 0; i < attendances.size(); i++) {
                        Attendance att = attendances.get(i);
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1))));
                        table.addCell(new Cell().add(new Paragraph(att.getRollNo())));
                        table.addCell(new Cell().add(new Paragraph(att.getStudentName())));
                        table.addCell(new Cell().add(new Paragraph(att.getCourseName())));
                        table.addCell(new Cell().add(new Paragraph(att.getSubjectName())));
                        table.addCell(new Cell().add(new Paragraph(att.getStatus())));
                        table.addCell(new Cell().add(new Paragraph(att.getDate().toString())));
                    }
                    document.add(table);
                } else {
                    document.add(new Paragraph("No attendance records found for the selected criteria."));
                }
            } else if ("subjectWise".equals(action)) {
                int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                String dateFromStr = request.getParameter("dateFrom");
                String dateToStr = request.getParameter("dateTo");
                
                List<Attendance> attendances = attendanceDAO.getAttendanceBySubject(subjectId);
                
                // Filter by date range if specified
                if (dateFromStr != null && !dateFromStr.isEmpty() && dateToStr != null && !dateToStr.isEmpty()) {
                    java.sql.Date dateFrom = java.sql.Date.valueOf(dateFromStr);
                    java.sql.Date dateTo = java.sql.Date.valueOf(dateToStr);
                    
                    attendances.removeIf(attendance -> 
                        attendance.getDate().before(dateFrom) || attendance.getDate().after(dateTo));
                }
                
                Subject subject = subjectDAO.getSubjectById(subjectId);
                document.add(new Paragraph("Subject-wise Report for: " + subject.getSubjectName() + " (" + subject.getCourseName() + ")"));
                
                if (!attendances.isEmpty()) {
                    Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 2, 2, 1})).setWidth(UnitValue.createPercentValue(100));
                    table.addHeaderCell(new Cell().add(new Paragraph("S.No.")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Date")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Roll No")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Student Name")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Course")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Status")));
                    
                    for (int i = 0; i < attendances.size(); i++) {
                        Attendance att = attendances.get(i);
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1))));
                        table.addCell(new Cell().add(new Paragraph(att.getDate().toString())));
                        table.addCell(new Cell().add(new Paragraph(att.getRollNo())));
                        table.addCell(new Cell().add(new Paragraph(att.getStudentName())));
                        table.addCell(new Cell().add(new Paragraph(att.getCourseName())));
                        table.addCell(new Cell().add(new Paragraph(att.getStatus())));
                    }
                    document.add(table);
                } else {
                    document.add(new Paragraph("No attendance records found for the selected criteria."));
                }
            } else if ("student".equals(action)) {
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                String dateFromStr = request.getParameter("dateFrom");
                String dateToStr = request.getParameter("dateTo");
                
                List<Attendance> attendances = attendanceDAO.getAttendanceByStudent(studentId);
                
                // Filter by date range if specified
                if (dateFromStr != null && !dateFromStr.isEmpty() && dateToStr != null && !dateToStr.isEmpty()) {
                    java.sql.Date dateFrom = java.sql.Date.valueOf(dateFromStr);
                    java.sql.Date dateTo = java.sql.Date.valueOf(dateToStr);
                    
                    attendances.removeIf(attendance -> 
                        attendance.getDate().before(dateFrom) || attendance.getDate().after(dateTo));
                }
                
                // Get student info
                String studentName = "";
                String rollNo = "";
                if (!attendances.isEmpty()) {
                    studentName = attendances.get(0).getStudentName();
                    rollNo = attendances.get(0).getRollNo();
                }
                
                document.add(new Paragraph("Student-wise Report for: " + studentName + " (Roll No: " + rollNo + ")"));
                
                if (!attendances.isEmpty()) {
                    Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 2, 2, 2, 1, 1})).setWidth(UnitValue.createPercentValue(100));
                    table.addHeaderCell(new Cell().add(new Paragraph("S.No.")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Date")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Roll No")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Student Name")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Course")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Subject")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Status")));
                    
                    for (int i = 0; i < attendances.size(); i++) {
                        Attendance att = attendances.get(i);
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1))));
                        table.addCell(new Cell().add(new Paragraph(att.getDate().toString())));
                        table.addCell(new Cell().add(new Paragraph(att.getRollNo())));
                        table.addCell(new Cell().add(new Paragraph(att.getStudentName())));
                        table.addCell(new Cell().add(new Paragraph(att.getCourseName())));
                        table.addCell(new Cell().add(new Paragraph(att.getSubjectName())));
                        table.addCell(new Cell().add(new Paragraph(att.getStatus())));
                    }
                    document.add(table);
                } else {
                    document.add(new Paragraph("No attendance records found for the selected criteria."));
                }
            } else if ("monthly".equals(action) && monthStr != null && yearStr != null) {
                int month = Integer.parseInt(monthStr);
                int year = Integer.parseInt(yearStr);
                int courseId = courseIdStr != null && !courseIdStr.isEmpty() ? Integer.parseInt(courseIdStr) : 0;
                int subjectId = subjectIdStr != null && !subjectIdStr.isEmpty() ? Integer.parseInt(subjectIdStr) : 0;
                
                List<Attendance> attendances = attendanceDAO.getMonthlyAttendance(month, year);
                
                // Filter by course or subject if specified
                if (courseId > 0) {
                    attendances.removeIf(attendance -> attendance.getCourseId() != courseId);
                }
                if (subjectId > 0) {
                    attendances.removeIf(attendance -> attendance.getSubjectId() != subjectId);
                }
                
                String monthName = getMonthName(month);
                document.add(new Paragraph("Monthly Report for: " + monthName + ", " + year));
                
                if (!attendances.isEmpty()) {
                    Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 2, 2, 2, 1})).setWidth(UnitValue.createPercentValue(100));
                    table.addHeaderCell(new Cell().add(new Paragraph("S.No.")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Date")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Roll No")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Student Name")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Course")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Subject")));
                    table.addHeaderCell(new Cell().add(new Paragraph("Status")));
                    
                    for (int i = 0; i < attendances.size(); i++) {
                        Attendance att = attendances.get(i);
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1))));
                        table.addCell(new Cell().add(new Paragraph(att.getDate().toString())));
                        table.addCell(new Cell().add(new Paragraph(att.getRollNo())));
                        table.addCell(new Cell().add(new Paragraph(att.getStudentName())));
                        table.addCell(new Cell().add(new Paragraph(att.getCourseName())));
                        table.addCell(new Cell().add(new Paragraph(att.getSubjectName())));
                        table.addCell(new Cell().add(new Paragraph(att.getStatus())));
                    }
                    document.add(table);
                } else {
                    document.add(new Paragraph("No attendance records found for the selected criteria."));
                }
            } else {
                document.add(new Paragraph("Invalid report parameters."));
            }
            
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error generating PDF: " + e.getMessage());
        }
    }
    
    private void showStudentReportForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);
        
        // Also fetch all students for the dropdown
        List<model.Student> students = new dao.StudentDAO().getAllStudents();
        request.setAttribute("students", students);
        
        request.getRequestDispatcher("/admin/studentReport.jsp").forward(request, response);
    }
    
    private void viewStudentReport(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String studentIdStr = request.getParameter("studentId");
            String dateFromStr = request.getParameter("dateFrom");
            String dateToStr = request.getParameter("dateTo");
                
            int studentId = Integer.parseInt(studentIdStr);
                
            List<Attendance> attendances = attendanceDAO.getAttendanceByStudent(studentId);
                
            // Filter by date range if specified
            if (dateFromStr != null && !dateFromStr.isEmpty() && dateToStr != null && !dateToStr.isEmpty()) {
                java.sql.Date dateFrom = java.sql.Date.valueOf(dateFromStr);
                java.sql.Date dateTo = java.sql.Date.valueOf(dateToStr);
                    
                attendances.removeIf(attendance -> 
                    attendance.getDate().before(dateFrom) || attendance.getDate().after(dateTo));
            }
                
            request.setAttribute("attendances", attendances);
            request.setAttribute("studentId", studentId);
            request.setAttribute("dateFrom", dateFromStr);
            request.setAttribute("dateTo", dateToStr);
                
            request.getRequestDispatcher("/admin/viewStudentReport.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid parameters: " + e.getMessage());
            showStudentReportForm(request, response);
        }
    }
    
    private String getMonthName(int month) {
        switch (month) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "Unknown";
        }
    }
}