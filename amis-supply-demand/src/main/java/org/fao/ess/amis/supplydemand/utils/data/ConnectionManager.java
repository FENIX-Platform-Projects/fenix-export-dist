package org.fao.ess.amis.supplydemand.utils.data;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionManager {
    private String url, usr, psw;


    public void init() {
        Properties prop = new Properties();
        InputStream input = null;
        try {

            input = getClass().getClassLoader().getResourceAsStream("connection/connection.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            this.url = prop.getProperty("url");
            this.usr = prop.getProperty("usr");
            this.psw = prop.getProperty("psw");

            Class.forName("org.postgresql.Driver");

            if (input != null)
                input.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public Connection getConnection() {
        Connection connection = null;
        try {

            connection = DriverManager.getConnection("jdbc:postgresql://" + url, usr, psw);

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

        }
        return connection;
    }

}
