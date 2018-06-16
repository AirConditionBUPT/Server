package AirCondition.Model;

import java.sql.SQLException;
import java.util.List;

public interface AirConditionDAO {

    //添加
    void add(AirCondition airCondition) throws SQLException;

    //删除
    void delete(String roomNumber) throws SQLException;

    //更改
    void update(AirCondition airCondition) throws SQLException;

    //查询全部房间信息
    List<AirCondition> findAll() throws SQLException;

    //查询单个房间信息
    AirCondition find(String roomNumber) throws SQLException;
}