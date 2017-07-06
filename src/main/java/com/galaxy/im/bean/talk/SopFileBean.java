package com.galaxy.im.bean.talk;

import com.galaxy.im.common.db.BaseEntity;

public class SopFileBean extends BaseEntity{

	private static final long serialVersionUID = -1634715566251408151L;
	
	private long belongUid;					//认领人id
	private long projectId;					//关联项目id
	private String projectProgress;			//项目进度
	private String fileWorkType;			//业务分类
	private long careerLine;				//所属事业线
	private String fileSource;				//档案来源 1:内部 2:外部
	private String fileType;				//存储类型 1:文档 2:图片 3:音视频
	private String remark;					//档案摘要
	private long voucherId;					//签署证明文件ID
	private String fileStatus;				//档案状态 缺失，已上传，已审核... 
	private long fileUid;					//上传人/起草者 uid
	private String filUri;					//存储地址
	private long fileLength;				//档案大小
	private String fileKey;					//档案阿里云存储Key
	private String bucketName;				//档案bucketName
	private String fileName;				//档案名称
	private String fileSuffix;				//档案文件后缀
	private long fileValid;					//文档是否有效，默认1表示有效，0表示无效
	private int recordType;					//是否为创意的标识
	private long meetingId;					//会议ID 关联sop_meeting_record
	private long interviewRecordId;			//访谈记录id
	private long meetinRecordId;			//会议记录id
	
	public SopFileBean(){
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

	public long getInterviewRecordId() {
		return interviewRecordId;
	}

	public void setInterviewRecordId(long interviewRecordId) {
		this.interviewRecordId = interviewRecordId;
	}

	public long getMeetinRecordId() {
		return meetinRecordId;
	}

	public void setMeetinRecordId(long meetinRecordId) {
		this.meetinRecordId = meetinRecordId;
	}
	
}
