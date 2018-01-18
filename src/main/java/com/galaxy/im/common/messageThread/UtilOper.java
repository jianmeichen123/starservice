package com.galaxy.im.common.messageThread;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.galaxy.im.bean.message.ScheduleMessageBean;
import com.galaxy.im.common.DateUtil;

public class UtilOper {

	
	/**
	 * 根据传入的    原始的  message.content , 重新封装 message
	 */
	public static String getMessContent(ScheduleMessageBean mess){
		
		//您有一个日程将于 <time>2017-4-15 16:56</time> 开始，日程名称"<name>测试其qqqqq程测试</name>"。<id>27</id><type>1.3</type>
		String content = mess.getContent();
				
		try {
			if(content.contains("time")){
				String timeMark = content.substring(content.indexOf("<time>"), content.indexOf("</time>")+"</time>".length());
				String nameMark = content.substring(content.indexOf("<name>"), content.indexOf("</name>")+"</name>".length());
				
				String nameStr = nameMark.substring(nameMark.indexOf("<name>")+"<name>".length(), nameMark.indexOf("</name>"));
				String beginTimeStr = timeMark.substring(timeMark.indexOf("<time>")+"<time>".length(), timeMark.indexOf("</time>"));
				String timeFormat = DateUtil.convertTimeForSuchDay(beginTimeStr);
				content = content.replace(timeMark, timeFormat).replace(nameMark, nameStr);
			}else if(content.contains("msg")){
				String uNameMark = content.substring(content.indexOf("<uname>"), content.indexOf("</uname>")+"</uname>".length());
				String pNameMark = content.substring(content.indexOf("<pname>"), content.indexOf("</pname>")+"</pname>".length());
				String msgMark = content.substring(content.indexOf("<msg>"), content.indexOf("</msg>")+"</msg>".length());
				
				String msgStr = msgMark.substring(msgMark.indexOf("<msg>")+"<msg>".length(), msgMark.indexOf("</msg>"));
				String uNameStr = uNameMark.substring(uNameMark.indexOf("<uname>")+"<uname>".length(), uNameMark.indexOf("</uname>"));
				String pNameStr = pNameMark.substring(pNameMark.indexOf("<pname>")+"<pname>".length(), pNameMark.indexOf("</pname>"));
				content = content.replace(uNameMark, uNameStr).replace(pNameMark, pNameStr).replace(msgMark, msgStr);
			}else if(content.contains("dname")){
				String uNameMark = content.substring(content.indexOf("<uname>"), content.indexOf("</uname>")+"</uname>".length());
				String pNameMark = content.substring(content.indexOf("<pname>"), content.indexOf("</pname>")+"</pname>".length());
				String dNameMark = content.substring(content.indexOf("<dname>"), content.indexOf("</dname>")+"</dname>".length());
				
				String dNameStr = dNameMark.substring(dNameMark.indexOf("<dname>")+"<dname>".length(), dNameMark.indexOf("</dname>"));
				String uNameStr = uNameMark.substring(uNameMark.indexOf("<uname>")+"<uname>".length(), uNameMark.indexOf("</uname>"));
				String pNameStr = pNameMark.substring(pNameMark.indexOf("<pname>")+"<pname>".length(), pNameMark.indexOf("</pname>"));
				content = content.replace(uNameMark, uNameStr).replace(pNameMark, pNameStr)
						.replace(dNameMark, dNameStr);
			}else{
				String uNameMark = content.substring(content.indexOf("<uname>"), content.indexOf("</uname>")+"</uname>".length());
				String pNameMark = content.substring(content.indexOf("<pname>"), content.indexOf("</pname>")+"</pname>".length());
				
				String uNameStr = uNameMark.substring(uNameMark.indexOf("<uname>")+"<uname>".length(), uNameMark.indexOf("</uname>"));
				String pNameStr = pNameMark.substring(pNameMark.indexOf("<pname>")+"<pname>".length(), pNameMark.indexOf("</pname>"));
				content = content.replace(uNameMark, uNameStr).replace(pNameMark, pNameStr);
			}
		} catch (Exception e) {
			return mess.getContent();
		}
		
		return content;
	}
	
	
	
	
	
	/**
		1 ：5分钟前         7 ：1天前
		2 ：15分钟前      8 ：2天前
		3 ：30分钟前      9 ：3天前
		4 ：1小时前      10 ：1周前
		5 ：1天前           11 ：不提醒
		6 ：不提醒
	 * 
	 * @param 
	 * 			  startTime: 开始时间
	 *            isAllday：   是否全天 0:否 1:是 
	 *            dictId：         schedule_dict字典表id
	 * @throws ParseException 
	 */
	public static Long getSendTimeBy(String startTime, byte isAllday, Long dictId) throws ParseException {
		Long sendTime = null;

		if (startTime == null || dictId == null)
			return null;

		String format = null;
		if (isAllday == 0) { // 非全天 2017-04-12 17:58:00
			format = "yyyy-MM-dd HH:mm";
			Long slong = DateUtil.stringToLong(startTime, format);

			switch (dictId.intValue()) {
			case 1:
				sendTime = slong - (long) 5 * 60 * 1000;
				break;
			case 2:
				sendTime = slong - (long) 15 * 60 * 1000;
				break;
			case 3:
				sendTime = slong - (long) 30 * 60 * 1000;
				break;
			case 4:
				sendTime = slong - (long) 1 * 60 * 60 * 1000;
				break;
			case 5:
				sendTime = slong - (long) 1 * 24 * 60 * 60 * 1000;
				break;
			case 6:
				break;
			default:
				break;
			}
		} else { // 全天  2017-04-12
			format = "yyyy-MM-dd";
			Date sdate = DateUtil.convertStringToDate(startTime, format);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdate);

			switch (dictId.intValue()) {
			case 7:
				// calendar.roll(Calendar.DATE, -1); 仅 date域发送变化
				calendar.add(Calendar.DATE, -1);
				calendar.set(Calendar.HOUR_OF_DAY, 9);

				sendTime = calendar.getTimeInMillis();
				break;
			case 8:
				calendar.add(Calendar.DATE, -2);
				calendar.set(Calendar.HOUR_OF_DAY, 9);

				sendTime = calendar.getTimeInMillis();
				break;
			case 9:
				calendar.add(Calendar.DATE, -3);
				calendar.set(Calendar.HOUR_OF_DAY, 9);

				sendTime = calendar.getTimeInMillis();
				break;
			case 10:
				calendar.add(Calendar.DATE, -7);
				calendar.set(Calendar.HOUR_OF_DAY, 9);

				sendTime = calendar.getTimeInMillis();
				break;
			case 11:
				break;
			default:
				break;
			}
		}

	/*	String tim = DateUtil.longString(sendTime);
		System.out.println(tim);*/
		return sendTime;
	}
	
	
	
	
	
	
	
	
}
