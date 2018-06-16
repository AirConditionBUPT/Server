package AirCondition.Service;


import AirCondition.Controller.AirConditonImpl;
import AirCondition.Model.AirCondition;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectClient {

    public static final int PORT = 1893;

    //初始化服务器端
    public void init(){
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);

        } catch (IOException e){
            System.out.println("服务器连接异常");
            e.printStackTrace();
        }
    }

    //处理客户端发来的请求
    private class HandleThread implements Runnable{
        private Socket socket;

        public HandleThread(Socket socket) {
            this.socket = socket;
            new Thread(this).start();
        }

        public void run() {

            //在线程中获取客户端发来的数据并且进行处理
            DataInputStream inputStream = null;
            DataOutputStream outputStream = null;
            try{
                System.out.println("成功连接...");
                inputStream = new DataInputStream(socket.getInputStream());
                //获取输出流
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[2048];
                int len;
                while((len = inputStream.read(bytes))!=-1){
                    byteArrayOutputStream.write(bytes,0, len);
                }
                //将获取到的byte类型转换为String类型
                String stringInput = new String(byteArrayOutputStream.toByteArray());
                //关闭连接
                socket.shutdownInput();
                byteArrayOutputStream.close();

                //处理数据
                //先将String还原成JSON格式
                JSONObject jsonObject = new JSONObject(stringInput);
                int type = jsonObject.getInt("type");
                //数据库操作
                AirConditonImpl dbQuery = new AirConditonImpl();
                //响应包
                Map<String,Object> map = new HashMap<String, Object>();
                switch (type){
                    //1代表收到的是开机信息
                    case 1:
                        String roomNumber = jsonObject.getString("room_number");
                        map.put("type",type);
                        try{
                            if(dbQuery.find(roomNumber) == null){
                                AirCondition openAir = new AirCondition();
                                openAir.setInitialTemperature(jsonObject.getFloat("initial_temperature"));
                                openAir.setRoomNumber(roomNumber);
                                openAir.setTemperature(jsonObject.getFloat("initial_temperature"));
                                openAir.setMode(1);
                                openAir.setWindSpeed(2);
                                dbQuery.add(openAir);
                                System.out.println("添加数据成功");
                                map.put("success",true);
                                map.put("msg","房间空调开启成功");
                            } else {
                                map.put("success", false);
                                map.put("msg","空调已经开启");
                            }

                        } catch (SQLException e){
                            e.printStackTrace();
                            map.put("success", false);
                            map.put("msg","数据库添加数据错误");
                        }
                        break;
                    //2代表收到的是关机信息
                    case 2:
                        roomNumber = jsonObject.getString("room_number");
                        map.put("type",type);
                        try{
                            dbQuery.delete(roomNumber);
                            map.put("success", true);
                            map.put("msg","房间空调关闭成功");
                        } catch (SQLException e){
                            e.printStackTrace();
                            map.put("success", false);
                            map.put("msg","房间空调未开启");
                        }
                        break;
                    //3代表收到调温调风信息
                    case 3:
                        AirCondition changeAir = new AirCondition();
                        changeAir.setRoomNumber(jsonObject.getString("room_number"));
                        changeAir.setWindSpeed(jsonObject.getInt("wind_speed"));
                        changeAir.setMode(jsonObject.getInt("mode"));
                        changeAir.setTemperature(jsonObject.getFloat("target_temperature"));
                        map.put("type",3);
                        //TODO:当前温度和目标温度的逻辑未完成
                        try{
                            dbQuery.update(changeAir);
                            map.put("success", true);
                            map.put("msg","更改房间空调信息成功");
                            //TODO:等待队列以及调度策略未完成
                        } catch (SQLException e){
                            e.printStackTrace();
                            map.put("success", false);
                            map.put("msg","更改房间信息失败");
                        }
                        break;
                    //4为定时请求信息
                    case 4:
                        map.put("type", 4);
                        roomNumber = jsonObject.getString("room_number");
                        try{
                            AirCondition airCondition = dbQuery.find(roomNumber);
                            if(airCondition != null){
                                map.put("success", true);
                                Map<String,Object> response = new HashMap<String, Object>();
                                response.put("temperature",airCondition.getTemperature());
                                response.put("wind_speed",airCondition.getWindSpeed());
                                response.put("mode",airCondition.getMode());
                                response.put("consumption", airCondition.getConsumption());
                                map.put("msg",response);
                            }
                        } catch (SQLException e){
                            e.printStackTrace();
                            map.put("success",false);
                            map.put("msg","定时请求消息失败");
                        }
                }
                JSONObject responseJSON = new JSONObject(map);
                //将JSON数据转换为字符串，并且发送给客户端
                String responseString = responseJSON.toString();
                outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                outputStream.writeUTF(responseString);
                outputStream.flush();
                outputStream.close();
                System.out.println("发送数据成功");

            } catch (IOException e){
                System.out.println("获取客户端数据异常,异常原因为:" + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
