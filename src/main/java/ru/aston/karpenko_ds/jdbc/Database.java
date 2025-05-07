package ru.aston.karpenko_ds.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;

    private Database() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = createConnection();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Connection createConnection() throws SQLException {
        String url = "jdbc:h2:~/library";
        String user = "sa";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
}
