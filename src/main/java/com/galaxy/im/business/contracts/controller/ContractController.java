package com.galaxy.im.business.contracts.controller;

import java.util.Date;
import java.util.HashMap;
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
			if(bean.getName()==null){
				resultBean.setMessage("添加联系人失败参数缺失");
				return resultBean;
			}
			if(bean.getName()!=null){
				String pn = PingYinUtil.getPingYin(bean.getName());
				bean.setFirstpinyin(pn);
			}
			
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			bean.setCreatedId(sessionBean.getGuserid());
			bean.setIsDel(0);
			bean.setCreatedTime(new Date().getTime());
			Long id = service.savePerson(bean);	
			if(id>0){
				resultBean.setStatus("OK");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", id);
				resultBean.setMap(map);
			}
		}catch (Exception e) {
			log.error(ContractController.class.getName() + "savePerson",e);
		}
		return resultBean;
	}

}
