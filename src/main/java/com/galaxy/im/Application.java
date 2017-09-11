package com.galaxy.im;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.galaxy.im.common.StaticConst;

@EnableAutoConfiguration
@ServletComponentScan
@Configuration
@ComponentScan
public class Application extends SpringBootServletInitializer{
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
    	StaticConst.ctx = ctx;
        
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();  
        for (String profile : activeProfiles) {  
            logger.warn("Spring Boot 使用profile为:{}" , profile);  
        }  
    } 
}
