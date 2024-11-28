package backend.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
   private static DatabaseManager instance;
   private Connection connection;
   private String url;
   private String username;
   private String password;

   private DatabaseManager() {
      try {
         System.out.println("Initializing DatabaseManager...");

         Properties properties = new Properties();
         InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("backend/db/db.properties");

         if (inputStream == null) {
            System.err.println("Error: db.properties file not found!");
            throw new RuntimeException("Cannot find db.properties file");
         }

         properties.load(inputStream);
         System.out.println("db.properties loaded successfully.");

         this.url = properties.getProperty("db.url");
         this.username = properties.getProperty("db.user");
         this.password = properties.getProperty("db.password");

         System.out.println("DB URL: " + this.url);
         System.out.println("DB User: " + this.username);

      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("Failed to initialize DatabaseManager");
      }
   }

   public static DatabaseManager getInstance() {
      if (instance == null) {
         instance = new DatabaseManager();
      }
      return instance;
   }

   public Connection getConnection() {
      try {
         if (this.connection == null || this.connection.isClosed() || !this.connection.isValid(2)) {
            System.out.println("Attempting to establish database connection...");
            System.out.println("Connecting to: " + this.url + " as user: " + this.username);

            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
            System.out.println("Database connection established successfully.");
         }
      } catch (SQLException e) {
         String errorMessage = "Error: Failed to establish database connection to URL: " + this.url;
         System.err.println(errorMessage);
         e.printStackTrace();
         throw new RuntimeException(errorMessage, e);
      }
      return this.connection;
   }

   public void closeConnection() {
      try {
         if (this.connection != null && !this.connection.isClosed()) {
            System.out.println("Closing database connection...");
            this.connection.close();
            System.out.println("Database connection closed successfully.");
         }
      } catch (SQLException e) {
         System.err.println("Error: Failed to close database connection.");
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {
      System.out.println("Testing DatabaseManager...");
      try (Connection connection = DatabaseManager.getInstance().getConnection()) {
         System.out.println("Database connection test successful!");
      } catch (Exception e) {
         System.err.println("Error during database connection test.");
         e.printStackTrace();
      }
   }
}
