package com.galaxy.im.common.messageThread;

import org.apache.log4j.Logger;

import com.galaxy.im.common.exception.BusinessException;

public abstract class BaseGalaxyTask implements GalaxyTask {

	private static final Logger logger = Logger.getLogger(BaseGalaxyTask.class);
	protected boolean disabled = false;

	@Override
	public void execute() throws BusinessException {
		if(isDisabled())
		{
			return;
		}
		String jobName = this.getClass().getName();
		try {
			logger.debug("======================"+jobName+" Start========================");
			executeInteral();
			logger.debug("======================"+jobName+" Success========================");
		} catch (Exception e) {
			logger.debug("======================"+jobName+" Error========================");
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
