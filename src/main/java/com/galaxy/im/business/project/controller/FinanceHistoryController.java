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
import com.galaxy.im.bean.project.FinanceHistoryBean;
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
	 * 新增／编辑融资历史
	 */
	@RequestMapping("saveFinanceHistory")
	@ResponseBody
	public Object saveFinanceHistory(HttpServletRequest request,@RequestBody FinanceHistoryBean historyBean){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			int count = 0;
			Long id = 0L;
			
			//字符串转时间类型，用于持久化数据库
			String financeString = historyBean.getFinanceDateStr(); 
			if(CUtils.get().stringIsNotEmpty(financeString)){
				Date date = DateUtil.convertStringToDate(historyBean.getFinanceDateStr());
				historyBean.setFinanceDate(date);
			}
			
			if(CUtils.get().stringIsNotEmpty(historyBean.getId())){
				historyBean.setUpdatedUid(CUtils.get().getBeanBySession(request).getGuserid());
				historyBean.setUpdatedTime(DateUtil.getMillis(new Date()));
				
				count = service.updateById(historyBean);
			}else{
				historyBean.setCreateUid(CUtils.get().getBeanBySession(request).getGuserid());
				historyBean.setCreateTime(DateUtil.getMillis(new Date()));
				
				id = service.insert(historyBean);
			}
			if(count>0 || id>0){
				result.setStatus("OK");
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
	public Object delFinanceHistory(HttpServletRequest request,@RequestBody FinanceHistoryBean historyBean){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			int count = service.deleteById(historyBean.getId());
			if(count>0){
				result.setStatus("OK");
			}
		}catch(Exception e){
		}
		return result;
	}
	
}
