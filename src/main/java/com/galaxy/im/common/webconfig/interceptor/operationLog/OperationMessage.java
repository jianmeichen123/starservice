package com.galaxy.im.common.webconfig.interceptor.operationLog;

import java.io.Serializable;
import java.util.List;

import com.galaxyinternet.framework.core.model.BaseEntity;

/**
 * 
 * @author vincent
 *
 */
public class OperationMessage extends BaseEntity{

	private static final long serialVersionUID = 5693509422650893898L;

	public final static String DEPARTMENT = "投资线/部门";
	public final static String ROLE = "角色";
	public final static String TYPE = "消息类型";
	public final static String PROJECT_NAME = "项目名称";
	public final static String OPERATOR = "操作人";
	public final static String CONTENT = "消息内容";
	public final static String MODULE = "模块";
	
	public static final String COMMENT = "消息";
	
	private String department;
	private String role ;
	private String type;
	private String projectName;
	private String operator;
	private String content;
	private Integer module;
	private Long projectId;
	private Long operatorId;
	private Long belongUid;
	private String belongUname;
	private Long operatorDepartmentId;
	private Long belongDepartmentId;
	private String messageType;
	private byte singleMark;
	
	private boolean flag; 
	
	private String assistColumn;//辅助字段，特殊消息处理  1：添加第一条实际金额发消息通知每月1号添加运营数据
	
	private Serializable userData;
	private List<String> messageList;
	
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getModule() {
		return module;
	}
	public void setModule(Integer module) {
		this.module = module;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getBelongUid() {
		return belongUid;
	}
	public void setBelongUid(Long belongUid) {
		this.belongUid = belongUid;
	}
	public String getBelongUname() {
		return belongUname;
	}
	public void setBelongUname(String belongUname) {
		this.belongUname = belongUname;
	}
	
	
	public Long getOperatorDepartmentId()
	{
		return operatorDepartmentId;
	}
	public void setOperatorDepartmentId(Long operatorDepartmentId)
	{
		this.operatorDepartmentId = operatorDepartmentId;
	}
	public Long getBelongDepartmentId()
	{
		return belongDepartmentId;
	}
	public void setBelongDepartmentId(Long belongDepartmentId)
	{
		this.belongDepartmentId = belongDepartmentId;
	}
	public String getMessageType()
	{
		return messageType;
	}
	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}
	

	
	public String getAssistColumn() {
		return assistColumn;
	}
	public void setAssistColumn(String assistColumn) {
		this.assistColumn = assistColumn;
	}
	public Serializable getUserData() {
		return userData;
	}
	public void setUserData(Serializable userData) {
		this.userData = userData;
	}
	public List<String> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	@Override
	public String toString() {
		return "OperationMessage [department=" + department + ", role=" + role + ", type=" + type + ", projectName="
				+ projectName + ", operator=" + operator + ", content=" + content + ", module=" + module
				+ ", projectId=" + projectId + ", operatorId=" + operatorId + ", flag=" + flag+ ", assistColumn=" + assistColumn+ ",singleMark=" + singleMark+  "]";
	}
	public boolean getFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public byte getSingleMark() {
		return singleMark;
	}
	public void setSingleMark(byte singleMark) {
		this.singleMark = singleMark;
	}
	
	
}