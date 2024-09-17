package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private DBConnection() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "root");
    }

    public Connection getConnection(){
        return connection;
    }

    public static  DBConnection getInstance() throws SQLException {
        return null==instance?instance=new DBConnection():instance;
    }

}
