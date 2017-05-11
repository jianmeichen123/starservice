package com.galaxy.im.common.webconfig.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.RedisCacheImpl;

public class LoginFilter implements Filter{

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		
		boolean flag = false;
		String sessionId = request.getHeader(StaticConst.CONST_SESSION_ID_KEY);
		if(cache.hasKey(sessionId)){
			if(CUtils.get().stringIsNotEmpty(cache.get(sessionId))){
				flag = true;
			}
		}
		
		if(flag){
			filterChain.doFilter(req, resp);
		}else{
			ResultBean<Object> resultBean = new ResultBean<Object>();
			resultBean.setStatus("ERROR");
			resultBean.setErrorCode(3);
			CUtils.get().outputJson(resp,resultBean);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
