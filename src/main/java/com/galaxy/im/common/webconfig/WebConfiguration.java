package com.galaxy.im.common.webconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.galaxy.im.common.webconfig.filter.LoginFilter;
import com.galaxy.im.common.webconfig.servlet.OneServlet;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter{
	/**
	 * 设置servlet，如果设置多个请重复如下代码即可,filter及listener同样
	 * @return
	 */
	@Bean
	public ServletRegistrationBean getOneServlet(){
		OneServlet demoServlet=new OneServlet();
		ServletRegistrationBean registrationBean=new ServletRegistrationBean();
		registrationBean.setServlet(demoServlet);
		List<String> urlMappings=new ArrayList<String>();
		urlMappings.add("/one");				////访问，可以添加多个
		registrationBean.setUrlMappings(urlMappings);
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}
	
	/**
	 * 配置过滤器(Filter)
	 * @return
	 */
	@Bean
	public FilterRegistrationBean getDemoFilter(){
		LoginFilter demoFilter=new LoginFilter();
		FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(demoFilter);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setOrder(1);
		return registrationBean;
	}
	
//	/**
//	 * 配置listener
//	 * @return
//	 */
//	@Bean
//	public ServletListenerRegistrationBean<EventListener> getDemoListener(){
//		ServletListenerRegistrationBean<EventListener> registrationBean
//		                           =new ServletListenerRegistrationBean<>();
//		registrationBean.setListener(new DemoListener());
////		registrationBean.setOrder(1);
//		return registrationBean;
//	}
	
	
	@Bean
	public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
	    return new EmbeddedServletContainerCustomizer() {
	        @Override 
	        public void customize(ConfigurableEmbeddedServletContainer container) {
	            container.setSessionTimeout(1, TimeUnit.MINUTES);
	        	  //container.setSessionTimeout(3);//单位为S
	        }
	    };
	}


}
