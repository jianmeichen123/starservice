package com.galaxy.im.common.configuration;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{
	
	 protected Object determineCurrentLookupKey() {
	        return DataSourceContextHolder.getDataSourceType();
	 }
}
