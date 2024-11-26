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

         Properties var1 = new Properties();
         InputStream var2 = this.getClass().getClassLoader().getResourceAsStream("backend/db/db.properties");

         if (var2 == null) {
            System.err.println("Error: db.properties file not found!");
            throw new RuntimeException("Cannot find db.properties file");
         } else {
            var1.load(var2);
            System.out.println("db.properties loaded successfully.");

            // 파일에서 읽은 DB 설정 정보 확인
            this.url = var1.getProperty("db.url");
            this.username = var1.getProperty("db.user");
            this.password = var1.getProperty("db.password");
            System.out.println("DB URL: " + this.url);
            System.out.println("DB User: " + this.username);

            // 드라이버 로드 확인
            Class.forName(var1.getProperty("db.driver"));
            System.out.println("JDBC Driver loaded successfully.");
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
            System.out.println("Attempting to establish database connection...");
            System.out.println("Connecting to: " + this.url + " as user: " + this.username);

            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
            System.out.println("Database connection established successfully.");
         }
      } catch (SQLException var2) {
         System.err.println("Error: Failed to establish database connection.");
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
         System.err.println("Error: Failed to close database connection.");
         var2.printStackTrace();
      }
   }

   public static void main(String[] var0) {
      System.out.println("Testing DatabaseManager...");
      DatabaseManager var1 = getInstance();

      try {
         Connection var2 = var1.getConnection();
         System.out.println("Database connection test successful!");
      } catch (Exception var6) {
         System.err.println("Error during database connection test.");
         var6.printStackTrace();
      } finally {
         var1.closeConnection();
      }
   }
}
