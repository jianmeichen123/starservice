package com.galaxy.im.business.flow.common.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.common.db.service.IBaseService;

public interface IFlowCommonService extends IBaseService<ProjectBean>{
	Map<String, Object> projectStatus(Map<String, Object> paramMap);
	Boolean vetoProject(Map<String,Object> paramMap);
	boolean enterNextFlow(Map<String,Object> paramMap);
	//代办任务创建
	Long insertsopTask(SopTask bean);
	//获取最新的会议信息
	Map<String, Object> getLatestMeetingRecordInfo(Map<String, Object> paramMap);
	//根据用户id获取所属部门id
	long getDeptId(Long guserid, HttpServletRequest request, HttpServletResponse response);
	//获取最新上传的文件信息
	Map<String, Object> getLatestSopFileInfo(Map<String, Object> paramMap);

}
