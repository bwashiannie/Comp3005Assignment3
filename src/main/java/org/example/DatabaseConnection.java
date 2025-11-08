package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static String url = "jdbc:postgresql://localhost:5432/studentdb";
    static String user = "postgres";
    static String password = "postgres";

    //To establish database connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            // Establish and return connection
            return DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
    }

    //To close the database connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection is closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    //To make sure the connection has been established
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("SUCCESS: Connected to PostgreSQL database!");
        } catch (SQLException e) {
            System.err.println("FAILED to connect to database: " + e.getMessage());
        }
    }

}
