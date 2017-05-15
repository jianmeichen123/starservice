package com.galaxy.im.bean.talk;

import java.beans.Transient;
import java.util.Date;

import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.db.PagableEntity;

public class TalkRecordBean extends PagableEntity{


	private static final long serialVersionUID = 7937887405956613675L;
	
	private String projectName;		//项目名称
	
	private long projectId;			//关联项目id
    private long fileId;			//关联附件id
	private String viewDate;		//记录时间
	
	private String viewTarget;      //访谈对象
    private String viewNotes;		//访谈纪要
    private String viewNotesText;	//访谈纪要文本值
    private long createdId;			//创建人
    private long createTime;		//创建时间
    private long scheduleId;		//拜访id
    
    public TalkRecordBean(){
		this.setCreateTime(DateUtil.getMillis(new Date()));
	}
    
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public String getViewNotes() {
		return viewNotes;
	}
	public void setViewNotes(String viewNotes) {
		this.viewNotes = viewNotes;
	}
	public String getViewNotesText() {
		return viewNotesText;
	}
	public void setViewNotesText(String viewNotesText) {
		this.viewNotesText = viewNotesText;
	}
	public long getCreatedId() {
		return createdId;
	}
	public void setCreatedId(long createdId) {
		this.createdId = createdId;
	}
	public long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	public String getViewTarget() {
		return viewTarget;
	}
	public void setViewTarget(String viewTarget) {
		this.viewTarget = viewTarget;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Transient
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public static String dateStrformat(String dateStr){  //2016-05-27 16:00:00   19
		int len = dateStr.length();
		if( dateStr.indexOf("/") != -1){
			dateStr = dateStr.replaceAll("/", "-");
		}
		switch (len) {
		case 10:
			dateStr = dateStr + " 00:00:00";
			break;
		case 13:
			dateStr = dateStr + ":00:00";
			break;
		case 16:
			dateStr = dateStr + ":00";
			break;
		default:
			break;
		}
		return dateStr;
	}

	public String getViewDate() {
		return viewDate;
	}

	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}


}
