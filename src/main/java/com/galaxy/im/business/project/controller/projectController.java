package com.galaxy.im.business.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectBeanVo;
import com.galaxy.im.business.project.service.IFinanceHistoryService;
import com.galaxy.im.business.project.service.IProjectService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.IRedisCache;
import com.galaxy.im.common.db.Page;
import com.galaxy.im.common.db.PageRequest;

@Controller
@RequestMapping("/project")
public class projectController {
	private Logger log = LoggerFactory.getLogger(projectController.class);
	@Autowired
	IProjectService service;
	
	@Autowired
	IFinanceHistoryService fsService;
	
	@Autowired
	private IRedisCache<String,Object> cache;
	
	@RequestMapping("getProjectList")
	@ResponseBody
	public Object getProjectList(HttpServletRequest request,HttpServletResponse response,@RequestBody ProjectBeanVo project){
		ResultBean<ProjectBean> resultBean = new ResultBean<ProjectBean>();
		resultBean.setStatus("error");
		try{
			SessionBean bean = CUtils.get().getBeanBySession(request);
			project.setCreatedId(bean.getGuserid());
			//判断缓存里面是有存在项目移交key
			boolean res = cache.hasKey(StaticConst.transfer_projects_key);
			if(res){
				//获取项目移交id
				String transferId =CUtils.get().object2String(cache.get(StaticConst.transfer_projects_key));
				//将获取到的项目id存到list里
				transferId = transferId.replace(" ", "");
				if(transferId.startsWith("[")){
					transferId = transferId.substring(1);
				}
				if(transferId.endsWith("]")){
					transferId = transferId.substring(0,transferId.length()-1);
				}
				String[] array = transferId.split(",");
				List<Long> list = new ArrayList<Long>();
				
				for(int i=0;i<array.length;i++){
					list.add(CUtils.get().object2Long(array[i]));
				}
				//list放到查询字段里
				project.setProjectIdList(list);
			}
			//分页查询
			Page<ProjectBean> pageProject = service.queryPageList(project,
					new PageRequest(project.getPageNum(),
							project.getPageSize(), 
							Direction.fromString(project.getDirection()),
							project.getProperty()));
			//查询结果放在List<ProjectBean>
			List<ProjectBean> projectList = new ArrayList<ProjectBean>();
			for(ProjectBean p : pageProject.getContent()){
				projectList.add(p);
			}
			//页面
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageNum", pageProject.getPageable().getPageNumber());
			map.put("pageSize", pageProject.getPageable().getPageSize());
			map.put("total", pageProject.getTotal());
			
			resultBean.setStatus("OK");
			resultBean.setEntityList(projectList);
			resultBean.setMap(map);
		}catch(Exception e){
			log.error(projectController.class.getName() + "_getProjectList",e);
		}
		return resultBean;
	}
	
	/**
	 * 获取项目基础信息接口
	 * @param id
	 * @return
	 * @author liyanqiao
	 */
	@RequestMapping("getBaseProjectInfo")
	@ResponseBody
	public Object getBaseProjectInfo(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> resultMap = new HashMap<String,Object>();
			Long projectId = CUtils.get().object2Long(paramMap.get("id"));
			
			//基础信息
			Map<String,Object> infoMap = service.getBaseProjectInfo(projectId);
			if(infoMap!=null && !infoMap.isEmpty()){
				infoMap.put("projectYjz", service.projectIsYJZ(projectId));
				resultMap.put("infoMap", infoMap);
			}
			
			//融资历史-最新一条
			paramMap.put("isOne", "true");
			List<Map<String,Object>> historyMap = fsService.getFinanceHistory(paramMap);
			if(historyMap!=null && historyMap.size()>0){
				resultMap.put("historyMap", historyMap.get(0));
			}
			
			//用户画像等理否为空
			Map<String,Object> nullMap = service.getProjectInoIsNull(projectId);
			if(nullMap!=null && !nullMap.isEmpty()){
				resultMap.put("nullMap", nullMap);
			}
			
			result.setEntity(resultMap);
			result.setStatus("OK");
			
		}catch(Exception e){
		}
		return result;
	}
	

	
	

}
