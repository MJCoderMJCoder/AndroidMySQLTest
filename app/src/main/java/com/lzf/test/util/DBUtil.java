/**
 *
 */
package com.lzf.test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author MJCoder
 */
public class DBUtil {
    // SQL Server
    // private static final String JDBC_DRIVER =
    // "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // JDBC 驱动名
    // private static final String DB_URL =
    // "jdbc:sqlserver://localhost:1433;databaseName=police"; // 数据库 URL
    // static final String USER = "sa"; // 数据库的用户名
    // static final String PASS = "6003"; // 数据库的密码

    // MySQL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // JDBC 驱动名
    // private static final String DB_URL = "jdbc:mysql://localhost:3306/sys"; //
    // 数据库 URL
    private static final String DB_URL = "jdbc:mysql://192.168.17.251:3306/sys"; // 数据库 URL
    static final String USER = "root"; // 数据库的用户名
    static final String PASS = "MJCoder"; // 数据库的密码

    /**
     *
     */
    public DBUtil() {
        // TODO Auto-generated constructor stub
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("连接数据库");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeDB(Connection conn, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("关闭数据库连接");
    }
}
