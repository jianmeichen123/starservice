package com.galaxy.im.business.rili.util;

import java.util.Comparator;

import com.galaxy.im.bean.schedule.ScheduleInfo;

public class ComparatorDate implements Comparator<ScheduleInfo> {  
	  
    public int compare(ScheduleInfo obj1, ScheduleInfo obj2) { 
    	

    	int flag=(obj1.getStartTime().compareTo(obj2.getStartTime())); 
    	
        	return flag;
     
    }  
}