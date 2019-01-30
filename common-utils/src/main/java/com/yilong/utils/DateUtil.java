package com.yilong.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    public static String getCurrentTime(String patten){
        SimpleDateFormat sdf=new SimpleDateFormat(patten);
       return  sdf.format(new Date());
    }

    public static String getCurrentTime(){
        String patten ="yyyy-MM-dd HH:mm:ss";
        return getCurrentTime(patten);
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return ts;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long s){
       String patten="yyyy-MM-dd HH:mm:ss";
        return DateUtil.stampToDate(s,patten);
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long s,String patten){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    //获取当前日期后N天的日期，可以负数表示前几天
    public static String getNextDay(String patten,int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, n);
        Date date=  calendar.getTime();
        SimpleDateFormat sdf=new SimpleDateFormat(patten);
        return sdf.format(date);

    }
}
