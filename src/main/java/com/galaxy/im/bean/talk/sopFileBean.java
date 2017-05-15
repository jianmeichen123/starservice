package com.galaxy.im.bean.talk;

import java.util.Date;

import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.db.BaseEntity;

public class sopFileBean extends BaseEntity{

	private static final long serialVersionUID = -1634715566251408151L;
	
	private long belongUid;
	private long projectId;
	private String projectProgress;
	private String fileWorkType;
	private long careerLine;
	private String fileSource;
	private String fileType;
	private String remark;
	private long voucherId;
	private String fileStatus;
	private long fileUid;
	private String filUri;
	private long updatedTime;
	private long createdTime;
	private long fileLength;
	private String fileKey;
	private String bucketName;
	private String fileName;
	private String fileSuffix;
	private long fileValid;
	private int recordType;
	private long meetingId;
	
	public sopFileBean(){
		this.setCreatedTime(DateUtil.getMillis(new Date()));
	}

	public long getBelongUid() {
		return belongUid;
	}

	public void setBelongUid(long belongUid) {
		this.belongUid = belongUid;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}


	public String getFileWorkType() {
		return fileWorkType;
	}

	public void setFileWorkType(String fileWorkType) {
		this.fileWorkType = fileWorkType;
	}

	public long getCareerLine() {
		return careerLine;
	}

	public void setCareerLine(long careerLine) {
		this.careerLine = careerLine;
	}

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public long getFileUid() {
		return fileUid;
	}

	public void setFileUid(long fileUid) {
		this.fileUid = fileUid;
	}

	public String getFilUri() {
		return filUri;
	}

	public void setFilUri(String filUri) {
		this.filUri = filUri;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public long getFileValid() {
		return fileValid;
	}

	public void setFileValid(long fileValid) {
		this.fileValid = fileValid;
	}

	public int getRecordType() {
		return recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	public long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(long meetingId) {
		this.meetingId = meetingId;
	}

	public String getProjectProgress() {
		return projectProgress;
	}

	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}
	
}
