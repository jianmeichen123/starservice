package com.galaxy.im.bean.operationLog;

public class OperationLogs extends RecordEntity{
	
	private static final long serialVersionUID = -8643891373590843588L;

	private Long uid;

    private String uname;

    private Long userRoleid;

    private String userRole;

    private Long userDepartid;

    private String departName;

    private String operationType;
    private String operationTypeStr;

    private Long projectId;

    private String projectName;

    private String operationContent;
    private String operationContentStr;
    
    private String sopstage;
    
    private String reason;
    
    private String progress;
    
    /**
     * 0项目 1创意
    
    private byte type;
 */
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public Long getUserRoleid() {
        return userRoleid;
    }

    public void setUserRoleid(Long userRoleid) {
        this.userRoleid = userRoleid;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    public Long getUserDepartid() {
        return userDepartid;
    }

    public void setUserDepartid(Long userDepartid) {
        this.userDepartid = userDepartid;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName == null ? null : departName.trim();
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent == null ? null : operationContent.trim();
    }

	public String getOperationTypeStr() {
		return operationTypeStr;
	}

	public void setOperationTypeStr(String operationTypeStr) {
		this.operationTypeStr = operationTypeStr;
	}

	public String getOperationContentStr() {
		return operationContentStr;
	}

	public void setOperationContentStr(String operationContentStr) {
		this.operationContentStr = operationContentStr;
	}

	public String getSopstage() {
		return sopstage;
	}

	public void setSopstage(String sopstage) {
		this.sopstage = sopstage;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}
}