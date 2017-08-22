package com.galaxy.im.business.common.dict.Controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.common.dict.service.IDictService;
import com.galaxy.im.common.CUtils;
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
	
	/**
	 * 结论选择
	 * @return
	 * @author liuli
	 */
	@RequestMapping("selectResultFilter")
	@ResponseBody
	public Object selectResultFilter(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			Map<String,Object> entityMap = dictService.selectResultFilter(paramMap);
			if(entityMap!=null && entityMap.size()>0){
				resultBean.setStatus("OK");
				resultBean.setMap(entityMap);
			}
		}catch(Exception e){
			log.error(DictController.class.getName() + "_selectResultFilter",e);
		}
		return resultBean;
	}
	
	/**
	 * 原因选择
	 * @return
	 * @author liuli
	 */
	@RequestMapping("selectReasonFilter")
	@ResponseBody
	public Object selectReasonFilter(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			Map<String,Object> entityMap = dictService.selectReasonFilter(paramMap);
			if(entityMap!=null && entityMap.size()>0){
				resultBean.setStatus("OK");
				resultBean.setMap(entityMap);
			}
		}catch(Exception e){
			log.error(DictController.class.getName() + "_selectReasonFilter",e);
		}
		return resultBean;
	}
	
	
	/**
	 * 1.项目类型		projectType
	 * 2.行业归属		industryOwn
	 * 3.融资轮次		FNO1（全息报告数据，添加：无尚未获投和不确定，筛选时：多尚未获投和不确定）     
	 * 4.项目进度 		projectProgress
	 * 5.项目来源		projectSource
	 * @return
	 * @author liuli
	 */
	@RequestMapping("getDictionaryList")
	@ResponseBody
	public Object getDictionaryList(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			List<Map<String, Object>> entityMap = dictService.getDictionaryList(paramMap);
			if(entityMap!=null && entityMap.size()>0){
				resultBean.setStatus("OK");
				resultBean.setMapList(entityMap);
			}
		}catch(Exception e){
			log.error(DictController.class.getName() + "getDictionaryList",e);
		}
		return resultBean;
	}
	

}
