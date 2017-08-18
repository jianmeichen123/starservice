package com.galaxy.im.common.webconfig.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.galaxy.im.common.webconfig.interceptor.operationLog.MessageHandlerInterceptor;
import com.galaxy.im.common.webconfig.interceptor.token.TokenHandlerInterceptor;

@Configuration
public class InterceptorManager extends WebMvcConfigurerAdapter {

	@Bean
	public MessageHandlerInterceptor messageInterceptor() {
		return new MessageHandlerInterceptor();
	}
	 
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TokenHandlerInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(messageInterceptor()).addPathPatterns("/**");
	}
	

}
