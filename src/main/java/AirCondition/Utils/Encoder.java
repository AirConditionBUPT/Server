package AirCondition.Utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Encoder {

    //对字符串进行MD5加密
    public String encodeByMD5(String rawPassword){
        BigInteger MD5Integer;
        String password = null;

        if(rawPassword != null){
            byte[] rawPasswordData = rawPassword.getBytes();
            try{
                //创建信息摘要
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(rawPasswordData);
                MD5Integer = new BigInteger(messageDigest.digest());
                password = MD5Integer.toString(16);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return password;
    }

    //对字符串进行SHA加密
    public String encodeBySHA(String rawPassword){
        BigInteger SHAInteger;
        String password = null;

        if(rawPassword != null){
            byte[] rawPasswordData = rawPassword.getBytes();
            try{
                //创建信息摘要
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(rawPasswordData);
                SHAInteger = new BigInteger(messageDigest.digest());
                password = SHAInteger.toString(32);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return password;

    }
}
