<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attendance Reports - Attendance System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        
        .container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
            max-width: 1200px;
            margin: 0 auto;
        }
        
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 35px;
            padding-bottom: 20px;
            border-bottom: 2px solid #f0f0f0;
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
            content: '📊';
            font-size: 1.5em;
        }
        
        .page-title {
            color: #2c3e50;
            margin-bottom: 15px;
            font-size: 26px;
            font-weight: 600;
        }
        
        .page-description {
            color: #7f8c8d;
            margin-bottom: 35px;
            font-size: 16px;
            line-height: 1.6;
        }
        
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 25px;
        }
        
        .card {
            background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
            border: 1px solid #e9ecef;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.05);
            transition: all 0.3s ease;
        }
        
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }
        
        .card h3 {
            margin: 0 0 15px 0;
            color: #2c3e50;
            font-size: 20px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .card p {
            color: #7f8c8d;
            margin-bottom: 20px;
            font-size: 14px;
            line-height: 1.5;
        }
        
        .btn-report {
            display: inline-block;
            padding: 12px 20px;
            background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            font-size: 14px;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            width: 100%;
            text-align: center;
        }
        
        .btn-report:hover {
            background: linear-gradient(135deg, #2980b9 0%, #3498db 100%);
            transform: translateY(-2px);
            box-shadow: 0 8px 15px rgba(52, 152, 219, 0.3);
        }
        
        /* Responsive design */
        @media (max-width: 768px) {
            .container {
                padding: 20px;
                margin: 10px;
            }
            
            .header {
                flex-direction: column;
                gap: 15px;
                text-align: center;
            }
            
            .welcome {
                font-size: 24px;
            }
            
            .dashboard-grid {
                grid-template-columns: 1fr;
                gap: 20px;
            }
            
            .card {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome">Attendance Reports</div>
            <div>
            </div>
        </div>
        
        <h2 class="page-title">Attendance Reporting Dashboard</h2>
        <p class="page-description">Select a report type to generate attendance reports</p>
        
        <div class="dashboard-grid">
            <div class="card">
                <h3>📅 Day-wise Report</h3>
                <p>View detailed attendance records for any specific date. Get comprehensive insights into daily attendance patterns.</p>
                <a href="<c:url value='/reports?action=daily' />" class="btn-report">Generate Report</a>
            </div>
            
            <div class="card">
                <h3>📆 Monthly Report</h3>
                <p>Analyze attendance trends over an entire month. Perfect for monthly reviews and performance tracking.</p>
                <a href="<c:url value='/reports?action=monthly' />" class="btn-report">Generate Report</a>
            </div>
            
            <div class="card">
                <h3>📄 Export PDF</h3>
                <p>Create professional PDF documents of your attendance reports for sharing and archival purposes.</p>
                <a href="<c:url value='/reports' />" class="btn-report">Export to PDF</a>
            </div>
            
            <div class="card">
                <h3>📊 Student Report</h3>
                <p>Track individual student attendance history and generate personalized attendance summaries.</p>
                <a href="<c:url value='/reports?action=student' />" class="btn-report">View Student Report</a>
            </div>
        </div>
    </div>
</body>
</html>