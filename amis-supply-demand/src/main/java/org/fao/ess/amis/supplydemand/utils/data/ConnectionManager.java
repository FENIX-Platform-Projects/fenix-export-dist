package org.fao.ess.amis.supplydemand.utils.data;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager {
    private String url,usr,psw;

    private static ThreadLocal<Connection> threadConnection = new ThreadLocal<>();
    public Connection getConnection() {
        return threadConnection.get();
    }


    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        url = context.getInitParameter("url");
        usr = context.getInitParameter("usr");
        psw = context.getInitParameter("psw");
    }

}
