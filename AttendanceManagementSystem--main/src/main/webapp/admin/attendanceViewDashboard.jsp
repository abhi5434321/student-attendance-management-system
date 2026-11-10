<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attendance Views - Attendance System</title>
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
        .btn-reports {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
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
        .btn-view {
            display: inline-block;
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 5px 0;
        }
        .btn-view:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Attendance Views</div>
            <div>
            </div>
        </div>
        
        <h2>Attendance Views Dashboard</h2>
        <p>Select a view type to see attendance data organized in different ways</p>
        
        <div class="dashboard-grid">
            <div class="card">
                <h3>📅 Day-wise View</h3>
                <p>View all attendance records for a specific day</p>
                <a href="<c:url value='/attendanceViews?action=dayWise' />" class="btn-view">View by Day</a>
            </div>
            
            <div class="card">
                <h3>📚 Subject-wise View</h3>
                <p>View attendance records for a specific subject</p>
                <a href="<c:url value='/attendanceViews?action=subjectWise' />" class="btn-view">View by Subject</a>
            </div>
            
            <div class="card">
                <h3>📊 Combined Views</h3>
                <p>Additional combined views coming soon</p>
                <a href="#" class="btn-view" style="background-color: #6c757d; pointer-events: none; opacity: 0.6;">Coming Soon</a>
            </div>
        </div>
    </div>
</body>
</html>