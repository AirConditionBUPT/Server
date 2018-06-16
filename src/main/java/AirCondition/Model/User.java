package AirCondition.Model;

import lombok.Data;

@Data
public class User {

    private int id;//用户id

    private String userName;//用户名

    private String password;//密码

    private String roles;//用户权限
}
