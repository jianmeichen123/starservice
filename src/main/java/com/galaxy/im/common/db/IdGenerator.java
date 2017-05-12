package com.galaxy.im.common.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局Id生成器
 * 
 * @author kaihu
 */
public class IdGenerator {
	private static Logger logger = LoggerFactory.getLogger(IdGenerator.class);

	public static <T> Long generateId(Class<T> clazz) {
		IdCreator idCreator = IdCreatorFactory.getTimeIdCreator(1);
		try {
			return idCreator.nextId(clazz.getName());
		} catch (Exception e) {
			logger.error("generate global id exception:", e);
			return -1L;
		}
	}
}
