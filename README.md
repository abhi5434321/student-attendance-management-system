# Attendance Management System

A web-based Attendance Management System developed using **Java Servlets, JSP, MySQL, HTML, CSS, and JavaScript**. This application helps educational institutions manage students, teachers, courses, subjects, attendance records, and reports through separate Admin and Teacher portals.

## Features

- **Admin Portal:** Manage students, teachers, courses, subjects, and class schedules.
- **Teacher Portal:** Mark student attendance and view attendance records.
- **Student Management:** Add, update, and manage student information.
- **Attendance Tracking:** Maintain daily attendance records.
- **Attendance Reports:** Generate daily, monthly, student-wise, and subject-wise reports.
- **Authentication:** Session-based login and registration for Admin and Teachers.
- **Responsive Interface:** User-friendly interface for desktop and mobile devices.
- **Database Integration:** MySQL database for persistent data storage.
- **In-Memory Fallback:** Supports fallback data handling when database connectivity is unavailable.

## System Architecture

The application follows a **3-Tier Architecture**.

### 1. Presentation Layer
- JSP pages for dynamic web content.
- HTML, CSS, and JavaScript for the user interface.
- AJAX for asynchronous operations.
- Responsive layouts using CSS Flexbox.

### 2. Business Logic Layerssss
- Java Servlets for handling HTTP requests.
- DAO (Data Access Object) classes for database operations.
- Model classes for managing application entities.
- Session management for user authentication.

### 3. Data Layer
- MySQL database for storing application data.
- Database connectivity through Java.
- Connection pooling for efficient database access.
- In-memory data structures as a fallback.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend Development |
| Java Servlets | Request Handling |
| JSP | Dynamic Web Pages |
| MySQL | Database Management |
| HTML5 | Frontend Structure |
| CSS3 | Styling and Responsive Design |
| JavaScript | Frontend Interactivity |
| AJAX | Asynchronous Requests |
| Maven | Build Management |
| Jetty | Embedded Web Server |

## Installation and Setup

### Prerequisites
- Java JDK
- IntelliJ IDEA or another Java IDE
- MySQL Server
- Apache Maven

### Steps to Run the Project

1. Open the Attendance Management System project in your IDE.
2. Configure the MySQL database connection details.
3. Import the database schema using the `database.sql` file.
4. Verify the database credentials and connection.
5. Open the terminal in the project directory.
6. Build the project:

   ```bash
   mvn clean install
   ```

7. Start the application:

   ```bash
   mvn jetty:run
   ```

8. Open the application in your browser:

   ```text
   http://localhost:8084/AttendanceSystem/
   ```

## Database Schema

The application uses MySQL with the following tables:

| Table Name | Description |
|---|---|
| admins | Stores administrator information |
| teachers | Stores teacher information |
| students | Stores student records |
| courses | Stores course details |
| subjects | Stores subject information |
| attendance | Maintains attendance records |
| schedule | Stores class schedules |

The complete database structure is available in the `database.sql` file.

## Application Modules

### Admin Module
- Admin Registration and Login
- Add and Manage Students
- Add and Manage Courses
- Add and Manage Subjects
- Manage Class Schedules
- View Attendance Records
- Generate Attendance Reports

### Teacher Module
- Teacher Registration and Login
- Teacher Dashboard
- Mark Student Attendance
- View Class Attendance
- Access Attendance Reports

## Main Application Endpoints

| Endpoint | Functionality |
|---|---|
| `/` | Home Page |
| `/register` | Registration |
| `/login` | Login |
| `/logout` | Logout |
| `/dashboard` | User Dashboard |
| `/attendance` | Attendance Management |
| `/report` | Attendance Reports |

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   ├── controller/
│   │   ├── dao/
│   │   ├── model/
│   │   └── util/
│   │
│   └── webapp/
│       ├── admin/
│       ├── teacher/
│       ├── WEB-INF/
│       │   └── web.xml
│       ├── index.html
│       └── redirect.html
│
database.sql
pom.xml
```

## Security Features

- Session-based authentication.
- Input validation and sanitization.
- Secure password handling.
- Role-based access through Admin and Teacher portals.

## Future Enhancements

- Student login portal.
- Email notifications for attendance updates.
- Advanced attendance analytics.
- Export attendance reports to Excel and PDF.
- Improved dashboard visualizations.

## Author

**Abhijeet Singh**

- **Role:** Java Full Stack Developer
- **GitHub:** [github.com/abhi5434321](https://github.com/abhi5434321)
- **LinkedIn:** [linkedin.com/in/abhijeet-singh-38b591360](https://linkedin.com/in/abhijeet-singh-38b591360)

---

⭐ If you find this project useful, consider giving it a star!