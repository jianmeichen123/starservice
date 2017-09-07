package com.galaxy.im.business.project.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.project.service.IFinanceHistoryService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/financehistory")
public class FinanceHistoryController {
	@Autowired
	private IFinanceHistoryService service;
	
	/**
	 * 融资历史列表
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getFinanceHistoryList")
	@ResponseBody
	public Object getFinanceHistoryList(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(paramMap!=null && !paramMap.isEmpty()){
				List<Map<String,Object>> historyList = service.getFinanceHistory(paramMap);
				if(historyList!=null && historyList.size()>0){
					result.setMapList(historyList);
				}
			}
			result.setStatus("OK");
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 融资历史详情
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getFinanceHistoryDetails")
	@ResponseBody
	public Object getFinanceHistoryDetails(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(paramMap!=null && !paramMap.isEmpty()){
				Map<String,Object> details = service.getFinanceHistoryDetails(paramMap);
				if(details!=null){
					if(details.containsKey("financeValuation") && details.get("financeValuation")!=null){
						String s= CUtils.get().object2String(details.get("financeValuation"));
						if(s.indexOf(".") > 0){  
				            s = s.replaceAll("0+?$", "");//去掉多余的0  
				            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
				        }
						details.put("financeValuation", s);
					}
					result.setStatus("OK");
					result.setMap(details);
				}
			}
			result.setStatus("OK");
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 新增／编辑融资历史
	 */
	@RequestMapping("saveFinanceHistory")
	@ResponseBody
	public Object saveFinanceHistory(HttpServletRequest request,@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Long id = 0L;
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(paramMap!=null && !paramMap.isEmpty()){
				paramMap.put("titleId", 1903);
				if(paramMap.containsKey("id") && CUtils.get().object2Long(paramMap.get("id"))!=0){
					paramMap.put("updateId", CUtils.get().getBeanBySession(request).getGuserid());
					paramMap.put("updatedTime", DateUtil.getMillis(new Date()));
					//更新
					id = service.updateFinanceHistory(paramMap);
				}else{
					paramMap.put("createId", CUtils.get().getBeanBySession(request).getGuserid());
					paramMap.put("createdTime", DateUtil.getMillis(new Date()));
					//添加
					id = service.addFinanceHistory(paramMap);
				}
				if(id>0){
					result.setStatus("OK");
				}
			}
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 删除融资历史
	 */
	@RequestMapping("delFinanceHistory")
	@ResponseBody
	public Object delFinanceHistory(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(paramMap!=null && !paramMap.isEmpty() && paramMap.containsKey("id")){
				int count = service.deleteById(CUtils.get().object2Long(paramMap.get("id")));
				if(count>0){
					result.setStatus("OK");
				}
			}
		}catch(Exception e){
		}
		return result;
	}
	
}
