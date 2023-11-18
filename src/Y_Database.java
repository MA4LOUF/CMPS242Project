import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Y_Database {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/y_database_test";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "Radamente2";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}