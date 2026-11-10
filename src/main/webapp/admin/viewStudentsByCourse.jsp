<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Students in ${course.courseName} - Attendance System</title>
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
        .btn-student {
            background: linear-gradient(135deg, #a8e063 0%, #56ab2f 100%);
        }
        .btn-home {
            background: linear-gradient(135deg, #56ab2f 0%, #a8e063 100%);
        }
        .btn-logout {
            background: linear-gradient(135deg, #ff6b6b 0%, #ff8e8e 100%);
        }
        .student-info {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border-left: 4px solid #007bff;
        }
        .table-container {
            overflow-x: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #007bff;
            color: white;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .error {
            color: #e74c3c;
            background: #ffeaea;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            border-left: 4px solid #e74c3c;
        }
        .action-btn {
            display: inline-block;
            padding: 5px 10px;
            background-color: #17a2b8;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 12px;
            margin: 0 2px;
        }
        .action-btn:hover {
            background-color: #138496;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin: 20px 0;
        }
        .stat-card {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            text-align: center;
            border: 1px solid #dee2e6;
        }
        .stat-value {
            font-size: 24px;
            font-weight: bold;
            color: #007bff;
        }
        .stat-label {
            font-size: 14px;
            color: #6c757d;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Students in ${course.courseName}</div>
            <div>
                <a href="<c:url value='/schedule?action=viewStudentList' />" class="btn btn-student">Student List</a>
            </div>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        
        <div class="student-info">
            <strong>Course:</strong> ${course.courseName}
            <span style="float: right;"><strong>Total Students:</strong> ${students.size()}</span>
        </div>
        
        <!-- Statistics -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-value">${students.size()}</div>
                <div class="stat-label">Total Students</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">
                    <c:set var="maleCount" value="0" />
                    <c:forEach var="student" items="${students}">
                        <c:if test="${student.gender == 'Male'}">
                            <c:set var="maleCount" value="${maleCount + 1}" />
                        </c:if>
                    </c:forEach>
                    ${maleCount}
                </div>
                <div class="stat-label">Male Students</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">
                    <c:set var="femaleCount" value="${students.size() - maleCount}" />
                    ${femaleCount}
                </div>
                <div class="stat-label">Female Students</div>
            </div>
        </div>
        
        <div style="margin-bottom: 20px;">
            <a href="<c:url value='/schedule?action=viewStudentList' />" class="btn">New Search</a>
            <a href="<c:url value='/students?action=add&courseId=${course.courseId}' />" class="btn" style="background-color: #28a745;">Add Student</a>
        </div>
        
        <c:choose>
            <c:when test="${empty students}">
                <div style="text-align: center; padding: 20px; color: #666;">
                    <h3>No students found for this course.</h3>
                    <p>There are currently no students enrolled in ${course.courseName}.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>S.No.</th>
                                <th>Roll No</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Gender</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="student" items="${students}" varStatus="loop">
                                <tr>
                                    <td>${loop.count}</td>
                                    <td>${student.rollNo}</td>
                                    <td>${student.name}</td>
                                    <td>${student.email}</td>
                                    <td>${student.phone}</td>
                                    <td>${student.gender}</td>
                                    <td>
                                        <a href="<c:url value='/students'>
                                            <c:param name='action' value='showEditForm'/>
                                            <c:param name='id' value='${student.studentId}'/>
                                        </c:url>" class="action-btn">Edit</a>
                                        <a href="<c:url value='/students'>
                                            <c:param name='action' value='delete'/>
                                            <c:param name='id' value='${student.studentId}'/>
                                        </c:url>" class="action-btn" style="background-color: #dc3545;" onclick="return confirm('Are you sure you want to delete this student?')">Delete</a>
                                        <a href="<c:url value='/reports'>
                                            <c:param name='action' value='student'/>
                                            <c:param name='studentId' value='${student.studentId}'/>
                                        </c:url>" class="action-btn" style="background-color: #ffc107; color: black;">View Report</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
        

    </div>
</body>
</html>