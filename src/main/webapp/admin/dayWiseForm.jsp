<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Day-wise View - Attendance System</title>
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
            max-width: 600px;
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
        .btn-views {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }
        .btn-home {
            background: linear-gradient(135deg, #56ab2f 0%, #a8e063 100%);
        }
        .btn-logout {
            background: linear-gradient(135deg, #ff6b6b 0%, #ff8e8e 100%);
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #333;
        }
        input[type="date"], select {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        input[type="date"]:focus, select:focus {
            border-color: #667eea;
            outline: none;
        }
        .btn-submit {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            font-weight: bold;
            width: 100%;
        }
        .btn-submit:hover {
            opacity: 0.9;
        }
        .error {
            color: #e74c3c;
            background: #ffeaea;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            border-left: 4px solid #e74c3c;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Day-wise View</div>
            <div>
            </div>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        
        <h2>View Attendance by Day</h2>
        
        <form method="post" action="<c:url value='/attendanceViews' />">
            <input type="hidden" name="action" value="viewDayWise">
            
            <div class="form-group">
                <label for="date">Select Date:</label>
                <input type="date" id="date" name="date" required>
            </div>
            
            <div class="form-group">
                <label for="courseId">Filter by Course (Optional):</label>
                <select id="courseId" name="courseId">
                    <option value="">All Courses</option>
                    <c:forEach var="course" items="${courses}">
                        <option value="${course.courseId}">${course.courseName}</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="subjectId">Filter by Subject (Optional):</label>
                <select id="subjectId" name="subjectId">
                    <option value="">All Subjects</option>
                    <c:forEach var="subject" items="${subjects}">
                        <option value="${subject.subjectId}">${subject.subjectName} (${subject.courseName})</option>
                    </c:forEach>
                </select>
            </div>
            
            <button type="submit" class="btn-submit">View Attendance</button>
        </form>
        

    </div>
</body>
</html>