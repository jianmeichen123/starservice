package com.galaxy.im.business.flow.common.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.common.db.IBaseDao;

public interface IFlowCommonDao extends IBaseDao<ProjectBean, Long>{
	Map<String,Object> projectStatus(Map<String,Object> paramMap);
	Integer vetoProject(Map<String,Object> paramMap);
	Integer enterNextFlow(Map<String,Object> paramMap);
	
	//代办任务创建
	Long insertsopTask(SopTask bean);
	//获取最新会议信息
	Map<String, Object> getLatestMeetingRecordInfo(Map<String, Object> paramMap);
	//获取最新上传文件的信息
	Map<String, Object> getLatestSopFileInfo(Map<String, Object> paramMap);
	//创建会议排期
	Long insertMeetingScheduling(MeetingScheduling bean);
	//上传文件保存
	Long addSopFile(SopFileBean bean);
	//获取上传文件信息
	List<Map<String, Object>> getSopFileList(Map<String, Object> paramMap);
	//更新上传文件
	long updateSopFile(SopFileBean bean);
	//获取项目基本信息
	SopProjectBean getSopProjectInfo(Map<String, Object> paramMap);
	//更新代办任务
	Long updateSopTask(SopTask bean);
}
