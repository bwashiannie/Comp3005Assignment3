package org.example;

import java.time.LocalDate;

public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate enrollmentDate;

    //default constructor
    public Student(){}
    public Student(int studentId, String firstName, String lastName, String email, LocalDate enrollmentDate) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enrollmentDate = enrollmentDate;
    }

    //constructor for creating new students without ID
    public Student(String firstName, String lastName, String email, LocalDate enrollmentDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enrollmentDate = enrollmentDate;
    }

    //getters and setters

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    //return a formatted string presentation of the student info entered
    @Override
    public String toString() {
        return String.format("Student ID: %d | Name: %s %s | Email: %s | Enrollment Date: %s",
                studentId, firstName, lastName, email, enrollmentDate);
    }
}
