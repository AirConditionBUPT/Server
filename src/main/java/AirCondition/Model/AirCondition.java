package AirCondition.Model;

import lombok.Data;

@Data
public class AirCondition {

    private float initialTemperature;//初始温度

    private String roomNumber;//房间号

    private int windSpeed;//风速，0自动，1低档，2中档，3高档

    private int mode;//模式,0自动，1节能，2高效

    private float temperature;//当前温度

    private float power;//功率

    private float consumption;//消费


}
