package com.galaxy.im.business.callon.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.callon.service.ICallonService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.db.QPage;

@Controller
@RequestMapping("/callon")
public class CallonController {
	
	@Autowired
	private ICallonService callonService;

	/**
	 * 保存/编辑拜访计划
	 * @param paramString
	 * @return
	 */
	@RequestMapping("saveCallonPlan")
	@ResponseBody
	public Object save(@RequestBody ScheduleInfo infoBean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			int updateCount = 0;
			Long id = 0L;
			if(infoBean!=null && infoBean.getId()!=null && infoBean.getId()!=0){
				updateCount = callonService.updateById(infoBean);
			}else{
				id = callonService.insert(infoBean);
			}
			if(updateCount!=0 || id!=0L){
				resultBean.setFlag(1);
			}
			resultBean.setStatus("OK");
			
		}catch(Exception e){
		}
		return resultBean;
	}
	
	/**
	 * 删除拜访计划
	 * @param paramString
	 * @return
	 */
	@RequestMapping("delCallonPlan")
	@ResponseBody
	public Object deletePlan(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> map = CUtils.get().jsonString2map(paramString);
			long planId = CUtils.get().object2Long(map.get("id"), 0L);
			if(planId!=0){
				boolean flag = callonService.delCallonById(planId);
				if(flag){
					resultBean.setFlag(1);
				}
			}
			resultBean.setStatus("OK");
		}catch(Exception e){
		}
		return resultBean;
	}
	
	/**
	 * 判断拜访计划能否删除或编辑
	 */
	@RequestMapping("callonEnableEditOrDel")
	@ResponseBody
	public Object callonEnableEditOrDel(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> map = CUtils.get().jsonString2map(paramString);
			Long planId = CUtils.get().object2Long(map.get("id"), 0L);
			
			resultBean.setStatus("OK");
			if(planId!=0){
				boolean flag = callonService.callonEnableEditOrDel(planId);
				if(!flag){
					resultBean.setFlag(1);
				}
			}
		}catch(Exception e){
		}
		return resultBean;
	}
	
	/**
	 * 拜访计划列表
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getCallonList")
	@ResponseBody
	public Object getCallonList(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			QPage page = callonService.selectCallonList(paramMap);
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
			
		}catch(Exception e){
		}
		return resultBean;
	}

}
