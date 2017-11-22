package com.galaxy.im.bean.clouddisk;

import com.galaxy.im.common.db.PagableEntity;

public class CloudDiskFiles extends PagableEntity{
	private static final long serialVersionUID = 2275533588110326418L;
	private String originalName;
	private String targeName;
	private Long fileSize;
	private String extName;
	private Long ownUser;
	private Long fileSign;
	private Integer fileType;
	private String ossKey;
	private String buckerName;
	private Integer isDel;
	private Long uploadTime;
	private Long uploadUser;
	
	public CloudDiskFiles(){}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getTargeName() {
		return targeName;
	}

	public void setTargeName(String targeName) {
		this.targeName = targeName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public Long getOwnUser() {
		return ownUser;
	}

	public void setOwnUser(Long ownUser) {
		this.ownUser = ownUser;
	}

	public Long getFileSign() {
		return fileSign;
	}

	public void setFileSign(Long fileSign) {
		this.fileSign = fileSign;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getOssKey() {
		return ossKey;
	}

	public void setOssKey(String ossKey) {
		this.ossKey = ossKey;
	}

	public String getBuckerName() {
		return buckerName;
	}

	public void setBuckerName(String buckerName) {
		this.buckerName = buckerName;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Long getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Long uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Long getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(Long uploadUser) {
		this.uploadUser = uploadUser;
	}

}
