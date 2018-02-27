package com.galaxy.im.bean.schedule;



import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.galaxy.im.common.db.PagableEntity;
@JsonInclude(value=JsonInclude.Include.NON_NULL) 
public class ScheduleInfo extends PagableEntity{
	private static final long serialVersionUID = -6443062953917242468L;
	private Long eventId;
	private Long parentId;
	private int type;
	private Long nameId;
	private String name;
	private int projectType;
	private Long projectId;
	private String startTime;
	private String endTime;
	private byte isAllday;
	private long wakeupId;
	private String remark;
	private int significance;
	private String callonAddress;
	private Long callonPerson;
	private String userName;     // 日历创建人姓名
	private String visitType;  //用来对应消息列表的type(拜访1.4)
	private Integer isDel;  //逻辑删除的字段(是否删除字段0:未删除,1:已删除 )
	private String remind;   // 提醒时间
	private String schedulePerson;//拜访人名称]
	
	private Date date ;//处理后的日期
 
	private Integer year;
	private Integer month;
	private Integer day;
	
	private Long idIsNotEq;      // id != idIsNotEq
	private String bqStartTime;  // 日程开始  起始时间     startTime  >   bqStartTime
    private String bqEndTime;    // 日程开始  结束时间     startTime  <   bqEndTime
    private String eqStartTime;  // 日程结束  起始时间     endTime >  eqStartTime
	
    private String sbTimeForAllday;    // 日程开始  起始时间     startTime  >   sbTimeForAllday
    private String seTimeForAllday;    // 日程开始  结束时间     startTime  <   seTimeForAllday
    private String queryForMounth;    //xml 查询月视图 特标识
    private String lastMouthDay;//当月最后一天
    
    private String dateTime;//用于-同步日历（同步苹果日历） 当下时间

	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getNameId() {
		return nameId;
	}
	public void setNameId(Long nameId) {
		this.nameId = nameId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProjectType() {
		return projectType;
	}
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public long getWakeupId() {
		return wakeupId;
	}
	public void setWakeupId(long wakeupId) {
		this.wakeupId = wakeupId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getSignificance() {
		return significance;
	}
	public void setSignificance(int significance) {
		this.significance = significance;
	}
	public String getCallonAddress() {
		return callonAddress;
	}
	public void setCallonAddress(String callonAddress) {
		this.callonAddress = callonAddress;
	}
	public Long getCallonPerson() {
		return callonPerson;
	}
	public void setCallonPerson(Long callonPerson) {
		this.callonPerson = callonPerson;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getRemind() {
		return remind;
	}
	public void setRemind(String remind) {
		this.remind = remind;
	}
	public String getSchedulePerson() {
		return schedulePerson;
	}
	public void setSchedulePerson(String schedulePerson) {
		this.schedulePerson = schedulePerson;
	}
	public byte getIsAllday() {
		return isAllday;
	}
	public void setIsAllday(byte isAllday) {
		this.isAllday = isAllday;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Long getIdIsNotEq() {
		return idIsNotEq;
	}
	public void setIdIsNotEq(Long idIsNotEq) {
		this.idIsNotEq = idIsNotEq;
	}
	public String getBqStartTime() {
		return bqStartTime;
	}
	public void setBqStartTime(String bqStartTime) {
		this.bqStartTime = bqStartTime;
	}
	public String getBqEndTime() {
		return bqEndTime;
	}
	public void setBqEndTime(String bqEndTime) {
		this.bqEndTime = bqEndTime;
	}
	public String getEqStartTime() {
		return eqStartTime;
	}
	public void setEqStartTime(String eqStartTime) {
		this.eqStartTime = eqStartTime;
	}
	public String getSbTimeForAllday() {
		return sbTimeForAllday;
	}
	public void setSbTimeForAllday(String sbTimeForAllday) {
		this.sbTimeForAllday = sbTimeForAllday;
	}
	public String getSeTimeForAllday() {
		return seTimeForAllday;
	}
	public void setSeTimeForAllday(String seTimeForAllday) {
		this.seTimeForAllday = seTimeForAllday;
	}
	
	public String getLastMouthDay() {
		return lastMouthDay;
	}
	public void setLastMouthDay(String lastMouthDay) {
		this.lastMouthDay = lastMouthDay;
	}
	public String getVisitType() {
		return visitType;
	}
	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	public String getQueryForMounth() {
		return queryForMounth;
	}
	public void setQueryForMounth(String queryForMounth) {
		this.queryForMounth = queryForMounth;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
}
