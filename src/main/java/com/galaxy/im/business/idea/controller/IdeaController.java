package com.galaxy.im.business.idea.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.Config;
import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.idea.IdeaBean;
import com.galaxy.im.business.common.config.service.ConfigService;
import com.galaxy.im.business.idea.service.IIdeaService;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.RedisCacheImpl;

@Controller
@RequestMapping("/idea")
public class IdeaController {
	private Logger log = LoggerFactory.getLogger(IdeaController.class);

	@Autowired
	IIdeaService service;
	
	private static final String IDEA_CODE_PREFIX = "CY";
	
	@Autowired
	private ConfigService configService;
	
	/**
	 * 创意列表，搜索
	 * @param paramString
	 * @return
	 */
	@RequestMapping("search")
	@ResponseBody
	public Object search(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			
		}catch(Exception e){
			log.error(IdeaController.class.getName() + "search",e);
		}
		return resultBean;
	}
	
	
	/**
	 * 添加创意之前请求接口获取ideacode
	 */
	@ResponseBody
	@RequestMapping("getAddIdeaInfo")
	public Object getAddIdeaInfo(HttpServletRequest request,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String, Object> paramMap = CUtils.get().jsonString2map(paramString);
		try {
			@SuppressWarnings("unchecked")
			RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
			//获取登录用户信息
			SessionBean bean = CUtils.get().getBeanBySession(request);
			if (bean==null) {
				resultBean.setMessage("未登录!");
			}
			Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
			IdeaBean idea = new IdeaBean();
			idea.setCreatedUid(CUtils.get().object2Long(user.get("id")));
			idea.setCreatedUname(CUtils.get().object2String(user.get("realName")));
			idea.setCreatedTime(System.currentTimeMillis());
			Config config = configService.getByKey(StaticConst.CONFIG_KEY_IDEA_CODE, true);
			int codeLength = config.getValue().length();
			String newCode = "";
			for(int i = 0;i<6-codeLength;i++){
				newCode += "0";
			}
			newCode += config.getValue();
			idea.setIdeaCode(IDEA_CODE_PREFIX + newCode);
			//是ceo或者dsz
			if( paramMap.get("isCEOORDSZ").equals(1)){
				idea.setDepartmentId(CUtils.get().object2Long(user.get("departmentId")));
				idea.setDepartmentEditable("false");
			}else{
				//为高管时所属事业线可编辑
				idea.setDepartmentEditable("true");;
			}
			resultBean.setEntity(idea);
			resultBean.setStatus("OK");
			
		} catch (Exception e) {
			log.error(IdeaController.class.getName() + "getAddIdeaInfo",e);
		}
		return resultBean;
	}
	
	/**
	 * 创意添加和编辑
	 */
	@ResponseBody
	@RequestMapping("addIdea")
	public Object addIdea(HttpServletRequest request,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String, Object > paramMap = CUtils.get().jsonString2map(paramString);
		try {
			@SuppressWarnings("unchecked")
			RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
			//获取登录用户信息
			SessionBean bean = CUtils.get().getBeanBySession(request);
			if (bean==null) {
				resultBean.setMessage("未登录!");
			}
			Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
			
			
		} catch (Exception e) {
			log.error(IdeaController.class.getName() + "addIdea",e);
		}
		return resultBean;
	}
	
}
