package backend.db;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    // Private constructor to enforce Singleton pattern
    private DatabaseManager() {
        try {
            // Load properties from db.properties file
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("backend/db/db.properties");
            if (inputStream == null) {
                throw new RuntimeException("Cannot find db.properties file");
            }
            properties.load(inputStream);

            this.url = properties.getProperty("db.url");
            this.username = properties.getProperty("db.username");
            this.password = properties.getProperty("db.password");

            // Load JDBC driver
            Class.forName(properties.getProperty("db.driver"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize DatabaseManager");
        }
    }

    // Singleton instance
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    // Get database connection
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish database connection");
        }
        return connection;
    }

    // Close database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a user to the User table
    public void addUser(String name, String email, String password) {
        String query = "INSERT INTO User (name, email, password) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all users from the User table
    public void getAllUsers() {
        String query = "SELECT * FROM User"; // SQL query to get all users
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int userId = rs.getInt("userId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("ID: " + userId + ", Name: " + name + ", Email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        System.out.println("Testing DatabaseManager...");

        DatabaseManager manager = DatabaseManager.getInstance();
        try {
            // Test database connection
            Connection connection = manager.getConnection();
            System.out.println("Database connection successful!");

            // Insert users into User table
            manager.addUser("John Doe", "john@example.com", "password123");
            manager.addUser("Jane Doe", "jane@example.com", "password456");

            // Retrieve all users from the User table
            manager.getAllUsers();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            manager.closeConnection();
        }
    }
}
