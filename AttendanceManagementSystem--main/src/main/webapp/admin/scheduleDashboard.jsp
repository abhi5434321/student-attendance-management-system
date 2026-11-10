<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Schedule Management - Attendance System</title>
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
        .btn-sched {
            display: inline-block;
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 5px 0;
        }
        .btn-sched:hover {
            background-color: #0056b3;
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
        .schedule-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .schedule-table th, .schedule-table td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .schedule-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .schedule-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Schedule Management</div>
            <div>
            </div>
        </div>
        
        <h2>View Schedules</h2>
        <p>Select an option to view schedules by different criteria</p>
        
        <div class="dashboard-grid">
            <div class="card">
                <h3>📚 View by Course</h3>
                <p>View schedules filtered by course</p>
                <form method="get" action="<c:url value='/schedule' />" style="margin-top: 10px;">
                    <input type="hidden" name="action" value="viewByCourse">
                    <div class="form-group">
                        <select name="courseId" required>
                            <option value="">Select Course</option>
                            <c:forEach var="course" items="${courses}">
                                <option value="${course.courseId}">${course.courseName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn-sched">View Schedule</button>
                </form>
            </div>
            
            <div class="card">
                <h3>📖 View by Subject</h3>
                <p>View schedules filtered by subject</p>
                <form method="get" action="<c:url value='/schedule' />" style="margin-top: 10px;">
                    <input type="hidden" name="action" value="viewBySubject">
                    <div class="form-group">
                        <select name="subjectId" required>
                            <option value="">Select Subject</option>
                            <c:forEach var="subject" items="${subjects}">
                                <option value="${subject.subjectId}">${subject.subjectName} (${subject.courseName})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn-sched">View Schedule</button>
                </form>
            </div>
            
            <div class="card">
                <h3>👨‍🏫 View by Teacher</h3>
                <p>View schedules filtered by teacher</p>
                <form method="get" action="<c:url value='/schedule' />" style="margin-top: 10px;">
                    <input type="hidden" name="action" value="viewByTeacher">
                    <div class="form-group">
                        <select name="teacherId" required>
                            <option value="">Select Teacher</option>
                            <c:forEach var="teacher" items="${teachers}">
                                <option value="${teacher.teacherId}">${teacher.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn-sched">View Schedule</button>
                </form>
            </div>
        </div>
        
        <c:if test="${not empty schedules}">
            <h3>Recent Schedules</h3>
            <div class="table-container">
                <table class="schedule-table">
                    <thead>
                        <tr>
                            <th>Course</th>
                            <th>Subject</th>
                            <th>Teacher</th>
                            <th>Day</th>
                            <th>Time</th>
                            <th>Classroom</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="schedule" items="${schedules}" begin="0" end="9">
                            <tr>
                                <td>${schedule.courseName}</td>
                                <td>${schedule.subjectName}</td>
                                <td>${schedule.teacherName}</td>
                                <td>${schedule.dayOfWeek}</td>
                                <td>${schedule.startTime} - ${schedule.endTime}</td>
                                <td>${schedule.classroom}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</body>
</html>