package com.galaxyinternet.model.user;

import java.sql.Timestamp;
import java.util.Date;

import com.galaxyinternet.framework.core.model.PagableEntity;
/**
 * APP端用户登录历史记录
 * @author LZJ
 * @ClassName  : UserLogonHis  
 * @Version  版本   
 * @ModifiedByLZJ  
 * @Copyright  Galaxyinternet  
 * @date  2016年7月11日 下午5:38:56
 */
public class UserLogonHis extends PagableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	private String accessClient ; // '访问客户端名称',
	private long userId  ; // '用户ID',
	private String  nickName; //'登录名称',
	private Date loginDate ; //'登录日期'
	private Timestamp  initLogonTime ; // '首次登录时间',
	private Timestamp  lastLogonTime ;  // '最后登录时间',
	private Integer  logonTimes;  //'当天登录累计次数',
	//新增app端版本号
	private String appVersion;
	//定义的返回时间
	private String inTime;
	//获取用户的登录总数
	private Long strCount;
	
	//2017/4/6 修改 
	private String androidVersion;    //Android版本号（系统版本号）
	
	private String androidClient;     //登录设备（手机设备）
	
	public Timestamp getLastLogonTime() {
		return lastLogonTime;
	}
	public void setLastLogonTime(Timestamp lastLogonTime) {
		this.lastLogonTime = lastLogonTime;
	}
	public String getAccessClient() {
		return accessClient;
	}
	public void setAccessClient(String accessClient) {
		this.accessClient = accessClient;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Timestamp getInitLogonTime() {
		return initLogonTime;
	}
	public void setInitLogonTime(Timestamp initLogonTime) {
		this.initLogonTime = initLogonTime;
	}
	public Integer getLogonTimes() {
		return logonTimes;
	}
	public void setLogonTimes(Integer logonTimes) {
		this.logonTimes = logonTimes;
	}
	public Long getStrCount() {
		return strCount;
	}
	public void setStrCount(Long strCount) {
		this.strCount = strCount;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getAndroidVersion() {
		return androidVersion;
	}
	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}
	public String getAndroidClient() {
		return androidClient;
	}
	public void setAndroidClient(String androidClient) {
		this.androidClient = androidClient;
	}

	

	
	
	
}
