package com.galaxy.im.common.webconfig.interceptor.operationLog;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.operationLog.OperationLogs;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.operationLog.service.IOperationLogsService;
import com.galaxy.im.common.CUtils;
import com.galaxyinternet.model.user.User;



/**
 * 操作日志
 * @author liuli
 */
public class MessageHandlerInterceptor extends HandlerInterceptorAdapter {
	final org.slf4j.Logger loger = LoggerFactory.getLogger(MessageHandlerInterceptor.class);
	
	@Autowired
	IOperationLogsService service;
	@Autowired
	private IFlowCommonService fcService;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void afterCompletion(final HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			@SuppressWarnings("unused")
			Logger logger = method.getAnnotation(Logger.class);
			final User user = new User();
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			
			final Map<String, Object> map = (Map<String, Object>) request.getAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP);
			if (null != map && !map.isEmpty()) {
				String uniqueKey = getUniqueKey(request, map);
				//OperationType type = OperationType.getObject(uniqueKey);
				final OperationLogType operLogType = OperationLogType.getObject(uniqueKey);
				if (operLogType != null) {
					//通过用户id获取一些信息
					List<Map<String, Object>> list = fcService.getDeptId(sessionBean.getGuserid(),request,response);
					if(list!=null){
						for(Map<String, Object> vMap:list){
							user.setId(CUtils.get().object2Long(vMap.get("uId")));
							user.setRealName(CUtils.get().object2String(vMap.get("userName")));
							user.setDepartmentId(CUtils.get().object2Long(vMap.get("deptId")));
							user.setDepartmentName(CUtils.get().object2String(vMap.get("deptName")));
						}
					}
					final RecordType recordType = RecordType.PROJECT;
					//线程池执行
					GalaxyThreadPool.getExecutorService().execute(new Runnable() {
						@Override
						public void run() {
							insertOperationLog(populateOperationLog(operLogType, user, map, recordType));
						}
					});
				}
			}
		}
		super.afterCompletion(request, response, handler, ex);
	}
	
	//保存操作日志
	private void insertOperationLog(OperationLogs operationLog) {
		try {
			@SuppressWarnings("unused")
			long id =service.saveOperationLog(operationLog);
		} catch (Exception e3) {
			loger.error("操作日志异常，请求数据：" + operationLog, e3);
		}
	}
	
	//获取操作日志内容
	private OperationLogs populateOperationLog(OperationLogType type, User user, Map<String, Object> map,
			com.galaxy.im.common.webconfig.interceptor.operationLog.RecordType recordType) {
		OperationLogs entity = new OperationLogs();
		
		entity.setOperationContent(type.getContent());
		if(type.getUniqueKey().contains("votedown")){
			entity.setOperationContent(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		}
		entity.setOperationType(type.getType());
		entity.setUid(user.getId());
		entity.setUname(user.getRealName());
		entity.setDepartName(user.getDepartmentName());
		entity.setUserDepartid(user.getDepartmentId());
		entity.setCreatedTime(new Date().getTime());
		entity.setProjectName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		entity.setProjectId(Long.valueOf(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_ID))));
		
		if(map.containsKey(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS)&& map.get(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS)!=null){
			entity.setSopstage(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS)));
		}else{
			Object flag=map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_NUM);
			Object obj=map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_STAGE);
			if(null!=flag&&null!=obj){
				boolean f=(boolean) flag;
				String stage=(String)obj;
				if(f==true&&!"".equals(stage)){
					entity.setSopstage(stage);
				 }
			}else{
				entity.setSopstage(type.getSopstage());
			}
		}
		entity.setReason(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_REASON)));
		entity.setRecordType(recordType.getType());
		
		return entity;
	}
	
	//获取url
	private String getUniqueKey(HttpServletRequest request, Map<String, Object> map) {
			String uniqueKey = request.getRequestURL().toString();
			if (null != map && !map.isEmpty()) {
				if (map.containsKey(PlatformConst.REQUEST_SCOPE_URL_NUMBER)) {
					uniqueKey = uniqueKey + "/" + map.get(PlatformConst.REQUEST_SCOPE_URL_NUMBER);
				}
			}
		return uniqueKey;
	}

	/*@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("在请求处理之前进行调用（Controller方法调用之前）");

        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        System.out.println("请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }*/
}
