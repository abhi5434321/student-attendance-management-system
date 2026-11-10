<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Schedule for ${course.courseName} - Attendance System</title>
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
        .btn-home {
            background: linear-gradient(135deg, #56ab2f 0%, #a8e063 100%);
        }
        .btn-logout {
            background: linear-gradient(135deg, #ff6b6b 0%, #ff8e8e 100%);
        }
        .schedule-info {
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
        .day-schedule {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .day-header {
            background-color: #007bff;
            color: white;
            padding: 8px 12px;
            border-radius: 4px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .subject-item {
            padding: 8px 12px;
            margin: 5px 0;
            background-color: white;
            border-radius: 4px;
            border-left: 3px solid #28a745;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Schedule for ${course.courseName}</div>
            <div>
                <a href="<c:url value='/schedule?action=viewSchedule' />" class="btn btn-schedule">Schedule Dashboard</a>
            </div>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        
        <div class="schedule-info">
            <strong>Course:</strong> ${course.courseName}
        </div>
        
        <div style="margin-bottom: 20px;">
            <a href="<c:url value='/schedule?action=viewSchedule' />" class="btn">New Search</a>
        </div>
        
        <c:choose>
            <c:when test="${empty schedules}">
                <div style="text-align: center; padding: 20px; color: #666;">
                    <h3>No schedules found for this course.</h3>
                    <p>There are currently no scheduled classes for ${course.courseName}.</p>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Group schedules by day of week -->
                <c:set var="days" value="Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday" />
                <c:forTokens items="${days}" delims="," var="day">
                    <c:set var="hasSchedule" value="false" />
                    <c:forEach var="schedule" items="${schedules}">
                        <c:if test="${schedule.dayOfWeek == day}">
                            <c:set var="hasSchedule" value="true" />
                        </c:if>
                    </c:forEach>
                    
                    <c:if test="${hasSchedule}">
                        <div class="day-schedule">
                            <div class="day-header">${day}</div>
                            <c:forEach var="schedule" items="${schedules}">
                                <c:if test="${schedule.dayOfWeek == day}">
                                    <div class="subject-item">
                                        <strong>${schedule.startTime} - ${schedule.endTime}</strong> | 
                                        ${schedule.subjectName} | 
                                        ${schedule.teacherName} | 
                                        Room: ${schedule.classroom}
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:forTokens>
                
                <!-- Also show as table -->
                <h3>Detailed Schedule Table</h3>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Day</th>
                                <th>Time</th>
                                <th>Subject</th>
                                <th>Teacher</th>
                                <th>Classroom</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="schedule" items="${schedules}">
                                <tr>
                                    <td>${schedule.dayOfWeek}</td>
                                    <td>${schedule.startTime} - ${schedule.endTime}</td>
                                    <td>${schedule.subjectName}</td>
                                    <td>${schedule.teacherName}</td>
                                    <td>${schedule.classroom}</td>
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