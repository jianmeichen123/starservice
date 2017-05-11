package com.galaxy.im.business.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectBeanVo;
import com.galaxy.im.business.project.service.IProjectService;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.IRedisCache;
import com.galaxy.im.common.db.Page;
import com.galaxy.im.common.db.PageRequest;

@Controller
@RequestMapping("/project")
public class projectController {
	
	@Autowired
	IProjectService service;
	
	@Autowired
	private IRedisCache<String,Object> cache;
	
	@RequestMapping("getProjectList")
	@ResponseBody
	public Object getProjectList(@RequestBody ProjectBeanVo project){
		ResultBean<ProjectBean> resultBean = new ResultBean<ProjectBean>();
		resultBean.setStatus("error");
		try{
			//判断缓存里面是有存在项目移交key
			boolean res = cache.hasKey(StaticConst.transfer_projects_key);
			if(res){
				//获取项目移交id
				String transferId =cache.get(StaticConst.transfer_projects_key).toString();
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
					list.add(Long.valueOf(array[i]));
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
			map.put("pageNo", pageProject.getPageable().getPageNumber());
			map.put("pageSize", pageProject.getPageable().getPageSize());
			map.put("total", pageProject.getTotal());
			
			resultBean.setStatus("OK");
			resultBean.setEntityList(projectList);
			resultBean.setMap(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultBean;
	}

}
