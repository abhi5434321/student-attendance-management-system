<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Students - Attendance System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
            content: '👥';
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
        
        .btn-add {
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
            color: white;
        }
        
        .btn-home {
            background: linear-gradient(135deg, #9b59b6 0%, #8e44ad 100%);
            color: white;
        }
        
        .btn-logout {
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
            color: white;
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
        
        .actions {
            display: flex;
            gap: 8px;
        }
        
        .btn-action {
            padding: 8px 12px;
            font-size: 12px;
            font-weight: 500;
            border-radius: 6px;
            transition: all 0.3s ease;
            text-decoration: none;
        }
        
        .btn-edit {
            background: linear-gradient(135deg, #f39c12 0%, #e67e22 100%);
            color: white;
        }
        
        .btn-edit:hover {
            background: linear-gradient(135deg, #e67e22 0%, #f39c12 100%);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(243, 156, 18, 0.3);
        }
        
        .btn-delete {
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
            color: white;
        }
        
        .btn-delete:hover {
            background: linear-gradient(135deg, #c0392b 0%, #e74c3c 100%);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(231, 76, 60, 0.3);
        }
        
        .success {
            color: #27ae60;
            background: #eafaf1;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border: 1px solid #d5f4e6;
            font-weight: 500;
        }
        
        .error {
            color: #e74c3c;
            background: #fdeded;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border: 1px solid #fadbd8;
            font-weight: 500;
        }
        
        .empty-message {
            text-align: center;
            padding: 60px 20px;
            color: #7f8c8d;
            font-style: italic;
            font-size: 18px;
        }
        
        .empty-message h3 {
            color: #2c3e50;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Student Management</div>
            <div class="nav-buttons">
                <a href="<c:url value='/students?action=add' />" class="btn btn-add">Add Student</a>
            </div>
        </div>
        
        <h2 style="color: #2c3e50; margin-bottom: 20px; font-size: 24px;">All Students</h2>
        <p style="color: #7f8c8d; margin-bottom: 30px; line-height: 1.6;">Manage student enrollment and records</p>
        
        <c:if test="${not empty successMessage}">
            <div class="success">${successMessage}</div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty students}">
                <div class="empty-message">
                    <h3>No students found</h3>
                    <p>Click "Add Student" to enroll your first student.</p>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Roll No</th>
                            <th>Name</th>
                            <th>Course</th>
                            <th>Semester</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${students}">
                            <tr>
                                <td>${student.studentId}</td>
                                <td>${student.rollNo}</td>
                                <td>${student.name}</td>
                                <td>${student.courseName}</td>
                                <td>${student.semester}</td>
                                <td>
                                    <div class="actions">
                                        <a href="<c:url value='/students?action=edit&studentId=${student.studentId}' />" class="btn btn-action btn-edit">Edit</a>
                                        <a href="javascript:confirmDelete(${student.studentId})" class="btn btn-action btn-delete">Delete</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    
    <script>
        function confirmDelete(studentId) {
            if (confirm("Are you sure you want to delete this student?")) {
                // Create a form and submit POST request to delete the student
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = '<c:url value="/students" />';
                
                const actionInput = document.createElement('input');
                actionInput.type = 'hidden';
                actionInput.name = 'action';
                actionInput.value = 'delete';
                form.appendChild(actionInput);
                
                const studentIdInput = document.createElement('input');
                studentIdInput.type = 'hidden';
                studentIdInput.name = 'studentId';
                studentIdInput.value = studentId;
                form.appendChild(studentIdInput);
                
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</body>
</html>