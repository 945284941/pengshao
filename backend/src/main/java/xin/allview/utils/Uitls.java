package xin.allview.utils;

/**
 * Created by kingcc on 2017-3-20.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工具类，用于存放常用的方法
 * <p>
 * Created by jiweibo on 15/11/3.
 */

public class Uitls {


    /**
     * 解析
     */

    public static class Ajax {


        /**
         * 返回请求成功的标准格式数据
         *
         * @param message 要返回给前台的信息
         * @param data    要返回的数据
         * @return map key-value格式的数据
         */

        public static Map<String, Object> success(Object message, Object data) {

            Map<String, Object> map = new HashMap<String ,Object>();

            map.put("issuccess", true);

            map.put("message", message);

            map.put("data", data);

            return map;

        }



        /**
         * 返回请求失败的标准格式数据
         *
         * @param message 要返回给前台的信息
         * @param data    要返回的数据
         * @return map类型的key-value格式的数据
         */

        public static Map<String, Object> failure(Object message, Object data) {

            Map<String, Object> map = new HashMap<String ,Object>();

            map.put("issuccess", false);

            map.put("message", message);

            map.put("data", data);

            return map;

        }

    }

    public static String currTime(){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.format(System.currentTimeMillis()).toString();

    }

    /**
     * 当前周的开始时间
     * @return
     */
    public static String getBeginDayOfWeek() {

        Date date=new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(cal.getTime());
    }

    /**
     * 当前周的结束时间
     * @return
     */
        public static String getEndDayOfWeek(){
            Date date=new Date();
               Calendar cal = Calendar.getInstance();
                cal.setTime(date);
               cal.add(Calendar.DAY_OF_WEEK, 6);
              Date weekEndSta = cal.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
               return df.format(weekEndSta);
        }

    public static  String currTimeDay(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(System.currentTimeMillis()).toString();
    }

    public static String ToMD5(String data) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(data.getBytes());

        byte[] buff = md.digest();


        StringBuffer md5str = new StringBuffer();

        int digital;

        for (int i = 0; i < buff.length; i++) {

            digital = buff[i];

            if (digital < 0) {

                digital += 256;

            }

            if (digital < 16) {

                md5str.append("0");

            }

            md5str.append(Integer.toHexString(digital));

        }
        return md5str.toString();
    }


    public static String urldecoder(String data) {

        if (data == null || data.trim().equals("")) {
            return data;
        }

        try {
            return URLDecoder.decode(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return data;
        }
    }


    public static int getRandom() {
        Random random = new Random();
        int num = random.nextInt(10);
        return num;
    }


    public static void main(String[] args) {

        System.out.println("mian");

    }

    public static String loadJSON(String url) {

        StringBuilder json = new StringBuilder();

        try {

            URL oracle = new URL(url);

            URLConnection yc = oracle.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(

                yc.getInputStream()));

            String inputLine = null;

            while ((inputLine = in.readLine()) != null) {

                json.append(inputLine);

            }

            in.close();

        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }

        return json.toString();

    }


}

