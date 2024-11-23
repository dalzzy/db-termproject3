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
         Properties var1 = new Properties();
         InputStream var2 = this.getClass().getClassLoader().getResourceAsStream("backend/db/db.properties");
         if (var2 == null) {
            throw new RuntimeException("Cannot find db.properties file");
         } else {
            var1.load(var2);
            this.url = var1.getProperty("db.url");
            this.username = var1.getProperty("db.username");
            this.password = var1.getProperty("db.password");
            Class.forName(var1.getProperty("db.driver"));
         }
      } catch (Exception var3) {
         var3.printStackTrace();
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
         if (this.connection == null || this.connection.isClosed()) {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
         }
      } catch (SQLException var2) {
         var2.printStackTrace();
         throw new RuntimeException("Failed to establish database connection");
      }

      return this.connection;
   }

   public void closeConnection() {
      try {
         if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            System.out.println("Database connection closed successfully.");
         }
      } catch (SQLException var2) {
         var2.printStackTrace();
      }

   }

   public static void main(String[] var0) {
      System.out.println("Testing DatabaseManager...");
      DatabaseManager var1 = getInstance();

      try {
         Connection var2 = var1.getConnection();
         System.out.println("Database connection successful!");
      } catch (Exception var6) {
         var6.printStackTrace();
      } finally {
         var1.closeConnection();
      }

   }
}
