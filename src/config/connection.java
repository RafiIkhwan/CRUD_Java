package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {

    public static Connection getConnection() {
        Connection connection   = null;
        String url              = "jdbc:mysql://localhost:3306/pos";
        String user             = "root";
        String password         = "";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e){
            System.out.println(e);
        }
        return connection;
    }

    public static void main(String[] args) {
        try {
            Connection connection = config.connection.getConnection();
            System.out.println(String.format("Connected to database %s " + "successfully.", connection.getCatalog()));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
