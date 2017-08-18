package com.galaxy.im.common.webconfig.interceptor.operationLog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 用于记录用户的操作 <br>
 *              {@link com.galaxyinternet.common.annotation.LogType}
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {

	/**
	 * @Description:请求的唯一标识;默认空字符串，根据请求地址判断。如果非空即根据该值判断
	 *
	 */
	String unique() default "";

	/**
	 * @Description:数据库存储的每条记录的类型：用于区分是创意还是项目.默认是项目
	 *
	 */
	RecordType recordType() default RecordType.PROJECT;

	/**
	 * 指定记录的范围 <br/>
	 * 默认生成“消息提醒”中的日志,既值为LogType.MESSAGE
	 * 
	 * @return
	 */
	LogType[] operationScope() default LogType.MESSAGE;
}
