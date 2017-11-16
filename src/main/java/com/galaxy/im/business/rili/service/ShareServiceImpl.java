package com.galaxy.im.business.rili.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.schedule.DeptNoUsers;
import com.galaxy.im.bean.schedule.ScheduleShared;
import com.galaxy.im.business.flow.common.service.FlowCommonServiceImpl;
import com.galaxy.im.business.rili.dao.IShareDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;
import com.galaxy.im.common.html.QHtmlClient;

@Service
public class ShareServiceImpl extends BaseServiceImpl<Test> implements IShareService{
	
	private Logger log = LoggerFactory.getLogger(ShareServiceImpl.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	IShareDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	/**
	 * 通过id删除共享人
	 */
	@Override
	public int delSharedUser(Map<String,Object> map) {
		try{
			return dao.delSharedUser(map);
		}catch(Exception e){
			log.error(ShareServiceImpl.class.getName() + "deleteById",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 添加共享人
	 */
	@Override
	public int saveSharedUsers(Long guserid, ScheduleShared query) {
		try{
			//删除创建人的所有共享人
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("createUid", guserid);
			dao.delSharedUser(map);
			
			List<ScheduleShared> list = new ArrayList<ScheduleShared>();
			
			if(query.getDeptNoUsers()!=null && !query.getDeptNoUsers().isEmpty()){
				for(DeptNoUsers temp : query.getDeptNoUsers()){
					List<Long> userIds = temp.getUserIds();
					if(userIds == null || userIds.isEmpty()) continue;
					for(Long uid : userIds){
						//日程共享
						query = new ScheduleShared();
						query.setToUid(uid);
						query.setDepartmentId(temp.getDeptId());
						query.setCreateUid(guserid);
						query.setCreatedTime(new Date().getTime());
						list.add(query);
					}
				}
			}
			//批量保存共享人
			if (list.size()>0) {
				return dao.saveSharedUsers(list);
			}
		}catch(Exception e){
			log.error(ShareServiceImpl.class.getName() + "saveSharedUsers",e);
			throw new ServiceException(e);
		}
		return 0;
	}

	/**
	 * 共享给自己的列表
	 */
	@Override
	public List<Map<String, Object>> querySharedUsers(HttpServletRequest request, HttpServletResponse response,Long guserid) {
		try {
			//总list
			List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
			
			//我的日历
			Map<String, Object> my =new HashMap<String, Object>();
			my.put("createUid", guserid);
			my.put("toUname", "我的日历");
			allList.add(my);
			
			//共享给本人的
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("toUid", guserid);
			List<Map<String, Object>> list = dao.querySharedUsers(map);
			if(list!=null && !list.isEmpty()){
				List<Long> uids = new ArrayList<Long>();
				for(Map<String,Object> m : list){
					uids.add(CUtils.get().object2Long(m.get("createUid")));
				}
				//权限系统通过用户id，获取用户名称
				Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
				String url = env.getProperty("power.server") + StaticConst.getUserByIds;
				Map<String,Object> qMap = new HashMap<String,Object>();
				qMap.put("userIds", CUtils.get().object2JSONString(uids));
				JSONArray array=null;
				//post请求
				String result = QHtmlClient.get().post(url, headerMap, qMap);
				Map<Long, String> name = new HashMap<Long, String>();
				if("error".equals(result)){
					log.error(FlowCommonServiceImpl.class.getName() + "获取信息时出错","此时服务器返回状态码非200");
				}else{
					JSONObject resultJson = JSONObject.parseObject(result);
					if(resultJson!=null && resultJson.containsKey("value")){
						array = resultJson.getJSONArray("value");
						if(resultJson.containsKey("success") && "true".equals(resultJson.getString("success"))){
							//操作
							List<Map<String, Object>> arrayList =CUtils.get().jsonString2list(array);
							if(arrayList!=null){
								for(Map<String, Object> vMap:arrayList){
									int isDel = CUtils.get().object2Integer(vMap.get("isDel"));
									int isShow = CUtils.get().object2Integer(vMap.get("isShow"));
									int isOuttage = CUtils.get().object2Integer(vMap.get("isOuttage"));
									//非删除，显示，禁用用户显示
									if(isDel==0 && isShow==0 && isOuttage==0){
										String ids = CUtils.get().object2String(vMap.get("userId"));
										String userName = CUtils.get().object2String(vMap.get("userName"));
										name.put(CUtils.get().object2Long(ids), userName);
									}else{
										//删除禁用，删除等用户的共享人信息
										Map<String,Object> pp = new HashMap<String,Object>();
										pp.put("toUid", guserid);
										pp.put("createUid", CUtils.get().object2Long(vMap.get("userId")));
										dao.delSharedUser(pp);
									}
								}
							}
						}
					}
				}
				//重新查询
				List<Map<String, Object>> qListt = dao.querySharedUsers(map);
				for(Map<String, Object> tempU : qListt){
					tempU.put("toUname", name.get(tempU.get("createUid")));
				}
				//共享给自己的全部数据
				allList.addAll(qListt);
			}
			return allList;
		} catch (Exception e) {
			log.error(ShareServiceImpl.class.getName() + "querySharedUsers",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 自己共享人的列表
	 */
	@Override
	public List<Map<String, Object>> queryMySharedUsers(HttpServletRequest request, HttpServletResponse response,
			Long guserid, String toUname) {
		try {
			Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);

			//自己共享的
			Map<String,Object> my = new HashMap<String,Object>();
			my.put("createUid", guserid);
			//列表
			if(toUname!=null){
				List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
				//权限系统通过用户id，获取用户名称
				String url = env.getProperty("power.server") + StaticConst.findUserByName;
				Map<String,Object> vmap = new HashMap<String,Object>();
				vmap.put("userName", toUname);
				JSONArray array=null;
				//post请求
				String result = QHtmlClient.get().post(url, headerMap, vmap);
				if("error".equals(result)){
					log.error(FlowCommonServiceImpl.class.getName() + "获取信息时出错","此时服务器返回状态码非200");
				}else{
					JSONObject resultJson = JSONObject.parseObject(result);
					if(resultJson!=null && resultJson.containsKey("value")){
						array = resultJson.getJSONArray("value");
						if(resultJson.containsKey("success") && "true".equals(resultJson.getString("success"))){
							//操作
							List<Map<String, Object>> arrayList =CUtils.get().jsonString2list(array);
							if(arrayList!=null){
								for(Map<String, Object> vMap:arrayList){
									my.put("toUid", CUtils.get().object2String(vMap.get("userId")));
									//查询
									List<Map<String, Object>> shareList = dao.querySharedUsers(my);
									for(Map<String, Object> tempU : shareList){
										tempU.put("toUname", CUtils.get().object2String(vMap.get("userName")));
									}
									list.addAll(shareList);
								}
							}
						}
					}
				}
				//去除列表中的自己
				for(int i = 0; i<list.size(); i++){
					if (list.get(i).get("toUid").equals(guserid)) {
						list.remove(i);
					}
				}
				return list;
			}else{
				List<Map<String, Object>> list = dao.querySharedUsers(my);
				if(list!=null && !list.isEmpty()){
					List<Long> uids = new ArrayList<Long>();
					for(Map<String,Object> m : list){
						uids.add(CUtils.get().object2Long(m.get("toUid")));
					}
					//权限系统通过用户id，获取用户名称
					String url = env.getProperty("power.server") + StaticConst.getUserByIds;
					Map<String,Object> qMap = new HashMap<String,Object>();
					qMap.put("userIds", CUtils.get().object2JSONString(uids));
					JSONArray array=null;
					//post请求
					String result = QHtmlClient.get().post(url, headerMap, qMap);
					Map<Long, String> name = new HashMap<Long, String>();
					if("error".equals(result)){
						log.error(FlowCommonServiceImpl.class.getName() + "获取信息时出错","此时服务器返回状态码非200");
					}else{
						JSONObject resultJson = JSONObject.parseObject(result);
						if(resultJson!=null && resultJson.containsKey("value")){
							array = resultJson.getJSONArray("value");
							if(resultJson.containsKey("success") && "true".equals(resultJson.getString("success"))){
								//操作
								List<Map<String, Object>> arrayList =CUtils.get().jsonString2list(array);
								if(arrayList!=null){
									for(Map<String, Object> vMap:arrayList){
										int isDel = CUtils.get().object2Integer(vMap.get("isDel"));
										int isShow = CUtils.get().object2Integer(vMap.get("isShow"));
										int isOuttage = CUtils.get().object2Integer(vMap.get("isOuttage"));
										//非删除，显示，禁用用户显示
										if(isDel==0 && isShow==0 && isOuttage==0){
											String ids = CUtils.get().object2String(vMap.get("userId"));
											String userName = CUtils.get().object2String(vMap.get("userName"));
											name.put(CUtils.get().object2Long(ids), userName);
										}else{
											//删除禁用，删除等用户的共享人信息
											Map<String,Object> pp = new HashMap<String,Object>();
											pp.put("toUid", CUtils.get().object2Long(vMap.get("userId")));
											pp.put("createUid", guserid);
											dao.delSharedUser(pp);
										}
									}
								}
							}
						}
					}
					//重新查询
					list = dao.querySharedUsers(my);
					for(Map<String, Object> tempU : list){
						tempU.put("toUname", name.get(tempU.get("toUid")));
					}
				}
				//去除列表中的自己
				for(int i = 0; i<list.size(); i++){
					if (list.get(i).get("toUid").equals(guserid)) {
						list.remove(i);
					}
				}
				return list ;
			}
		} catch (NumberFormatException e) {
			log.error(ShareServiceImpl.class.getName() + "queryMySharedUsers",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询所有事业部下的人
	 */
	@Override
	public List<Map<String, Object>> queryAppPerson(HttpServletRequest request, HttpServletResponse response,
			Long guserid, Map<String, Object> map) {
		try {
			Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
			List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
			//权限系统通过用户id，获取用户名称
			String url = env.getProperty("power.server") + StaticConst.getUsersByKey;
			Map<String,Object> vmap = new HashMap<String,Object>();
			vmap.put("userKey", CUtils.get().object2String(map.get("uname")));
			
			JSONArray array=null;
			//post请求
			String result = QHtmlClient.get().post(url, headerMap, vmap);
			if("error".equals(result)){
				log.error(FlowCommonServiceImpl.class.getName() + "获取信息时出错","此时服务器返回状态码非200");
			}else{
				JSONObject resultJson = JSONObject.parseObject(result);
				if(resultJson!=null && resultJson.containsKey("value")){
					array = resultJson.getJSONArray("value");
					if(resultJson.containsKey("success") && "true".equals(resultJson.getString("success"))){
						//操作
						List<Map<String, Object>> arrayList =CUtils.get().jsonString2list(array);
						if(arrayList!=null){
							for(Map<String, Object> vMap:arrayList){
								Map<String, Object> m =new HashMap<String, Object>();
								if(!CUtils.get().object2String(vMap.get("userId")).equals(guserid)){
									m.put("userId", CUtils.get().object2String(vMap.get("userId")));
									m.put("userName", CUtils.get().object2String(vMap.get("userName")));
									m.put("departName", CUtils.get().object2String(vMap.get("depName")));
									m.put("departId", CUtils.get().object2String(vMap.get("depId")));
									list.add(m);
								}
							}
						}
					}
				}
			}
			return list;
		} catch (Exception e) {
			log.error(ShareServiceImpl.class.getName() + "queryAppPerson",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 事业部下所有人
	 */
	@Override
	public List<Map<String, Object>> queryDeptUinfo(HttpServletRequest request, HttpServletResponse response,
			Long guserid, Map<String, Object> map) {
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		try {
			//是否选中
			Boolean isChecked=false;
			//返回结果
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			//部门下的 已经选择的人员id
			List<Long> deptCheckedUid = new ArrayList<Long>(); 
			Map<String,Object> my = new HashMap<String,Object>();
			my.put("createUid", guserid);
			List<Map<String, Object>> list = dao.querySharedUsers(my);
			for(Map<String, Object> tempU : list){
				deptCheckedUid.add(CUtils.get().object2Long(tempU.get("toUid"))) ;
			}
			
			//获取所有事业部
			String url = env.getProperty("power.server") + StaticConst.getLeafDepartList;
			Map<String,Object> vmap = new HashMap<String,Object>();
			JSONArray array=null;
			
			String result = QHtmlClient.get().post(url, headerMap, vmap);
			if("error".equals(result)){
				log.error(FlowCommonServiceImpl.class.getName() + "获取信息时出错","此时服务器返回状态码非200");
			}else{
				JSONObject resultJson = JSONObject.parseObject(result);
				if(resultJson!=null && resultJson.containsKey("value")){
					array = resultJson.getJSONArray("value");
					if(resultJson.containsKey("success") && "true".equals(resultJson.getString("success"))){
						//操作
						List<Map<String, Object>> arrayList =CUtils.get().jsonString2list(array);
						for(Map<String, Object> vMap:arrayList){
							//用户列表
							List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
							vmap.put("depId", CUtils.get().object2Long(vMap.get("depId")));
							
							//事业部下的所有用户
							String urlU = env.getProperty("power.server") + StaticConst.getUsersByDepId;
							JSONArray rr=null;
							
							String res = QHtmlClient.get().post(urlU, headerMap, vmap);
							if("error".equals(res)){
								log.error(FlowCommonServiceImpl.class.getName() + "获取信息时出错","此时服务器返回状态码非200");
							}else{
								JSONObject json = JSONObject.parseObject(res);
								if(json!=null && json.containsKey("value")){
									rr = json.getJSONArray("value");
									if(json.containsKey("success") && "true".equals(json.getString("success"))){
										//操作
										List<Map<String, Object>> rrList =CUtils.get().jsonString2list(rr);
										for(Map<String, Object> pMap:rrList){
											Map<String, Object> m =new HashMap<String, Object>();
											//去除列表中的本人
											if (!CUtils.get().object2Long(pMap.get("userId")).equals(guserid)) {
												m.put("departId", CUtils.get().object2Long(vMap.get("depId")));
												m.put("id", CUtils.get().object2Long(pMap.get("userId")));
												m.put("name", CUtils.get().object2String(pMap.get("userName")));
												if(deptCheckedUid.contains(CUtils.get().object2Long(pMap.get("userId")))){
													isChecked=true;
													m.put("isChecked", isChecked);
								 				}
											}
											userList.add(m);
										}
									}
								}
							}
							Map<String, Object> deptMap =new HashMap<String, Object>();
							deptMap.put("deptUsers", userList);
							deptMap.put("remarkType", CUtils.get().object2Long(map.get("remarkType")));
							deptMap.put("departmentId", CUtils.get().object2Long(vMap.get("depId")));
							deptMap.put("deptName", CUtils.get().object2String(vMap.get("depName")));
							resultList.add(deptMap);
						}
					}
				}
			}
			return resultList;
		} catch (Exception e) {
			log.error(ShareServiceImpl.class.getName() + "queryDeptUinfo",e);
			throw new ServiceException(e);
		}
	}

}
