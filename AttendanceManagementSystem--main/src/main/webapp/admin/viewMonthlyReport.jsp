<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Monthly Report - ${month}/${year} - Attendance System</title>
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
        .report-info {
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
        .status-present {
            color: #28a745;
            font-weight: bold;
        }
        .status-absent {
            color: #dc3545;
            font-weight: bold;
        }
        .pagination {
            margin-top: 20px;
            text-align: center;
        }
        .btn-export {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            margin-left: 10px;
        }
        .btn-export:hover {
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
        .summary-stats {
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
            <div class="welcome">Monthly Report - ${month}/${year}</div>
            <div>
            </div>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        
        <div class="report-info">
            <strong>Month/Year:</strong> 
            <c:choose>
                <c:when test="${month == 1}">January</c:when>
                <c:when test="${month == 2}">February</c:when>
                <c:when test="${month == 3}">March</c:when>
                <c:when test="${month == 4}">April</c:when>
                <c:when test="${month == 5}">May</c:when>
                <c:when test="${month == 6}">June</c:when>
                <c:when test="${month == 7}">July</c:when>
                <c:when test="${month == 8}">August</c:when>
                <c:when test="${month == 9}">September</c:when>
                <c:when test="${month == 10}">October</c:when>
                <c:when test="${month == 11}">November</c:when>
                <c:when test="${month == 12}">December</c:when>
            </c:choose>
            , ${year}
            <c:if test="${courseId != 0}">
                | <strong>Course:</strong> 
                <c:forEach var="course" items="${courses}" varStatus="loop">
                    <c:if test="${course.courseId == courseId}">${course.courseName}</c:if>
                </c:forEach>
            </c:if>
            <c:if test="${subjectId != 0}">
                | <strong>Subject:</strong> 
                <c:forEach var="subject" items="${subjects}" varStatus="loop">
                    <c:if test="${subject.subjectId == subjectId}">${subject.subjectName}</c:if>
                </c:forEach>
            </c:if>
        </div>
        
        <!-- Summary Statistics -->
        <div class="summary-stats">
            <div class="stat-card">
                <div class="stat-value">${attendances.size()}</div>
                <div class="stat-label">Total Records</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">
                    <c:set var="presentCount" value="0" />
                    <c:forEach var="attendance" items="${attendances}">
                        <c:if test="${attendance.status eq 'Present'}">
                            <c:set var="presentCount" value="${presentCount + 1}" />
                        </c:if>
                    </c:forEach>
                    ${presentCount}
                </div>
                <div class="stat-label">Present Count</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">
                    <c:set var="absentCount" value="0" />
                    <c:forEach var="attendance" items="${attendances}">
                        <c:if test="${attendance.status eq 'Absent'}">
                            <c:set var="absentCount" value="${absentCount + 1}" />
                        </c:if>
                    </c:forEach>
                    ${absentCount}
                </div>
                <div class="stat-label">Absent Count</div>
            </div>
                <div class="stat-card">
                <div class="stat-value">
                    <c:choose>
                        <c:when test="${attendances.size() > 0}">
                            <c:set var="attendanceRate" value="${(presentCount * 100.0) / attendances.size()}" />
                            <fmt:formatNumber value="${attendanceRate}" pattern="#.##" />
                        </c:when>
                        <c:otherwise>0</c:otherwise>
                    </c:choose>%
                </div>
                <div class="stat-label">Attendance Rate</div>
            </div>
        </div>
        
        <div style="margin-bottom: 20px;">
            <a href="<c:url value='/reports?action=monthly' />" class="btn">New Report</a>
            <a href="<c:url value='/reports?action=pdf&reportType=monthly&month=${month}&year=${year}' />" class="btn btn-export">Export to PDF</a>
        </div>
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>S.No.</th>
                        <th>Date</th>
                        <th>Roll No</th>
                        <th>Student Name</th>
                        <th>Course</th>
                        <th>Subject</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty attendances}">
                            <tr>
                                <td colspan="7" style="text-align: center; color: #666;">No attendance records found for the selected criteria.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="attendance" items="${attendances}" varStatus="loop">
                                <tr>
                                    <td>${loop.count}</td>
                                    <td>${attendance.date}</td>
                                    <td>${attendance.rollNo}</td>
                                    <td>${attendance.studentName}</td>
                                    <td>${attendance.courseName}</td>
                                    <td>${attendance.subjectName}</td>
                                    <td class="status-${attendance.status.toLowerCase()}">${attendance.status}</td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        

    </div>
    

</body>
</html>