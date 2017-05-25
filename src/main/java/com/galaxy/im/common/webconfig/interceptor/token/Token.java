package com.galaxy.im.common.webconfig.interceptor.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
	public String TOKEN = "TOKEN";
	
	/**
	 * @return 是否移除，默认“是”
	 */
	boolean remove() default true;
}
