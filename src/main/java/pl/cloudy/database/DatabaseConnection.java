package pl.cloudy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{

    public Connection getConnect()
    {
        return connect;
    }

    private Connection connect = null;
    public DatabaseConnection()
    {
        String url = "jdbc:mysql://localhost:3306/testresults4?autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "";

        try
        {
            connect = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
