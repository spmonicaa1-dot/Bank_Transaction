package com.wipro.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    public static Connection getDBConnection() {

        Connection connection = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // SID based connection
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String user = "system";
            String pass = "kavitha";

            connection = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
