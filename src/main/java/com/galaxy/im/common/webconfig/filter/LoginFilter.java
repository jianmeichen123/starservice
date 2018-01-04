package com.galaxy.im.common.webconfig.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.QErrorCode;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.RedisCacheImpl;
import com.galaxy.im.common.configuration.BodyReaderHttpServletRequestWrapper;
import com.galaxy.im.common.configuration.DataSourceContextHolder;
import com.galaxy.im.common.configuration.DataSourceEnum;

public class LoginFilter implements Filter{

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		
		//其他访问
		String sessionId = request.getHeader(StaticConst.CONST_SESSION_ID_KEY);
		
		//session过期判断
		/*Object userObj = request.getSession().getAttribute(StaticConst.SESSION_USER_KEY);
		if (userObj == null) {
			cache.remove(sessionId);
		}*/
		
		boolean flag = false;
		ServletRequest requestWrapper = null;  
        if(request instanceof HttpServletRequest) {  
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);  
        }
		//登陆访问
		if(request.getRequestURI().indexOf(StaticConst.FILTER_WHITE_LOGIN)>0){
			String tt = CUtils.get().getRequestBody(requestWrapper.getReader());
			Map<String,Object> paramMap = CUtils.get().jsonString2map(tt);
			if(paramMap.containsKey("userName")&&CUtils.get().object2String(paramMap.get("userName")).equals(StaticConst.TEST_USERNAME)){
				DataSourceContextHolder.setDataSourceType(DataSourceEnum.slaver);
			}else{
				DataSourceContextHolder.setDataSourceType(DataSourceEnum.master);
			}
			filterChain.doFilter(requestWrapper, resp);
		}else{
			if(sessionId!=null && cache.hasKey(sessionId)){
				if(CUtils.get().stringIsNotEmpty(cache.get(sessionId))){
					flag = true;
				}
			}
			
			if(flag){
				 Map<String, Object> user = BeanUtils.toMap(cache.get(sessionId));
				if(user.containsKey("realName") && CUtils.get().object2String(user.get("realName")).equals(StaticConst.TEST_REALNAME)){
					DataSourceContextHolder.setDataSourceType(DataSourceEnum.slaver);
				}else{
					DataSourceContextHolder.setDataSourceType(DataSourceEnum.master);
				}
				filterChain.doFilter(requestWrapper, resp);
			}else{
				ResultBean<Object> resultBean = new ResultBean<Object>();
				resultBean.setStatus("ERROR");
				resultBean.setErrorCode(QErrorCode.SESSION_IS_PAST);
				CUtils.get().outputJson(resp,resultBean);
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
