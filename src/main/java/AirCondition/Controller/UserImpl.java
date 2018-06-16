package AirCondition.Controller;

import AirCondition.Model.User;
import AirCondition.Model.UserDAO;
import AirCondition.Utils.ConnectDB;
import AirCondition.Utils.Encoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserImpl implements UserDAO {
    public void add(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Encoder encoder = new Encoder();//加密操作
        String sql = "insert into user(user_name, password, roles) VALUES (?,?,?)";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,encoder.encodeBySHA(user.getPassword()));
            preparedStatement.setString(3,user.getRoles());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("添加数据失败");
        } finally {
            ConnectDB.close(null,preparedStatement,connection);
        }
    }

    public void delete(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "delete from user where id=?";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("删除数据失败");
        } finally {
            ConnectDB.close(null,preparedStatement,connection);
        }
    }

    public void updateUserName(String userName,int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql="update user set user_name=? where id=?";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("变更用户名失败");
        } finally {
            ConnectDB.close(null,preparedStatement,connection);
        }
    }

    public void updatePassword(String password, int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql="update user set password=? where id=?";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,password);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("变更密码失败");
        } finally {
            ConnectDB.close(null,preparedStatement,connection);
        }
    }

    public void updateRoles(String roles,int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql="update user set roles=? where id=?";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,roles);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("变更用户权限失败，失败原因");
        } finally {
            ConnectDB.close(null,preparedStatement,connection);
        }
    }

    public List<User> findAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();
        User user = null;
        String sql="select user_name,password,roles from user";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            user = new User();
            while(resultSet.next()){
                user.setUserName(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setRoles(resultSet.getString(3));
                users.add(user);
            }
        } catch (SQLException e){
            throw new SQLException("查询所有用户信息失败");
        } finally {
            ConnectDB.close(resultSet,preparedStatement,connection);
        }
        return users;
    }

    public User find(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        String sql = "select user_name,password,roles from user where id=?";
        try {
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            user = new User();
            if(resultSet.next()){
                user.setUserName(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setRoles(resultSet.getString(3));
            }
        } catch (SQLException e){
            throw new SQLException("查询" + user.getUserName() + "用户失败");
        } finally {
            ConnectDB.close(resultSet,preparedStatement,connection);
        }
        return user;
    }

    public User findByUserName(String userName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        String sql = "select user_name,password,roles from user where user_name=?";
        try {
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            resultSet = preparedStatement.executeQuery();
            user = new User();
            if(resultSet.next()){
                user.setUserName(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setRoles(resultSet.getString(3));
            }
        } catch (SQLException e){
            throw new SQLException("查询" + user.getUserName() + "用户失败");
        } finally {
            ConnectDB.close(resultSet,preparedStatement,connection);
        }
        return user;
    }



}
