package com.galaxy.im.business.common.dict.Controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.common.dict.service.IDictService;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/dict")
public class DictController {
	private Logger log = LoggerFactory.getLogger(DictController.class);
	
	@Autowired
	private IDictService dictService;
	
	/**
	 * 拜访相关 获取拜访进度、重要性、记录缺失 相关字典值
	 * @return
	 */
	@RequestMapping("selectCallonFilter")
	@ResponseBody
	public Object selectCallonFilter(){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> entityMap = dictService.selectCallonFilter();
			if(entityMap!=null && entityMap.size()>0){
				resultBean.setStatus("OK");
				resultBean.setMap(entityMap);
			}
		}catch(Exception e){
			log.error(DictController.class.getName() + "_selectCallonFilter",e);
		}
		return resultBean;
	}
	

}
