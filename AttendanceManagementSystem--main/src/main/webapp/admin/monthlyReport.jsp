<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Monthly Report - Attendance System</title>
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
        .btn-reports {
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
        input[type="number"], select {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        input[type="number"]:focus, select:focus {
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
        .row {
            display: flex;
            gap: 15px;
        }
        .col {
            flex: 1;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Monthly Report</div>
            <div>
            </div>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        
        <h2>Generate Monthly Attendance Report</h2>
        
        <form method="post" action="<c:url value='/reports' />">
            <input type="hidden" name="action" value="viewMonthly">
            
            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <label for="month">Select Month:</label>
                        <select id="month" name="month" required>
                            <option value="">Select Month</option>
                            <option value="1">January</option>
                            <option value="2">February</option>
                            <option value="3">March</option>
                            <option value="4">April</option>
                            <option value="5">May</option>
                            <option value="6">June</option>
                            <option value="7">July</option>
                            <option value="8">August</option>
                            <option value="9">September</option>
                            <option value="10">October</option>
                            <option value="11">November</option>
                            <option value="12">December</option>
                        </select>
                    </div>
                </div>
                
                <div class="col">
                    <div class="form-group">
                        <label for="year">Select Year:</label>
                        <input type="number" id="year" name="year" min="2020" max="2030" required>
                    </div>
                </div>
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
            
            <button type="submit" class="btn-submit">Generate Report</button>
        </form>
        

    </div>
</body>
</html>