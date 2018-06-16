package AirCondition.Utils;

import java.sql.*;

public class ConnectDB {
    //JDBC连接数据库URL
    private final static String URL = "jdbc:mysql://localhost:3306/aircondition?serverTimezone=UTC&useSSL=false";

    //用户名
    private final static String USERNAME = "root";

    //密码
    private final static String PASSWORD = "Yzx970520";

    //driver
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";

    private ConnectDB() {
    }

    //静态模块只加载一次驱动
    static{
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //建立连接
    public static Connection getConnection(){
        Connection connection = null;
        try{
            //connect the jdbc to the ason database
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("获取连接失败");
        }
        return connection;
    }

    //关闭连接
    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        try{
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
