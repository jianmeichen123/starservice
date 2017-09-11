package com.galaxy.im.common.db;

public interface IdCreator {
	Long nextId(String sKey) throws Exception;
}
