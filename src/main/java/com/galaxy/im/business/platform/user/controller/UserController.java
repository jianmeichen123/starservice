package com.galaxy.im.business.platform.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.html.QHtmlClient;

@Controller
public class UserController {
	
	@Autowired
	private Environment env;
	
	/**
	 * 修改密码
	 * @param paramString
	 * @return
	 */
	@RequestMapping("/user/resetPwd")
	@ResponseBody
	public Object resetPwd(HttpServletRequest request,@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			String url = env.getProperty("power.server") + StaticConst.resetPwd;
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			String htmlString = QHtmlClient.get().post(url, null, paramMap);
			
			if(CUtils.get().stringIsNotEmpty(htmlString) && !"error".equals(htmlString)){
				JSONObject resultJson = CUtils.get().object2JSONObject(htmlString); 
				if(resultJson!=null && resultJson.containsKey("success")){
					if(resultJson.getBoolean("success")){
						JSONObject valueJson = resultJson.getJSONObject("value");
						result.setEntity(valueJson);
						result.setStatus("OK");
					}else{
						result.setMessage(resultJson.getString("message"));
					}
				}
			}
		}catch(Exception e){
		}
		return result;
	}

}
