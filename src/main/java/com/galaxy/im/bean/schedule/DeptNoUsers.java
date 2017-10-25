package com.galaxy.im.bean.schedule;

import java.io.Serializable;
import java.util.List;


/**
 * 共享日程
 */
public class DeptNoUsers implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long deptId;		//部门id
	List<Long> userIds;    		// 共享的人的 ids
	private Integer userCount; 	//部门下所选人 数量
	
	
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	public List<Long> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}
	
	public Integer getUserCount() {
		return userCount;
	}
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
	
}