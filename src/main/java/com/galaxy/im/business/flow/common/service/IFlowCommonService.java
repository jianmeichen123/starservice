package com.galaxy.im.business.flow.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.common.db.service.IBaseService;
import com.galaxy.im.common.webconfig.interceptor.operationLog.UrlNumber;

public interface IFlowCommonService extends IBaseService<ProjectBean>{
	Map<String, Object> projectStatus(Map<String, Object> paramMap);
	Boolean vetoProject(Map<String,Object> paramMap);
	boolean enterNextFlow(Map<String,Object> paramMap);
	//创建代办任务
	Long insertsopTask(SopTask bean);
	//获取最新的会议信息
	Map<String, Object> getLatestMeetingRecordInfo(Map<String, Object> paramMap);
	//根据用户id获取所属部门id
	List<Map<String, Object>> getDeptId(Long guserid, HttpServletRequest request, HttpServletResponse response);
	//获取最新上传的文件信息
	Map<String, Object> getLatestSopFileInfo(Map<String, Object> paramMap);
	//获取部门id
	int getDeptIdByDeptName(String name, HttpServletRequest request, HttpServletResponse response);
	//创建会议排期
	Long insertMeetingScheduling(MeetingScheduling bean);
	//上传文件保存
	Long addSopFile(SopFileBean bean);
	//获取上传文件
	List<Map<String, Object>> getSopFileList(Map<String, Object> paramMap);
	//更新上传文件
	long updateSopFile(SopFileBean bean);
	//获取项目基本信息
	SopProjectBean getSopProjectInfo(Map<String, Object> paramMap);
	//根据文件名后缀，确定文档类型
	String getFileType(String fileSuffix);
	//拆分文件名称
	Map<String, String> transFileNames(String fileName);
	//更新代办任务
	Long updateSopTask(SopTask bean);
	//获取人事，法务，财务的认领状态信息
	List<Map<String, Object>> getSopTaskList(Map<String, Object> paramMap);
	//更新文件上传的历史记录的valid=0
	int updateValid(Long id);
	//获取项目状态
	Map<String, Object> getProjectStatus(Map<String, Object> paramMap);
	//文件类型区分
	UrlNumber setNumForFile(int prograss, SopFileBean bean);
	//通过用户id获取角色code
	List<String> selectRoleCodeByUserId(Long guserid, HttpServletRequest request, HttpServletResponse response);
	//通过部门id获取部门名称
	List<Map<String, Object>> getDeptNameByDeptId(Long deptId, HttpServletRequest request,HttpServletResponse response);
	//获取全息报告信息
	List<InformationResult> getReportInfo(Map<String, Object> map);
	//添加全息报告同步的数据
	long addInformationResult(InformationResult result);
	//更新全息报告同步数据
	long updateInformationResult(InformationResult res);
	//获取会议记录信息（投资，闪投===通过）
	Map<String, Object> getMeetingRecordInfo(Map<String, Object> map);
	//获取部门下所有用户
	List<Map<String, Object>> getUserListByDeptId(int fdDeptId);

}
