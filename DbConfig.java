package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    public Connection connInit()
    {

        // Connection configuration
        String DB_URL ="jdbc:mysql://localhost/ticketing";
        String USER = "root";
        String PASS = "";
        Connection conn = null;

        // Attempt to locate JDBC Driver
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        // Attempt to establish connection to DB
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Successfully established Connection to DB...");

        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
