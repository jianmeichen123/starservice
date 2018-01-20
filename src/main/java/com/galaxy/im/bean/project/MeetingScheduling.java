package com.galaxy.im.bean.project;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.db.PagableEntity;

public class MeetingScheduling extends PagableEntity {
	
	private static final long serialVersionUID = -384452291609016650L;

	private Long projectId;						//项目id
	private String meetingType;					//会议类型
	private Integer meetingCount;				//过会次数
	private Date meetingDate;					//会议时间
	private String status;						//会议状态
	private String remark;						//备注
	private Integer scheduleStatus;				//排期结果
	private Timestamp applyTime;				//排期申请时间
	private Timestamp lastTime;					//上次过会时间
	private Timestamp reserveTimeStart;			//排期预约开始时间
	private Timestamp reserveTimeEnd;			//排期预约结束时间
	private Integer isDelete;					//删除标识： 0：正常；1：删除
	
	private String reserveTimeStartStr;
	private String reserveTimeEndStr;
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getMeetingType() {
		return meetingType;
	}
	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}
	public Integer getMeetingCount() {
		return meetingCount;
	}
	public void setMeetingCount(Integer meetingCount) {
		this.meetingCount = meetingCount;
	}
	public Date getMeetingDate() {
		return meetingDate;
	}
	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(Integer scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	public Timestamp getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}
	public Timestamp getLastTime() {
		return lastTime;
	}
	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public Timestamp getReserveTimeStart() {
		if (StringUtils.isNotBlank(this.reserveTimeStartStr)) {
			Date tmp = null;
			Timestamp timestmp = null;
			try {
				tmp = DateUtil
						.convertStringToDateTimeForChina(this.reserveTimeStartStr);
				timestmp = new Timestamp(tmp.getTime());
				this.setReserveTimeStart(timestmp);
				return timestmp;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return reserveTimeStart;
	}

	public void setReserveTimeStart(Timestamp reserveStartTime) {
		this.reserveTimeStart = reserveStartTime;
		if (reserveStartTime != null) {
			reserveTimeStartStr = DateUtil
					.convertDateToStringForChina(reserveTimeStart);
		}
	}
	
	
	public Timestamp getReserveTimeEnd() {
		if (StringUtils.isNotBlank(this.reserveTimeEndStr)) {
			Date tmp = null;
			Timestamp timestmp = null;
			try {
				tmp = DateUtil
						.convertStringToDateTimeForChina(this.reserveTimeEndStr);
				timestmp = new Timestamp(tmp.getTime());
				this.setReserveTimeEnd(timestmp);
				return timestmp;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return reserveTimeEnd;
	}

	public void setReserveTimeEnd(Timestamp reserveTimeEnd) {
		this.reserveTimeEnd = reserveTimeEnd;
		if (reserveTimeEnd != null) {
			reserveTimeEndStr = DateUtil
					.convertDateToStringForChina(reserveTimeEnd);
		}
	}
	
	
}