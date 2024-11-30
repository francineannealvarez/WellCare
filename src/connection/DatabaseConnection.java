package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        // Change these values to match your MySQL setup
        String url = "jdbc:mysql://localhost:3306/mysql_oop";  //Replace the last word with the name of the database that you've created
        String username = "root";  // Use your username
        String password = "@francinealvarez";      // Use your password

        try {
            // Make sure the JDBC driver is registered
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }
    }
}

