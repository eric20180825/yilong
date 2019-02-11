package com.yilong.utils;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static String defaultPatten = "yyyy-MM-dd HH:mm:ss";

    public static SimpleDateFormat getSimpleDateFormat() {
        return getSimpleDateFormat(defaultPatten);
    }

    public static SimpleDateFormat getSimpleDateFormat(String patten) {
        return new SimpleDateFormat(patten);
    }

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

    //获取当月第一天
    public static String getCurrentMonthFirstDay(String patten) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = getSimpleDateFormat(patten).format(c.getTime());
        return first;
    }

    //获取当月最后一天
    public static String getCurrentMonthLastDay(String patten) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = getSimpleDateFormat(patten).format(ca.getTime());
       return last;
    }

    public static void main(String[] args) {
        String first=DateUtil.getCurrentMonthFirstDay("yyyyMMdd");
        String last=DateUtil.getCurrentMonthLastDay("yyyyMMdd");
        System.out.println(first+","+last);
    }
}
