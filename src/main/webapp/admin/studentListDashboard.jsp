<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student List Management - Attendance System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            max-width: 1200px;
            margin: 0 auto;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 15px;
            border-bottom: 2px solid #eee;
        }
        .welcome {
            font-size: 24px;
            font-weight: bold;
            color: #333;
        }
        .btn {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            margin-left: 10px;
        }
        .btn:hover {
            opacity: 0.9;
        }
        .btn-schedule {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }
        .btn-student {
            background: linear-gradient(135deg, #a8e063 0%, #56ab2f 100%);
        }
        .btn-home {
            background: linear-gradient(135deg, #56ab2f 0%, #a8e063 100%);
        }
        .btn-logout {
            background: linear-gradient(135deg, #ff6b6b 0%, #ff8e8e 100%);
        }
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            background-color: #fafafa;
        }
        .card h3 {
            margin-top: 0;
            color: #007bff;
        }
        .btn-stud {
            display: inline-block;
            padding: 10px 15px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 5px 0;
        }
        .btn-stud:hover {
            background-color: #218838;
        }
        .form-group {
            margin-bottom: 15px;
        }
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .student-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .student-table th, .student-table td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .student-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .student-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Student List Management</div>
            <div>
                <a href="<c:url value='/schedule?action=viewSchedule' />" class="btn btn-schedule">Schedule</a>
            </div>
        </div>
        
        <h2>View Student Lists</h2>
        <p>Select a course to view its enrolled students</p>
        
        <div class="dashboard-grid">
            <div class="card">
                <h3>👥 View Students by Course</h3>
                <p>View all students enrolled in a specific course</p>
                <form method="get" action="<c:url value='/schedule' />" style="margin-top: 10px;">
                    <input type="hidden" name="action" value="listByCourse">
                    <div class="form-group">
                        <select name="courseId" required>
                            <option value="">Select Course</option>
                            <c:forEach var="course" items="${courses}">
                                <option value="${course.courseId}">${course.courseName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn-stud">View Students</button>
                </form>
            </div>
            
            <div class="card">
                <h3>📋 All Students</h3>
                <p>View all students in the system</p>
                <a href="<c:url value='/students?action=view' />" class="btn-stud">View All Students</a>
            </div>
            
            <div class="card">
                <h3>📊 Student Statistics</h3>
                <p>View student enrollment statistics</p>
                <a href="#" class="btn-stud" style="background-color: #6c757d; pointer-events: none; opacity: 0.6;">Coming Soon</a>
            </div>
        </div>
        
        <c:if test="${not empty courses}">
            <h3>Enrollment Overview</h3>
            <div class="table-container">
                <table class="student-table">
                    <thead>
                        <tr>
                            <th>Course</th>
                            <th>Total Students</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="course" items="${courses}">
                            <tr>
                                <td>${course.courseName}</td>
                                <td>
                                    <c:set var="studentCount" value="0" />
                                    <c:forEach var="item" items="${courses}">
                                        <c:if test="${item.courseId == course.courseId}">
                                            <c:set var="studentCount" value="${studentCount + 1}" />
                                        </c:if>
                                    </c:forEach>
                                    ${studentCount}
                                </td>
                                <td>
                                    <a href="<c:url value='/schedule'>
                                        <c:param name='action' value='listByCourse'/>
                                        <c:param name='courseId' value='${course.courseId}'/>
                                    </c:url>" class="btn-stud" style="padding: 5px 10px; font-size: 12px;">View</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</body>
</html>