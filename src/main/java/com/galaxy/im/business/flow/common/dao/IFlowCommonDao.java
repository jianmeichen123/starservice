package com.galaxy.im.business.flow.common.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.report.InformationFile;
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
	//获取人事，法务，财务的认领状态信息
	List<Map<String, Object>> getSopTaskList(Map<String, Object> paramMap);
	//更新文件上传后历史记录的valid=0
	int updateValid(Map<String, Object> paramMap);
	//获取全息报告信息
	List<InformationResult> getReportInfo(Map<String, Object> map);
	//添加全息报告同步的数据
	long addInformationResult(InformationResult result);
	//更新全息报告同步数据
	long updateInformationResult(InformationResult result);
	//获取会议记录信息（投资，闪投===通过）
	Map<String, Object> getMeetingRecordInfo(Map<String, Object> map);
	//交割前事项修改
	int updateCreateUid(InformationListdata ild);
	//交割前事项文件修改
	int updateCreateUid(InformationFile iF);
	
}
