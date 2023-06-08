package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class JdbcDao {

    private final static String connectionString = "jdbc:mysql://localhost:3306/posdb";
    private final static String user = "root";
    private final static String pwd = ""; // xampp: ""  mamp: "root"
    private final static String SELECT_QUERY = "SELECT * FROM users WHERE username = ? AND password = ?";

    public boolean validate(String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(connectionString,user,pwd);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(connectionString,user,pwd);
            return connection;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        } catch (SQLException e) {
            printSQLException(e);
            return null;
        }
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + ex.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.err.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}


