package com.galaxy.im.business.platform.login.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.platform.login.service.ILoginService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.TokenGenerator;
import com.galaxy.im.common.cache.redis.IRedisCache;
import com.galaxy.im.common.html.QHtmlClient;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.model.user.UserLogonHis;

@Controller
@ResponseBody
public class LoginController {
	
	private Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private IRedisCache<String, Object> cache;
	@Autowired
	private IFlowCommonService fcService;
	@Autowired
	ILoginService service;
	
	/**
	 * 登录
	 * @param paramString
	 * @return
	 */
	@RequestMapping("/userlogin/login")
	public Object login(HttpServletRequest request,@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		User user = new User();
		Map<String, Object> map = null;
		try{
			String aclient = request.getHeader("gt");
			String url = env.getProperty("power.server") + StaticConst.login;
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			String htmlString = QHtmlClient.get().post(url, null, paramMap);
			if(CUtils.get().stringIsNotEmpty(htmlString) && !"error".equals(htmlString)){
				JSONObject resultJson = CUtils.get().object2JSONObject(htmlString); 
				if(resultJson!=null && resultJson.containsKey("success")){
					if(resultJson.getBoolean("success")){
						String sessionId = request.getSession().getId();
						JSONObject valueJson = resultJson.getJSONObject("value");
						valueJson.put("sessionId", sessionId);
						result.setEntity(valueJson);
						result.setStatus("OK");
						result.setMessage(resultJson.getString("message"));
						//设置sessionID
						map=CUtils.get().jsonString2map(valueJson);
						user.setId(CUtils.get().object2Long(map.get("id")));
						user.setNickName(CUtils.get().object2String(map.get("loginName")));
						user.setRealName(CUtils.get().object2String(map.get("realName")));
						user.setDepartmentId(CUtils.get().object2Long(map.get("departmentId")));
						user.setDepartmentName(CUtils.get().object2String(map.get("departmentName")));
						user.setRole(CUtils.get().object2String(map.get("roleName")));
						user.setSessionId(sessionId);
						cache.put(sessionId, user); 
						request.getSession().setAttribute(StaticConst.SESSION_USER_KEY, user);
						
						//记录登陆历史信息
						Date date = new Date();       
					    Timestamp initdate = new Timestamp(date.getTime());
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
						if(aclient!=null && (aclient.equals("android") || aclient.equals("Android"))){
							paramMap.put("accessClient", aclient);
						}else{
							paramMap.put("accessClient", "ios");
						}
						paramMap.put("userId", user.getId());
						paramMap.put("loginDate", format.format(date));
					     
						UserLogonHis his = service.findUserLogonHis(paramMap);
						if(his!=null){
							//更新
							his.setLogonTimes(his.getLogonTimes()+1);
							his.setLastLogonTime(initdate);
							service.updateLogonHis(his);
						}else{
							//保存
							UserLogonHis userLogonHis = new UserLogonHis();	
							if(aclient!=null && (aclient.equals("android") || aclient.equals("Android"))){
								userLogonHis.setAccessClient(aclient);
							}else{
								userLogonHis.setAccessClient("ios");
							}
							userLogonHis.setUserId(user.getId());
							userLogonHis.setNickName(user.getNickName());
							
						    userLogonHis.setLoginDate(date);
							userLogonHis.setInitLogonTime(initdate);
							userLogonHis.setLogonTimes(1);					
							service.saveLogonHis(userLogonHis);
						}
					}else{
						result.setMessage(resultJson.getString("message"));
					}
				}
			}
		}catch(Exception e){
			log.error(LoginController.class.getName() + "login",e);
		}
		return result;
	}
	
	/**
	 * 退出登录
	 */
	@RequestMapping("/userlogin/logout")
	@ResponseBody
	public Object logout(HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			if(sessionBean!=null && CUtils.get().stringIsNotEmpty(sessionBean.getSessionid())){
				cache.remove(sessionBean.getSessionid());
				result.setStatus("OK");
			}
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 获得Token
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/platform/formtoken")
	public Object fetchFormToken(HttpServletRequest request) {
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			String tokenValue = TokenGenerator.getInstance().generateToken();
			cache.put(tokenValue, tokenValue,StaticConst.TOKEN_IN_REDIS_TIMEOUT_SECONDS,TimeUnit.SECONDS);
			result.setStatus("OK");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("TOKEN", tokenValue);
			result.setMap(map);
		}catch(Exception e){
		}
		
		return result;
	}
	
	/**
	 * 获得事业线
	 */
	@RequestMapping("/platform/departmentList")
	@ResponseBody
	public Object departmentList(HttpServletRequest request,HttpServletResponse response){
		ResultBean<Object> result = new ResultBean<Object>();
		SessionBean sessionBean = CUtils.get().getBeanBySession(request);
		Long deptId = 0L;
		//System.out.println(cache.get(sessionBean.getSessionid()));
		//通过用户id获取一些信息
		List<Map<String, Object>> list = fcService.getDeptId(sessionBean.getGuserid(),request,response);
		if(list!=null){
			for(Map<String, Object> vMap:list){
				deptId = CUtils.get().object2Long( vMap.get("deptId"));
			}
		}
		String url = env.getProperty("power.server") + StaticConst.getCareerLineList;
		String htmlString = QHtmlClient.get().post(url, null, null);
		if(CUtils.get().stringIsNotEmpty(htmlString) && !"error".equals(htmlString)){
			JSONArray array = CUtils.get().object2JSONArray(htmlString);
			List<Map<String, Object>> dataList = CUtils.get().jsonString2list(array);
			for(Map<String, Object> map : dataList){
				if(map.containsKey("id")){
					if(deptId==CUtils.get().object2Long(map.get("id"))){
						map.put("isCurrentUser", true);
					}else{
						map.put("isCurrentUser", false);
					}
				}
			}
			result.setStatus("OK");
			result.setMapList(dataList);
		}
		return result;
	}

}
