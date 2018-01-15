package com.galaxy.im.business.contracts.controller;

import java.util.Date;
import java.util.List;

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
import com.galaxy.im.bean.contracts.ContractsBean;
import com.galaxy.im.business.contracts.service.IContractsService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.PingYinUtil;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/contacts")
public class ContractController {
	private Logger log = LoggerFactory.getLogger(ContractController.class);
	@Autowired
	IContractsService service;
	
	/**
	 * 查询联系人是否存在
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping("selectPersonByName")
	@ResponseBody
	public Object selectPersonByName(HttpServletRequest request,HttpServletResponse response,@RequestBody ContractsBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			bean.setCreatedId(sessionBean.getGuserid());
			bean.setIsDel(0);
			ContractsBean ss = service.selectPersonByName(bean);
			if(ss!= null){
				if(bean.getuId()!=null && CUtils.get().object2String(ss.getId()).equals(CUtils.get().object2String(bean.getuId()))){
					resultBean.setStatus("OK");
					resultBean.setMessage("不存在");
				}else{
					resultBean.setEntity(ss);
					resultBean.setStatus("OK");
					resultBean.setMessage("您添加的拜访对象的姓名在联系人中已存在，是否使用此联系人？");
				}
			}else{
				resultBean.setStatus("OK");
				resultBean.setMessage("不存在");
			}
		}catch (Exception e) {
			log.error(ContractController.class.getName() + "selectPersonByName",e);
		}
		return resultBean;
	}
	
	/**
	 * 保存联系人
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping("savePerson")
	@ResponseBody
	public Object savePerson(HttpServletRequest request,HttpServletResponse response,@RequestBody ContractsBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			Long uid=0L;
			Long id=0L;
			
			if(bean.getName()!=null){
				String pn = PingYinUtil.getPingYin(bean.getName());
				bean.setFirstpinyin(pn);
			}
			
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			
			if(bean.getId()!=null && bean.getId()!=0){
				//编辑
				bean.setUpdatedTime(new Date().getTime());
				bean.setUpdatedId(sessionBean.getGuserid());
				uid = service.updatePerson(bean);	
				if(uid>0){
					resultBean.setStatus("OK");
					resultBean.setId(uid);
				}
			}else{
				//添加
				bean.setCreatedId(sessionBean.getGuserid());
				bean.setIsDel(0);
				bean.setCreatedTime(new Date().getTime());
				id = service.savePerson(bean);	
				if(id>0){
					resultBean.setStatus("OK");
					resultBean.setId(id);
				}
			}
		}catch (Exception e) {
			log.error(ContractController.class.getName() + "savePerson",e);
		}
		return resultBean;
	}
	
	/**
	 * 分页的联系人列表查询
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping("selectPersonList")
	@ResponseBody
	public Object selectPersonList(HttpServletRequest request,HttpServletResponse response,@RequestBody ContractsBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			bean.setCreatedId(sessionBean.getGuserid());
			bean.setDirection("ASC");
			bean.setProperty("firstpinyin");
			bean.setIsDel(0);
			List<ContractsBean> list = service.selectPersonList(bean);
			resultBean.setStatus("OK");
			resultBean.setEntity(list);
		}catch (Exception e) {
			log.error(ContractController.class.getName() + "savePerson",e);
		}
		return resultBean;
	}
	
	/**
	 * 联系人详情
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping("selectById")
	@ResponseBody
	public Object selectById(HttpServletRequest request,HttpServletResponse response,@RequestBody ContractsBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			ContractsBean contractsBean = service.queryById(bean.getId());
			if(contractsBean!=null){
				resultBean.setEntity(contractsBean);
				resultBean.setStatus("OK");
			}
		}catch (Exception e) {
			log.error(ContractController.class.getName() + "selectById",e);
		}
		return resultBean;
	}
	
	/**
	 * 删除联系人
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping("deleteById")
	@ResponseBody
	public Object deleteById(HttpServletRequest request,HttpServletResponse response,@RequestBody ContractsBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			ContractsBean contractsBean = service.queryById(bean.getId());
			if(contractsBean!=null){
				contractsBean.setIsDel(1);
				Long id = service.updatePerson(contractsBean);
				if(id>0){
					resultBean.setStatus("OK");
					resultBean.setMessage("删除联系人成功");
				}
			}else{
				resultBean.setMessage("该联系人不存在");
			}
		}catch (Exception e) {
			log.error(ContractController.class.getName() + "selectById",e);
		}
		return resultBean;
	}

}
