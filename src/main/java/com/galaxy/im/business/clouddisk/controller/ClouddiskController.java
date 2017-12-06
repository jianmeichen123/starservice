package com.galaxy.im.business.clouddisk.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.galaxy.im.bean.clouddisk.CloudDiskFiles;
import com.galaxy.im.business.clouddisk.service.IClouddiskService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.Page;

@Controller
@RequestMapping("/cloud")
public class ClouddiskController {
	private Logger log = LoggerFactory.getLogger(ClouddiskController.class);
	private String className = ClouddiskController.class.getName();
	
	@Autowired
	private IClouddiskService service;
		
	/**
	 * 保存文件信息
	 * @param paramString
	 * @param request
	 * @return
	 */
	@RequestMapping("saveCloudFileInfo")
	@ResponseBody
	private Object saveCloudFileInfo(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<Object>();
		result.setStatus("OK");
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);		
			if(paramMap!=null && paramMap.size()>0){
				String targeName = CUtils.get().object2String(paramMap.get("targeName"), "");
				Long fileSize = CUtils.get().object2Long(paramMap.get("fileSize"),-1L);
				Long ownUser = CUtils.get().object2Long(paramMap.get("ownUser"), -1L);
				int fileType = CUtils.get().object2Integer(paramMap.get("fileType"), -1);
				String ossKey = CUtils.get().object2String(paramMap.get("ossKey"), "");
				String buckerName = CUtils.get().object2String(paramMap.get("buckerName"), "");
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success",0);
				result.setMap(map);
				
				if(CUtils.get().stringIsEmpty(targeName)){
					map.put("message", "文件名称不能为空");
					map.put("errorCode", 1);
				}else if(fileSize<=0){
					map.put("message", "文件大小有错误");
					map.put("errorCode", 2);
				}else if(ownUser<=0){
					map.put("message", "所属用户的ID不正确");
					map.put("errorCode", 3);
				}else if(fileType<=0){
					map.put("message", "文件类型不正确");
					map.put("errorCode", 4);
				}else if(CUtils.get().stringIsEmpty(ossKey)){
					map.put("message", "没有传递阿里云文件KEY");
					map.put("errorCode", 5);
				}else if(CUtils.get().stringIsEmpty(buckerName)){
					map.put("message", "没有传递阿里云桶名");
					map.put("errorCode", 6);
				}else{
					//添加文件前再次验证上传的文件容量是否大于总容量
					Long usedVolume = service.getUsedVolumes(paramMap);
					if(StaticConst.CLOUD_VOLUME_DOSE<usedVolume+fileSize){
						map.put("message", "云盘容量不足");
						map.put("errorCode", 7);
					}else{
						//添加其他字段
						//String extName = "";
						//if(targeName.indexOf(".")>-1){
						//	extName = targeName.substring(targeName.indexOf(".")+1,targeName.length());
						//}
						paramMap.put("originalName", targeName);
						//paramMap.put("extName", extName);
						paramMap.put("uploadUser", ownUser);
						paramMap.put("uploadTime", DateUtil.getMillis(new Date()));
						paramMap.put("isDel", 0);
						paramMap.put("fileSign", -1);
						
						if(service.saveFileInfo(paramMap)){
							map.put("success",1);
						}
					}
				}
			}
		}catch(Exception e){
			log.error(className + ":saveCloudFileInfo",e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取个人云盘信息
	 * 
	 */
	@RequestMapping("getPersonCloudInfo")
	@ResponseBody
	private Object getPersonCloudInfo(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<Object>(); 
		result.setStatus("OK");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", 0);
		result.setMap(map);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);		
			Map<String,Object> infoMap = service.getPersonCloudInfo(paramMap);
			if(infoMap!=null && infoMap.size()>0){
				map.put("success", 1);
				map.put("infoMap", infoMap);
			}
		}catch(Exception e){
			log.error(className + ":getPersonCloudInfo",e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 重命名云端文件
	 * 
	 */
	@RequestMapping("renameCloudFile")
	@ResponseBody
	private Object renameCloudFile(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<Object>(); 
		Map<String,Object> map = new HashMap<String,Object>();
		result.setMap(map);
		map.put("success", 0);
		
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);		
			boolean flag = service.renameCloudFile(paramMap);
			if(flag){
				map.put("success", 1);
			}
			
		}catch(Exception e){
			log.error(className + ":renameCloudFile",e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除云端文件
	 * 
	 */
	@RequestMapping("deleteCloudFile")
	@ResponseBody
	private Object deleteCloudFile(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<Object>(); 
		result.setStatus("OK");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", 0);
		result.setMap(map);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);		
			boolean flag = service.deleteCloudFile(paramMap);
			if(flag){
				map.put("success", "1");
			}
			
		}catch(Exception e){
			log.error(className + ":deleteCloudFile",e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 批量删除云端文件
	 * 
	 */
	@RequestMapping("deleteBatches")
	@ResponseBody
	public Object deleteBatches(@RequestBody Map<String,Object> paramMap){
		ResultBean<Object> result = new ResultBean<Object>();
		result.setStatus("OK");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", 0);
		result.setMap(map);
		try{
			if(paramMap!=null && paramMap.size()>0){
				Long ownUser = CUtils.get().object2Long(paramMap.get("ownUser"));
				JSONArray array = JSON.parseArray(CUtils.get().object2String(paramMap.get("ids")));
				List<Long> idList = new ArrayList<Long>();
				if(array!=null && array.size()>0){
					for(int i=0;i<array.size();i++){
						idList.add(array.getLong(i));
					}
					Map<String,Object> delParam = new HashMap<String,Object>();
					delParam.put("ownUser", ownUser);
					delParam.put("ids", idList);
					
					//删除
					int count = service.deleteBatches(delParam);
					if(count>0){
						map.put("success", "1");
					}
					
				}
			}
		}catch(Exception e){
			log.error(className + ":deleteCloudFile",e);
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 判断云端文件的使用容量是否满足
	 * 
	 */
	@RequestMapping("isVolumnEnough")
	@ResponseBody
	private Object isVolumnEnough(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<Object>(); 
		result.setStatus("OK");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isEnough", 0);
		result.setMap(map);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);		
			Long usedVolume = service.getUsedVolumes(paramMap);
			Long fileSize = CUtils.get().object2Long(paramMap.get("fileSize"),-1L);
			
			if(StaticConst.CLOUD_VOLUME_DOSE>=usedVolume+fileSize){
				map.put("isEnough", 1);
			}
		}catch(Exception e){
			log.error(className + ":isVolumnEnough",e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取云端文件列表
	 * 
	 */
	@RequestMapping("getCloudFileList")
	@ResponseBody
	private Object getCloudFileList(@RequestBody CloudDiskFiles query,HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<Object>(); 
		result.setStatus("OK");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", 0);
		result.setMap(map);
		
		int pageNum=0,pageSize=10000;
		try{
			//初始化分页信息，
			if(query!=null){
				if(query.getPageNum()!=null){
					pageNum = CUtils.get().object2Integer(query.getPageNum());
				}
				if(query.getPageSize()!=null){
					pageSize = CUtils.get().object2Integer(query.getPageSize());
				}
				
				//分页查询
				Page<CloudDiskFiles> pageInfo = service.queryPageList(query, 
						new org.springframework.data.domain.PageRequest(pageNum, pageSize, Direction.DESC, "uploadTime"));
				
				if(pageInfo!=null){
					map.put("success", 1);
					map.put("totalCount", pageInfo.getTotal());
					map.put("dataList", pageInfo.getContent());
				}
			}
			
		}catch(Exception e){
			log.error(className + ":getCloudFileList",e);
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	
	
	

}
