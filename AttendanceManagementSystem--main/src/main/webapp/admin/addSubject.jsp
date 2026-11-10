<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Subject - Attendance System</title>
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
            max-width: 600px;
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
            content: '📚';
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
        
        .btn-home {
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
            color: white;
        }
        
        .btn-logout {
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
            color: white;
        }
        
        .btn-view {
            background: linear-gradient(135deg, #9b59b6 0%, #8e44ad 100%);
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
            margin-bottom: 25px;
        }
        
        label {
            display: block;
            margin-bottom: 10px;
            font-weight: 600;
            color: #2c3e50;
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        
        input[type="text"], select {
            width: 100%;
            padding: 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            box-sizing: border-box;
            transition: all 0.3s ease;
            background: #f8f9fa;
        }
        
        input[type="text"]:focus, select:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
            background: white;
        }
        
        .btn-submit {
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
            color: white;
            padding: 15px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            width: 100%;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        
        .btn-submit:hover {
            background: linear-gradient(135deg, #27ae60 0%, #2ecc71 100%);
            transform: translateY(-2px);
            box-shadow: 0 8px 15px rgba(46, 204, 113, 0.4);
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
        
        .back-links {
            text-align: center;
            margin-top: 25px;
        }
        
        .back-link {
            display: inline-block;
            background: linear-gradient(135deg, #95a5a6 0%, #7f8c8d 100%);
            color: white;
            padding: 12px 20px;
            text-decoration: none;
            border-radius: 8px;
            transition: all 0.3s ease;
            font-weight: 500;
        }
        
        .back-link:hover {
            background: linear-gradient(135deg, #7f8c8d 0%, #95a5a6 100%);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(127, 140, 141, 0.3);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Add Subject</div>
            <div class="nav-buttons">
                <a href="<c:url value='/admin/viewSubjects.jsp' />" class="btn btn-view">View Subjects</a>
            </div>
        </div>
        
        <h2 style="color: #2c3e50; margin-bottom: 20px; font-size: 24px;">Add New Subject</h2>
        <p style="color: #7f8c8d; margin-bottom: 30px; line-height: 1.6;">Enter the details for the new subject</p>
        
        <c:if test="${not empty successMessage}">
            <div class="success">${successMessage}</div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        
        <div class="form-container">
            <form method="post" action="<c:url value='/subjects' />">
                <input type="hidden" name="action" value="add">
                
                <div class="form-group">
                    <label for="subjectName">Subject Name:</label>
                    <input type="text" id="subjectName" name="subjectName" required placeholder="Enter subject name">
                </div>
                
                <div class="form-group">
                    <label for="courseId">Associated Course:</label>
                    <select id="courseId" name="courseId" required>
                        <option value="">Select a course</option>
                        <c:forEach var="course" items="${courses}">
                            <option value="${course.courseId}">${course.courseName}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <button type="submit" class="btn-submit">Add Subject</button>
            </form>
        </div>
        
        <div class="back-links">
            <a href="<c:url value='/admin/viewSubjects.jsp' />" class="back-link">Back to Subjects List</a>
        </div>
    </div>
</body>
</html>