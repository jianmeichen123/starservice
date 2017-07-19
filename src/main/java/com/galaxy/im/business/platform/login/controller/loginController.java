package com.galaxy.im.business.platform.login.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.TokenGenerator;
import com.galaxy.im.common.cache.redis.IRedisCache;
import com.galaxy.im.common.html.QHtmlClient;
import com.galaxyinternet.model.user.User;

@Controller
@ResponseBody
public class loginController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private IRedisCache<String, Object> cache;
	
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
						// 将sessionId存入cache
						//cache.put(sessionId, resultJson);
					}else{
						result.setMessage(resultJson.getString("message"));
					}
				}
			}
		}catch(Exception e){
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
	public Object departmentList(){
		ResultBean<Object> result = new ResultBean<Object>();
		String url = env.getProperty("power.server") + StaticConst.getCareerLineList;
		String htmlString = QHtmlClient.get().post(url, null, null);
		if(CUtils.get().stringIsNotEmpty(htmlString) && !"error".equals(htmlString)){
			JSONArray array = CUtils.get().object2JSONArray(htmlString);
			result.setStatus("OK");
			result.setEntity(array);
		}
		return result;
	}

}
