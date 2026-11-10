-- Attendance Management System Database Schema

-- Create database
CREATE DATABASE IF NOT EXISTS attendance_system;
USE attendance_system;

-- Admin Table
CREATE TABLE admin_table (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Course Table
CREATE TABLE course_table (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL UNIQUE
);

-- Teacher Table
CREATE TABLE teacher_table (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES course_table(course_id)
);

-- Subject Table
CREATE TABLE subject_table (
    subject_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL,
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES course_table(course_id)
);

-- Student Table
CREATE TABLE student_table (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    roll_no VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    gender VARCHAR(10) DEFAULT NULL,
    course_id INT,
    semester INT,
    FOREIGN KEY (course_id) REFERENCES course_table(course_id)
);

-- Attendance Table
CREATE TABLE attendance_table (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    subject_id INT,
    course_id INT,
    date DATE NOT NULL,
    status ENUM('Present', 'Absent') NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student_table(student_id),
    FOREIGN KEY (subject_id) REFERENCES subject_table(subject_id),
    FOREIGN KEY (course_id) REFERENCES course_table(course_id),
    UNIQUE KEY unique_attendance (student_id, subject_id, date)
);

-- Schedule Table
CREATE TABLE schedule_table (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    subject_id INT,
    teacher_id INT,
    day_of_week VARCHAR(10) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    classroom VARCHAR(50),
    FOREIGN KEY (course_id) REFERENCES course_table(course_id),
    FOREIGN KEY (subject_id) REFERENCES subject_table(subject_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher_table(teacher_id)
);

-- Insert sample data for testing
INSERT INTO course_table (course_name) VALUES 
('Computer Science'),
('Electronics'),
('Mechanical Engineering');

INSERT INTO schedule_table (course_id, subject_id, teacher_id, day_of_week, start_time, end_time, classroom) VALUES
(1, 1, 1, 'Monday', '09:00:00', '10:30:00', 'Room A101'),
(1, 1, 1, 'Wednesday', '09:00:00', '10:30:00', 'Room A101'),
(1, 2, 1, 'Tuesday', '11:00:00', '12:30:00', 'Room A102');

INSERT INTO admin_table (name, email, password) VALUES 
('Admin User', 'admin@example.com', 'admin123');

INSERT INTO teacher_table (name, email, password, course_id) VALUES 
('John Doe', 'john@teacher.com', 'teacher123', 1),
('Jane Smith', 'jane@teacher.com', 'teacher123', 2);

INSERT INTO subject_table (subject_name, course_id) VALUES 
('Java Programming', 1),
('Data Structures', 1),
('Digital Electronics', 2);

INSERT INTO student_table (roll_no, name, email, phone, gender, course_id, semester) VALUES 
('CS001', 'Alice Johnson', 'alice@example.com', '123-456-7890', 'Female', 1, 3),
('CS002', 'Bob Wilson', 'bob@example.com', '098-765-4321', 'Male', 1, 3),
('EC001', 'Charlie Brown', 'charlie@example.com', '555-123-4567', 'Male', 2, 2);