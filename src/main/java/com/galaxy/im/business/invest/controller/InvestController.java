package com.galaxy.im.business.invest.controller;

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
import com.galaxy.im.bean.invest.InvestBean;
import com.galaxy.im.business.invest.service.IInvestService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.db.QPage;

@Controller
@RequestMapping("/invest")
public class InvestController {

	private Logger log = LoggerFactory.getLogger(InvestController.class);
	
	@Autowired
	private IInvestService investService;
	
	/**
	 * 新增或编辑投资方
	 */
	@RequestMapping("saveInvestCompany")
	@ResponseBody
	public Object saveInvest(HttpServletRequest request,HttpServletResponse response,@RequestBody InvestBean investBean){
		
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try {
			int updateCount = 0;
			Long id = 0L;
			SessionBean bean = CUtils.get().getBeanBySession(request);
			
				if (investBean!=null&&investBean.getId()!=null&&investBean.getId()!=0) {
					//更新用户id
					investBean.setUpdatedId(bean.getGuserid());
					updateCount=investService.updateById(investBean);
					
				}else {
					//保存用户id
					investBean.setCreatedId(bean.getGuserid());
					id=investService.insert(investBean);
				}
				if(updateCount!=0 || id!=0L){
					resultBean.setFlag(1);
				}
				resultBean.setStatus("OK");
				
			
		} catch (Exception e) {
			log.error(InvestController.class.getName() + "：saveInvest",e);
			
		}
		return resultBean;
	}
	/**
	 * 删除投资方
	 * @return
	 */
	@RequestMapping("delInvestCompany")
	@ResponseBody
	public Object delInvest(HttpServletRequest request,@RequestBody InvestBean investBean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
		
			SessionBean bean = CUtils.get().getBeanBySession(request);
			investBean.setUpdatedId(bean.getGuserid());
			int count= investService.deleteById(investBean.getId());
			if(count!=0){
				resultBean.setFlag(1);
			
		}
		resultBean.setStatus("OK");
	}catch(Exception e){
		log.error(InvestController.class.getName() + "：delInvest",e);
	}
	return resultBean;
	}
	
	
	/**
	*//**
	 * 查询投资方列表
	 */
	@RequestMapping("getInvestCompanyList")
	@ResponseBody
	public Object getInvestList(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			QPage page = investService.selectInvestList(paramMap);
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
			
		}catch(Exception e){
			log.error(InvestController.class.getName() + "：getInvestList",e);
		}
		return resultBean;
	}

}
