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
	
	
	
	public static final String getShareUserList = "user/getShareUserList"; 	//power外部接口：获取共享列表
	public static final String getCreadIdInfo = "user/getCreadIdInfo";   	//power外部接口：获取用户名和部门名称
	public static final String login = "login/userLoginForApp";				//登录
	
	//过滤器白名单
	public static final String FILTER_WHITE_LOGIN = "userlogin/login";		//用户登录白名单
	
	
	
	public final static Long TOKEN_IN_REDIS_TIMEOUT_SECONDS = 60 * 60L;		//Token（防止重复提交）	的Redis过期时间
	public final static String TOKEN_REMOVE_KEY = "token_remove";
	
	
}
