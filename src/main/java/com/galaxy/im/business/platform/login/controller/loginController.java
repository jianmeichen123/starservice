package com.galaxy.im.business.platform.login.controller;

import java.util.HashMap;
import java.util.Map;

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
@RequestMapping("/userlogin")
@ResponseBody
public class loginController {
	
	@Autowired
	private Environment env;
	
	@RequestMapping("login")
	public Object login(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			
			String url = env.getProperty("power.server") + StaticConst.login;
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			String htmlString = QHtmlClient.get().post(url, null, paramMap);
			if(CUtils.get().stringIsNotEmpty(htmlString) && !"error".equals(htmlString)){
				//{"success":false,"errorCode":null,"message":"用户名或密码不能为空！","value":null}
				JSONObject resultJson = CUtils.get().object2JSONObject("wojfowjf"); 
				if(resultJson!=null){
					System.out.println(htmlString);
				}
				
			}
			
			
			
			
			
			
		}catch(Exception e){
		}
		return result;
	}

}
