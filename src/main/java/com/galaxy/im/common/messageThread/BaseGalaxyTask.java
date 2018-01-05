package com.galaxy.im.common.messageThread;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxy.im.common.exception.BusinessException;
import com.galaxy.im.common.messageThread.zk.LeaderSelector;

@Component
public abstract class BaseGalaxyTask implements GalaxyTask {
	private static final Logger logger = Logger.getLogger(BaseGalaxyTask.class);
	protected boolean disabled = false;
	
	@Autowired
	private LeaderSelector leaderSelector;

	@Override
	public void execute() throws BusinessException {
		if(isDisabled()){
			return;
		}
		
		if(leaderSelector != null && !leaderSelector.isLeader()){
			logger.info(String.format("当前节点[ID=%s]不是Leader节点[LeaderId=%s]", leaderSelector.getId(),leaderSelector.getLeaderId()));
			return;
		}
		
		
		String jobName = this.getClass().getName();
		try {
			logger.info("======================"+jobName+" Start========================");
			executeInteral();
			logger.info("======================"+jobName+" Success========================");
		} catch (Exception e) {
			logger.info("======================"+jobName+" Error========================");
			throw e;
		}

	}
	
	protected abstract void executeInteral() throws BusinessException;

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
