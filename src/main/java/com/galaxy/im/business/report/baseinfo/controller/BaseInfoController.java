package com.galaxy.im.business.report.baseinfo.controller;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.report.InformationTitle;
import com.galaxy.im.business.report.baseinfo.service.IBaseInfoService;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/report/baseinfo")
public class BaseInfoController {
	private Logger log = LoggerFactory.getLogger(BaseInfoController.class);

	@Autowired
	IBaseInfoService service;
	
	@ResponseBody
	@RequestMapping("/getTitleResults/{titleId}/{projectId}")
	public Object getTitleResults(@PathVariable String titleId,@PathVariable String projectId)
	{
		ResultBean<InformationTitle> resultBean = new ResultBean<InformationTitle>();
		resultBean.setStatus("error");
		try
		{
			List<InformationTitle> list = service.searchWithData(titleId, projectId);
			Iterator<InformationTitle> sListIterator = list.iterator();  
			while(sListIterator.hasNext()){  
				InformationTitle e = sListIterator.next();  
			    if(e.getSign()!=2){  
			    	sListIterator.remove();  
			    }  
			}  
			resultBean.setEntityList(list);
		} catch (Exception e)
		{
			log.error("获取标题失败，信息:titleId="+titleId,e);
		}
		return resultBean;
	}

}
