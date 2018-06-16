/*
    数据库DAO模式接口实现
 */
package AirCondition.Controller;

import AirCondition.Model.AirCondition;
import AirCondition.Model.AirConditionDAO;
import AirCondition.Utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirConditonImpl implements AirConditionDAO {

    //添加数据接口实现
    public void add(AirCondition airCondition) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "insert into aircondition(room_number, initial_temperature, temperature, wind_speed, mode, power, consumption) VALUES (?,?,?,?,?,?,?)";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,airCondition.getRoomNumber());
            preparedStatement.setFloat(2,airCondition.getInitialTemperature());
            preparedStatement.setFloat(3,airCondition.getTemperature());
            preparedStatement.setInt(4,airCondition.getWindSpeed());
            preparedStatement.setInt(5,airCondition.getMode());
            preparedStatement.setFloat(6,airCondition.getPower());
            preparedStatement.setFloat(7,airCondition.getConsumption());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("添加数据失败");
        } finally {
            ConnectDB.close(null,preparedStatement,connection);
        }
    }

    //删除数据接口实现
    public void delete(String roomNumber) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql="delete from aircondition where room_number=?";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("删除数据失败");
        } finally {
            ConnectDB.close(null,preparedStatement,connection);
        }
    }

    public void update(AirCondition airCondition) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql="update aircondition set temperature=?,wind_speed=?,mode=? where room_number=?";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1,airCondition.getTemperature());
            preparedStatement.setFloat(2,airCondition.getWindSpeed());
            preparedStatement.setFloat(3,airCondition.getMode());
            preparedStatement.setString(4,airCondition.getRoomNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("更新数据失败");
        } finally {
            ConnectDB.close(null,preparedStatement,connection);
        }
    }

    public List<AirCondition> findAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<AirCondition> airConditions = new ArrayList<AirCondition>();
        AirCondition airCondition = null;
        String sql = "select room_number,initial_temperature,temperature,wind_speed,mode,power,consumption from aircondition";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                airCondition = new AirCondition();
                airCondition.setRoomNumber(resultSet.getString(1));
                airCondition.setInitialTemperature(resultSet.getFloat(2));
                airCondition.setTemperature(resultSet.getFloat(3));
                airCondition.setWindSpeed(resultSet.getInt(4));
                airCondition.setMode(resultSet.getInt(5));
                airCondition.setPower(resultSet.getFloat(6));
                airCondition.setConsumption(resultSet.getFloat(7));
            }
            airConditions.add(airCondition);
        } catch (SQLException e){
            throw new SQLException("查询所有数据失败");
        } finally {
            ConnectDB.close(resultSet,preparedStatement,connection);
        }
        return airConditions;
    }

    public AirCondition find(String roomNumber) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        AirCondition airCondition = null;
        String sql = "select room_number,initial_temperature,temperature,wind_speed,mode,power,consumption from aircondition where aircondition.aircondition.room_number=?";
        try{
            connection = ConnectDB.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,roomNumber);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                airCondition = new AirCondition();
                airCondition.setInitialTemperature(resultSet.getFloat(1));
                airCondition.setTemperature(resultSet.getFloat(2));
                airCondition.setWindSpeed(resultSet.getInt(3));
                airCondition.setMode(resultSet.getInt(4));
                airCondition.setPower(resultSet.getFloat(5));
                airCondition.setConsumption(resultSet.getFloat(6));
            }
        } catch (SQLException e){
            throw new SQLException("查询此房间"+ roomNumber +"数据失败");
        } finally {
            ConnectDB.close(resultSet,preparedStatement,connection);
        }
        return airCondition;
    }
}
