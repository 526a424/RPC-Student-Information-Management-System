```sql
-- Class1 表
CREATE TABLE Class1 (
    classId CHAR(10) PRIMARY KEY,
    className CHAR(50) NOT NULL,
    departmentId CHAR(10) NOT NULL,
    CONSTRAINT fk_department FOREIGN KEY (departmentId) REFERENCES Department(departmentId)
);

-- Course 表
CREATE TABLE Course (
    courseId CHAR(10) PRIMARY KEY,
    courseName CHAR(100) NOT NULL,
    teacherName CHAR(50),
    credit INT NOT NULL
);

-- CourseRegistration 表
CREATE TABLE CourseRegistration (
    studentId CHAR(10),
    courseId CHAR(10),
    PRIMARY KEY (studentId, courseId),
    CONSTRAINT fk_student FOREIGN KEY (studentId) REFERENCES Student(studentId),
    CONSTRAINT fk_course FOREIGN KEY (courseId) REFERENCES Course(courseId)
);

-- Department 表
CREATE TABLE Department (
    departmentId CHAR(10) PRIMARY KEY,
    departmentName CHAR(100) NOT NULL
);

-- Student 表
CREATE TABLE Student (
    studentId CHAR(10) PRIMARY KEY,
    name CHAR(100) NOT NULL,
    gender CHAR(1) NOT NULL,
    age CHAR(3),
    classId CHAR(10),
    CONSTRAINT fk_class FOREIGN KEY (classId) REFERENCES Class1(classId)
);

-- StudentGrade 表
CREATE TABLE StudentGrade (
    studentId CHAR(10),
    courseId CHAR(10),
    grade INT NOT NULL,
    PRIMARY KEY (studentId, courseId),
    CONSTRAINT fk_student_grade FOREIGN KEY (studentId) REFERENCES Student(studentId),
    CONSTRAINT fk_course_grade FOREIGN KEY (courseId) REFERENCES Course(courseId)
);

-- User 表
CREATE TABLE User (
    username CHAR(50) PRIMARY KEY,
    password CHAR(50) NOT NULL,
    role CHAR(20) NOT NULL
);

-- Class1 表插入测试数据
INSERT INTO Class1 (classId, className, departmentId) VALUES
('C001', 'Class A', 'D001'),
('C002', 'Class B', 'D001'),
('C003', 'Class C', 'D002'),
('C004', 'Class D', 'D002'),
('C005', 'Class E', 'D003'),
('C006', 'Class F', 'D003'),
('C007', 'Class G', 'D004'),
('C008', 'Class H', 'D004'),
('C009', 'Class I', 'D005'),
('C010', 'Class J', 'D005');

-- Course 表插入测试数据
INSERT INTO Course (courseId, courseName, teacherName, credit) VALUES
('CO001', 'Mathematics', 'Mr. Smith', 3),
('CO002', 'Physics', 'Ms. Johnson', 4),
('CO003', 'Chemistry', 'Dr. Brown', 3),
('CO004', 'Biology', 'Prof. Davis', 4),
('CO005', 'English', 'Ms. Wilson', 3),
('CO006', 'History', 'Mr. Clark', 3),
('CO007', 'Computer Science', 'Dr. Martinez', 4),
('CO008', 'Art', 'Ms. Lee', 2),
('CO009', 'Music', 'Mr. Allen', 2),
('CO010', 'Physical Education', 'Coach Johnson', 2);

-- CourseRegistration 表插入测试数据
INSERT INTO CourseRegistration (studentId, courseId) VALUES
('S001', 'CO001'),
('S001', 'CO002'),
('S002', 'CO001'),
('S002', 'CO003'),
('S003', 'CO002'),
('S003', 'CO004'),
('S004', 'CO005'),
('S004', 'CO006'),
('S005', 'CO007'),
('S005', 'CO008');

-- Department 表插入测试数据
INSERT INTO Department (departmentId, departmentName) VALUES
('D001', 'Mathematics'),
('D002', 'Physics'),
('D003', 'Chemistry'),
('D004', 'Biology'),
('D005', 'Computer Science'),
('D006', 'Arts'),
('D007', 'History');

-- Student 表插入测试数据
INSERT INTO Student (studentId, name, gender, age, classId) VALUES
('S001', 'John Doe', 'M', '20', 'C001'),
('S002', 'Jane Smith', 'F', '21', 'C001'),
('S003', 'David Johnson', 'M', '19', 'C002'),
('S004', 'Emily Brown', 'F', '20', 'C002'),
('S005', 'Michael Clark', 'M', '22', 'C003'),
('S006', 'Sarah Davis', 'F', '21', 'C003'),
('S007', 'James Wilson', 'M', '20', 'C004'),
('S008', 'Olivia Martinez', 'F', '19', 'C004'),
('S009', 'Emma Lee', 'F', '22', 'C005'),
('S010', 'Daniel Allen', 'M', '21', 'C005');

-- StudentGrade 表插入测试数据
INSERT INTO StudentGrade (studentId, courseId, grade) VALUES
('S001', 'CO001', 85),
('S001', 'CO002', 90),
('S002', 'CO001', 88),
('S002', 'CO003', 92),
('S003', 'CO002', 78),
('S003', 'CO004', 85),
('S004', 'CO005', 95),
('S004', 'CO006', 88),
('S005', 'CO007', 90),
('S005', 'CO008', 85);

-- USER 表插入测试数据
INSERT INTO USER (username, PASSWORD, role) VALUES
('user1', 'password1', 'admin'),
('user2', 'password2', 'student'),
('user3', 'password3', 'faculty'),
('user4', 'password4', 'staff'),
('user5', 'password5', 'student'),
('user6', 'password6', 'student'),
('user7', 'password7', 'faculty'),
('user8', 'password8', 'staff'),
('user9', 'password9', 'admin'),
('user10', 'password10', 'student');
```