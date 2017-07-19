package com.galaxy.im.bean.user;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.galaxy.im.common.DateUtil;


public class User extends BaseUser {
	private static final long serialVersionUID = 1L;

	private String realName;// 真实姓名
	private String nickName;// 登录名称
	private String email;// 邮箱
	private String mobile;// 手机
	private String telephone;// 座机
	private String status;// 账户状态 正常 禁用
	private String type;// 账户类型
	private String employNo;// 工号
	private Long departmentId;// 部门Id
	private String departmentName;// 部门名称
	private String role;// 角色
	private String password;// 密码
	private String originPassword;// 初始密码
	private Boolean gender;// 性别
	private Date birth;// 生日
	private String birthStr;
	private String address;// 地址
	private Boolean isAdmin;// 是否管理员
	
	//2016/8/23 修改app端获取当前登录人的版本号
	private String version;
	//2016/12/21 添加app端的新字段
	private boolean isCurrentUser;
	
	//部门Ids2016/12/21
	private List<Long> departmentIds;
	
	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	@NotNull(message="角色不能为空")
	private Long roleId;// 角色Id

	private List<Long> ids;
	private String nameMbLike;
	
	//2017/4/6 修改 添加设备区分开 
	
	private String aclient;         //客户端名称
	/*
	 * private String salt; private String originSalt;
	 */
	private String androidVersion;   //获取Android版本号（系统版本号）
	
	private String androidClient;    //登录设备（手机设备） 

	public String getBirthStr() {
		return birthStr;
	}

	public String getAclient() {
		return aclient;
	}

	public void setAclient(String aclient) {
		this.aclient = aclient;
	}

	public void setBirthStr(String birthStr) {
		this.birthStr = birthStr;
	}

	public String getNameMbLike() {
		return nameMbLike;
	}

	public void setNameMbLike(String nameMbLike) {
		this.nameMbLike = nameMbLike;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOriginPassword() {
		return originPassword;
	}

	public void setOriginPassword(String originPassword) {
		this.originPassword = originPassword;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public Date getBirth() {
		if (StringUtils.isNotBlank(this.getBirthStr())) {
			try {
				this.setBirth(DateUtil.convertStringToDate(this.getBirthStr()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public String getEmployNo() {
		return employNo;
	}

	public void setEmployNo(String employNo) {
		this.employNo = employNo;
	}
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isCurrentUser() {
		return isCurrentUser;
	}

	public void setCurrentUser(boolean isCurrentUser) {
		this.isCurrentUser = isCurrentUser;
	}

	public List<Long> getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(List<Long> departmentIds) {
		this.departmentIds = departmentIds;
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
