package AirCondition.Model;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    //添加
    void add(User user) throws SQLException;

    //删除
    void delete(int id) throws SQLException;

    //更改用户名
    void updateUserName(String userName,int id) throws SQLException;

    //更改密码
    void updatePassword(String password,int id) throws SQLException;

    //更改用户权限
    void updateRoles(String roles, int id) throws SQLException;

    //查询全部用户信息
    List<User> findAll() throws SQLException;

    //查询单个用户信息
    User find(int id) throws SQLException;

    ///通过用户名查询用户信息
    User findByUserName(String userName) throws SQLException;
}
