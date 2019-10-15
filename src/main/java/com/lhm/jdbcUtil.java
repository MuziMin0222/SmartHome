package com.lhm;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/*
这是数据库连接的工具类
 */
public class jdbcUtil {
    private String driver;
    private String url;
    private String user;
    private String password;
    private static jdbcUtil util;

    //私有化构造器
    private jdbcUtil(){
        try {
            //初始化数据库连接信息
            Properties properties = new Properties();
            //将配置信息读取到本程序中
            InputStream is = jdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(is);
            //通过properties对象获取相关参数的值
            driver = properties.getProperty("db.driver");
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");

            //注册驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //通过静态方法获取该类的对象
    public static jdbcUtil getInstance(){
        if (util == null){
            synchronized (jdbcUtil.class){
                if (util == null){
                    util = new jdbcUtil();
                }
            }
        }
        return util;
    }

    //获取连接对象
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
       return connection;
    }

    //根据SQL语句的不同自行选择SQL语句执行的方式
    //获取statement对象
    public Statement geStatement() throws SQLException {
        Statement statement = getConnection().createStatement();
        return statement;
    }
    //获取PreparedStatement对象
    public PreparedStatement gePreparedStatement(String sql) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        return ps;
    }

    //关闭资源
    public void close(ResultSet rs,Statement state,Connection conn){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (state!= null){
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
