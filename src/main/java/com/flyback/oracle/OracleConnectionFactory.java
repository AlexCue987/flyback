package com.flyback.oracle;

import com.flyback.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnectionFactory implements ConnectionFactory{
    private final String owner;
    private final String pwd;
    private final String connectionString;

    public OracleConnectionFactory(String owner, String pwd, String connectionString) {
        this.owner = owner;
        this.pwd = pwd;
        this.connectionString = connectionString;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(connectionString, owner, pwd);
    }
}
