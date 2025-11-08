package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class main {
    private static Scanner scanner;
    public static void main(String[] args) {
        System.out.println("--- Here is the Student Management system ---");

        //test database connection at startup;
        DatabaseConnection.testConnection();

        scanner = new Scanner(System.in);
        try {
            displayMenu();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

    }
    private static void displayMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n --- Main Menu ---");
            System.out.println("Choose an option (1-5)");
            System.out.println("1. View all students");
            System.out.println("2. Add new students");
            System.out.println("3. Update student email");
            System.out.println("4. Delete student");
            System.out.println("5. Quit(exit)");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    getAllStudents();
                    break;
                case "2":
                    addStudent();
                    break;
                case "3":
                    updateStudentEmail();
                    break;
                case "4":
                    deleteStudent();
                    break;
                case "5":
                    running = false;
                    System.out.println("Thank you for using the student Management System!");
                    break;

                default:
                    System.out.println("Invalid option! Please choose 1-5.");

            }
        }

    }

    private static void getAllStudents() {
        System.out.println("\n--- All Students ---");
        //SQL query to select all students
        String sql = "SELECT * FROM students";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            boolean hasStudents = false;

            // Iterate through all rows in the result set
            while (resultSet.next()) {
                hasStudents = true;

                // Extract data from current row
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                LocalDate enrollmentDate = resultSet.getDate("enrollment_date").toLocalDate();

                // Create Student object and display
                Student student = new Student(studentId, firstName, lastName, email, enrollmentDate);
                System.out.println(student);
            }

            if (!hasStudents) {
                System.out.println("No students found in the database.");
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
    }
    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");

        try {
            // Collect student information from user
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine().trim();

            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter enrollment date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine().trim();
            LocalDate enrollmentDate = LocalDate.parse(dateInput);

            // Validate input
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                System.out.println("Error: First name, last name, and email are required!");
                return;
            }

            // SQL insert statement
            String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                // Set parameters for the prepared statement
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.setDate(4, Date.valueOf(enrollmentDate));

                // Execute the insert
                int affectedRows = statement.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int newStudentId = generatedKeys.getInt(1);
                            System.out.println("Student added successfully with ID: " + newStudentId);
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error adding student: " + e.getMessage());
            System.out.println("Please ensure date format is YYYY-MM-DD and email is unique.");
        }
    }
    private static void updateStudentEmail(){
        System.out.println("\n--- Update Student Email ---");

        try {
            System.out.print("Enter student ID to update: ");
            int studentId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter new email: ");
            String newEmail = scanner.nextLine().trim();

            if (newEmail.isEmpty()) {
                System.out.println("Error: Email cannot be empty!");
                return;
            }
            // SQL update statement
            String sql = "UPDATE students SET email = ? WHERE student_id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, newEmail);
                statement.setInt(2, studentId);

                int affectedRows = statement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Email updated successfully for student ID: " + studentId);
                } else {
                    System.out.println("No student found with ID: " + studentId);
                }
            }catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid student ID (number).");
            }
        }catch (SQLException e) {
            System.err.println("Error updating email: " + e.getMessage());
        }

    }
    private static void deleteStudent(){
        System.out.println("\n--- Delete Student ---");

        try {
            System.out.print("Enter student ID to delete: ");
            int studentId = Integer.parseInt(scanner.nextLine().trim());

            // Confirm deletion
            System.out.print("Are you sure you want to delete student with ID " + studentId + "? (yes/no): ");
            String confirmation = scanner.nextLine().trim();

            if (!confirmation.equalsIgnoreCase("yes")) {
                System.out.println("Deletion cancelled.");
                return;
            }
            // SQL delete statement
            String sql = "DELETE FROM students WHERE student_id = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, studentId);

                int affectedRows = statement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Student deleted successfully!");
                } else {
                    System.out.println("No student found with ID: " + studentId);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid student ID (number).");
            }
        }catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }

    }

}