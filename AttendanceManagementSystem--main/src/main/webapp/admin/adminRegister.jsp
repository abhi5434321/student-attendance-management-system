<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Registration - Attendance System</title>
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
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        
        .register-container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 25px 50px rgba(0,0,0,0.25);
            width: 100%;
            max-width: 450px;
            position: relative;
            overflow: hidden;
        }
        
        .register-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 6px;
            background: linear-gradient(90deg, #667eea, #764ba2);
        }
        
        .logo {
            text-align: center;
            margin-bottom: 35px;
        }
        
        .logo h1 {
            color: #2c3e50;
            margin: 0;
            font-size: 32px;
            font-weight: 700;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 12px;
        }
        
        .logo h1::before {
            content: '🔐';
            font-size: 1.2em;
        }
        
        .logo p {
            color: #7f8c8d;
            margin: 8px 0 0 0;
            font-size: 16px;
            font-weight: 500;
        }
        
        .form-group {
            margin-bottom: 25px;
        }
        
        label {
            display: block;
            margin-bottom: 10px;
            color: #2c3e50;
            font-weight: 600;
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 15px;
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            font-size: 16px;
            transition: all 0.3s ease;
            box-sizing: border-box;
            background: #f8f9fa;
        }
        
        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.2);
            background: white;
        }
        
        .btn {
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-top: 15px;
        }
        
        .btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 25px rgba(102, 126, 234, 0.4);
        }
        
        .btn:active {
            transform: translateY(-1px);
        }
        
        .links {
            text-align: center;
            margin-top: 25px;
        }
        
        .links a {
            color: #667eea;
            text-decoration: none;
            margin: 0 15px;
            font-weight: 500;
            transition: color 0.3s ease;
        }
        
        .links a:hover {
            color: #764ba2;
            text-decoration: underline;
        }
        
        .error {
            color: #e74c3c;
            text-align: center;
            margin-bottom: 20px;
            padding: 15px;
            background: #fdeded;
            border-radius: 8px;
            border: 1px solid #fadbd8;
            font-weight: 500;
        }
        
        .success {
            color: #27ae60;
            text-align: center;
            margin-bottom: 20px;
            padding: 15px;
            background: #eafaf1;
            border-radius: 8px;
            border: 1px solid #d5f4e6;
            font-weight: 500;
        }
        
        .password-requirements {
            font-size: 12px;
            color: #7f8c8d;
            margin-top: 8px;
            font-style: italic;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <div class="logo">
            <h1>🔐 Admin Registration</h1>
            <p>Create your admin account</p>
        </div>
        
        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="error"><%= request.getAttribute("errorMessage") %></div>
        <% } %>
        
        <% if (request.getAttribute("successMessage") != null) { %>
            <div class="success"><%= request.getAttribute("successMessage") %></div>
        <% } %>
        
        <form action="../register" method="post">
            <input type="hidden" name="userType" value="admin">
            
            <div class="form-group">
                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" required placeholder="Enter your full name">
            </div>
            
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" required placeholder="Enter your email">
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required placeholder="Create a password">
                <div class="password-requirements">
                    Password should be at least 6 characters
                </div>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="Confirm your password">
            </div>
            
            <button type="submit" class="btn">Register as Admin</button>
        </form>
        
        <div class="links">
            <a href="adminLogin.jsp">Already have an account?</a> | 
            <a href="<c:url value='/index.html' />">Back to Home</a>
        </div>
    </div>
</body>
</html>