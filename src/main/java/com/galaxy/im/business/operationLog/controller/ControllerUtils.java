package com.galaxy.im.business.operationLog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.common.webconfig.interceptor.operationLog.OperationMessage;
import com.galaxy.im.common.webconfig.interceptor.operationLog.PlatformConst;
import com.galaxy.im.common.webconfig.interceptor.operationLog.UrlNumber;
import com.galaxyinternet.model.user.User;


public class ControllerUtils {
	
	
	public static String getProjectNameLink(OperationMessage message) {
		return "projectname";
	}

	public static String getIdeaZixunCodeLink(OperationMessage message) {
		return "ideazixuncode";
	}
	/**
	 * 
	 * @Description:消息提醒临时传参方法
	 * @param request
	 * @param projectName
	 *            项目名称
	 * @param projectId
	 *            项目id
	 *
	 */
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId) 
	{
		String messageType = null;
		setRequestParamsForMessageTip(request,projectName,projectId,messageType);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId, String messageType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId, String messageType,
			boolean flag,User userData,String reason,String stage) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_NUM, flag);
		params.put(PlatformConst.REQUEST_SCOPE_USER_DATA, userData);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_REASON, reason);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_STAGE, stage);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId, String messageType,Object userData) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		params.put(PlatformConst.REQUEST_SCOPE_USER_DATA, userData);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId,UrlNumber number) 
	{
		setRequestParamsForMessageTip(request,projectName,projectId,null,number);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId, String messageType,UrlNumber number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		if(number != null){
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
		}
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	/**
	 * 
	 * 项目添加第一条实际注资的发出消息通知以后每月一号添加运营数据
	 * @param request
	 * @param projectName
	 * @param projectId
	 * @param messageType
	 * @param assistColumn    辅助字段用于特殊消息标记
	 * @param number
	 */
	public static void setRequestParamsForMessageTip(HttpServletRequest request, User user, SopProjectBean priject, String messageType,String assistColumn, UrlNumber number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, priject.getProjectName());
		params.put(PlatformConst.REQUEST_SCOPE_USER, user);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, priject.getId());
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS, priject.getProjectProgress());
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_ASSISRCOLUMN, assistColumn);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		if(number != null){
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
		}
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId, String messageType,UrlNumber number,Object userData) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		if(number!=null){
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());	
		}
		params.put(PlatformConst.REQUEST_SCOPE_USER_DATA, userData);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, User user, String projectName, Long projectId,UrlNumber number) 
	{
		setRequestParamsForMessageTip(request,user,projectName,projectId,null,number);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, User user, String projectName, Long projectId, String messageType, UrlNumber number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_USER, user);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		if(number != null){
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
		}
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	
	public static void setRequestParamsForMessageTip(HttpServletRequest request, User user, SopProjectBean priject, String messageType, UrlNumber number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, priject.getProjectName());
		params.put(PlatformConst.REQUEST_SCOPE_USER, user);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, priject.getId());
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS, priject.getProjectProgress());
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		if(number != null){
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
		}
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	
	public static void setRequestParamsForMessageTip(HttpServletRequest request, User user, String projectName, Long projectId, String messageType, UrlNumber number,Object userData) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_USER, user);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		if(number != null){
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
		}
		params.put(PlatformConst.REQUEST_SCOPE_USER_DATA, userData);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	
	public static void setRequestIdeaParamsForMessageTip(HttpServletRequest request, User user, String ideaName, Long ideaId,String content,UrlNumber number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_IDEA_NAME, ideaName);
		params.put(PlatformConst.REQUEST_SCOPE_USER, user);
		params.put(PlatformConst.REQUEST_SCOPE_IDEA_ID, ideaId);
		params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
		params.put(PlatformConst.REQUEST_SCOPE_IDEA_CONTENT, content);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	
	public static void setRequestBatchParamsForMessageTip(HttpServletRequest request,List<Map<String, Object>> mapList){
		Map<String,List<Map<String, Object>>> params = new HashMap<String,List<Map<String, Object>>>();
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_BATCH, mapList);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
}
