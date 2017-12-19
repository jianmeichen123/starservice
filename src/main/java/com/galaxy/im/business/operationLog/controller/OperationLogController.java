package com.galaxy.im.business.operationLog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.business.operationLog.service.IOperationLogsService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.db.QPage;

@Controller
@RequestMapping("/operationLog")
public class OperationLogController {
	private Logger log = LoggerFactory.getLogger(OperationLogController.class);
	
	@Autowired
	IOperationLogsService service;
	
	/**
	 * 获取操作日志信息列表（代办任务）
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getOperationLogList")
	@ResponseBody
	public Object getOperationLogList(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			if(sessionBean.getGuserid()!=null){
				paramMap.put("uid", sessionBean.getGuserid());
			}
			QPage page = service.getOperationLogList(paramMap);
			if (page!=null) {
				List<Map<String,Object>> list = page.getDataList();
				for(Map<String,Object> map : list){
					if (map.get("operationType")!= null && map.get("operationContent")!=null) {
						map.put("operationContent", CUtils.get().object2String(map.get("operationType")) + 
								CUtils.get().object2String(map.get("operationContent")));
					}
				}
			}
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
		}catch(Exception e){
			log.error(OperationLogController.class.getName() + "getOperationLogList",e);
		}
		return resultBean;
	}
}
