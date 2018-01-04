package com.galaxy.im.bean.report;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class InformationFile extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

    private Long projectId;

    private Long titleId;

    private String fileType;

    private String fileLength;

    private String fileKey;

    private String fileName;

    private String fileSuffix;

    private String bucketName;

    private String newFileName;

    private String fileUrl;

    private String wide;

    private String high;

    private Integer isValid;

    private Long createdTime;

    private Long createId;

    private Long updatedTime;

    private Long updateId;
    
    private String fileReidsKey;
    
    private String tempPath;
    
    private String deleteids;
    private String infoFileids;
    private Map<String,List<InformationFile>> commonFileList;
    
    private String data;
    private Set<String> titleIds;
    private List<String> fileIds;
    
    
	

	public Map<String, List<InformationFile>> getCommonFileList() {
		return commonFileList;
	}

	public void setCommonFileList(Map<String, List<InformationFile>> commonFileList) {
		this.commonFileList = commonFileList;
	}

	public String getInfoFileids() {
		return infoFileids;
	}

	public void setInfoFileids(String infoFileids) {
		this.infoFileids = infoFileids;
	}

	public String getDeleteids() {
		return deleteids;
	}

	public void setDeleteids(String deleteids) {
		this.deleteids = deleteids;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
    
	private MultipartFile multipartFile;

	  
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
    

    public String getFileReidsKey() {
		return fileReidsKey;
	}

	public void setFileReidsKey(String fileReidsKey) {
		this.fileReidsKey = fileReidsKey;
	}

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    
    public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength == null ? null : fileLength.trim();
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey == null ? null : fileKey.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName == null ? null : bucketName.trim();
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName == null ? null : newFileName.trim();
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public String getWide() {
        return wide;
    }

    public void setWide(String wide) {
        this.wide = wide == null ? null : wide.trim();
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high == null ? null : high.trim();
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public Set<String> getTitleIds()
	{
		return titleIds;
	}
 
	public void setTitleIds(Set<String> titleIds)
	{
		this.titleIds = titleIds;
	}

	public List<String> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<String> fileIds) {
		this.fileIds = fileIds;
	}
	
}