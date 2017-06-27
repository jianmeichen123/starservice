package com.galaxy.im.common;

import org.springframework.context.ApplicationContext;

public class StaticConst {
	public static ApplicationContext ctx;
	
	public static final String CONST_SESSION_ID_KEY = "sessionId";		//用户
	public static final String CONST_USER_ID_KEY = "guserId";			//用户ID对应的Key
	public static final String CONST_SYSTEM_TYPE_KEY = "gt";			//请求数据的系统类型（ios,android,pc）
	
	
	public static final String transfer_projects_key = "transfer_projects_key";		//项目移交key
	
	
	
	public static final String pushAddSchedule = "galaxy/scheduleInfo/pushAddSchedule";
	public static final String pushUpdateSchedule = "galaxy/scheduleInfo/pushUpdateSchedule";
	public static final String pushDeleteSchedule = "galaxy/scheduleInfo/pushDeleteSchedule";
	
	
	
	public static final String getShareUserList = "user/getShareUserList"; 		//power外部接口：获取共享列表
	public static final String getCreadIdInfo = "user/getCreadIdInfo";   		//power外部接口：获取用户名和部门名称
	public static final String login = "login/userLoginForApp";					//登录
	public static final String getCareerLineList = "depart/getCareerLineList";	//获得事业线
	
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
	//会议结论
	public static final String MEETING_RESULT_1 = "meetingResult:1";	//通过
	public static final String MEETING_RESULT_2 = "meetingResult:2";	//待定
	public static final String MEETING_RESULT_3 = "meetingResult:3";	//否决
	
	
	
	
	
	
	
}
