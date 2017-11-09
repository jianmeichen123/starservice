package com.galaxy.im.common;

import org.springframework.context.ApplicationContext;

public class StaticConst {
	public static ApplicationContext ctx;
	
	public static final String CONST_SESSION_ID_KEY = "sessionId";		//用户
	public static final String CONST_USER_ID_KEY = "guserId";			//用户ID对应的Key
	public static final String CONST_SYSTEM_TYPE_KEY = "gt";			//请求数据的系统类型（ios,android,pc）
	public final static String SESSION_USER_KEY = "galax_session_user";
	
	public static final String transfer_projects_key = "transfer_projects_key";		//项目移交key
	
	public static final String TEST_USERNAME = "jingjinghan";		//测试账号
	public static final String TEST_REALNAME = "韩晶晶";				//测试账号
	
	
	public static final String pushAddSchedule = "galaxy/scheduleInfo/pushAddSchedule";
	public static final String pushUpdateSchedule = "galaxy/scheduleInfo/pushUpdateSchedule";
	public static final String pushDeleteSchedule = "galaxy/scheduleInfo/pushDeleteSchedule";
	
	
	
	public static final String getShareUserList = "user/getShareUserList"; 				//power外部接口：获取拜访共享列表
	public static final String getCreadIdInfo = "user/getCreadIdInfo";   				//power外部接口：获取用户名和部门名称
	public static final String getDeptIdByDeptName = "depart/getDeptIdByDeptName";   	//power外部接口：获取部门名称
	public static final String login = "login/userLoginForApp";							//登录
	public static final String resetPwd = "user/resetPasswordForApp";					//重置密码
	public static final String getCareerLineList = "depart/getCareerLineList";			//获得事业线
	public static final String getRoleCodeByUserId = "role/getRoleCodeByUserId";		//角色code
	public static final String getDeptInfo = "depart/getDeptInfo";						//通过部门id获取部门名称
	public static final String getUserByIds = "user/getUserByIds";						//通过id，获取用户名称
	public static final String findUserByName = "user/findUserByName";					//通过用户名称获取用户信息
	public static final String getUsersByKey = "user/getUsersByKey";					//用户名称模糊查询
	public static final String getLeafDepartList = "depart/getLeafDepartList";			//事业部列表
	public static final String getUsersByDepId = "user/getUsersByDepId";				//事业部下的所有用户
	
	//过滤器白名单
	public static final String FILTER_WHITE_LOGIN = "userlogin/login";		//用户登录白名单
	
	
	
	public final static Long TOKEN_IN_REDIS_TIMEOUT_SECONDS = 60 * 60L;		//Token（防止重复提交）	的Redis过期时间
	public final static String TOKEN_REMOVE_KEY = "token_remove";
	
	
	//任务名称
	public static final String TASK_NAME_WSJL = "完善简历";
	public static final int TASK_FLAG_WSJL = 0;
	public static final String TASK_NAME_SCTZYXS = "上传投资意向书";
	public static final int TASK_FLAG_SCTZYXS = 1;
	public static final String TASK_NAME_RSJD = "上传人事尽职调查报告";
	public static final int TASK_FLAG_RSJD = 2;
	public static final String TASK_NAME_FWJD = "上传法务尽职调查报告";
	public static final int TASK_FLAG_FWJD = 3;
	public static final String TASK_NAME_CWJD = "上传财务尽职调查报告";
	public static final int TASK_FLAG_CWJD = 4;
	public static final String TASK_NAME_YWJD = "上传业务尽职调查报告";
	public static final int TASK_FLAG_YWJD = 5;
	public static final String TASK_NAME_TZXY = "上传投资协议";
	public static final int TASK_FLAG_TZXY = 6;
	public static final String TASK_NAME_GQZR = "上传股权转让协议";
	public static final int TASK_FLAG_GQZR = 7;
	public static final String TASK_NAME_ZJBF = "上传资金拨付凭证";
	public static final int TASK_FLAG_ZJBF = 8;
	public static final String TASK_NAME_GSBG = "上传工商转让凭证";
	public static final int TASK_FLAG_GSBG = 9;
	//任务类型
	public static final String TASK_TYPE_SPLC = "taskType:1";			//审批流程
	public static final String TASK_TYPE_XTBG = "taskType:2";			//协同办公
	//任务状态
	public static final String TASK_STATUS_DRL = "taskStatus:1";		//待认领
	public static final String TASK_STATUS_DWG = "taskStatus:2";		//待完工
	public static final String TASK_STATUS_YWG = "taskStatus:3";		//已完工
	//任务排序
	public static final int TASK_ORDER_NORMAL = 0;						//正常
	public static final int TASK_ORDER_JINJ = 1;						//紧急
	public static final int TASK_ORDER_TEJ = 2;							//特急
	//会议类型
	public static final String MEETING_TYPE_INTERNAL = "meetingType:1";		//内部评审
	public static final String MEETING_TYPE_CEO = "meetingType:2";			//CEO评审
	public static final String MEETING_TYPE_APPROVAL = "meetingType:3";		//立项会
	public static final String MEETING_TYPE_INVEST = "meetingType:4";		//投决会
	public static final String MEETING_TYPE_BUSINESS = "meetingType:5";		//商务谈判
	//流程阶段
	public static final String PROJECT_PROGRESS_1 = "projectProgress:1";	//接触访谈
	public static final String PROJECT_PROGRESS_2 = "projectProgress:2";	//内部评审
	public static final String PROJECT_PROGRESS_3 = "projectProgress:3";	//CEO评审
	public static final String PROJECT_PROGRESS_4 = "projectProgress:4";	//立项会
	public static final String PROJECT_PROGRESS_5 = "projectProgress:5";	//投资意向书
	public static final String PROJECT_PROGRESS_6 = "projectProgress:6";	//尽职调查
	public static final String PROJECT_PROGRESS_7 = "projectProgress:7";	//投资决策会
	public static final String PROJECT_PROGRESS_8 = "projectProgress:8";	//投资协议
	public static final String PROJECT_PROGRESS_9 = "projectProgress:9";	//股权交割
	public static final String PROJECT_PROGRESS_10 = "projectProgress:10";	//投后运营
	public static final String PROJECT_PROGRESS_11 = "projectProgress:11";	//会后商务谈判
	//项目状态
	public static final String PROJECT_STATUS_0 = "projectStatus:0";	//跟进中
	public static final String PROJECT_STATUS_1 = "projectStatus:1";	//投后运营
	public static final String PROJECT_STATUS_2 = "projectStatus:2";	//已否决
	public static final String PROJECT_STATUS_3 = "projectStatus:3";	//已退出
	//项目类型
	public static final String PROJECT_TYPE_1 = "projectType:1";		//外部投资
	public static final String PROJECT_TYPE_2 = "projectType:2";		//内部创建
	//会议结论
	public static final String MEETING_RESULT_1 = "meetingResult:1";	//通过
	public static final String MEETING_RESULT_2 = "meetingResult:2";	//待定
	public static final String MEETING_RESULT_3 = "meetingResult:3";	//否决
	//上传文件类型
	public static final String FILE_WORKTYPE_1 = "fileWorktype:1";	    	//业务尽职调查报告
	public static final String FILE_WORKTYPE_2 = "fileWorktype:2";	    	//人力资源尽职调查报告
	public static final String FILE_WORKTYPE_3 = "fileWorktype:3";	    	//法务尽职调查报告
	public static final String FILE_WORKTYPE_4 = "fileWorktype:4";	    	//财务尽职调查报告
	public static final String FILE_WORKTYPE_5 = "fileWorktype:5";	    	//投资意向书
	public static final String FILE_WORKTYPE_6 = "fileWorktype:6";	    	//投资协议
	public static final String FILE_WORKTYPE_7 = "fileWorktype:7";	    	//股权转让协议
	public static final String FILE_WORKTYPE_8 = "fileWorktype:8";	    	//工商转让凭证
	public static final String FILE_WORKTYPE_9 = "fileWorktype:9";	    	//资金拨付凭证
	public static final String FILE_WORKTYPE_10 = "fileWorktype:10";	    //公司资料
	public static final String FILE_WORKTYPE_11 = "fileWorktype:11";	    //财务预测报告
	public static final String FILE_WORKTYPE_12 = "fileWorktype:12";	    //商业计划书
	public static final String FILE_WORKTYPE_13 = "fileWorktype:13";	    //业务尽职调查清单
	public static final String FILE_WORKTYPE_14 = "fileWorktype:14";	    //人力资源尽职调查清单
	public static final String FILE_WORKTYPE_15 = "fileWorktype:15";	    //法务尽职调查清单
	public static final String FILE_WORKTYPE_16 = "fileWorktype:16";	    //财务尽职调查清单
	public static final String FILE_WORKTYPE_17 = "fileWorktype:17";	    //立项报告
	public static final String FILE_WORKTYPE_18 = "fileWorktype:18";	    //尽职调查启动会报告
	public static final String FILE_WORKTYPE_19 = "fileWorktype:19";	    //尽职调查总结会报告
	//部门名称
	public static final String DEPT_NAME_HR = "人力资源部";	    	//人力资源部
	public static final String DEPT_NAME_LAW = "法务部";	    	//法务部
	public static final String DEPT_NAME_FD = "财务部";	    	//财务部
	//文件状态
	public static final String FILE_STATUS_1 = "fileStatus:1";	    	//缺失
	public static final String FILE_STATUS_2 = "fileStatus:2";	    	//已上传
	public static final String FILE_STATUS_3 = "fileStatus:3";	    	//已签署
	public static final String FILE_STATUS_4 = "fileStatus:4";	    	//已放弃
	//文件类型
	public static final String FILE_TYPE_1 = "fileType:1";	    	//文档
	public static final String FILE_TYPE_2 = "fileType:2";	    	//音频文件
	public static final String FILE_TYPE_3 = "fileType:3";	    	//视频文件
	public static final String FILE_TYPE_4 = "fileType:4";	    	//图片

	
	//角色类型
	public static final String TZJL = "TZJL";
	public static final String HHR = "HHR";
	public static final String CEO = "CEO";
	public static final String CEOMS = "CEOMS";
	public static final String DSZ = "DSZ";
	public static final String DMS = "DMS";

	
	
	
	
	
}
