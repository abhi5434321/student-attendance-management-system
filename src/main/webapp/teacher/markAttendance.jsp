<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mark Attendance - Attendance System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
        }
        
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #eee;
        }
        
        .welcome {
            font-size: 28px;
            font-weight: 600;
            color: #2c3e50;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .welcome::before {
            content: '📝';
            font-size: 1.5em;
        }
        
        .nav-buttons {
            display: flex;
            gap: 15px;
        }
        
        .btn {
            padding: 12px 24px;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            font-size: 14px;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 15px rgba(0,0,0,0.2);
        }
        
        .btn-dashboard {
            background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
            color: white;
        }
        
        .btn-logout {
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
            color: white;
        }
        
        .form-container {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
            margin-bottom: 25px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #2c3e50;
            font-size: 14px;
        }
        
        select, input {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            box-sizing: border-box;
            transition: border-color 0.3s ease;
        }
        
        select:focus, input:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
        }
        
        .btn-primary {
            background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
            margin-right: 10px;
        }
        
        .btn-primary:hover {
            background: linear-gradient(135deg, #2980b9 0%, #3498db 100%);
            transform: translateY(-2px);
            box-shadow: 0 8px 15px rgba(52, 152, 219, 0.4);
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }
        
        th, td {
            border: 1px solid #e0e0e0;
            padding: 15px;
            text-align: left;
        }
        
        th {
            background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
            color: white;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 12px;
            letter-spacing: 0.5px;
        }
        
        tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        
        tr:hover {
            background-color: #f1f3f4;
        }
        
        .attendance-present {
            background-color: #d4edda;
            color: #155724;
            padding: 5px 10px;
            border-radius: 4px;
            font-weight: 500;
        }
        
        .attendance-absent {
            background-color: #f8d7da;
            color: #721c24;
            padding: 5px 10px;
            border-radius: 4px;
            font-weight: 500;
        }
        
        .nav-links {
            margin-top: 30px;
            text-align: center;
            display: flex;
            justify-content: center;
            gap: 15px;
            flex-wrap: wrap;
        }
        
        .nav-link {
            display: inline-block;
            background: linear-gradient(135deg, #95a5a6 0%, #7f8c8d 100%);
            color: white;
            padding: 12px 20px;
            text-decoration: none;
            border-radius: 8px;
            margin: 0 5px;
            transition: all 0.3s ease;
            font-weight: 500;
        }
        
        .nav-link:hover {
            background: linear-gradient(135deg, #7f8c8d 0%, #95a5a6 100%);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(127, 140, 141, 0.3);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Mark Attendance</div>
            <div class="nav-buttons">
                <a href="<c:url value='/teacher/dashboard.jsp' />" class="btn btn-dashboard">Dashboard</a>
            </div>
        </div>
        
        <h2 style="color: #2c3e50; margin-bottom: 20px; font-size: 24px;">Mark Daily Attendance</h2>
        <p style="color: #7f8c8d; margin-bottom: 30px; line-height: 1.6;">Select course, subject, and date to mark attendance for students</p>
        
        <c:if test="${not empty requestScope.errorMessage}">
            <div style="background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 8px; margin-bottom: 20px; border: 1px solid #f5c6cb;">
                ${requestScope.errorMessage}
            </div>
        </c:if>
        
        <c:if test="${not empty requestScope.successMessage}">
            <div style="background-color: #d4edda; color: #155724; padding: 15px; border-radius: 8px; margin-bottom: 20px; border: 1px solid #c3e6cb;">
                ${requestScope.successMessage}
            </div>
        </c:if>
        
        <div class="form-container">
            <form method="post" action="<c:url value='/markAttendance' />">
                <input type="hidden" name="action" value="loadStudents">
                <div class="form-group">
                    <label for="course">Select Course:</label>
                    <select id="course" name="courseId" required>
                        <option value="">-- Select Course --</option>
                        <c:forEach var="course" items="${requestScope.courses}">
                            <option value="${course.courseId}">${course.courseName}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="subject">Select Subject:</label>
                    <select id="subject" name="subjectId" required>
                        <option value="">-- Select Subject --</option>
                        <c:forEach var="subject" items="${requestScope.subjects}">
                            <option value="${subject.subjectId}">${subject.subjectName}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="date">Date:</label>
                    <input type="date" id="date" name="date" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" required>
                </div>
                
                <button type="submit" class="btn-primary">Load Students</button>
                <a href="<c:url value='/teacher/dashboard.jsp' />" class="nav-link">Cancel</a>
            </form>
        </div>
        
        <c:if test="${not empty requestScope.students}">
            <h3 style="color: #2c3e50; margin: 25px 0 15px 0;">Students List</h3>
            <form method="post" action="<c:url value='/markAttendance' />">
                <input type="hidden" name="action" value="saveAttendance">
                <input type="hidden" name="courseId" value="${requestScope.courseId}">
                <input type="hidden" name="subjectId" value="${requestScope.subjectId}">
                <input type="hidden" name="date" value="${requestScope.date}">
                <table>
                    <thead>
                        <tr>
                            <th>Roll No</th>
                            <th>Student Name</th>
                            <th>Attendance</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${requestScope.students}">
                            <tr>
                                <td>${student.rollNo}</td>
                                <td>${student.name}</td>
                                <td>
                                    <select name="attendance_${student.studentId}" required>
                                        <option value="">-- Select --</option>
                                        <option value="Present">Present</option>
                                        <option value="Absent">Absent</option>
                                    </select>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <div style="margin-top: 20px; display: flex; gap: 10px;">
                    <button type="submit" class="btn-primary">Save Attendance</button>
                    <a href="<c:url value='/teacher/dashboard.jsp' />" class="nav-link">Back to Dashboard</a>
                </div>
            </form>
        </c:if>
        
        
    </div>
</body>
</html>