package com.galaxy.im.business.rili.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccountDate {

    private static transient int gregorianCutoverYear = 1582;
    
    /** 闰年中每月天数 */
    private static final int[] DAYS_P_MONTH_LY= {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    
    /** 非闰年中每月天数 */
    private static final int[] DAYS_P_MONTH_CY= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    
    /** 代表数组里的年、月、日 */
    private static final int Y = 0, M = 1, D = 2;
    
    /**
    * 将代表日期的字符串分割为代表年月日的整形数组
    * @param date
    * @return
    */
    public static int[] splitYMD(String date){
        date = date.replace("-", "");
        int[] ymd = {0, 0, 0};
        ymd[Y] = Integer.parseInt(date.substring(0, 4));
        ymd[M] = Integer.parseInt(date.substring(4, 6));
        ymd[D] = Integer.parseInt(date.substring(6, 8));
        return ymd;
    }
    
    /**
    * 检查传入的参数代表的年份是否为闰年
    * @param year
    * @return
    */
    public static boolean isLeapYear(int year) {
        return year >= gregorianCutoverYear ?
            ((year%4 == 0) && ((year%100 != 0) || (year%400 == 0))) : (year%4 == 0); 
    }
      
    /**
    * 日期加1天
    * @param year
    * @param month
    * @param day
    * @return
    */
    private static int[] addOneDay(int year, int month, int day){
        if(isLeapYear( year )){
            day++;
            if( day > DAYS_P_MONTH_LY[month -1 ] ){
                month++;
                if(month > 12){
                    year++;
                    month = 1;
                }
                day = 1;
            }
        }else{
            day++;
            if( day > DAYS_P_MONTH_CY[month -1 ] ){
                month++;
                if(month > 12){
                    year++;
                    month = 1;
                }
                day = 1;
            }
        }
        int[] ymd = {year, month, day};
        return ymd;
    }
    
    /**
    * 将不足两位的月份或日期补足为两位
    * @param decimal
    * @return
    */
    public static String formatMonthDay(int decimal){
        DecimalFormat df = new DecimalFormat("00");
        return df.format( decimal );
    }
    
    /**
    * 将不足四位的年份补足为四位
    * @param decimal
    * @return
    */
    public static String formatYear(int decimal){
        DecimalFormat df = new DecimalFormat("0000");
        return df.format( decimal );
    }
    
    /**
    * 计算两个日期之间相隔的天数
    * @param begin
    * @param end
    * @return
    * @throws ParseException
    */
    public static long countDay(String begin,String end){
           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           Date beginDate , endDate;
           long day = 0;
           try {
            beginDate= format.parse(begin);
            endDate=  format.parse(end);
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);  
        } catch (ParseException e) {
            e.printStackTrace();
        }                
           return day;
    }
    
    /**
    * 以循环的方式计算日期
    * @param beginDate endDate
    * @param days
    * @return
    */
    public static List<String> getXiuEveryday(String beginDate , String endDate,String lastDayOfMonth ){
        long days = countDay(beginDate, endDate);
        int[] ymd = splitYMD( beginDate );
        List<String> everyDays = new ArrayList<String>();
        everyDays.add(beginDate);
        for(int i = 0; i < days; i++){
            ymd = addOneDay(ymd[Y], ymd[M], ymd[D]);
            String ss = formatYear(ymd[Y])+"-"+formatMonthDay(ymd[M])+"-"+formatMonthDay(ymd[D]);
            if(ss.equals(lastDayOfMonth))break;           
            everyDays.add(ss);
        }     
        return everyDays;
    }
    
    public static List<String> getEveryday(String beginDate , String endDate){
        long days = countDay(beginDate, endDate);
        int[] ymd = splitYMD( beginDate );
        List<String> everyDays = new ArrayList<String>();
        everyDays.add(beginDate);
        for(int i = 0; i < days; i++){
            ymd = addOneDay(ymd[Y], ymd[M], ymd[D]);         
            everyDays.add(formatYear(ymd[Y])+"-"+formatMonthDay(ymd[M])+"-"+formatMonthDay(ymd[D]));
        }     
        return everyDays;
    }
    
   /* public static void main(String[] args) {
    	String ss = getLastDayOfMonth(2017,4);
        List<String> list = AccountDate.getXiuEveryday("2017-04-01", "2017-05-14",ss);
    
        for (String result : list) {
        	
            System.out.println(result);
        }
    }*/
    
    
    
  //获取指定年月的最后一天
	 public static String getLastDayOfMonth(Integer year, Integer month) {
	        Calendar calendar = Calendar.getInstance();
	        if (year == null) {
	            year = calendar.get(Calendar.YEAR);
	        }
	        if (month == null) {
	            month = calendar.get(Calendar.MONTH);
	        }
	        calendar.set(year, month, 1);
	        calendar.roll(Calendar.DATE, -1);
	        
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			return sdf.format(calendar.getTime());
			
	    }
	    //判断两个时间是否相等(yyyy-MM-dd HH:mm:ss 与yyyy-MM-dd HH:mm:ss )
		 public static Boolean get(String  year, String month) {
			 
			 String yearTime = year.substring(0,10);
			 String monthTime = month.substring(0,10);			 
			 Boolean boolen= yearTime.equals(monthTime);			 
			 return boolen;
				
		    }
		 //判断时间是否与指定的时间相等(yyyy-MM-dd HH:mm:ss 与  yyyy-MM-dd)
		 public static Boolean getApp(String  year, String month) {
			 
			 String yearTime = year.substring(0,10);
			 //String monthTime = month.substring(0,10);			 
			 Boolean boolen= yearTime.equals(month);			 
			 return boolen;
				
		    }
		 
		 
		 
		 
		 
}