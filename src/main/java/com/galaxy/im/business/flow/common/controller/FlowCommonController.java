package com.galaxy.im.business.flow.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.common.ResultBean;

/**
 * 各流程阶段-共用的一些接口
 * 
 * @author liyanqiao
 */
@Controller
@RequestMapping("/flow/common")
public class FlowCommonController {
	private Logger log = LoggerFactory.getLogger(FlowCommonController.class);
	
	/**
	 * 公用接口：上传/更新文件的接口
	 * @param paramString
	 * @return
	 */
	@RequestMapping("addSopFile")
	@ResponseBody
	public Object addSopFile(HttpServletRequest request,HttpServletResponse response,@RequestBody SopFileBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			if(bean!=null){
				//文件存在，进行更新操作，否则保存
				if(bean.getId()!=null && bean.getId()!=0){
					
				}else{
					
				}
			}
		}catch(Exception e){
			log.error(FlowCommonController.class.getName() + "_addSopFile",e);
		}
		return resultBean;
	}
}