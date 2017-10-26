package com.galaxy.im.common.messageThread;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeTask {
	
	@Autowired
	SchedulePushMessTask task;
	@Autowired
	SchedulePushInitTask initTask;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    //每30秒执行一次
    @Scheduled(fixedRate = 30000)
    public void timerRate() {
    	task.executeInteral();
    }

    //每天00点01分00秒时执行
    @Scheduled(cron = "00 01 00 * * ?")
    public void timerCron() {
    	initTask.executeInteral();
    }
}