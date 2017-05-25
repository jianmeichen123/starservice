package com.galaxy.im.common.webconfig.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.galaxy.im.common.webconfig.interceptor.token.TokenHandlerInterceptor;

@Configuration
public class InterceptorManager extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TokenHandlerInterceptor()).addPathPatterns("/**");
	}
	

}
