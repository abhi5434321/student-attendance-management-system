<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Attendance System</title>
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
            content: '🎓';
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
        
        .btn-home {
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
            color: white;
        }
        
        .btn-logout {
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
            color: white;
        }
        
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 25px;
            margin-top: 30px;
        }
        
        .card {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border: none;
            position: relative;
            overflow: hidden;
        }
        
        .card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, #667eea, #764ba2);
        }
        
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.15);
        }
        
        .card h3 {
            margin-top: 0;
            margin-bottom: 15px;
            font-size: 20px;
            color: #2c3e50;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .card p {
            color: #7f8c8d;
            margin-bottom: 20px;
            line-height: 1.6;
        }
        
        .btn-card {
            display: inline-block;
            padding: 12px 20px;
            background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 500;
            transition: all 0.3s ease;
            margin: 5px 5px 0 0;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }
        
        .btn-card:hover {
            background: linear-gradient(135deg, #2980b9 0%, #3498db 100%);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(52, 152, 219, 0.4);
        }
        
        .btn-card:nth-child(odd) {
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
        }
        
        .btn-card:nth-child(odd):hover {
            background: linear-gradient(135deg, #c0392b 0%, #e74c3c 100%);
            box-shadow: 0 5px 15px rgba(231, 76, 60, 0.4);
        }
        
        .btn-card:nth-child(3n) {
            background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
        }
        
        .btn-card:nth-child(3n):hover {
            background: linear-gradient(135deg, #27ae60 0%, #2ecc71 100%);
            box-shadow: 0 5px 15px rgba(46, 204, 113, 0.4);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Admin Dashboard</div>
            <div class="nav-buttons">
                <a href="<c:url value='/index.html' />" class="btn btn-home">🏠 Home</a>
                <a href="../logout" class="btn btn-logout">Logout</a>
            </div>
        </div>
        
        <h2 style="color: #2c3e50; margin-bottom: 20px; font-size: 24px;">Welcome to the Attendance Management System</h2>
        <p style="color: #7f8c8d; margin-bottom: 30px; line-height: 1.6;">Manage courses, subjects, students, and attendance records efficiently</p>
        
        <div class="dashboard-grid">
            <div class="card">
                <h3>📚 Course Management</h3>
                <p>Manage academic courses and programs</p>
                <a href="<c:url value='/courses?action=add' />" class="btn-card">Add Course</a>
                <a href="<c:url value='/courses?action=view' />" class="btn-card">View Courses</a>
            </div>
            
            <div class="card">
                <h3>📖 Subject Management</h3>
                <p>Manage subjects for each course</p>
                <a href="<c:url value='/subjects?action=add' />" class="btn-card">Add Subject</a>
                <a href="<c:url value='/subjects?action=view' />" class="btn-card">View Subjects</a>
            </div>
            
            <div class="card">
                <h3>👥 Student Management</h3>
                <p>Manage student records and enrollment</p>
                <a href="<c:url value='/students?action=add' />" class="btn-card">Add Student</a>
                <a href="<c:url value='/students?action=view' />" class="btn-card">View Students</a>
            </div>
            
            <div class="card">
                <h3>📋 Attendance Reports</h3>
                <p>Generate and view attendance reports</p>
                <a href="<c:url value='/reports?action=daily' />" class="btn-card">Day-wise Report</a>
                <a href="<c:url value='/reports?action=monthly' />" class="btn-card">Monthly Report</a>
            </div>
            
            <div class="card">
                <h3>📊 Attendance Views</h3>
                <p>View attendance data in different ways</p>
                <a href="<c:url value='/attendanceViews?action=dayWise' />" class="btn-card">Day-wise View</a>
                <a href="<c:url value='/attendanceViews?action=subjectWise' />" class="btn-card">Subject-wise View</a>
            </div>
            
            <div class="card">
                <h3>🗓️ Schedule Management</h3>
                <p>View class schedules and timetables</p>
                <a href="<c:url value='/schedule?action=viewSchedule' />" class="btn-card">View Schedules</a>
                <a href="<c:url value='/schedule?action=viewStudentList' />" class="btn-card">Student Lists</a>
            </div>
        </div>
    </div>
</body>
</html>