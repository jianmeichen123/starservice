package com.galaxy.im.common.messageThread;

import com.galaxy.im.common.exception.BusinessException;

public interface GalaxyTask 
{
	public void execute() throws BusinessException;
}
