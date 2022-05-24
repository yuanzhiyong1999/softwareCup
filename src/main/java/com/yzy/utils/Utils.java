package com.yzy.utils;

import org.springframework.util.DigestUtils;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class Utils {

    public static String MD5(String input) {
        //获取MD5机密实例
        return DigestUtils.md5DigestAsHex(input.getBytes());
    }

    public static Timestamp getTime(){
        Date date = new Date();//获得系统时间.

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.

        return Timestamp.valueOf(nowTime);

    }

    public static String get_path_name(String task_name){
        Calendar ca = Calendar.getInstance();
        String year = String.valueOf(ca.get(Calendar.YEAR));
        String month = String.valueOf(ca.get(Calendar.MONTH) + 1);
        String day = String.valueOf(ca.get(Calendar.DATE));
        String xxx = year + '/' + month + '/' + day;
        return String.format("img/%s/%s/%s", task_name, xxx, UUID.randomUUID().toString());
    }

}