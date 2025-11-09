# Student Management System

A Java application that demonstrates CRUD operations using pure JDBC and PostgreSQL for Assignment 3.

## Features

- **getAllStudents()** - Retrieves and displays all records from the students table
- **addStudent(first_name, last_name, email, enrollment_date)** - Inserts new student records into the database
- **updateStudentEmail(student_email, new_email)** - Updates the email address for a specific student
- **deleteStudent(email)** - Deletes the record of a student with the specified email
- Pure JDBC implementation (no ORM frameworks for this assignment)
- PostgreSQL database integration
- Menu-driven console interface

## Video Demonstration

link: https://youtu.be/xVsd90or6ew

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher
- pgAdmin 4 (for database management)

## Database Setup

### 1. Create Database
1. Open pgAdmin4
2. Connect to your PostgreSQL server
3. Create a new database named `studentdb`

### 2. Create Table and Insert Initial Data
Execute the following SQL in pgAdmin Query Tool:

```sql
CREATE TABLE students (
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    enrollment_date DATE
);

INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');
