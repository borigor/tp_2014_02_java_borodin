package db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by igor on 5/21/14.
 */
public class Connector {

    public static Connection getConnection() {
        try {
            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://localhost:3306/javadb?user=root&password=qwe123";
            return DriverManager.getConnection(url);
        }
        catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
