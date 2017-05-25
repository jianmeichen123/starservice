package com.galaxy.im.common.webconfig.interceptor.token;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.QErrorCode;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.IRedisCache;

public class TokenHandlerInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private IRedisCache<String, Object> cache;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			Method method = handlerMethod.getMethod();
			Token token = method.getAnnotation(Token.class);
			Object removeReq = request.getAttribute(StaticConst.TOKEN_REMOVE_KEY);
			if(token!=null){
				boolean remove = token.remove();
				String tokenValue = request.getHeader(Token.TOKEN);
				if(null==removeReq && remove){
					removeSessionToken(tokenValue);
				}else{
					setSessionToken(tokenValue);
				}
			}
		}
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			Method method = handlerMethod.getMethod();
			Token token = method.getAnnotation(Token.class);
			if(token!=null){
				String requestTokenvalue = request.getHeader(Token.TOKEN);
				if(this.isRepeatSubmitted(requestTokenvalue)){
					ResultBean<Object> resultBean = new ResultBean<Object>();
					resultBean.setStatus("ERROR");
					resultBean.setErrorCode(QErrorCode.TOKEN_IS_ERROR);
					resultBean.setMessage("Token is repeat or null,please check param!");
					CUtils.get().outputJson(response,resultBean);
					return false;
				}
				this.removeSessionToken(requestTokenvalue);
			}
		}
		return true;
	}
	
	//-------------------------------------------- 私有方法 ---------------------------------------------
	private boolean isRepeatSubmitted(String requestToken) {
		if (requestToken == null) {
			return true;
		}
		String sessionToken = getSessionToken(requestToken);
		if (sessionToken == null) {
			return true;
		}

		if (!sessionToken.equals(requestToken)) {
			return true;
		}
		return false;
	}
	
	private String getSessionToken(String tokenValue) {
		Object token = cache.get(tokenValue);
		if (null == token) {
			return null;
		} else {
			return String.valueOf(token);
		}
	}
	
	private void setSessionToken(String tokenValue) {
		cache.put(tokenValue,tokenValue,StaticConst.TOKEN_IN_REDIS_TIMEOUT_SECONDS,TimeUnit.SECONDS);
	}

	private void removeSessionToken(String tokenValue) {
		cache.remove(tokenValue);
	}
	
	
	
	
	
	
}
