package com.special.ResideMenuDemo.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

public class DateUtil {
	public  static String getStringDate(Date date){
		String d;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		d=simpleDateFormat.format(date);
		return d;
	}
	public static String getStringdateYMD(Date date){
		String d;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
		d=simpleDateFormat.format(date);
		return d;
	} 
	public static String getStringChinese(Date date){
		String d;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
		d=simpleDateFormat.format(date);
		return d;
	}
	public static String getStringdateY_M_D(Date date){
		String d;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		d=simpleDateFormat.format(date);
		return d;
	}
	public static String getStringdateY_M_D1(Timestamp date){
		String d;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		d=simpleDateFormat.format(date);
		return d;
	} 
	public static String getStringdateYMDHMS(Date date){
		String d;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		d=simpleDateFormat.format(date);
		return d;
	} 
	public static String getStringdateYMDHMS(Timestamp date){
		String d;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(date==null) return null;
			d=simpleDateFormat.format(date);
		return d;
	} 
	public static String getStringdateHMSS(Date date){
		String d;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss_SSS");
		d=simpleDateFormat.format(date);
		return d;
	} 
	public static Date  getDate(String str){
		Date date = null;
		DateFormat dateFormat=DateFormat.getDateInstance();
		try {
			date=dateFormat.parse(str);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
			
	}
		
	public static String getString(Date date){
		String s=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		s=sdf.format(date);	
		return s;
	}
	public  static Date getDateString(String date){
		Date d=null;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		d=simpleDateFormat.parse(date,new ParsePosition(0));
		return d;
	}

	public static Date getDateFromString1(String date){
		Date s=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		s=sdf.parse(date, new ParsePosition(0));
		return s;
	}
	
	
	public static Date getDateFromString(String date){
		Date s=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		s=sdf.parse(date, new ParsePosition(0));
		return s;
	}
	public static String getStringAll1(Date date){
		String s=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		s=sdf.format(date);	
		return s;
	}
	/**
	 * @see 根据输入的date和format输出时间字串
	 * @param date
	 * @param format
	 * @return "yyyy-MM-dd_HH-mm-ss"
	 */
	public static String getDateString(Date date,String format){
		String s=new SimpleDateFormat(format).format(date);	
		return s;
	}
	
	public static String getLongToString(Long time){
		String s=null;
		Date date=new Date(time);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		s=sdf.format(date);
		return s;
	}
	public static Long addTime(String StringDate ,int ss,int unit_int){
		Long time=null;
		String unit = "";
		Date date;
		try {
			date = new SimpleDateFormat("yyyy/mm/dd HH:MM:ss").parse(StringDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			switch(unit_int){
			case 1: unit="年" ;break;
			case 2:unit="季度" ;break;
			case 3:unit="月" ;break;
			case 4:unit="周" ;break;
			case 5:unit="日" ;break;
			case 6:unit="时" ;break;
			case 7:unit="分" ;break;
			case 8:unit="秒" ;break;
			}
			if(unit.equals("年")){
				cal.add(Calendar.YEAR, ss);
			}if(unit.equals("月")){
				cal.add(Calendar.MONTH, ss);
			}if(unit.equals("日")){
				cal.add(Calendar.DAY_OF_MONTH, ss);
			}if(unit.equals("时")){
				cal.add(Calendar.HOUR_OF_DAY, ss);
			}if(unit.equals("分")){
				cal.add(Calendar.MINUTE, ss);
			}if(unit.equals("秒")){
				cal.add(Calendar.SECOND, ss);
			}if(unit.equals("季度")){
				cal.add(Calendar.MONTH, ss*3);
			}if(unit.equals("周")){
				cal.add(Calendar.WEEK_OF_MONTH, ss);
			}
			time=cal.getTime().getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			time=null;
		}
		return time;
		
	}
	/**
	 * 转换单据号为时间格式
	 * @param serial
	 * @return
	 */
	public static String transformSerial(Long serial){
		String STRserial=null;
		if(serial!=null){
			Date date = new Date(serial);
			STRserial=DateUtil.getStringAll1(date);
		}
		return STRserial;
	}
	
	//获取下一天的日期
	public static Date nextDay(Date currentDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.DATE, 1);//在日期上加7天
		return cal.getTime();
	}
	//获取下一周的日期
	public static Date nextWeek(Date currentDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.DATE, 7);//在日期上加7天
		return cal.getTime();
	}
	//获取本周日的日期
	/**
	 * @see 按照周末为最后一天  获取当周周末的日期
	 */
	public static Date getSunday(Date monday) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(monday);
		cal.add(GregorianCalendar.DATE, 6);//在日期上加6天
		return cal.getTime();
	}
	//获取本周日的日期
	public static Date getSunday(Date currentDate,boolean is_sunday_first) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		if(is_sunday_first)
		{
			cal.add(GregorianCalendar.DATE, 0-currentDate.getDay());//在日期上加0-x天
		}
		else
		{
			cal.add(GregorianCalendar.DATE, 7-currentDate.getDay());//在日期上加7-x天
		}
		return cal.getTime();
		}
	//获取本月1日的日期
	public static Date getFirstDayOfMonth(Date currentDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.set(GregorianCalendar.DAY_OF_MONTH,1);
		return cal.getTime();
	}
	
	//获取本月最后一天的日期
	public static Date getLastDayOfMonth(Date currentDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.MONTH,1);
		cal.set(GregorianCalendar.DAY_OF_MONTH,1);
		cal.add(GregorianCalendar.DATE, -1);
		return cal.getTime();
	}
	
	public static Date getFirstDayOfWeek(Date currentDate,boolean is_sunday_first)
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		if(is_sunday_first)
		{
			cal.set(GregorianCalendar.DAY_OF_WEEK,GregorianCalendar.SUNDAY);
		}else
		{
			cal.set(GregorianCalendar.DAY_OF_WEEK,GregorianCalendar.MONDAY);
		}
		return cal.getTime();
	}
	public static Date getLastDayOfWeek(Date currentDate,boolean is_sunday_first)
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		if(is_sunday_first)
		{
			cal.set(GregorianCalendar.DAY_OF_WEEK,GregorianCalendar.SATURDAY);
		}else
		{
			cal.add(GregorianCalendar.DATE, 7);
			cal.set(GregorianCalendar.DAY_OF_WEEK,GregorianCalendar.SUNDAY);
		}
		return cal.getTime();
	}
	//获取本周1的日期
	public static Date getMonday(Date currentDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.set(GregorianCalendar.DAY_OF_WEEK,GregorianCalendar.MONDAY);
		return cal.getTime();
	}
	//获取下一月的日期
	public static Date getFirstDayOfSeason(Date currentDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);

		int month = cal.get(GregorianCalendar.MONTH)/3*3;
		cal.set(GregorianCalendar.MONTH, month);
		cal.set(GregorianCalendar.DATE,1);
		return cal.getTime();
	}
	//获取下一年的日期
	public static Date getLastDayOfSeason(Date currentDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		int month = (cal.get(GregorianCalendar.MONTH)+3)/3*3;
		System.out.println(month);
		cal.set(GregorianCalendar.MONTH, month);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		cal.add(GregorianCalendar.DATE, -1);
		return cal.getTime();
	}
	//获取下一月的日期
	public static Date nextMonth(Date currentDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.MONTH, 1);//在月份上加1
		return cal.getTime();
	}
	//获取下一年的日期
	public static Date nextYear(Date currentDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.YEAR, 1);//在年上加1
		return cal.getTime();
	}
	public static final int DAY=1;
	public static final int WEEK=2;
	public static final int MONTH=3;
	public static final int QUARTER=4;
	public static final int YEAR=5;
	public static List<String> getDateList(int result_format,Date start,Date end)
	{
		List<String> list = new ArrayList<String>();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		switch(result_format)
		{
			case DAY:
			{
				while(cal.getTime().before(end))
				{
					list.add(DateUtil.getStringdateY_M_D(cal.getTime()));
					cal.add(GregorianCalendar.DATE, 1);
				}
			}
			break;
			case WEEK:
			{
				cal.set(GregorianCalendar.DAY_OF_WEEK,cal.getFirstDayOfWeek());
				while(cal.getTime().before(end))
				{
					list.add(String.format("%04d%02d", 
									cal.get(GregorianCalendar.YEAR),
									cal.get(GregorianCalendar.WEEK_OF_YEAR)));
					cal.add(GregorianCalendar.WEEK_OF_YEAR, 1);
				}
			}
			break;
			case MONTH:
			{
				cal.set(GregorianCalendar.DAY_OF_MONTH,1);
				while(cal.getTime().before(end))
				{
					list.add(String.format("%04d%02d", 
									cal.get(GregorianCalendar.YEAR),
									cal.get(GregorianCalendar.MONTH)+1));
					cal.add(GregorianCalendar.MONTH, 1);
				}
			}
			break;
			case QUARTER:
			{
				cal.set(GregorianCalendar.MONTH,cal.get(GregorianCalendar.MONTH)/3*3);
				cal.set(GregorianCalendar.DAY_OF_MONTH,1);
				
				while(cal.getTime().before(end))
				{
					list.add(String.format("%04d%02d", 
									cal.get(GregorianCalendar.YEAR),
									(cal.get(GregorianCalendar.MONTH)+3)/3));
					cal.add(GregorianCalendar.MONTH, 3);
				}
			}
			break;
			case YEAR:
			{
				cal.set(GregorianCalendar.DAY_OF_YEAR,1);
				while(cal.getTime().before(end))
				{
					list.add(String.format("%04d", 
									cal.get(GregorianCalendar.YEAR)));
					cal.add(GregorianCalendar.YEAR, 1);
				}
			}
			break;
		}
		return list;
	}
	
		  
	public static void main1(String[] args) throws ParseException {
		System.out.println(getDateFromString("2012-01-01 12:12:12").after(getDateFromString("2012-01-01 12:12:11")));
		System.out.println(getDateFromString("2012-01-01 12:12:12").before(getDateFromString("2012-01-01 12:12:11")));
		System.out.println(getLongToString(getDate("2011-01-01 13:12:12").getTime()));
	}
	
	public static Integer getDays(String source){
		Integer days=0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
	    try {
	      Date date = format.parse(source);
	      Calendar calendar = new GregorianCalendar();
	      calendar.setTime(date);
	      days=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return days;
	}
	
	
	public static String getWeek(){
		  final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" };
		   Date date=new Date();
		/*    SimpleDateFormat nowDate = new SimpleDateFormat("yyyy-MM-dd");
	        String s = nowDate.format(date);
	         SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm");*/
	         Calendar calendar = Calendar.getInstance();
	    /*   try {
	           date = sdfInput.parse(s);
	      } catch (ParseException e) {
	         e.printStackTrace();
	      }*/
	    calendar.setTime(date);
	    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
	      if(dayOfWeek<0) dayOfWeek=0;
       return dayNames[dayOfWeek];		
	  	
		
	}
	
	public static Calendar parse(String source) throws ParseException {
		DateFormat df =DateFormat.getDateInstance();
		df.parse(source);
		return df.getCalendar();
	}

	public static void main(String[] args) {
		System.out.println( DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
	}
}
