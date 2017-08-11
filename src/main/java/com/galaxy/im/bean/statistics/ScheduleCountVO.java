package com.galaxy.im.bean.statistics;

import java.io.Serializable;
/**
 * 记录当日投资经理，合伙人，ceo的排期 各个会议的个数
 * @author zhangchunyuan
 *
 */
public class ScheduleCountVO implements Serializable {
	
	private static final long serialVersionUID = 4773958600352172529L;
	
	/**
	 * 会议类型 
	 * ceo评审  meetingType:2
		
	        立项会类型 meetingType:3
		
	        投诀会类型 meetingType:4
		
	 */
	private String meetingType;
	
	/**
	 * 会议个数
	 */
	private Integer number;

	/*public ScheduleCountVO(String meetingType, Integer number) {
		this.meetingType = meetingType;
		this.number = number;
	}*/

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
}
